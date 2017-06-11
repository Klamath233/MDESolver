package trigsolver

import chisel3._

class FiveToTwoModule(val m:Int) extends Module {
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
}