package model;

public class most {
    private String description;
    private int qty;

    public most() {
    }

    public most(String description, int qty) {
        this.description = description;
        this.qty = qty;
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

    @Override
    public String toString() {
        return "most{" +
                "description='" + description + '\'' +
                ", qty=" + qty +
                '}';
    }
}
