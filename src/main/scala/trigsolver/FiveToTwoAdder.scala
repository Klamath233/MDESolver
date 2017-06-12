package trigsolver

import chisel3._
import chisel3.util._

class FiveToTwoAdder(val m:Int) extends Module {
  val io = IO(new Bundle {
    val i0 = Input(SInt((m + 3).W))  // SSX.XXXX old sum
    val i1 = Input(SInt((m + 3).W))  // SXX.XXXC old carry (right most C is Cl or Cr)
    val i2 = Input(SInt((m + 3).W))  // SSX.XXXX left multiple
    val i3 = Input(SInt((m + 3).W))  // SSX.XXXX right multiple
    val i4 = Input(SInt((m + 3).W))  // SSD.000C d of the module itself concat with Cr or Cl
    val cIn0 = Input(UInt(1.W))
    val cIn1 = Input(UInt(1.W))
    val cOut0 = Output(UInt(1.W))
    val cOut1 = Output(UInt(1.W))
    val o0 = Output(SInt((m + 3).W)) // SSX.XXXX new sum
    val o1 = Output(SInt((m + 4).W)) // SSXX.XXX0 new carry
  })

  val adders = Array.fill(m + 3)(Module(new FiveToTwoModule))
  var o1Wires = Wire(Vec(m + 4, 0.U))
  var o0Wires = Wire(Vec(m + 3, 0.U))

  for (i <- m + 2 to 1 by -1) {
    val curr = adders(i)
    val right = adders(i - 1)

    curr.io.i0 := io.i0(i)
    curr.io.i1 := io.i1(i)
    curr.io.i2 := io.i2(i)
    curr.io.i3 := io.i3(i)
    curr.io.i4 := io.i4(i)
    curr.io.cIn1 := right.io.cOut1
    curr.io.cIn0 := right.io.cOut0

    o1Wires(i + 1) := curr.io.o1
    o0Wires(i) := curr.io.o0

    if (i == m + 2) {
      io.cOut1 := curr.io.cOut1
      io.cOut0 := curr.io.cOut0
    }
  }

  val lsd = adders(0)
  lsd.io.i0 := io.i0(0)
  lsd.io.i1 := io.i1(0)
  lsd.io.i2 := io.i2(0)
  lsd.io.i3 := io.i3(0)
  lsd.io.i4 := io.i4(0)
  lsd.io.cIn1 := io.cIn1
  lsd.io.cIn0 := io.cIn0

  o1Wires(1) := lsd.io.o1
  o1Wires(0) := 0.U

  o0Wires(0) := lsd.io.o0

  io.o1 := Reverse(Cat(o1Wires)).asSInt()
  io.o0 := Reverse(Cat(o0Wires)).asSInt()
}