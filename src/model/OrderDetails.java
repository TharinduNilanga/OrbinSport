package model;

public class OrderDetails {
    private String orderId;
    private String pId;
    private String orderType;
    private int qty;
    private int discount;
    private double unitPrice;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String pId, String orderType, int qty, int discount, double unitPrice) {
        this.orderId = orderId;
        this.pId = pId;
        this.orderType = orderType;
        this.qty = qty;
        this.discount = discount;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
                ", pId='" + pId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
