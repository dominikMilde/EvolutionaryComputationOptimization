package hr.fer.zemris.trisat;

import hr.fer.zemris.trisat.algs.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TriSATSolver {
    public static void main(String[] args) {
        if(args.length != 2){
            throw new IllegalArgumentException("2 arguments needed. First is # of desired algorithm and second is path to cnf file.");
        }
        SATFormula formula = ucitajIzDatoteke(args[1]);
        IOptAlgorithm alg = null;
        switch (args[0]){
            case "1": {
                alg = new Algorithm1(formula);
                break;
            }
            case "2": {
                alg = new Algorithm2(formula);
                break;
            }
            case "3":{
                alg = new Algorithm3(formula);
                break;
            }
            case "4":{
                alg = new Algorithm4(formula);
                break;
            }
            case "5":{
                alg = new Algorithm5(formula);
                break;
            }
            case "6":{
                alg = new Algorithm6(formula);
                break;
            }
        }

        Optional<BitVector> solution = alg.solve(Optional.empty());
        if(solution.isPresent()) {
            BitVector sol = solution.get();
            System.out.println("Imamo rješenje: " + sol);
        } else {
            System.out.println("Rješenje nije pronađeno.");
        }
    }

    private static SATFormula ucitajIzDatoteke(String neka_staza) {
        Path p = Paths.get(neka_staza);
        List<String> listOfLines;
        try{
            listOfLines = Files.readAllLines(p);
        }
        catch (IOException SecurityException){
            throw new IllegalArgumentException("Access to given file is not permitted.");
        }

        int numOfVars = 0;
        int numOfClauses = 0;
        ArrayList<Clause> clauses = new ArrayList<>();
        int counter = 0;
        for(String line : listOfLines){
            line = line.strip();
            if (line.charAt(0) == '%') break;
            if (line.charAt(0) == 'c') continue;
            if (line.charAt(0) == 'p'){
                String[] inner = line.split("\\s+");
                //System.out.println(inner[3]);
                numOfVars = Integer.parseInt(inner[2]);
                numOfClauses = Integer.parseInt(inner[3]);
                continue;
            }
            String[] indexes = line.split("\\s+");
            List<String> list = new ArrayList<String>(Arrays.asList(indexes));
            list.remove("0");
            indexes = list.toArray(new String[0]);
            int intIndexes[] = new int[indexes.length];
            for(int i = 0; i< indexes.length; i++){
                intIndexes[i] = Integer.parseInt(indexes[i]);
                //System.out.println(intIndexes[i]);
            }
            clauses.add(new Clause(intIndexes));

        }
        Clause[] clauseArray = clauses.toArray(new Clause[0]);
        return new SATFormula(numOfVars, clauseArray);
    }
}
