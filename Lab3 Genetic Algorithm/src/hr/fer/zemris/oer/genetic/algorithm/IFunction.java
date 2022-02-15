package hr.fer.zemris.oer.genetic.algorithm;

import org.apache.commons.math3.linear.RealMatrix;

public interface IFunction {
    public int getNumOfVars();
    public double getValueInPoint(Individual i);
}
