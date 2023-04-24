import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Config {
    private int maxTiers;
    private int targetTxCountPerTier;
    private double removeTierPrice;
    private double addTierPrice;
    private int delayFrequency;
    private int probabilityDecrease;
    private int tierFrequency;
    private int pricePercentage;
    private int delayPercentage;
    private int blockSize;
    private int blockCount;
    private ArrayList<Integer> simulationRegions;
    private ArrayList<Integer> simulationRegionLoads;
    private int correlatedRegionCount;

    private ArrayList<Double> tierPriceMultipliers;

    private ArrayList<Double> tierDelayMultipliers;

    public Config() {
    }


    public int getMaxTiers() {
        return maxTiers;
    }

    public void setMaxTiers(int maxTiers) {
        this.maxTiers = maxTiers;
    }

    public int getTargetTxCountPerTier() {
        return targetTxCountPerTier;
    }

    public void setTargetTxCountPerTier(int targetTxCountPerTier) {
        this.targetTxCountPerTier = targetTxCountPerTier;
    }

    public double getRemoveTierPrice() {
        return removeTierPrice;
    }

    public void setRemoveTierPrice(double removeTierPrice) {
        this.removeTierPrice = removeTierPrice;
    }

    public double getAddTierPrice() {
        return addTierPrice;
    }

    public void setAddTierPrice(double addTierPrice) {
        this.addTierPrice = addTierPrice;
    }

    public int getDelayFrequency() {
        return delayFrequency;
    }

    public void setDelayFrequency(int delayFrequency) {
        this.delayFrequency = delayFrequency;
    }

    public int getProbabilityDecrease() {
        return probabilityDecrease;
    }

    public void setProbabilityDecrease(int probabilityDecrease) {
        this.probabilityDecrease = probabilityDecrease;
    }

    public int getTierFrequency() {
        return tierFrequency;
    }

    public void setTierFrequency(int tierFrequency) {
        this.tierFrequency = tierFrequency;
    }

    public int getPricePercentage() {
        return pricePercentage;
    }

    public void setPricePercentage(int pricePercentage) {
        this.pricePercentage = pricePercentage;
    }

    public int getDelayPercentage() {
        return delayPercentage;
    }

    public void setDelayPercentage(int delayPercentage) {
        this.delayPercentage = delayPercentage;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }

    public ArrayList<Integer> getSimulationRegions() {
        return simulationRegions;
    }

    public void setSimulationRegions(ArrayList<Integer> simulationRegions) {
        this.simulationRegions = simulationRegions;
    }

    public ArrayList<Integer> getSimulationRegionLoads() {
        return simulationRegionLoads;
    }

    public void setSimulationRegionLoads(ArrayList<Integer> simulationRegionLoads) {
        this.simulationRegionLoads = simulationRegionLoads;
    }

    public int getCorrelatedRegionCount() {
        return correlatedRegionCount;
    }

    public void setCorrelatedRegionCount(int correlatedRegionCount) {
        this.correlatedRegionCount = correlatedRegionCount;
    }

    public ArrayList<Double> getTierDelayMultipliers() {
        return tierDelayMultipliers;
    }

    public void setTierDelayMultipliers(ArrayList<Double> tierDelayMultipliers) {
        this.tierDelayMultipliers = tierDelayMultipliers;
    }

    public ArrayList<Double> getTierPriceMultipliers() {
        return tierPriceMultipliers;
    }

    public void setTierPriceMultipliers(ArrayList<Double> tierPriceMultipliers) {
        this.tierPriceMultipliers = tierPriceMultipliers;
    }
}
