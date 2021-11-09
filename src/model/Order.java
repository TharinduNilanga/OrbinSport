package model;

import java.util.ArrayList;

public class Order {
    private String  orderId;
    private String customerId;
    private String pId;
    private String  warranty;
    private String orderType;
    private int qty;
    private int discount;
    private double unitPrice;
    private String  orderDate;
    private ArrayList<OrderDetails> orderDetails;

    public Order() {
    }

    public Order(String orderId, String customerId, String pId, String warranty, int qty, int discount, double unitPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.pId = pId;
        this.warranty = warranty;
        this.qty = qty;
        this.discount = discount;
        this.unitPrice = unitPrice;
    }
    public Order(String orderId, String customerId, String pId, String warranty, String orderType, int qty, int discount, double unitPrice, String orderDate,ArrayList<OrderDetails> orderDetails) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.pId = pId;
        this.warranty = warranty;
        this.orderType = orderType;
        this.qty = qty;
        this.discount = discount;
        this.unitPrice = unitPrice;
        this.orderDate = orderDate;
        this.orderDetails=orderDetails;
    }
    public Order(String orderId, String customerId, String pId, String warranty, String orderType, int qty,int discount, double unitPrice, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.pId = pId;
        this.warranty = warranty;
        this.orderType = orderType;
        this.qty = qty;
        this.discount = discount;
        this.unitPrice = unitPrice;
        this.orderDate = orderDate;

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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }



    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", pId='" + pId + '\'' +
                ", warranty='" + warranty + '\'' +
                ", orderType='" + orderType + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", unitPrice=" + unitPrice +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
