package com.al333z.itemstore

import com.al333z.itemstore.Pricing.{ Each, PerVolume, Pricings }
import org.scalatest.FunSuite

class PricesTest extends FunSuite {

  test("amount for calculation") {

    val itemA = Item("A")
    val itemB = Item("B")

    val prices = Prices.from(
      Map(
        itemA -> Pricings(Each(Price(2)), List(PerVolume(7, Price(4)))),
        itemB -> Pricings(Each(Price(12)))
      )
    )

    assert(prices.amountFor(itemA, 2) === Some(Price(4)))
    assert(prices.amountFor(itemA, 7) === Some(Price(4)))
    assert(prices.amountFor(itemA, 8) === Some(Price(6)))

    assert(prices.amountFor(itemB, 3) === Some(Price(36)))
  }

}
