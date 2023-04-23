import java.util.ArrayList;
import java.util.stream.Collectors;

public class Block {

    private final int MAX_TRANSACTIONS;

    private final ArrayList<Transaction> TRANSACTIONS = new ArrayList<>();


    public Block(int maxTransactions) {
        this.MAX_TRANSACTIONS = maxTransactions;
    }

    public void addTransaction(Transaction tx) throws RuntimeException {
        if (TRANSACTIONS.size() >= MAX_TRANSACTIONS) throw new RuntimeException("Block is full");

        TRANSACTIONS.add(tx);
    }

    public ArrayList<Transaction> getTransactions() {
        return TRANSACTIONS;
    }

    public int getTransactionCountInTier(Tier tier) {
        return TRANSACTIONS
                .stream()
                .filter(tx -> tx.getTier().equals(tier))
                .collect(Collectors.toCollection(ArrayList::new)).size();
    }

    public int getMaxTransactions() {
        return MAX_TRANSACTIONS;
    }

    public double getFees() {
        return TRANSACTIONS
                .stream()
                .map(Transaction::getTier)
                .map(Tier::getPrice)
                .reduce(Double::sum).orElse(0.0);
    }
}
