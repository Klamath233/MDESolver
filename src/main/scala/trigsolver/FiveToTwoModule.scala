package trigsolver

import chisel3._

class FiveToTwoModule extends Module {
  val io = IO(new Bundle {
    val i0 = Input(UInt(1.W))  
    val i1 = Input(UInt(1.W))
    val i2 = Input(UInt(1.W))
    val i3 = Input(UInt(1.W))
    val i4 = Input(UInt(1.W))
    val cIn0 = Input(UInt(1.W))
    val cIn1 = Input(UInt(1.W))
    val cOut0 = Input(UInt(1.W))
    val cOut1 = Input(UInt(1.W))
    val o0 = Output(UInt(1.W))
    val o1 = Output(UInt(1.W))
  })

  val topFA = Module(new FullAdder)
  val midFA = Module(new FullAdder)
  val botFA = Module(new FullAdder)

  midFA.io.a := io.i4
  midFA.io.b := io.i3
  topFA.io.a := io.i2
  topFA.io.b := io.i1
  topFA.io.cin := io.i0

  midFA.io.cin := topFA.io.sum
  io.cOut1 := topFA.io.cout
  io.cOut0 := midFA.io.cout

  botFA.io.a := midFA.io.sum
  botFA.io.b := io.cIn1
  botFA.io.cin := io.cIn0

  io.o1 := botFA.io.cout
  io.o0 := botFA.io.sum
}