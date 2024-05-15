package model;

import org.jgap.*;

public class TrafficChromosome extends BaseGene{
    public TrafficChromosome(Configuration a_configuration) throws InvalidConfigurationException {
        super(a_configuration);
        //TODO Auto-generated constructor stub
    }

    private int[] greenTimes; // Array to store green times for each direction at an intersection
    private int cycleLength; // Total cycle length
    private int offset; // Offset time for synchronizing signals
    private int[] adjacentGreenTimes; // Green times for adjacent intersections

    private static final int YELLOW_TIME = 4;
    private static final int MIN_RED_TIME = 15;
    private static final int MIN_GREEN_TIME = 10;
    private static final int MAX_GREEN_TIME = 30;
    private static final int MIN_CYCLE_LENGTH = 60;
    private static final int MAX_CYCLE_LENGTH = 120;
    private static final int MIN_OFFSET = 0;
    private static final int MAX_OFFSET = 30;

    // Constructor to initialize the chromosome with all necessary parameters
    // public TrafficChromosome(int[] greenTimes, int cycleLength, int offset, int[] adjacentGreenTimes) {
    //     this.greenTimes = new int[greenTimes.length];
    //     System.arraycopy(greenTimes, 0, this.greenTimes, 0, greenTimes.length);
    //     this.cycleLength = cycleLength;
    //     this.offset = offset;
    //     this.adjacentGreenTimes = new int[adjacentGreenTimes.length];
    //     System.arraycopy(adjacentGreenTimes, 0, this.adjacentGreenTimes, 0, adjacentGreenTimes.length);

    //     validateCycleLength();
    //     validateGreenTimes();
    //     this.offset = validateValue(this.offset, MIN_OFFSET, MAX_OFFSET);
    // }

    // Validate that the cycle length is sufficient to accommodate green, yellow,
    // and minimum red times
    private void validateCycleLength() {
        int requiredCycleLength = 0;
        for (int greenTime : greenTimes) {
            requiredCycleLength += greenTime;
        }
        requiredCycleLength += YELLOW_TIME + MIN_RED_TIME;
        this.cycleLength = Math.max(requiredCycleLength, this.cycleLength);
        this.cycleLength = validateValue(this.cycleLength, MIN_CYCLE_LENGTH, MAX_CYCLE_LENGTH);
    }

    // Ensure all green times are within the allowable range
    private void validateGreenTimes() {
        for (int i = 0; i < greenTimes.length; i++) {
            greenTimes[i] = validateValue(greenTimes[i], MIN_GREEN_TIME, MAX_GREEN_TIME);
        }
    }

    // Helper method to keep values within specified bounds
    private int validateValue(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    // Random mutation of the chromosome
    public void mutate(RandomGenerator generator) {
        // Randomly select a part of the chromosome to mutate
        int partToMutate = generator.nextInt(4); // 0-3, corresponding to greenTimes, cycleLength, offset,
                                                 // adjacentGreenTimes

        switch (partToMutate) {
            case 0: // Mutate one of the green times
                int index = generator.nextInt(greenTimes.length);
                greenTimes[index] = mutateValue(greenTimes[index], generator);
                break;
            case 1: // Mutate cycle length
                cycleLength = mutateValue(cycleLength, generator);
                break;
            case 2: // Mutate offset
                offset = mutateValue(offset, generator);
                break;
            case 3: // Mutate one of the adjacent green times
                index = generator.nextInt(adjacentGreenTimes.length);
                adjacentGreenTimes[index] = mutateValue(adjacentGreenTimes[index], generator);
                break;
        }

        validateCycleLength();
    }

    // Method to mutate a value within its defined bounds
    private int mutateValue(int currentValue, RandomGenerator generator) {
        int mutationAmount = generator.nextInt(11) - 5; // Random mutation amount between -5 and 5
        int newValue = currentValue + mutationAmount;
        return validateValue(newValue, MIN_GREEN_TIME, MAX_GREEN_TIME); // Assuming all mutations keep within green time
                                                                        // bounds
    }

    // Getters and Setters as needed for GA operations
    public int[] getGreenTimes() {
        return greenTimes;
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public int getOffset() {
        return offset;
    }

    public int[] getAdjacentGreenTimes() {
        return adjacentGreenTimes;
    }

    @Override
    public void applyMutation(int arg0, double arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyMutation'");
    }

    @Override
    public String getPersistentRepresentation() throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPersistentRepresentation'");
    }

    @Override
    public void setAllele(Object arg0) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'setAllele'");
        this.greenTimes = new int[greenTimes.length];
        System.arraycopy(greenTimes, 0, this.greenTimes, 0, greenTimes.length);
        this.cycleLength = cycleLength;
        this.offset = offset;
        this.adjacentGreenTimes = new int[adjacentGreenTimes.length];
        System.arraycopy(adjacentGreenTimes, 0, this.adjacentGreenTimes, 0,
        adjacentGreenTimes.length);

        validateCycleLength();
        validateGreenTimes();
        this.offset = validateValue(this.offset, MIN_OFFSET, MAX_OFFSET);
    }

    @Override
    public void setToRandomValue(RandomGenerator arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setToRandomValue'");
    }

    @Override
    public void setValueFromPersistentRepresentation(String arg0)
            throws UnsupportedOperationException, UnsupportedRepresentationException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setValueFromPersistentRepresentation'");
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    @Override
    protected Object getInternalValue() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInternalValue'");
    }

    @Override
    protected Gene newGeneInternal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'newGeneInternal'");
    }
}
