package hr.fer.zemris.optjava.dz2;

import hr.fer.zemris.optjava.dz2.Functions.Function1;
import hr.fer.zemris.optjava.dz2.Functions.Function2;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Random;

public class Jednostavno {
    public static void main(String[] args) {
        IFunction f = new Function1();
        Random r = new Random();
        double start = -100;
        double end = 100;

        double x = start + (r.nextDouble() * (end - start));
        double y = start + (r.nextDouble() * (end - start));
        double[] startPoint = {x, y};
        if(args.length == 4){
            startPoint = new double[]{Double.valueOf(args[2]), Double.valueOf(args[3])};
        }
        else if (args.length < 2){
            System.out.println("Neispravna vrijednost argumenta.");
        }
        int numOfIterations = Integer.valueOf(args[1]);
        switch (args[0]){
            case "1":{
                f = new Function1();
                break;
            }
            case "2":{
                f = new Function2();
                break;
            }
            default:
                System.out.println("Neispravna vrijednost prvog argumenta");
                System.exit(1);
        }
        RealMatrix result = NumOptAlgorithms.gradientDescent(f, numOfIterations, new Array2DRowRealMatrix(startPoint));
    }
}
