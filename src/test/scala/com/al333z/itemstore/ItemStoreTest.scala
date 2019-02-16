package com.al333z.itemstore

import cats.implicits._
import com.al333z.itemstore.Pricing.{ Each, PerVolume, Pricings }
import org.scalatest.FunSuite

class ItemStoreTest extends FunSuite {

  val a = Item("A")
  val b = Item("B")
  val c = Item("C")
  val d = Item("D")

  val prices: Prices = Prices.from(
    Map(
      a -> Pricings(Each(Price(2)), List(PerVolume(4, Price(7)))),
      b -> Pricings(Each(Price(12))),
      c -> Pricings(Each(Price(1.25)), List(PerVolume(6, Price(6)))),
      d -> Pricings(Each(Price(0.15)))
    )
  )
  test("compute total") {
    assert(scanAndResult(List(a, b, c, d, a, b, a, a)) == Price(32.40))
    assert(scanAndResult(List(c, c, c, c, c, c, c)) == Price(7.25))
    assert(scanAndResult(List(a, b, c, d)) == Price(15.40))
  }

  private def scanAndResult(inputs: List[Item]): Price =
    (for {
      store <- ItemStore.fromPrices(prices)
      _     <- inputs.traverse(i => store.scan(i.id))
      res   <- store.result
    } yield res).unsafeRunSync()
}
