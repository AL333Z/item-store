package com.al333z.itemstore

import com.al333z.itemstore.Price.usd
import com.al333z.itemstore.Pricing.{Each, PerVolume, Pricings}
import org.scalatest.FunSuite

class PricesTest extends FunSuite {

  test("amount for calculation"){

    val itemA = Item("A")
    val itemB = Item("B")

    val prices = Prices.from(
      Map(
        itemA -> Pricings(Each(usd(2)), List(PerVolume(7, usd(4)))),
        itemB -> Pricings(Each(usd(12)))
      )
    )

    assert(prices.amountFor(itemA, 2) === 4)
    assert(prices.amountFor(itemA, 7) === 4)
    assert(prices.amountFor(itemA, 8) === 6)

    assert(prices.amountFor(itemB, 3) === 36)
  }

}
