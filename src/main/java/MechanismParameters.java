public class MechanismParameters {

    private final int MAX_TIERS;
    private final int MAX_TRANSACTIONS_PER_BLOCK;
    private final int TARGET_TX_COUNT_PER_TIER;
    private final double REMOVE_TIER_PRICE;
    private final double ADD_TIER_PRICE;
    private final int DELAY_FREQUENCY;
    private final int PROBABILITY_DECREASE;
    private final int TIER_FREQUENCY;
    private final int PRICE_PERCENTAGE;
    private final int DELAY_PERCENTAGE;

    public MechanismParameters(Builder builder) {
        this.MAX_TIERS = builder.maxTiers;
        this.MAX_TRANSACTIONS_PER_BLOCK = builder.maxTransactionsPerBlock;
        this.TARGET_TX_COUNT_PER_TIER = builder.targetTxCountPerTier;
        this.REMOVE_TIER_PRICE = builder.removeTierPrice;
        this.ADD_TIER_PRICE = builder.addTierPrice;
        this.DELAY_FREQUENCY = builder.delayFrequency;
        this.PROBABILITY_DECREASE = builder.probabilityDecrease;
        this.TIER_FREQUENCY = builder.tierFrequency;
        this.PRICE_PERCENTAGE = builder.pricePercentage;
        this.DELAY_PERCENTAGE = builder.delayPercentage;
    }

    public int getMaxTiers() {
        return MAX_TIERS;
    }


    public int getTargetTxCountPerTier() {
        return TARGET_TX_COUNT_PER_TIER;
    }

    public double getRemoveTierPrice() {
        return REMOVE_TIER_PRICE;
    }

    public double getAddTierPrice() {
        return ADD_TIER_PRICE;
    }

    public int getDelayFrequency() {
        return DELAY_FREQUENCY;
    }

    public int getTierFrequency() {
        return TIER_FREQUENCY;
    }

    public double getPriceMultiplier() {
        return PRICE_PERCENTAGE / 100.0;
    }

    public double getMinDelayMultiplier() {
        return DELAY_PERCENTAGE / 100.0;
    }

    public int getProbabilityDecrease() {
        return PROBABILITY_DECREASE;
    }

    public int getMaxTransactionPerBlock() {
        return MAX_TRANSACTIONS_PER_BLOCK;
    }

    public static class Builder {
        private int maxTiers;
        private int maxTransactionsPerBlock;
        private int targetTxCountPerTier;
        private double removeTierPrice;
        private double addTierPrice;
        private int delayFrequency;
        private int tierFrequency;
        private int probabilityDecrease;
        private int pricePercentage;
        private int delayPercentage;

        private Builder() {
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder setMaxTiers(int maxTiers) throws RuntimeException {
            if (maxTiers < 1)
                throw new RuntimeException("Max tiers must be greater than 0");

            this.maxTiers = maxTiers;
            return this;
        }

        public Builder setMaxTransactionsPerBlock(int maxTransactionsPerBlock) throws RuntimeException {
            if (maxTransactionsPerBlock < 1)
                throw new RuntimeException("Max transactions per block must be greater than 0");

            this.maxTransactionsPerBlock = maxTransactionsPerBlock;
            return this;
        }


        public Builder setTargetTxCountPerTier(int targetTxCountPerTier) throws RuntimeException {
            if (targetTxCountPerTier < 1)
                throw new RuntimeException("Target transaction count per tier must be greater than 0");

            this.targetTxCountPerTier = targetTxCountPerTier;
            return this;
        }

        public Builder setRemoveTierPrice(double removeTierPrice) throws RuntimeException {
            if (removeTierPrice < 0)
                throw new RuntimeException("Remove tier price must be greater than 0");

            this.removeTierPrice = removeTierPrice;
            return this;
        }

        public Builder setAddTierPrice(double addTierPrice) throws RuntimeException {
            if (addTierPrice < 0)
                throw new RuntimeException("Add tier price must be greater than 0");

            this.addTierPrice = addTierPrice;
            return this;
        }

        public Builder setDelayFrequency(int delayFrequency) throws RuntimeException {
            if (delayFrequency < 0)
                throw new RuntimeException("Delay frequency must be greater than 0");

            this.delayFrequency = delayFrequency;
            return this;
        }

        public Builder setTierFrequency(int tierFrequency) throws RuntimeException {
            if (tierFrequency < 0)
                throw new RuntimeException("Tier frequency must be greater than 0");

            this.tierFrequency = tierFrequency;
            return this;
        }

        public Builder setProbabilityDecrease(int probabilityDecrease) throws RuntimeException {
            if (probabilityDecrease < 0 || probabilityDecrease > 100)
                throw new RuntimeException("Probability decrease must be in the range [0, 100]");

            this.probabilityDecrease = probabilityDecrease;
            return this;
        }

        public Builder setPricePercentage(int priceMultiplier) throws RuntimeException {
            if (priceMultiplier < 0 || priceMultiplier > 100)
                throw new RuntimeException("Price multiplier must be in the range [0, 100]");

            this.pricePercentage = priceMultiplier;
            return this;
        }

        public Builder setDelayPercentage(int delayPercentage) throws RuntimeException {
            if (delayPercentage < 0 || delayPercentage > 100)
                throw new RuntimeException("Delay percentage must be in the range [0, 100]");

            this.delayPercentage = delayPercentage;
            return this;
        }

        public MechanismParameters build() {
            return new MechanismParameters(this);
        }
    }
}
