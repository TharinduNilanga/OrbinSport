package view.tm;

public class ProductTable {
    private String productId;
    private String description;
    private String brand;
    private int qty;
    private double unitPrice;

    public ProductTable() {
    }

    public ProductTable(String productId, String description, String brand, int qty, double unitPrice) {
        this.productId = productId;
        this.description = description;
        this.brand = brand;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ProductTable{" +
                "productId='" + productId + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
