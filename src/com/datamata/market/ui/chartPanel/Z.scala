package com.datamata; package market; package ui; package chartPanel; import Fx.*; import language.implicitConversions

import scala.language.reflectiveCalls

object Z:

  class CandleNode extends Abstract.Group:
    autoSizeChildren = false
    private val OpenClose = Style.PseudoGroup("close-above-open", "open-above-close")
    private val mainIdx = new Idx(true)
    private val openIdx = new Idx(false).^(_.startX = -3)
    private val closeIdx = new Idx(false).^(_.startX = 3)

    object tooltip extends Fx.Tooltip:
      val time, duration, open, close, high, low, volume =  Label()
      graphic = new Pane.Grid {
        add(0, 0, "time", time); add(1, 0, "duration", duration); add(2, 0, "open", open); add(3, 0, "high", high); add(4, 0, "low", low); add(5, 0, "close", close); add(6, 0, "volume", volume)
      }
      attachTo(CandleNode.this)
      def update(v: Quote.Bar): Unit =
        time.text = v.start.toString
        duration.text = v.duration.toString
        open.text = v.open.toString
        high.text = v.high.toString
        low.text = v.low.toString
        close.text = v.close.toString
        volume.text = v.volume.toString

    def update(v: Quote.Bar, pos: Price => Double, candleWidth: Double): Unit =
      val openPos = pos(v.open);
      val closeY = pos(v.close) - openPos;
      mainIdx.y = (pos(v.high) - openPos, pos(v.low) - openPos)
      closeIdx.y = (closeY, closeY)
      val c: Double = v.close.real - v.open.real;
      OpenClose(mainIdx, openIdx, closeIdx)(c >= 0, c < 0)
      tooltip.update(v)

    private class Idx(isMain: Boolean) extends Fx.Shape.Line:
      styleClasses += Style.Class(isMain ? "market-candlestick-line" or "market-candlestick-line-open-close")
      CandleNode.this.children += this

