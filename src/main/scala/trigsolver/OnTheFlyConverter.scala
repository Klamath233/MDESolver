package trigsolver

import chisel3._
import chisel3.util._

class OnTheFlyConverter(val m:Int) extends Module {
  val io = IO(new Bundle {
    val d = Input(SInt(2.W))
    val y = Output(SInt((m + 1).W))
  })

  val counter = Counter(m + 2)
  val regQ = RegInit(0.asUInt((m + 1).W))
  val regQM = RegInit(1.asUInt((m + 1).W)) // Q[0] = QM[0] + 2^0

  val loadQ = io.d(1)
  val loadQM = ~io.d(1) & io.d(0)

  val inQ = io.d(0)
  val inQM = ~io.d(0)

  val finished = RegNext(counter.value === (m + 1).U)
  val initialized = (counter.value =/= 0.U)

  when (initialized & ~finished) {
  	regQ := Mux(loadQ, Cat(regQM, inQ), Cat(regQ, inQ))
  	regQM := Mux(loadQM, Cat(regQ, inQM), Cat(regQM, inQM))
  }

  when (~finished) {
    counter.inc()
  }

  io.y := regQ.asSInt()
}