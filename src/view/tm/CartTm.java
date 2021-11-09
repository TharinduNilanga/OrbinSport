package view.tm;

public class CartTm {
    private String orderId;
    private String customerId;
    private String productId;
    private String warranty;
    private String orderType;
    private int Qty;
    private int discount;
    private double unitPrice;
    private double total;

    public CartTm() {
    }

    public CartTm(String orderId, String customerId, String productId, String warranty, String orderType, int qty, int discount, double unitPrice, double total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.warranty = warranty;
        this.orderType = orderType;
        Qty = qty;
        this.discount = discount;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartTm{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", productId='" + productId + '\'' +
                ", warranty='" + warranty + '\'' +
                ", orderType='" + orderType + '\'' +
                ", Qty=" + Qty +
                ", discount=" + discount +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
