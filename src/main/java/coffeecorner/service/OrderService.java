package coffeecorner.service;

import entities.Item;
import entities.OrderRow;
import entities.ReceiptType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static entities.ReceiptType.BONUS;
import static entities.ReceiptType.ITEM;
import static entities.ReceiptType.STAMP;
import static java.util.stream.Collectors.groupingBy;

public class OrderService {
  private final OfferService offerService;

  public OrderService(OfferService offerService) {
    this.offerService = offerService;
  }

  public Map<ReceiptType, List<OrderRow>> processOrder(List<Item> products) {

    Stream<OrderRow> items = getReceipt(products);
    Map<ReceiptType, Stream<OrderRow>> qualifyingOffers = offerService.process(products);

    return Map.of(
      BONUS, qualifyingOffers.get(BONUS).collect(Collectors.toList()),
      STAMP, qualifyingOffers.get(STAMP).collect(Collectors.toList()),
      ReceiptType.ITEM, items.collect(Collectors.toList()));
  }

  private Stream<OrderRow> getReceipt(List<Item> items) {

    return items.stream().collect(groupingBy(Item::getName,
        LinkedHashMap::new, Collectors.toList()))
      .entrySet().stream().map(es -> new OrderRow(
        es.getValue().size(),
        es.getKey(),
        es.getValue().stream()
          .map(Item::getPrice)
          .reduce(BigDecimal.ZERO, BigDecimal::add))
      );
  }

  public BigDecimal getTotalCost(Map<ReceiptType, List<OrderRow>> receipt) {
    return Stream.of(receipt.get(ITEM), receipt.get(BONUS)).flatMap(Collection::stream)
      .map(OrderRow::getRowValue)
      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
