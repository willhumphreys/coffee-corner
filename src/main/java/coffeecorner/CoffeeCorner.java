package coffeecorner;

import coffeecorner.service.OfferService;
import coffeecorner.service.OrderService;
import coffeecorner.service.PrintService;
import coffeecorner.service.ProductService;
import entities.OrderRow;
import entities.ReceiptType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CoffeeCorner {

  private final OrderService orderService;

  private final ProductService productService;
  private final PrintService printService;

  public CoffeeCorner() {
    productService = new ProductService();
    printService = new PrintService();
    orderService = new OrderService(new OfferService());
    productService.loadProducts();
  }

  public static void main(String[] args) {
    CoffeeCorner coffeeCorner = new CoffeeCorner();
    coffeeCorner.submitOrder(Arrays.asList(args));
  }

  public String submitOrder(List<String> items) {
    Map<ReceiptType, List<OrderRow>> orderRows = orderService.processOrder(productService.map(items));
    return printService.print(orderRows, orderService.getTotalCost(orderRows));
  }
}