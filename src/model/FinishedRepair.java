package model;

public class FinishedRepair {
    private String repairNo;
    private String customerId;
    private String repairType;
    private String productUsed;
    int qty ;
    private double totalPrice;

    public FinishedRepair() {
    }

    public FinishedRepair(String repairNo, String customerId, String repairType, String productUsed,int qty, double totalPrice) {
        this.repairNo = repairNo;
        this.customerId = customerId;
        this.repairType = repairType;
        this.productUsed = productUsed;
        this.qty=qty;
        this.totalPrice = totalPrice;
    }

    public String getRepairNo() {
        return repairNo;
    }

    public void setRepairNo(String repairNo) {
        this.repairNo = repairNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getProductUsed() {
        return productUsed;
    }

    public void setProductUsed(String productUsed) {
        this.productUsed = productUsed;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "FinishedRepair{" +
                "repairNo='" + repairNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", repairType='" + repairType + '\'' +
                ", productUsed='" + productUsed + '\'' +
                ", qty=" + qty +
                ", totalPrice=" + totalPrice +
                '}';
    }
}


