package hr.fer.zemris.optjava.dz2.Functions;

import hr.fer.zemris.optjava.dz2.IFunction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class FunctionTransferable implements IFunction {
    private RealMatrix[] coefs;
    private int numOfVars;
    private double[] solutions;

    public FunctionTransferable(RealMatrix[] coefs){
        this.coefs = coefs;
        this.numOfVars = coefs[0].getColumn(0).length - 1;
        System.out.println(numOfVars);
        this.solutions = new double[coefs.length];
        for(int i=0; i < coefs.length; i++){
            //System.out.println(i);
            solutions[i] = coefs[i].getColumn(0)[numOfVars]; //gledam redak i stupac suprotno od linearne zbog 2DArray umetanja
        }
    }

    @Override
    public int getNumOfVars() {
        return numOfVars;
    }

    @Override
    public double getValueInPoint(RealMatrix p) {
        double value = 0;
        double a = p.getColumn(0)[0];
        double b = p.getColumn(0)[1];
        double c = p.getColumn(0)[2];
        double d = p.getColumn(0)[3];
        double e = p.getColumn(0)[4];
        double f = p.getColumn(0)[5];

        for(int i = 0; i<coefs.length; i++){
            double[] co = coefs[i].getColumn(0);
            double x1 = co[0];
            double x2 = co[1];
            double x3 = co[2];
            double x4 = co[3];
            double x5 = co[4];

            double tempValue = a * x1
                        +b * Math.pow(x1, 3) * x2
                        +c * Math.exp(d * x3) * (1 + Math.cos(e * x4))
                        + f * x4 * Math.pow(x5,2);
            tempValue = tempValue - solutions[i];
            value += (tempValue * tempValue);

        }
        return value;
    }

    @Override
    public RealMatrix getGradientInPoint(RealMatrix p) {
        double[] gradient = new double[p.getColumn(0).length];
        double value = 0;
        double a = p.getColumn(0)[0];
        double b = p.getColumn(0)[1];
        double c = p.getColumn(0)[2];
        double d = p.getColumn(0)[3];
        double e = p.getColumn(0)[4];
        double f = p.getColumn(0)[5];
        for(int i = 0 ; i<coefs.length; i++){
            double[] co = coefs[i].getColumn(0);
            double x1 = co[0];
            double x2 = co[1];
            double x3 = co[2];
            double x4 = co[3];
            double x5 = co[4];
            value = a * x1
                    +b * Math.pow(x1, 3) * x2
                    +c * Math.exp(d * x3) * (1 + Math.cos(e * x4))
                    + f * x4 * Math.pow(x5,2);

            //deriviram po svakoj od varijabli
            gradient[0] += 2 * x1 * value;
            gradient[1] += 2 * Math.pow(x1, 3) * x2 * value;
            //TODO???
        }
        Array2DRowRealMatrix out = new Array2DRowRealMatrix(gradient);
        return out;
    }
}
