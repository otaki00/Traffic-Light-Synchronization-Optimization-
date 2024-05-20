package model.Gene;

import org.jgap.*;

public class IntersectionGene extends BaseGene {
    private int grTimingSet1;
    private int grTimingSet2;
    private int grTimingSet3;
    private int cycleLength;
    private int offset;

    private final int YALLOW_TIME = 4; 
    private final int LEAST_RED_TIME = 15;

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
            this.grTimingSet1 = validateValue(newValues[0], 10, 30); 
            this.grTimingSet2 = validateValue(newValues[1], 10, 30);
            this.grTimingSet3 = validateValue(newValues[2], 10, 30);
            this.cycleLength = newValues[3];
            this.offset = validateValue(newValues[4], 0, 30);
        }
    }

    private int validateValue(int value, int min, int max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
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
        int sumOfGreenAndYellowAndRed = this.grTimingSet1 + this.grTimingSet2 + this.grTimingSet3 + YALLOW_TIME + LEAST_RED_TIME;
        int randomCycleLength = 60 + a_numberGenerator.nextInt(61); // Range [60,120]
        this.cycleLength = randomCycleLength > sumOfGreenAndYellowAndRed ? randomCycleLength : sumOfGreenAndYellowAndRed;
        this.offset = a_numberGenerator.nextInt(31);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof IntersectionGene) {
        IntersectionGene otherGene = (IntersectionGene) o;
        return Integer.compare(this.cycleLength, otherGene.cycleLength);
    } else {
        // As per the Comparable contract, throw an exception if the object is of the wrong type
        throw new ClassCastException("Expected IntersectionGene object");
    }
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

    @Override
    public void applyMutation(int index, double a_percentage) {
        RandomGenerator generator = getConfiguration().getRandomGenerator();
        // Mutate based on index. Ensure mutation respects the gene's constraints.
        // This switch-case can be refined as shown previously.
        switch (index % 5) { // Assuming 5 parameters
            case 0: // Mutate GR timing for set1
                this.grTimingSet1 = mutateValue(this.grTimingSet1, a_percentage, generator, 10, 30);
                break;
            case 1:
                this.grTimingSet2 = mutateValue(this.grTimingSet2, a_percentage, generator, 10, 30);
                break;
            case 2:
                this.grTimingSet3 = mutateValue(this.grTimingSet3, a_percentage, generator, 10, 30);
                break;
            case 3:
                this.cycleLength = mutateValue(this.cycleLength, a_percentage, generator, 60, 120);
                break;
            case 4:
                this.offset = mutateValue(this.offset, a_percentage, generator, 0, 30);
                break;
            default:
                throw new IllegalStateException("Unexpected index for mutation.");
        }
    }

    private int mutateValue(int currentValue, double a_percentage, RandomGenerator generator, int min, int max) {
        int mutationAmount = (int) (currentValue * a_percentage);
        int newValue = currentValue + (generator.nextBoolean() ? mutationAmount : -mutationAmount);
        // Ensure the new value does not exceed constraints
        return Math.max(min, Math.min(max, newValue));
    }

}
