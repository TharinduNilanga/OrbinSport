package model;

public class Return {
    String returnNo;
    String orderId;
    String userId;
    String returnedItems;
    double cost;

    public Return() {
    }

    public Return(String returnNo, String orderId, String userId, String returnedItems, double cost) {
        this.returnNo = returnNo;
        this.orderId = orderId;
        this.userId = userId;
        this.returnedItems = returnedItems;
        this.cost = cost;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReturnedItems() {
        return returnedItems;
    }

    public void setReturnedItems(String returnedItems) {
        this.returnedItems = returnedItems;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Return{" +
                "returnNo='" + returnNo + '\'' +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", returnedItems='" + returnedItems + '\'' +
                ", cost=" + cost +
                '}';
    }
}
