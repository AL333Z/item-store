package com.al333z.itemstore

import cats.implicits._

class ItemStore(prices: Prices) {

  def compute(scannedItems: List[Item]): Price =
    scannedItems
      .groupBy(identity)
      .mapValues(_.size)
      .toList
      .map { case (item, qty) => prices.amountFor(item, qty).getOrElse(Price.zero) }
      .combineAll

}
