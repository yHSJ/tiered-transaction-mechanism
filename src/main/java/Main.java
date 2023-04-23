import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
        Config config = null;
        try {
            config = mapper.readValue(new File("config.json"), Config.class);
        } catch (DatabindException e) {
            System.out.println("Failed to parse config.json file");
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Failed to find config.json file");
            System.exit(1);
        }

        if (config == null) {
            System.out.println("Something went very wrong!");
            System.exit(1);
        }

        if (config.getBlockSize() < 1) {
            System.out.println("block_size must be greater than or equal to 1");
            System.exit(1);
        }

        if (config.getBlockCount() < 1) {
            System.out.println("block_count must be greater than or equal to 1");
            System.exit(1);
        }

        if (config.getSimulationRegions().size() + 1 < config.getSimulationRegionLoads().size()) {
            System.out.println("you must specify a load for each region ("
                    + "Regions: " + config.getSimulationRegions().size()
                    + " Loads: " + config.getSimulationRegionLoads().size()
                    + ")"
            );
            System.exit(1);
        }

        if (config.getSimulationRegions().stream().anyMatch(x -> x < 0)) {
            System.out.println("There may not be a negative region value");
            System.exit(1);
        }


        if (config.getSimulationRegionLoads().stream().anyMatch(x -> x < 0)) {
            System.out.println("There may not be a negative load value");
            System.exit(1);
        }

        if (config.getCorrelatedRegionCount() < 0) {
            System.out.println("correlated_region_count must be greater than or equal to 0");
            System.exit(1);
        }

        MechanismParameters parameters;
        try {
            parameters = MechanismParameters.Builder
                    .newInstance()
                    .setMaxTiers(config.getMaxTiers())
                    .setMaxTransactionsPerBlock(config.getBlockSize())
                    .setTargetTxCountPerTier(config.getTargetTxCountPerTier())
                    .setRemoveTierPrice(config.getRemoveTierPrice())
                    .setAddTierPrice(config.getAddTierPrice())
                    .setDelayFrequency(config.getDelayFrequency())
                    .setTierFrequency(config.getTierFrequency())
                    .setProbabilityDecrease(config.getProbabilityDecrease())
                    .setPricePercentage(config.getPricePercentage())
                    .setDelayPercentage(config.getDelayPercentage())
                    .build();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
            return;
        }

        Blockchain simulatedBlockchain = new Blockchain(
                config.getBlockSize(),
                config.getBlockCount(),
                config.getSimulationRegions(),
                config.getSimulationRegionLoads(),
                config.getCorrelatedRegionCount(),
                parameters);

        simulatedBlockchain.simulate();
    }
}
