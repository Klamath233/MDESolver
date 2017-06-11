package trigsolver

import chisel3._

class Solver(val m: Int, val n: Int) extends Module {
  val io = IO(new Bundle {
    val leftAVec = Input(Vec(n, SInt((m + 1).W)))
    val rightAVec = Input(Vec(n, SInt((m + 1).W)))
    val bVec = Input(Vec(n, SInt((m + 1).W)))
    val yVec = Output(Vec(n, SInt((m + 1).W)))
  })

  /* Instantiate n basic modules */
  val modules = Array.fill(n)(Module(new BasicModule(m)))

  /* Instantiate n on-the-fly converters */
  val converters = Array.fill(n)(Module(new OnTheFlyConverter(m)))

  /* Make the connections */
  for (i <- 0 until (n - 1)) {
    modules(i).io.b := io.bVec(i)
    modules(i).io.aLeft := io.leftAVec(i)
    modules(i).io.aRight := io.rightAVec(i)

    if (i == 0) {
      modules(i).io.dLeft := 0.S
    } else {
      modules(i).io.dLeft := modules(i - 1).io.dOut
    }

    if (i == n - 1) {
      modules(i).io.dRight := 0.S
    } else {
      modules(i).io.dRight := modules(i + 1).io.dOut
    }

    converters(i).io.d := modules(i).io.dOut
    io.yVec(i) := converters(i).io.y
  }
}