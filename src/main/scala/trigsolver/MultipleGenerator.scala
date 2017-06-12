package trigsolver

import chisel3._

class MultipleGenerator(val m:Int) extends Module {
  val io = IO(new Bundle {
    val d = Input(SInt(2.W))  // SD
    val a = Input(SInt((m + 1).W)) // S.XXXX if m = 4
    val mult = Output(SInt((m + 1).W)) // S.XXXX if m = 4
    val comp = Output(UInt(1.W)) // C - should complement?
  })

  val zeros = 0.asSInt((m + 1).W)
  val aInv = io.a ^ -1.asSInt((m + 1).W)

  io.mult := Mux(~io.d(0), zeros, Mux(io.d(1), io.a, aInv))
  io.comp := ~io.d(1) & io.d(0)
}