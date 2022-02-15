package hr.fer.zemris.oer.pso;

public interface IFunction {
    public int getNumOfVars();
    public double getValueInPoint(double[] position);
}
