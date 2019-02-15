package com.al333z.itemstore

import cats.Order

object Pricing {

  sealed trait Pricing

  case class Each(price: Price) extends Pricing

  case class PerVolume(volume: Int, price: Price) extends Pricing

  case class Pricings(each: Each, perVolumes: List[PerVolume] = Nil)

  implicit val orderInstance: Order[PerVolume] = Order.fromLessThan[PerVolume] { (a, b) =>
    a.volume < b.volume
  }
}
