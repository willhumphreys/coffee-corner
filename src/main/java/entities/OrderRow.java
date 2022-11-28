package entities;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderRow {

  private final int quantity;
  private final String rowDescription;
  private final BigDecimal rowValue;

  public OrderRow(int quantity, String rowDescription, BigDecimal rowValue) {
    this.quantity = quantity;
    this.rowDescription = rowDescription;
    this.rowValue = rowValue;
  }

  public BigDecimal getRowValue() {
    return rowValue;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getDescription() {
    return rowDescription;
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity, rowDescription, rowValue);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderRow that = (OrderRow) o;
    return quantity == that.quantity && rowDescription.equals(that.rowDescription) && rowValue.equals(that.rowValue);
  }

  @Override
  public String toString() {
    return "ReceiptRow{" +
      "quantity=" + quantity +
      ", rowDescription='" + rowDescription + '\'' +
      ", rowValue=" + rowValue +
      '}';
  }
}
