package trigsolver

import chisel3._

class BasicModule(val m: Int) extends Module {
  val io = IO(new Bundle {
    val aLeft = Input(SInt((m + 1).W))  // S.XXXX if m = 4
    val b = Input(SInt((m + 1).W))      // S.XXXX
    val aRight = Input(SInt((m + 1).W)) // S.XXXX
    val dLeft = Input(SInt(2.W))        // SD
    val dRight = Input(SInt(2.W))       // SD
    val dOut =  Output(SInt(2.W))       // SD
  })

  // TODO: implement it!
}