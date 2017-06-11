package trigsolver

import chisel3._

class OnTheFlyConverter(val m:Int) extends Module {
  val io = IO(new Bundle {
    val d = Input(SInt(2.W))
    val y = Output(SInt((m + 1).W))
  })

  // TODO: implement it!
}