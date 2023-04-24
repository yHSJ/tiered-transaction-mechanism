# Tiered Transaction Pricing Mechanism in Java

This is a Java implementation of the Tiered Transaction Pricing mechanism for calculating blockchain fees.
The mechanism was first introduced in the
paper [Tiered Mechanisms for Blockchain Transaction Fees](https://arxiv.org/pdf/2304.06014.pdf)
by [Kiayias, et al.](https://kiayias.com/Aggelos_Kiayias/Home_of_Aggelos_Kiayias.html)

It can be used to run simulations to understand the mechanism better and how the parameters affect the fees and tiers.

## Usage

This project uses [Gradle](https://gradle.org/) as the build tool, and requires at least Java 11. To build and run the
project, you can use the following command in the root of the repository:

```bash
git clone https://github.com/yHSJ/tiered-transaction-mechanism.git \
&&  cd tiered-transaction-mechanism \
&& ./gradlew build \
&& java -jar tiered-transaction-mechanism.jar
```

## Configuration

In order to modify the parameters of the mechanism and your simulation, you can edit the `config.json` file.
By default, the configuration values are the same as those used in the experiment described in the paper if applicable.

### Simulation Parameters

These parameters are used to configure the simulation, such as the number of blocks to simulate and the maximum size of
a block.

- `block_size`: `integer > 0`

  The maximum number of transactions that can be included in a block.

- `block_count`: `integer > 0`

  The total number of blocks to be generated in the simulation.

- `simulated_regions`: `integer[]`, where `integer[i] > 0`

  This array represents the number of regions to be simulated. Each element of the array represents the exclusive upper
  bound of that region, i.e. the block number that begins the next region. The final region does not need an upper
  bound.

- `simulated_region_loads`: `integer[]`, where `integer[i] > 0`

  This array represents the load of each region to be simulated. Each element of the array represents the number of
  transactions of the
  corresponding region in `simulated_regions`. The load of the final region is at index `simulated_regions.length + 1`.

- `correlated_region_count`: `integer >= 0`

  This is the number of regions in which `value(0)` is correlated with the urgency. The final `n` regions will be
  uncorrelated,
  where `n` is the value of this parameter.

### Mechanism Parameters

These parameters are used to configure the mechanism and are described in the paper.

- `target_tx_count_per_tier`: `integer > 0`

  This is the target number of transactions per tier. The mechanism will try to achieve this number of transactions per
  tier.

- `max_tiers`: `integer > 0`

  This is the maximum number of tiers that can be created by the mechanism.

- `delay_frequency`: `integer > 0`

  This is how often the mechanism updates the delay of the tiers, measured in blocks.

- `probability_decrease`: `integer` in the range `[0, 100]`

  This is the percent chance that the mechanism will decrease the delay of a tier, if the delay does not need to be
  increased.

- `tier_frequency`: `integer > 0`

  This is how often the mechanism updates the number of tiers, measured in blocks.

- `add_tier_price`: `double > 0`

  This determines the maximum price of the final tier. If the price of the last tier is greater than this price, a new
  tier is added. The space for the new tier is taken from the first tier.

- `remove_tier_price`: `double > 0`

  This determines the minimum price of the final tier. If the price of the last tier is less than this price, the last
  tier is removed. The space of the removed tier is added to the first tier.

- `tier_price_multipliers`: `double[]`

  This is the parameter <code>µ<sub>i</sub></code> in the paper. If price of a tier `i+1` is less
  than <code>tier_price_multipliers<sub>i+1</sub> * p<sub>i</sub></code> where <code>p<sub>i</sub></code> is the price
  of the `i`th tier, the delay of the `i + 1`th tier is increased by one.

- `tier_delay_multipliers`: `double[]`

  This is the parameter <code>λ<sub>i</sub></code> in the paper. The delay of tier `i` must be at least <code>p<sub>i-1</sub> * λ<sub>i-1</sub></code>.
