import com.al333z.itemstore.Price._
import com.al333z.itemstore.{ Item, Prices }
import com.al333z.itemstore.Pricing.{ Each, PerVolume, Pricings }
import scala.App

class Demo extends App {

  val prices = Prices.from(
    Map(
      Item("A") -> Pricings(Each(usd(2)), List(PerVolume(7, usd(4)))),
      Item("B") -> Pricings(Each(usd(12))),
      Item("C") -> Pricings(Each(usd(1.25)), List(PerVolume(6, usd(6)))),
      Item("D") -> Pricings(Each(usd(0.15)))
    )
  )

}
