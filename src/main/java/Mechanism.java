import java.util.ArrayList;
import java.util.Random;

public class Mechanism {

    private final MechanismParameters PARAMS;
    private final ArrayList<Tier> TIERS;
    private int tierUpdateCount = 0;


    public Mechanism(MechanismParameters params) {
        this.PARAMS = params;

        TIERS = new ArrayList<>();
        TIERS.add(new Tier(0, PARAMS.getTierPriceMultiplier(0), PARAMS.getTierDelayMultiplier(0), 1, PARAMS.getRemoveTierPrice(), PARAMS.getMaxTransactionPerBlock()));
    }

    public boolean addTransactionToBestTier(Transaction tx) {
        Tier bestTier = null;
        double bestUtility = 0;
        for (Tier tier : TIERS) {
            double utility = tx.getUtility(tier.getDelay(), tier.getPrice());
            if (utility <= 0) continue;
            if (bestUtility == 0) {
                bestTier = tier;
                bestUtility = utility;
                continue;
            }

            if (utility > bestUtility || (utility == bestUtility && new Random().nextBoolean())) {
                bestTier = tier;
                bestUtility = utility;
            }
        }

        tx.setTier(bestTier);
        if (bestTier != null) bestTier.incrementDemand();
        return bestTier != null;
    }


    public void updateTierParameters(Block block) {
        System.out.println("=== Tiers for Last Block ===");
        for (Tier tier : TIERS)
            System.out.println(tier.toString());
        System.out.println("============================");
        System.out.println("Updating tier parameters...");
        tierUpdateCount++;
        for (Tier tier : TIERS) {
            tier.updateEIP1559(block.getMaxTransactions());
        }
        if (tierUpdateCount % PARAMS.getDelayFrequency() == 0) {
            System.out.println("Updating delays...");
            updateDelays();
        }
        if (tierUpdateCount % PARAMS.getTierFrequency() == 0) {
            System.out.println("Updating tier count...");
            updateTierSizes();
        }
    }

    private void updateDelays() {
        for (int i = 0; i < TIERS.size() - 1; i++) {
            Tier tier = TIERS.get(i);
            Tier nextTier = TIERS.get(i + 1);
            if (nextTier.getPrice() > tier.getPrice() * tier.getPriceMultiplier()) {
                nextTier.setDelay(nextTier.getDelay() + 1);

            } else {
                if (Math.random() * 100 <= PARAMS.getProbabilityDecrease()) nextTier.setDelay(nextTier.getDelay() - 1);

                nextTier.setDelay(
                        Math.max(
                                nextTier.getDelay(),
                                (int) Math.ceil(tier.getDelayMultiplier() * tier.getDelay())
                        )
                );
            }
        }
    }

    private void updateTierSizes() {
        if (TIERS.size() > 1 && TIERS.get(TIERS.size() - 1).getPrice() < PARAMS.getRemoveTierPrice()) {
            Tier removedTier = TIERS.remove(TIERS.size() - 1);
            TIERS.get(0).setTargetTxCount(TIERS.get(0).getTargetTxCount() + removedTier.getTargetTxCount());
        }

        if (TIERS.size() < PARAMS.getMaxTiers() && TIERS.get(TIERS.size() - 1).getPrice() > PARAMS.getAddTierPrice()) {
            Tier newTier = new Tier(
                    TIERS.size(),
                    PARAMS.getTierPriceMultiplier(TIERS.size()),
                    PARAMS.getTierDelayMultiplier(TIERS.size()),
                    (int) Math.ceil(PARAMS.getTierDelayMultiplier(TIERS.size() - 1) * TIERS.get(TIERS.size() - 1).getDelay()),
                    Math.min(PARAMS.getRemoveTierPrice(), PARAMS.getTierPriceMultiplier(TIERS.size() - 1) * PARAMS.getRemoveTierPrice()),
                    PARAMS.getTargetTxCountPerTier()
            );

            TIERS.add(newTier);
            TIERS.get(0).setTargetTxCount(TIERS.get(0).getTargetTxCount() - PARAMS.getTargetTxCountPerTier());

        }
    }


    public ArrayList<Tier> getTiers() {
        return TIERS;
    }
}
