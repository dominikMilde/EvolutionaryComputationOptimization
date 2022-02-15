package hr.fer.zemris.optjava.dz2.Functions;

import hr.fer.zemris.optjava.dz2.IFunction;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class FunctionFromText implements IFunction {
    private RealMatrix[] coefs;
    private int numOfVars;
    private double[] solutions;

    public FunctionFromText(RealMatrix[] coefs){
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
    public double getValueInPoint(RealMatrix p) {
        double out = 0;
        int counterSol = 0;
        for(RealMatrix c : coefs){
            double[] list = c.getColumn(0);
            double tempValue = 0;
            for(int i = 0; i<numOfVars; i++){
                tempValue += list[i] * p.getColumn(0)[i];
            }
            out += Math.pow(tempValue-solutions[counterSol], 2);
            counterSol++;
        }
        return out;
    }

    @Override
    public RealMatrix getGradientInPoint(RealMatrix p) {
        double[] gradient = new double[numOfVars];
        double[] coefList;
        for(int i = 0; i < coefs.length; i++){
            coefList = coefs[i].getColumn(0);
            double value = 0;
            for(int j = 0; j < numOfVars; j++){
                value += coefList[j] * p.getColumn(0)[j];
            }
            value = value - solutions[i];
            for(int k=0; k < numOfVars; k++){
                gradient[k] += 2 * (coefList[k] * value);
            }
        }
        Array2DRowRealMatrix out = new Array2DRowRealMatrix(gradient);
        return out;
    }
}
