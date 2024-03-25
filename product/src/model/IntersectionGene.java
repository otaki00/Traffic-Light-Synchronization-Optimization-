package model;

import org.jgap.*;
import org.jgap.impl.*;

public class IntersectionGene extends BaseGene {
    private int grTimingSet1;
    private int grTimingSet2;
    private int grTimingSet3;
    private int cycleLength;
    private int offset;

    public IntersectionGene(Configuration a_conf) throws InvalidConfigurationException {
        super(a_conf);
    }

    @Override
    public Gene newGene() {
        try {
            return new IntersectionGene(getConfiguration());
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException("Error creating IntersectionGene", ex);
        }
    }

    @Override
    public void setAllele(Object a_newValue) {
        if (a_newValue instanceof int[]) {
            int[] newValues = (int[]) a_newValue;
            this.grTimingSet1 = newValues[0];
            this.grTimingSet2 = newValues[1];
            this.grTimingSet3 = newValues[2];
            this.cycleLength = newValues[3];
            this.offset = newValues[4];
        }
    }

    @Override
    public Object getAllele() {
        return new int[] { this.grTimingSet1, this.grTimingSet2, this.grTimingSet3, this.cycleLength, this.offset };
    }

    @Override
    protected Object getInternalValue() {
        return getAllele();
    }

    @Override
    public void setToRandomValue(RandomGenerator a_numberGenerator) {
        // Define ranges for each of the parameters
        this.grTimingSet1 = 10 + a_numberGenerator.nextInt(21); // Range [10,30]
        this.grTimingSet2 = 10 + a_numberGenerator.nextInt(21); // Range [10,30]
        this.grTimingSet3 = 10 + a_numberGenerator.nextInt(21); // Range [10,30]
        this.cycleLength = 60 + a_numberGenerator.nextInt(61); // Range [60,120]
        this.offset = a_numberGenerator.nextInt(31);
    }

    @Override
    public void applyMutation(int index, double a_percentage) {
        RandomGenerator generator = getConfiguration().getRandomGenerator();

        // Decide which parameter to mutate based on the 'index'.
        // This is a simple round-robin approach.
        switch (index % 5) { // Assuming 5 parameters
            case 0: // Mutate GR timing for set1
                this.grTimingSet1 = mutateValue(this.grTimingSet1, a_percentage, generator);
                break;
            case 1: // Mutate GR timing for set2
                this.grTimingSet2 = mutateValue(this.grTimingSet2, a_percentage, generator);
                break;
            case 2: // Mutate GR timing for set3
                this.grTimingSet3 = mutateValue(this.grTimingSet3, a_percentage, generator);
                break;
            case 3: // Mutate cycle length
                this.cycleLength = mutateValue(this.cycleLength, a_percentage, generator);
                break;
            case 4: // Mutate offset
                this.offset = mutateValue(this.offset, a_percentage, generator);
                break;
            default:
                throw new IllegalStateException("Unexpected index for mutation.");
        }
    }

    private int mutateValue(int currentValue, double a_percentage, RandomGenerator generator) {
        // Calculate the mutation amount
        int mutationAmount = (int) (currentValue * a_percentage);
        // Apply mutation in either direction, add or subtract the mutation amount
        return currentValue + (generator.nextBoolean() ? mutationAmount : -mutationAmount);
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    @Override
    public String getPersistentRepresentation() throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPersistentRepresentation'");
    }

    @Override
    public void setValueFromPersistentRepresentation(String arg0)
            throws UnsupportedOperationException, UnsupportedRepresentationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setValueFromPersistentRepresentation'");
    }

    @Override
    protected Gene newGeneInternal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newGeneInternal'");
    }

}
