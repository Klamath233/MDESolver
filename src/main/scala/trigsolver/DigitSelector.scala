package trigsolver

import chisel3._

class DigitSelector extends Module {
  val io = IO(new Bundle {
    val wcHat = Input(SInt(4.W))
    val wsHat = Input(SInt(4.W))
    val d = Output(SInt(2.W))   // SD
  })

  // TODO: implement it!
}