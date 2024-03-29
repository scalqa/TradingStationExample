package com.datamata.market; package app.trading_station; package details; import Live.*; import language.implicitConversions

class ChartTab(pro: Pro.O[Position]) extends Fx.Pane.Tab.Panel("Chart"):

  content = new Fx.Pane.Border {
    top = new Fx.Pane.Border {
      left = Fx.Pane.HorizontalBox(LengthSelector.items.stream.asInstanceOf[~[Fx.Node]])
      right = Fx.Pane.HorizontalBox(
        Fx.CheckBox("AskBid ", Chart.showBidsAsksPro),
        Fx.CheckBox("Trades ", Chart.showTradesPro),
        Fx.CheckBox("Volume ", Chart.showVolumePro))
    }
    center = Chart
  }

  object LengthSelector extends Fx.Toggle.Group {
    items.addAll(Stream(6.Weeks, 1.Week, 1.Day, 4.Hours, 1.Hour, 15.Minutes, 5.Minutes, 1.Minute).map(d => new Button(d.tag, d)) +@ (0, new Button("All", VOID)))
    select(items.head)

    def apply() = selected.asInstanceOf[Button].length

    private class Button(s: String, val length: Time.Length) extends Fx.Toggle.Button { text = s; onActionRun(Chart.reset(pro())) }
  }

  object Chart extends Ui.ChartPanel() {
    pro.onValueChange(reset(_))
    showVolumePro() = false
    def reset(p: Position): Unit = {
      val u: Time.Length = LengthSelector()
      val r = p.Last.range
      if (u.isVoid || r.isEmpty)
        apply(p)
      else {
        val i = r.timeIndex(r.last.start - u)
        val t = r(i).period.end
        val bt = if (i <= 0) r else new TailViewList(r, i)

        def tail[A <: Quote](l: Idx.O[A]): Idx.O[A] = { val i = l.orderedSearchBy(_.created, t).start; if (i <= 0) l else new TailViewList[A](l, i) }

        priceChart.Candles.apply(bt)
        priceChart.AskQuotes.apply(tail(p.Ask.list))
        priceChart.LastQuotes.apply(tail(p.Last.list))
        priceChart.BidQuotes.apply(tail(p.Bid.list))
        priceChart.TradeFills.apply(tail(p.fills))
        volumeChart.Bars.apply(bt)
      }
    }
  }

private class TailViewList[A](protected val real: Idx.O[A], pos: Int) extends Idx.O[A]:
  val start = Pro.M(pos)
  def apply(i: Int) = real(i + start())
  def size = real.size - start()
  def onChange[U](l: Pack[Idx.O.Event[A]] => U): Event.Control = real.onChange(Event.Id.map1(l,list => l(list.stream.mapOpt{
    case v: Idx.Event.Remove[A] => (if (v.range.start >= start()) v:Idx.Event[A] else { start() -= 1; VOID })
    case v: Idx.Event.Add[A]    => Idx.Event.Add(v.range.start - start(), v.items) :Idx.Event[A]
    case v: Idx.Event.Update[A] => Idx.Event.Update.refresh(size - 1, v.items(0))  :Idx.Event[A]
    case v                      => VOID
  }.pack)))

