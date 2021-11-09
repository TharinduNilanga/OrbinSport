package model;

public class cash {
    private double cash;

    public cash() {
    }

    public cash(double cash) {
        this.cash = cash;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "cash{" +
                "cash=" + cash +
                '}';
    }
}
