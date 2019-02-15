import com.al333z.itemstore.Pricing.{ Each, PerVolume, Pricings }
import com.al333z.itemstore.{ Item, ItemStore, Price, Prices }

class Demo extends App {

  val prices = Prices.from(
    Map(
      Item("A") -> Pricings(Each(Price(2)), List(PerVolume(4, Price(7)))),
      Item("B") -> Pricings(Each(Price(12))),
      Item("C") -> Pricings(Each(Price(1.25)), List(PerVolume(6, Price(6)))),
      Item("D") -> Pricings(Each(Price(0.15)))
    )
  )

  new ItemStore(prices)

}
