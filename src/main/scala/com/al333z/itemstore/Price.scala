package com.al333z.itemstore

import cats.Monoid
import cats.implicits._
import com.al333z.itemstore.Pricing.{ Pricings, _ }

import scala.collection.Map

object Price {

  val zero = Price(0)

  implicit val monoidPrice: Monoid[Price] = new Monoid[Price] {
    override def empty: Price                       = zero
    override def combine(x: Price, y: Price): Price = Price(x.amount + y.amount)
  }

}

case class Price(amount: BigDecimal)

case class Prices private (values: Map[Item, Pricings]) {

  def amountFor(item: Item, volume: Int): Option[Price] =
    values.get(item).map { pricings =>
      def go(v: Int, perVolumes: List[PerVolume]): Price = (v, perVolumes) match {
        case (0, _)   => Price(0)
        case (1, _)   => pricings.each.price
        case (n, Nil) => Price(pricings.each.price.amount * n)
        case (n, vs) => {
          val applicablePricings = vs.filter(_.volume <= n)

          applicablePricings.maximumOption
            .fold(go(n, Nil)) { max =>
              max.price.combine(go(n - max.volume, applicablePricings))
            }
        }
      }

      go(volume, pricings.perVolumes)
    }

}

object Prices {
  def from(map: Map[Item, Pricings]) = Prices(map)
}
