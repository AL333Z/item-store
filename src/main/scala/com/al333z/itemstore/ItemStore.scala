package com.al333z.itemstore

import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.implicits._

class ItemStore private (prices: Prices, scanned: Ref[IO, List[Item]]) {

  def scan(in: String): IO[Unit] = {
    val i = Item(in)
    if (prices.values.contains(i))
      scanned.update(_ :+ i)
    else
      IO.raiseError(new RuntimeException("Invalid item."))
  }

  def result: IO[Price] =
    scanned.get.map(
      _.groupBy(identity)
        .mapValues(_.size)
        .toList
        .map { case (item, qty) => prices.amountFor(item, qty).getOrElse(Price.zero) }
        .combineAll
    )
}

object ItemStore {
  def fromPrices(prices: Prices): IO[ItemStore] =
    Ref
      .of[IO, List[Item]](Nil)
      .map(scanned => new ItemStore(prices, scanned))
}
