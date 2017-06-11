package trigsolver

import chisel3._

class Solver(val m: Int, val n: Int) extends Module {
  val io = IO(new Bundle {
    val leftAVec = Input(Vec(n, SInt((m + 1).W)))
    val RightAVec = Input(Vec(n, SInt((m + 1).W)))
    val bVec = Input(Vec(n, SInt((m + 1).W)))
  })

  // TODO: implement me!
}