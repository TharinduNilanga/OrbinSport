package model;

public class Repair {
    String repairNo;
    String customerId;
    String repairType;
    double estimatedPrice;

    public Repair() {
    }

    public Repair(String repairNo, String customerId, String repairType, double estimatedPrice) {
        this.repairNo = repairNo;
        this.customerId = customerId;
        this.repairType = repairType;
        this.estimatedPrice = estimatedPrice;
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

    public double getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(double estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairNo='" + repairNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", repairType='" + repairType + '\'' +
                ", estimatedPrice=" + estimatedPrice +
                '}';
    }
}
