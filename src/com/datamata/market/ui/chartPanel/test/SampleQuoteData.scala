package com.datamata; package market; package ui; package chartPanel; package test; import language.implicitConversions

object SampleQuoteData extends Quote.Range.X.BarBase {
  private val data = scala.Array.ofDim[Double](31, 6).^(a => {
    var cnt = 0;
    def add(v: Double*): Unit = { v.~.foreachIndexed((i, d) => { a(cnt)(i) = d }); cnt += 1 }
    add(1, 25, 20, 32, 16, 20)
    add(2, 26, 30, 33, 22, 25.0)
    add(3, 30, 38, 40, 20, 32.0)
    add(4, 24, 30, 34, 22, 30.0)
    add(5, 26, 36, 40, 24, 32.0)
    add(6, 28, 38, 45, 25, 34.0)
    add(7, 36, 30, 44, 28, 39.0)
    add(8, 30, 18, 36, 16, 31.0)
    add(9, 40, 50, 52, 36, 41.0)
    add(10, 30, 34, 38, 28, 36.0)
    add(11, 24, 12, 30, 8, 32.4)
    add(12, 28, 40, 46, 25, 31.6)
    add(13, 28, 18, 36, 14, 32.6)
    add(14, 38, 30, 40, 26, 30.6)
    add(15, 28, 33, 40, 28, 30.6)
    add(16, 25, 10, 32, 6, 30.1)
    add(17, 26, 30, 42, 18, 27.3)
    add(18, 20, 18, 30, 10, 21.9)
    add(19, 20, 10, 30, 5, 21.9)
    add(20, 26, 16, 32, 10, 17.9)
    add(21, 38, 40, 44, 32, 18.9)
    add(22, 26, 40, 41, 12, 18.9)
    add(23, 30, 18, 34, 10, 18.9)
    add(24, 12, 23, 26, 12, 18.2)
    add(25, 30, 40, 45, 16, 18.9)
    add(26, 25, 35, 38, 20, 21.4)
    add(27, 24, 12, 30, 8, 19.6)
    add(28, 23, 44, 46, 15, 22.2)
    add(29, 28, 18, 30, 12, 23.0)
    add(30, 28, 18, 30, 12, 23.2)
    add(31, 28, 18, 30, 12, 22.0)
  })

  def apply(i: Int): Quote.Bar = {
    val a = data(i);
    new Quote.Bar.Default(Time(Year().months(0).days(a(0).toInt - 1)), _1_Day, Price(a(1)), Price(a(3)), Price(a(4)), Price(a(2)), Qnty((a(5) * 100).toLong))
  }

  def size = 31
}
