package trigsolver

import chisel3._
import chisel3.util._

class BasicModule(val m: Int, val i: Int) extends Module {
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

  val counter = Counter(m + 2)
  val finished = RegNext(counter.value === (m + 1).U)
  val initialized = (counter.value =/= 0.U)
  val cv = counter.value

  val sel = Module(new DigitSelector)
  val dReg = RegNext(Mux(initialized, sel.io.d, 0.asSInt(2.W)))
  val adder = Module(new FiveToTwoAdder(m))

  val vc = adder.io.o1 << 1
  val vs = adder.io.o0 << 1

  val sumReg = RegNext(Mux(initialized, vs, io.b.pad(m + 3)))
  val carryReg = RegNext(Mux(initialized, vc, 0.asSInt((m + 3).W)))
 

  val i0 = mgLeft.io.mult.pad(m + 3)
  val i1 = mgRight.io.mult.pad(m + 3)
  val i2 = sumReg
  val i3 = Cat(carryReg(m + 2, 1), mgLeft.io.comp).asSInt()
  val i4 = Cat(~dReg.pad(3) + 1.S, mgRight.io.comp.pad(m)).asSInt()
  val bb = io.b.pad(m + 3)

  val dLeft = io.dLeft
  val dRight = io.dRight
  val aLeft = io.aLeft
  val aRight = io.aRight

  val w = sumReg + carryReg

  mgLeft.io.d := io.dLeft
  mgLeft.io.a := io.aLeft
  mgRight.io.d := io.dRight
  mgRight.io.a := io.aRight

  adder.io.i0 := mgLeft.io.mult.pad(m + 3)
  adder.io.i1 := mgRight.io.mult.pad(m + 3)
  adder.io.i2 := sumReg
  adder.io.i3 := Cat(carryReg(m + 2, 1), mgLeft.io.comp).asSInt()
  adder.io.i4 := Cat(~dReg.pad(3) + 1.S, mgRight.io.comp.pad(m)).asSInt()
  adder.io.cIn1 := 0.U
  adder.io.cIn0 := 0.U

  sel.io.wcHat := vc(m + 2, m - 1).asSInt()
  sel.io.wsHat := vs(m + 2, m - 1).asSInt()

  io.dOut := dReg

  when (~finished) {
    counter.inc()
  }

  printf(p"$cv, $i: 0b${Binary(i0)} 0b${Binary(i1)} 0b${Binary(i2)} 0b${Binary(i3)} 0b${Binary(i4)} $dReg 0b${Binary(w)}\n")
}