package trigsolver.test

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}
import trigsolver.OnTheFlyConverter

class OnTheFlyConverterUnitTester(c: OnTheFlyConverter) extends PeekPokeTester(c) {

  private val otf = c
  private val digits: Array[Int] = Array(1, 1, 0, 1, -1, 0, 0, -1, 1, 0, 1, 0)
  step(1)
  for (i <- 0 until digits.length) {
    poke(otf.io.d, digits(i))
    step(1)
    val y = peek(otf.io.y)
    println(s"$y")
  }
}

class OnTheFlyConverterTester extends ChiselFlatSpec {
  private val backendNames = Array[String]("firrtl", "verilator")
  for ( backendName <- backendNames ) {
    "OTF Converter" should s"show the same table in the book (with $backendName)" in {
      Driver(() => new OnTheFlyConverter(12), backendName) {
        c => new OnTheFlyConverterUnitTester(c)
      } should be (true)
    }
  }
}