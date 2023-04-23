import java.util.ArrayList;
import java.util.Iterator;

public class Blockchain {

    private final ArrayList<Block> BLOCKS = new ArrayList<>();

    private final ArrayList<Transaction> MEMPOOL = new ArrayList<>();
    private final Mechanism MECHANISM;

    private final int BLOCK_SIZE;
    private final int BLOCK_COUNT;
    private final ArrayList<Integer> SIMULATION_REGIONS;
    private final ArrayList<Integer> SIMULATION_REGIONS_LOAD;

    private final int CORRELATED_REGION_COUNT;

    public Blockchain(int blockSize, int blockCount, ArrayList<Integer> simulationRegions, ArrayList<Integer> simulationRegionsLoad, int correlatedRegionCount, MechanismParameters params) {
        this.BLOCK_SIZE = blockSize;
        this.BLOCK_COUNT = blockCount;
        this.SIMULATION_REGIONS = simulationRegions;
        this.SIMULATION_REGIONS_LOAD = simulationRegionsLoad;
        this.CORRELATED_REGION_COUNT = correlatedRegionCount;
        MECHANISM = new Mechanism(params);
    }


    public void simulate() {
        for (int i = 0; i < BLOCK_COUNT; i++) {
            Block block = createBlock(getLoad(i), isCorrelated(i));

            for (Tier tier : MECHANISM.getTiers()) {
                System.out.println("Tier " + tier.getID() + " has " + block.getTransactionCountInTier(tier) + " transactions");
            }

            BLOCKS.add(block);

            System.out.println("Block " + i + " added to chain | Fees: " + block.getFees());
            MECHANISM.updateTierParameters(block);
        }
    }

    private boolean isCorrelated(int blockNumber) {
        for (int i = 0; i < SIMULATION_REGIONS.size(); i++)
            if (blockNumber < SIMULATION_REGIONS.get(i))
                return SIMULATION_REGIONS.size() - (i + 1) < CORRELATED_REGION_COUNT;

        return CORRELATED_REGION_COUNT > 0;
    }

    private int getLoad(int blockNumber) {
        for (int i = 0; i < SIMULATION_REGIONS.size(); i++)
            if (blockNumber < SIMULATION_REGIONS.get(i)) return SIMULATION_REGIONS_LOAD.get(i);

        return SIMULATION_REGIONS_LOAD.get(SIMULATION_REGIONS_LOAD.size() - 1);
    }

    private Block createBlock(int load, boolean isCorrelated) {
        MEMPOOL.clear(); // There is no queueing mempool in the experiment in the paper, so we clear it every block
        Block block = new Block(BLOCK_SIZE);
        while (load > 0) {
            MEMPOOL.add(new Transaction(isCorrelated));
            load--;
        }

        addMempoolTransactionsToBlock(block);

        System.out.println("Block created with " + block.getTransactions().size() + " transactions | Mempool size: " + MEMPOOL.size());
        return block;
    }

    private void addMempoolTransactionsToBlock(Block block) {
        Iterator<Transaction> memPoolTransactions = MEMPOOL.iterator();
        while (memPoolTransactions.hasNext()) {
            Transaction tx = memPoolTransactions.next();
            if (MECHANISM.addTransactionToBestTier(tx)) {
                try {
                    block.addTransaction(tx);
                    memPoolTransactions.remove();
                } catch (RuntimeException e) {
                    // Block is full
                }
            }
        }
    }
}

