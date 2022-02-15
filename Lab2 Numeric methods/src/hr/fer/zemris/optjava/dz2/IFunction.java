package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.RealMatrix;

public interface IFunction {
     public int getNumOfVars();
     public double getValueInPoint(RealMatrix p);
     public RealMatrix getGradientInPoint(RealMatrix p);
}
