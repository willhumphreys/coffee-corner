package coffeecorner.service;

import coffeecorner.repo.ProductRepo;
import entities.Item;
import entities.ProductType;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

  private final ProductRepo productRepo;

  public ProductService() {
    this.productRepo = new ProductRepo();
  }

  public List<Item> map(List<String> order) {
    return order.stream().map(productRepo::get).collect(Collectors.toList());
  }

  public void loadProducts() {

    productRepo.save(new Item("Small coffee", new BigDecimal("2.5"), ProductType.BEVERAGE));
    productRepo.save(new Item("Medium coffee", new BigDecimal("3.0"), ProductType.BEVERAGE));
    productRepo.save(new Item("Large coffee", new BigDecimal("3.5"), ProductType.BEVERAGE));
    productRepo.save(new Item("Orange juice", new BigDecimal("3.95"), ProductType.BEVERAGE));
    productRepo.save(new Item("Extra milk", new BigDecimal("0.3"), ProductType.EXTRA));
    productRepo.save(new Item("Foamed milk", new BigDecimal("0.5"), ProductType.EXTRA));
    productRepo.save(new Item("Special roast", new BigDecimal("0.9"), ProductType.EXTRA));
    productRepo.save(new Item("Bacon roll", new BigDecimal("4.5"), ProductType.SNACK));

  }

}
