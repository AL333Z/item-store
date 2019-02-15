import cats.effect.Console.io._
import cats.effect.{ ExitCode, IO, IOApp }
import cats.implicits._
import com.al333z.itemstore.Pricing.{ Each, PerVolume, Pricings }
import com.al333z.itemstore.{ Item, ItemStore, Price, Prices }

object Demo extends IOApp {

  val store: ItemStore = setup()

  override def run(args: List[String]): IO[ExitCode] =
    for {
      items <- getItems
      price = store.compute(items)
      _     <- putStrLn(s"Total: ${price.show}")
    } yield ExitCode.Success

  private def getItems: IO[List[Item]] = {
    def go(acc: List[Item]): IO[List[Item]] =
      for {
        _    <- putStrLn("Enter next item (\"A\", \"B\", \"C\" or \"D\"), or \"done\" to complete")
        next <- readLn
        res <- if (next == "done") IO.pure(acc)
              else go(acc :+ Item(next))
      } yield res

    go(Nil)
  }

  private def setup(): ItemStore =
    new ItemStore(
      Prices.from(
        Map(
          Item("A") -> Pricings(Each(Price(2)), List(PerVolume(4, Price(7)))),
          Item("B") -> Pricings(Each(Price(12))),
          Item("C") -> Pricings(Each(Price(1.25)), List(PerVolume(6, Price(6)))),
          Item("D") -> Pricings(Each(Price(0.15)))
        )
      )
    )
}
