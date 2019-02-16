import cats.effect.Console.io._
import cats.effect.{ ExitCode, IO, IOApp }
import cats.implicits._
import com.al333z.itemstore.Pricing.{ Each, PerVolume, Pricings }
import com.al333z.itemstore.{ Item, ItemStore, Price, Prices }

object Demo extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    for {
      store <- initItemStore
      _     <- scanItems(store)
      price <- store.result
      _     <- putStrLn(s"Total: ${price.show}")
    } yield ExitCode.Success

  private def scanItems(store: ItemStore): IO[Unit] =
    for {
      _    <- putStrLn("Enter next item (\"A\", \"B\", \"C\" or \"D\"), or \"done\" to complete")
      next <- readLn
      res <- if (next == "done")
              IO.pure(())
            else
              store
                .scan(next)
                .recoverWith { case t => putError(t.getMessage) }
                .*>(scanItems(store))
    } yield res

  private def initItemStore: IO[ItemStore] =
    ItemStore.fromPrices(
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
