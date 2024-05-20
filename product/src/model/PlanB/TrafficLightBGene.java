package model.PlanB;

import org.jgap.*;
// import org.jgap.impl.*;

public class TrafficLightBGene extends BaseGene implements Gene {
    private int greenTime;

    public TrafficLightBGene(Configuration a_config) throws InvalidConfigurationException {
        super(a_config);
    }

    @Override
    public Gene newGene() {
        try {
            return new TrafficLightBGene(getConfiguration());
        } catch (InvalidConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void setAllele(Object a_newValue) {
        greenTime = (int) a_newValue;
    }

    @Override
    public Object getAllele() {
        return greenTime;
    }

    @Override
    public void setToRandomValue(RandomGenerator a_numberGenerator) {
        greenTime = a_numberGenerator.nextInt(60);
    }

    @Override
    public void applyMutation(int index, double a_percentage) {
        greenTime = (int) (greenTime + greenTime * a_percentage);
    }

    @Override
    public int compareTo(Object a_other) {
        TrafficLightBGene other = (TrafficLightBGene) a_other;
        return Integer.compare(greenTime, other.greenTime);
    }

    @Override
    public boolean equals(Object a_other) {
        if (!(a_other instanceof TrafficLightBGene))
            return false;
        TrafficLightBGene other = (TrafficLightBGene) a_other;
        return greenTime == other.greenTime;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(greenTime);
    }

    @Override
    public String toString() {
        return String.valueOf(greenTime);
    }

    @Override
    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return String.valueOf(greenTime);
    }

    @Override
    public void setValueFromPersistentRepresentation(String representation)
            throws UnsupportedOperationException, UnsupportedRepresentationException {
        try {
            greenTime = Integer.parseInt(representation);
        } catch (NumberFormatException e) {
            throw new UnsupportedRepresentationException("Invalid integer representation: " + representation);
        }
    }

    @Override
    protected Object getInternalValue() {
        return greenTime;
    }

    @Override
    protected Gene newGeneInternal() {
        try {
            return new TrafficLightBGene(getConfiguration());
        } catch (InvalidConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }
}
