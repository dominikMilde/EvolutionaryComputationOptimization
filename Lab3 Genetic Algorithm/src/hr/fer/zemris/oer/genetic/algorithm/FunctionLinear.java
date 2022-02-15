package hr.fer.zemris.oer.genetic.algorithm;

import org.apache.commons.math3.linear.RealMatrix;

public class FunctionLinear implements IFunction {
    private RealMatrix[] coefs;
    private int numOfVars;
    private double[] solutions;

    public FunctionLinear(RealMatrix[] coefs){
        this.coefs = coefs;
        this.numOfVars = coefs[0].getColumn(0).length - 1;
        this.solutions = new double[coefs.length];
        for(int i=0; i < coefs.length; i++){
            //System.out.println(i);
            solutions[i] = coefs[i].getColumn(0)[numOfVars]; //gledam redak i stupac suprotno od linearne zbog 2DArray umetanja
        }
    }

    @Override
    public int getNumOfVars() {
        return this.numOfVars;
    }

    @Override
    public double getValueInPoint(Individual ind) {
        double out = 0;
        int counterSol = 0;
        for(RealMatrix c : coefs){
            double[] list = c.getColumn(0);
            double tempValue = 0;
            for(int i = 0; i<numOfVars; i++){
                tempValue += list[i] * ind.getParams()[i];
            }
            out += Math.pow(tempValue-solutions[counterSol], 2);
            counterSol++;
        }
        return out;
    }
}
