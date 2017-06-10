package trigsolver

import chisel3._

class DigitSelector extends Module {
  val io = IO(new Bundle(){
    val vhat = Input(SInt(3.W)) // SX.X truncated v
    val d = Output(SInt(2.W))   // SD
  }

  // TODO: implement it!
}