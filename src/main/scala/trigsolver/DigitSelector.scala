package trigsolver

import chisel3._

class DigitSelector extends Module {
  val io = IO(new Bundle {
    val wcHat = Input(SInt(4.W))
    val wsHat = Input(SInt(4.W))
    val d = Output(SInt(2.W))   // SD
  })
  
  val wConv = io.wcHat + io.wsHat

  // FIXIT: can we do better by making this a comb network?
  when (wConv <= -2.S) {       // 111.0 -1.0
  	io.d := -1.S
  } .elsewhen (wConv >= 1.S) { // 000.1 0.5
  	io.d := 1.S
  } .otherwise {
  	io.d := 0.S
  }
}