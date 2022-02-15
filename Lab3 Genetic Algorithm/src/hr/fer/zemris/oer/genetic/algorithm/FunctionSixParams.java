package hr.fer.zemris.oer.genetic.algorithm;

import org.apache.commons.math3.linear.RealMatrix;

public class FunctionSixParams implements IFunction {
    private RealMatrix[] coefs;
    private int numOfVars;
    private double[] solutions;

    public FunctionSixParams(RealMatrix[] coefs){
        this.coefs = coefs;
        this.numOfVars = coefs[0].getColumn(0).length - 1;
        System.out.println(numOfVars);
        //System.out.println(numOfVars);
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
    public double getValueInPoint(Individual ind) {
        double value = 0;
        double a = ind.getParams()[0];
        double b = ind.getParams()[1];
        double c = ind.getParams()[2];
        double d = ind.getParams()[3];
        double e = ind.getParams()[4];
        double f = ind.getParams()[5];

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
                    +f * x4 * Math.pow(x5,2);
            tempValue = tempValue - solutions[i];
            value += (tempValue * tempValue);
        }
        return value;
    }
}
