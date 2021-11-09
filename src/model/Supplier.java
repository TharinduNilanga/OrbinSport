package model;

public class Supplier {
    private String supplierId;
    private String brand;
    private String description;
    private String company;

    public Supplier() {
    }

    public Supplier(String supplierId, String brand, String description, String company) {
        this.supplierId = supplierId;
        this.brand = brand;
        this.description = description;
        this.company = company;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
