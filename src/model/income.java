package model;

public class income {
    double cost;

    public income() {
    }

    public income(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "income{" +
                "cost=" + cost +
                '}';
    }
}
