package hr.fer.zemris.oer.pso;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1){
            System.out.println("Neispravna vrijednost argumenta.");
            System.exit(1);
        }
          Path filePath = Paths.get(args[0]);
        List<String> allLines = null;
        try{
            allLines = Files.readAllLines(filePath);
        }
        catch (IOException e){
            System.out.println("Neispravna putanja.");
        }
        RealMatrix[] eqs = parseToCoefs(allLines);
        IFunction f = new FunctionSixParams(eqs);
        PSO alg = new PSOLocalChain(f, 6);

        alg.start();
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
            System.out.println(Arrays.toString(coefs));
            out.add(new Array2DRowRealMatrix(coefs));
            index++;
        }
        RealMatrix[] outArray = out.toArray(new RealMatrix[0]);
        return outArray;
    }
}
