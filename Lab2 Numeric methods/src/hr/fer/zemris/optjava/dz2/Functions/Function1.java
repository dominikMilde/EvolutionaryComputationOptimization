package hr.fer.zemris.optjava.dz2.Functions;

import hr.fer.zemris.optjava.dz2.IFunction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class Function1 implements IFunction {
    @Override
    public int getNumOfVars() {
        return 2;
    }

    @Override
    public double getValueInPoint(RealMatrix p) {
        double firstLiteral = p.getEntry(0,0);
        double secondLiteral = p.getEntry(1,0)-1;
        return firstLiteral*firstLiteral + secondLiteral*secondLiteral;
    }

    @Override
    public RealMatrix getGradientInPoint(RealMatrix p) {
        double[] doubles = {2*p.getEntry(0,0), 2*(p.getEntry(1,0)-1)};
        RealMatrix out = new Array2DRowRealMatrix(doubles);
        return out;
    }
}
