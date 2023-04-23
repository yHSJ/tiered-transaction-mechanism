import java.util.Random;

public class Transaction {

    private final double URGENCY;
    private final double VALUE_0;

    private Tier tier;


    // Mirror the paper's experimental valuation function
    public Transaction(boolean isCorrelated) {
        Random rand = new Random();
        if (!isCorrelated) {
            VALUE_0 = rand.nextGaussian() * 2 + 20;
            URGENCY = rand.nextDouble() + 1;
            return;
        }

        // generate random values based on distribution 1 with probability 0.2
        if (rand.nextDouble() < 0.2) {
            VALUE_0 = rand.nextGaussian() * 10 + 200;
            URGENCY = rand.nextDouble() + 3;
        }
        // generate random values based on distribution 2 with probability 0.4
        else if (rand.nextDouble() < 0.4) {
            VALUE_0 = rand.nextGaussian() * 10 + 50;
            URGENCY = rand.nextDouble() + 2;
        }
        // generate random values based on distribution 3 with probability 0.4
        else {
            VALUE_0 = rand.nextGaussian() * 2 + 20;
            URGENCY = rand.nextDouble() * 0.5 + 1;
        }
    }


    public double getValue(int delay) {
        return VALUE_0 * (URGENCY / delay);
    }

    public double getUtility(int delay, double price) {
        return getValue(delay) - price;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }
}
