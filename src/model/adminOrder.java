package model;

public class adminOrder {
    private String orderId;
    private String productId;
    private String orderType;
    private int QTY;
    private int discount;
    private double unitPrice;

    public adminOrder() {
    }

    public adminOrder(String orderId, String productId, String orderType, int QTY, int discount, double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderType = orderType;
        this.QTY = QTY;
        this.discount = discount;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
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

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", QTY=" + QTY +
                ", discount=" + discount +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
