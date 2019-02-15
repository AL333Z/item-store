package com.al333z.itemstore

import com.al333z.itemstore.Pricing.{ Each, PerVolume, Pricings }
import org.scalatest.FunSuite

class ItemStoreTest extends FunSuite {

  test("compute total") {

    val a = Item("A")
    val b = Item("B")
    val c = Item("C")
    val d = Item("D")

    val prices = Prices.from(
      Map(
        a -> Pricings(Each(Price(2)), List(PerVolume(4, Price(7)))),
        b -> Pricings(Each(Price(12))),
        c -> Pricings(Each(Price(1.25)), List(PerVolume(6, Price(6)))),
        d -> Pricings(Each(Price(0.15)))
      )
    )

    val itemStore = new ItemStore(prices)

    assert(itemStore.compute(List(a, b, c, d, a, b, a, a)) == Price(32.40))
    assert(itemStore.compute(List(c, c, c, c, c, c, c)) == Price(7.25))
    assert(itemStore.compute(List(a, b, c, d)) == Price(15.40))
  }

}
