package service;

import coffeecorner.service.OfferService;
import coffeecorner.service.OrderService;
import coffeecorner.service.ProductService;
import entities.OrderRow;
import entities.ReceiptType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {

  private OrderService orderService;
  private ProductService productService;

  @Before
  public void setUp() {
    productService = new ProductService();

    productService.loadProducts();

    orderService = new OrderService(new OfferService());
  }

  @Test
  public void testEmptyOrder() {

    Map<ReceiptType, List<OrderRow>> orderRows = orderService.processOrder(List.of());

    assertEquals(orderRows.get(ReceiptType.ITEM), List.of());
    assertEquals(orderRows.get(ReceiptType.BONUS), List.of());
    assertEquals(orderRows.get(ReceiptType.STAMP),
      List.of(new OrderRow(0, "Stamps", BigDecimal.ZERO)));
  }

  @Test
  public void testOrderWithDrinkAndExtraDiscounts() {

    List<String> order = List.of(
      "Small coffee",
      "Medium coffee",
      "Small coffee",
      "Large coffee",
      "Orange juice",
      "Extra milk",
      "Special roast",
      "Bacon roll"
    );

    List<OrderRow> itemsOutput = List.of(
      new OrderRow(2, "Small coffee", new BigDecimal("5.0")),
      new OrderRow(1, "Medium coffee", new BigDecimal("3.0")),
      new OrderRow(1, "Large coffee", new BigDecimal("3.5")),
      new OrderRow(1, "Orange juice", new BigDecimal("3.95")),
      new OrderRow(1, "Extra milk", new BigDecimal("0.3")),
      new OrderRow(1, "Special roast", new BigDecimal("0.9")),
      new OrderRow(1, "Bacon roll", new BigDecimal("4.5")));

    List<OrderRow> bonusOutput = List.of(
      new OrderRow(1, "FREE -> Extra milk", new BigDecimal("-0.3")),
      new OrderRow(1, "5th Drink Free Small coffee", BigDecimal.valueOf(-2.5)
      ));

    List<OrderRow> stampOutput = List.of(
      new OrderRow(0, "Stamps", BigDecimal.ZERO)
    );

    Map<ReceiptType, List<OrderRow>> orderRows = orderService.processOrder(productService.map(order));

    assertEquals(orderRows.get(ReceiptType.ITEM), itemsOutput);
    assertEquals(orderRows.get(ReceiptType.BONUS), bonusOutput);
    assertEquals(orderRows.get(ReceiptType.STAMP), stampOutput);
  }

  @Test
  public void testOrderWithMultipleDrinkAndExtraDiscounts() {

    List<String> order = List.of(
      "Small coffee",
      "Medium coffee",
      "Medium coffee",
      "Medium coffee",
      "Medium coffee",
      "Medium coffee",
      "Small coffee",
      "Large coffee",
      "Orange juice",
      "Orange juice",
      "Orange juice",
      "Extra milk",
      "Special roast",
      "Bacon roll",
      "Bacon roll"

    );

    List<OrderRow> itemsOutput = List.of(
      new OrderRow(2, "Small coffee", new BigDecimal("5.0")),
      new OrderRow(5, "Medium coffee", new BigDecimal("15.0")),
      new OrderRow(1, "Large coffee", new BigDecimal("3.5")),
      new OrderRow(3, "Orange juice", new BigDecimal("11.85")),
      new OrderRow(1, "Extra milk", new BigDecimal("0.3")),
      new OrderRow(1, "Special roast", new BigDecimal("0.9")),
      new OrderRow(2, "Bacon roll", new BigDecimal("9.0")));

    List<OrderRow> bonusOutput = List.of(
      new OrderRow(1, "FREE -> Special roast", new BigDecimal("-0.9")),
      new OrderRow(1, "FREE -> Extra milk", new BigDecimal("-0.3")),
      new OrderRow(2, "5th Drink Free Small coffee", BigDecimal.valueOf(-5.0)
      ));

    List<OrderRow> stampOutput = List.of(
      new OrderRow(1, "Stamps", BigDecimal.ZERO)
    );

    Map<ReceiptType, List<OrderRow>> orderRows = orderService.processOrder(productService.map(order));

    assertEquals(orderRows.get(ReceiptType.ITEM), itemsOutput);
    assertEquals(orderRows.get(ReceiptType.BONUS), bonusOutput);
    assertEquals(orderRows.get(ReceiptType.STAMP), stampOutput);
  }

}
