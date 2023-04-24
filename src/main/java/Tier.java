public class Tier {

    private final int ID;
    private final double PRICE_MULTIPLIER;
    private final double DELAY_MULTIPLIER;
    int demand = 0;
    private int delay;
    private double price;
    private int targetTxCount;

    public Tier(int id, double priceMultiplier, double delayMultiplier, int delay, double price, int targetTxCount) {
        this.ID = id;
        this.PRICE_MULTIPLIER = priceMultiplier;
        this.DELAY_MULTIPLIER = delayMultiplier;
        this.delay = delay;
        this.price = price;
        this.targetTxCount = targetTxCount;
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

    public double getDelayMultiplier() {
        return DELAY_MULTIPLIER;
    }

    public double getPriceMultiplier() {
        return PRICE_MULTIPLIER;
    }

    public double getPrice() {
        return price;
    }

    public double getFullness() {
        return (double) demand / targetTxCount;
    }

    public int getTargetTxCount() {
        return targetTxCount;
    }

    public void setTargetTxCount(int targetFullness) {
        this.targetTxCount = targetFullness;
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
        return (delay == otherTier.delay) && (price == otherTier.price) && (targetTxCount == otherTier.targetTxCount);
    }

    @Override
    public String toString() {
        return "Tier " + ID + ": delay = " + delay + ", price = " + price + ", targetFullness = " + targetTxCount + ", demand = " + demand;
    }
}
