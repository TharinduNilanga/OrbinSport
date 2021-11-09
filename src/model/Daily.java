package model;

public class Daily {
    private String date;
    private double total;

    public Daily() {
    }

    public Daily(String date, double total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "date='" + date + '\'' +
                ", total=" + total +
                '}';
    }
}
