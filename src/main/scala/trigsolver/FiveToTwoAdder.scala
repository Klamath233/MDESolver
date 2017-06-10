package trigsolver

import chisel3._

class FiveToTwoAdder(val m:UInt) extends Module {
  val io = IO(new Bundle(){
    val i0 = Input(SInt((m + 3).W))  // SSX.XXXX old sum
    val i1 = Input(SInt((m + 3).W))  // SXX.XXXC old carry (right most C is Cl or Cr)
    val i2 = Input(SInt((m + 3).W))  // SSX.XXXX left multiple
    val i3 = Input(SInt((m + 3).W))  // SSX.XXXX right multiple
    val i4 = Input(SInt((m + 3).W))  // SSD.000C d of the module itself concat with Cr or Cl
    val o0 = Output(SInt((m + 3).W)) // SSX.XXXX new sum
    val o1 = Output(SInt((m + 3).W)) // SXX.XXX0 new carry
  })

  // TODO: implement it!
}