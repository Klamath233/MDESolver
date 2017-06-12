package trigsolver.test

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import trigsolver.FiveToTwoAdder

class FiveToTwoAdderUnitTester(c: FiveToTwoAdder) extends PeekPokeTester(c) {

  private val adder = c

  poke(adder.io.i0, 13)
  poke(adder.io.i1, 24)
  poke(adder.io.i2, 3)
  poke(adder.io.i3, 44)
  poke(adder.io.i4, 12)
  poke(adder.io.cIn0, 0)
  poke(adder.io.cIn1, 0)

  val o1 = peek(adder.io.o1)
  val o0 = peek(adder.io.o0)

  println(s"$o1 $o0")

}

class FiveToTwoAdderTester extends ChiselFlatSpec {
  private val backendNames = Array[String]("firrtl", "verilator")
  for ( backendName <- backendNames ) {
    "5:2 adder" should s"produce correct result (with $backendName)" in {
      Driver(() => new FiveToTwoAdder(8), backendName) {
        c => new FiveToTwoAdderUnitTester(c)
      } should be (true)
    }
  }
}