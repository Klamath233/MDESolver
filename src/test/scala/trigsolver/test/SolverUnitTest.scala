package trigsolver.test

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import trigsolver.Solver

class SolverUnitTester(c: Solver) extends PeekPokeTester(c) {

  private val solver = c

  poke(solver.io.leftAVec(0), 0) // 0
  poke(solver.io.leftAVec(1), 0) // 0
  poke(solver.io.leftAVec(2), 1) // 1/16
  poke(solver.io.leftAVec(3), 2) // 1/8

  poke(solver.io.rightAVec(0), 2)
  poke(solver.io.rightAVec(1), 2)
  poke(solver.io.rightAVec(2), 1)
  poke(solver.io.rightAVec(3), 0)

  poke(solver.io.bVec(0), 8)
  poke(solver.io.bVec(1), 8)
  poke(solver.io.bVec(2), 2)
  poke(solver.io.bVec(3), 8)

  for (i <- 0 until 6) {
    step(1)
    val y0 = peek(c.io.yVec(0))
    val y1 = peek(c.io.yVec(1))
    val y2 = peek(c.io.yVec(2))
    val y3 = peek(c.io.yVec(3))

    println(s"$y0 $y1 $y2 $y3")
  }
}

class SolverTester extends ChiselFlatSpec {
  private val backendNames = Array[String]("firrtl", "verilator")
  for ( backendName <- backendNames ) {
    "Solver" should s"produce corrent result (with $backendName)" in {
      Driver(() => new Solver(4, 4), backendName) {
        c => new SolverUnitTester(c)
      } should be (true)
    }
  }
}