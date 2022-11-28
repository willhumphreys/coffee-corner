package coffeecorner.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Item implements Comparable<Item> {
  private final String name;
  private final BigDecimal price;
  private final ProductType productType;

  public Item(String name, BigDecimal price, ProductType productType) {
    this.name = name;
    this.price = price;
    this.productType = productType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return Objects.equals(name, item.name);
  }

  @Override
  public String toString() {
    return "Product{" +
      "name='" + name + '\'' +
      ", price=" + price +
      ", type=" + productType +
      '}';
  }

  public ProductType getType() {
    return productType;
  }

  public String getName() {
    return name;
  }

  @Override
  public int compareTo(Item item) {
    return this.getPrice().compareTo(item.getPrice());
  }

  public BigDecimal getPrice() {
    return price;
  }
}
