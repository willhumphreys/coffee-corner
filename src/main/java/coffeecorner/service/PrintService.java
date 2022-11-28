package coffeecorner.service;

import entities.OrderRow;
import entities.ReceiptType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

public class PrintService {
  public String print(Map<ReceiptType, List<OrderRow>> receipt, BigDecimal totalCost) {
    return Stream.of(
      receipt.get(ReceiptType.ITEM).stream().map(row -> format("%d %s %s", row.getQuantity(), row.getDescription(),
        row.getRowValue())).collect(Collectors.toList()),

      List.of("---- Bonuses ----"),

      receipt.get(ReceiptType.BONUS).stream().map(row -> format("%d %s %s", row.getQuantity(), row.getDescription()
        , row.getRowValue())).collect(Collectors.toList()),

      List.of("---- Stamp Balance ----"),

      receipt.get(ReceiptType.STAMP).stream().map(row -> Integer.toString(row.getQuantity())).collect(Collectors.toList()),

      List.of("TOTAL: " + totalCost + " CHF")).flatMap(Collection::stream).collect(Collectors.joining("\n"));
  }
}
