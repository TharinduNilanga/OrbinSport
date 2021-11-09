package model;

public class RepairProducts {
    private String productId;
    private String description;
    private int qty;
    private double unitPrice;

    public RepairProducts() {
    }

    public RepairProducts(String productId, String description, int qty, double unitPrice) {
        this.productId = productId;
        this.description = description;
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
        return "RepairProducts{" +
                "productId='" + productId + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
