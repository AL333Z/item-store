package com.al333z.itemstore

import com.al333z.itemstore.Pricing.Pricings
import scala.collection.Map

case class Price private (amount: BigDecimal, currency: String)

object Price {

  def usd(amount: BigDecimal) = Price(amount, "USD")
}

object Prices {

  case class Prices private (map: Map[Item, Pricings]) {
    def amountFor(item: Item, volume: Int): Option[BigDecimal] = ???
  }

  def from(map: Map[Item, Pricings]) = Prices(map)
}
