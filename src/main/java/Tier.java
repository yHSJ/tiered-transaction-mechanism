public class Tier {

    private final int ID;
    int demand = 0;
    private int delay;
    private double price;
    private int targetFullness;

    public Tier(int id, int delay, double price, int targetFullness) {
        this.ID = id;
        this.delay = delay;
        this.price = price;
        this.targetFullness = targetFullness;
    }

    public void updateEIP1559(int maxBlockSize) {
        double targetFullness = (double) getTargetTxCount() / maxBlockSize;
        price *= (1 + (1.0 / 8) * ((getFullness() - targetFullness) / targetFullness));
        demand = 0;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public double getPrice() {
        return price;
    }

    public double getFullness() {
        return (double) demand / targetFullness;
    }

    public int getTargetTxCount() {
        return targetFullness;
    }

    public void setTargetTxCount(int targetFullness) {
        this.targetFullness = targetFullness;
    }

    public int getID() {
        return ID;
    }

    public void incrementDemand() {
        demand++;
    }

    @Override
    public boolean equals(Object other) {
        if ((other == null) || (getClass() != other.getClass())) return false;

        Tier otherTier = (Tier) other;
        return (delay == otherTier.delay)
                && (price == otherTier.price)
                && (targetFullness == otherTier.targetFullness);
    }

    @Override
    public String toString() {
        return "Tier " + ID + ": delay = " + delay + ", price = " + price + ", targetFullness = " + targetFullness + ", demand = " + demand;
    }
}
