package hr.fer.zemris.optjava.dz2;

import hr.fer.zemris.optjava.dz2.Functions.FunctionFromText;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sustav {
    public static void main(String[] args) {

        double start = -100;
        double end = 100;

        if (args.length < 2){
            System.out.println("Neispravna vrijednost argumenta.");
            System.exit(1);
        }
        int numOfIterations = Integer.valueOf(args[0]);
        Path filePath = Paths.get(args[1]);
        List<String> allLines = null;
        try{
            allLines = Files.readAllLines(filePath);

        }
        catch (IOException e){
            System.out.println("Neispravna putanja.");
        }

        RealMatrix[] eqs = parseToCoefs(allLines);
        //System.out.println(eqs);
        Random r = new Random();
        double[] startPoint = new double[eqs.length];
        for(int i = 0; i < eqs.length; i++){
            startPoint[i] = start + (r.nextDouble() * (end - start));
        }
        IFunction f = new FunctionFromText(eqs);
        RealMatrix result = NumOptAlgorithms.gradientDescent(f, numOfIterations, new Array2DRowRealMatrix(startPoint));
    }

    private static RealMatrix[] parseToCoefs(List<String> allLines) {
        ArrayList<RealMatrix> out = new ArrayList<>();
        int index = 0;
        for(String line : allLines){
            line = line.strip();
            if(line.startsWith("#")){
                continue;
            }

            line = line.replace("[", "").replace("]", "");
            String[] splitted = line.split(",");
            //out = new RealMatrix[splitted.length - 1];
            //System.out.println(splitted.length);
            double[] coefs = new double[splitted.length];
            for(int i=0; i<splitted.length; i++){
                coefs[i] = Double.parseDouble(splitted[i]);
            }
            out.add(new Array2DRowRealMatrix(coefs));
            //System.out.println(out[index]);
            index++;
        }
        RealMatrix[] outArray = out.toArray(new RealMatrix[0]);
        return outArray;
    }
}
