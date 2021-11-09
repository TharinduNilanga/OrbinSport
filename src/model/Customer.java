package model;

public class Customer {
    private String customerId;
    private String userId;
    private String name;
    private String nic;
    private String phoneNo;

    public Customer() {
    }

    public Customer(String customerId, String userId, String name, String nic, String phoneNo) {
        this.setCustomerId(customerId);
        this.setUserId(userId);
        this.setName(name);
        this.setNic(nic);
        this.setPhoneNo(phoneNo);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", nic='" + nic + '\'' +
                ", phoneNo=" + phoneNo +
                '}';
    }
}
