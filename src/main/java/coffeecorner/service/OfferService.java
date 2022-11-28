package coffeecorner.service;

import coffeecorner.entities.Item;
import coffeecorner.entities.OrderRow;
import coffeecorner.entities.ProductType;
import coffeecorner.entities.ReceiptType;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static coffeecorner.entities.ReceiptType.BONUS;
import static coffeecorner.entities.ReceiptType.STAMP;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.groupingBy;

public class OfferService {

  private static final int FREE_DRINK_COUNT = 5;
  private static final BigDecimal MINUS_ONE = BigDecimal.valueOf(-1);

  Map<ReceiptType, Stream<OrderRow>> process(List<Item> order) {

    Map<ReceiptType, Stream<OrderRow>> freeDrinks = calculateFreeDrinks(order, FREE_DRINK_COUNT);

    Stream<OrderRow> freeExtraRows = getBonusForSandwichAndDrink(order);
    Stream<OrderRow> freeDrinkRows = freeDrinks.get(BONUS);

    Stream<OrderRow> stampRows = freeDrinks.get(STAMP);

    return Map.of(
      BONUS, Stream.concat(freeExtraRows, freeDrinkRows),
      STAMP, stampRows
    );
  }

  private Map<ReceiptType, Stream<OrderRow>> calculateFreeDrinks(List<Item> order, int drinkCount) {

    long beverageCountAndStampCount =
      order.stream().filter(r -> r.getType() == ProductType.BEVERAGE || r.getType() == ProductType.STAMP).count();

    long freeDrinks = beverageCountAndStampCount / drinkCount;
    long stampCount = beverageCountAndStampCount % drinkCount;

    Stream<OrderRow> products =
      order.stream()
        .filter(r -> r.getType() == ProductType.BEVERAGE)
        .sorted()
        .limit(freeDrinks)
        .collect(groupingBy(Item::getName,
          LinkedHashMap::new, Collectors.toList()))
        .entrySet().stream().map(es -> new OrderRow(
          es.getValue().size(), format("%dth Drink Free %s", drinkCount, es.getKey()),
          es.getValue().stream().map(Item::getPrice).reduce(ZERO, BigDecimal::add).multiply(MINUS_ONE))
        );

    return Map.of(
      BONUS, products,
      STAMP, Stream.of(new OrderRow((int) stampCount, "Stamps", ZERO))

    );
  }

  private Stream<OrderRow> getBonusForSandwichAndDrink(List<Item> order) {

    Map<ProductType, List<Item>> typeListMap = order.stream().collect(groupingBy(Item::getType));

    List<Item> beverages = typeListMap.getOrDefault(ProductType.BEVERAGE, List.of());
    List<Item> snacks = typeListMap.getOrDefault(ProductType.SNACK, List.of());

    List<Item> extras = typeListMap.getOrDefault(ProductType.EXTRA, List.of());

    int possibleFreeExtras = Math.min(beverages.size(), snacks.size());

    int actualFreeExtras = Math.min(extras.size(), possibleFreeExtras);

    return extras.stream()
      .sorted()
      .limit(actualFreeExtras)
      .collect(Collectors.groupingBy(Item::getName))
      .entrySet()
      .stream().map(es -> new OrderRow(
        es.getValue().size(),
        "FREE -> " + es.getKey(),
        es.getValue().stream().map(Item::getPrice).reduce(ZERO, BigDecimal::add).multiply(MINUS_ONE)));
  }

}