package com.al333z.itemstore

import cats.Order

object Pricing {

  sealed trait Pricing

  case class Each(price: Price) extends Pricing

  case class PerVolume(volume: Int, price: Price) extends Pricing

  case class Pricings(each: Each, perVolumes: List[PerVolume] = Nil)

  implicit val orderInstance: Order[Pricing] = Order.fromLessThan[Pricing] { (a, b) =>
    (a, b) match {
      case (Each(_), PerVolume(_, _))           => true
      case (PerVolume(_, _), Each(_))           => false
      case (Each(_), Each(_))                   => false
      case (PerVolume(va, _), PerVolume(vb, _)) => va < vb
    }
  }
}
