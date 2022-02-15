package hr.fer.zemris.optjava.dz2;

import org.apache.commons.math3.linear.RealMatrix;

public class NumOptAlgorithms {
    private static double threshold = 0.5*10E-2;
    public static RealMatrix gradientDescent(IFunction o, int iterations, RealMatrix point){
        for(int iteration = 0; iteration < iterations; iteration++){
            //System.out.println(o.getGradientInPoint(point).getNorm());
            if(o.getGradientInPoint(point).getNorm() < threshold){
                break;
            }
            System.out.println("Korak " + iteration + " " + point + "vrijednost: " + o.getValueInPoint(point));
            RealMatrix gradient = o.getGradientInPoint(point).scalarMultiply(-1);
            double lambda = calculateLambda(o, point, gradient);

            //System.out.println("ladbda "  + lambda);
            point = point.add(gradient.scalarMultiply(lambda));
        }
        System.out.println("KRAJ " + point + "vrijednost: " + o.getValueInPoint(point));
        return point;
    }

    private static double calculateLambda(IFunction o, RealMatrix point, RealMatrix gradient) {
        //first setting lambda limits
        double lambdaLower = 0;
        double lambdaUpper = 1;

        while(calculateTheta(o, point, gradient, lambdaUpper) < 0){
            lambdaUpper = lambdaUpper * 2;
        }
        //System.out.println("Lupper:" + lambdaUpper);

        //calculating optimal lambda between limits
        double lambdaAvg, theta;
        do{
            lambdaAvg = (lambdaUpper + lambdaLower) / 2;
            theta = calculateTheta(o, point, gradient, lambdaAvg);
            if(theta > 0) { lambdaUpper = lambdaAvg; }
            else { lambdaLower = lambdaAvg; }
        } while(Math.abs(theta) > threshold );

        return lambdaAvg;
    }

    private static double calculateTheta(IFunction o, RealMatrix point, RealMatrix gradient, double lambda) {
        double val = o.getGradientInPoint(point.add(gradient.scalarMultiply(lambda))).transpose().multiply(gradient).getEntry(0,0);
        //System.out.println("theta:" + val);
        return val;
    }
}
