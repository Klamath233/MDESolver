package trigsolver

import chisel3._
import chisel3.util._

class BasicModule(val m: Int) extends Module {
  val io = IO(new Bundle {
    val aLeft = Input(SInt((m + 1).W))  // S.XXXX if m = 4
    val b = Input(SInt((m + 1).W))      // S.XXXX
    val aRight = Input(SInt((m + 1).W)) // S.XXXX
    val dLeft = Input(SInt(2.W))        // SD
    val dRight = Input(SInt(2.W))       // SD
    val dOut =  Output(SInt(2.W))       // SD
  })

  val aLeftReg = RegInit(io.aLeft)
  val aRightreg = RegInit(io.aRight)

  val mgLeft = Module(new MultipleGenerator(m))
  val mgRight = Module(new MultipleGenerator(m))

  val sel = Module(new DigitSelector)
  val dReg = RegNext(sel.io.d)
  val adder = Module(new FiveToTwoAdder(m))

  val sumReg = RegNext(adder.io.o0, io.b.pad(m + 3))
  val carryReg = RegNext(adder.io.o1, 0.asSInt((m + 3).W))

  mgLeft.io.d := io.dLeft
  mgLeft.io.a := io.aLeft
  mgRight.io.d := io.dRight
  mgRight.io.a := io.aRight

  adder.io.i0 := mgLeft.io.mult
  adder.io.i1 := mgRight.io.mult
  adder.io.i2 := sumReg
  adder.io.i3 := Cat(carryReg(m + 1, 1), mgLeft.io.comp)
  adder.io.i4 := Cat(dReg.pad(3), mgRight.io.comp.pad(m))

  sel.io.wcHat := carryReg(m + 2, m - 1)
  sel.io.wsHat := sumReg(m + 2, m - 1)

  io.dOut := sel.io.d
}