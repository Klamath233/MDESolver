package trigsolver

import chisel3._

class MultipleGenerator(val m:UInt) extends Module {
  val io = IO(new Bundle {
    val d = Input(SInt(2.W))  // SD
    val a = Input(SInt((m + 1).W)) // S.XXXX if m = 4
    val mult = Output(SInt(m + 2).W) // SX.XXXX if m = 4
    val comp = Output(UInt(1.W)) // C - should complement?
  })

  // TODO: implement it!
}