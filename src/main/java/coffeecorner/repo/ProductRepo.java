package coffeecorner.repo;

import coffeecorner.entities.Item;

import java.util.HashMap;
import java.util.Map;

public class ProductRepo {

  private final Map<String, Item> productMap;

  public ProductRepo() {
    this.productMap = new HashMap<>();
  }

  public void save(Item item) {
    productMap.put(item.getName(), item);
  }

  public Item get(String name) {
    return productMap.get(name);
  }
}
