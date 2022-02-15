package hr.fer.zemris.trisat.algs;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

import java.util.*;

public class Algorithm4 implements IOptAlgorithm{
    private SATFormula formula;
    private SATFormulaStats statistics;
    private int MAXFLIPS = 100;
    private int MAXTRIES = 100;

    public Algorithm4(SATFormula formula) {
        //System.out.println("konstruktor");
        this.formula = formula;
        this.statistics = new SATFormulaStats(formula);
    }
    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        Random random = new Random();
        Optional<BitVector> out = Optional.empty();


        int tryCount = 0;
        while (tryCount < MAXTRIES){
            BitVector initialAssignment = new BitVector(random, this.formula.getNumberOfVariables());
            System.out.println("Try: "+ tryCount);
            int fitnessOfCurrAssignment = fitnessFunction(initialAssignment);
            System.out.println("cur fit:" + fitnessOfCurrAssignment);
            if(fitnessOfCurrAssignment == formula.getNumberOfClauses()){
                out = Optional.of(initialAssignment);
                System.out.println(out.get());
                break;
            }
            for(int flip = 0; flip<MAXFLIPS; flip++){
                SortedMap<Integer, BitVector> neighbourhood = new TreeMap<>();
                BitVectorNGenerator gen = new BitVectorNGenerator(initialAssignment);
                //System.out.println(iterations);
                for (BitVector n : gen) {
                    // radi nešto sa susjedom
                    //System.out.println("flipped " + fitnessFunction(n));
                    neighbourhood.put(fitnessFunction(n), n.copy());
                }
                boolean passed = false;
                for(BitVector bitVector : neighbourhood.values()){
                    if(!passed){
                        initialAssignment = bitVector.copy();
                    }
                    else{
                        break;
                    }
                }

            }
            fitnessOfCurrAssignment = fitnessFunction(initialAssignment);
            System.out.println("cur fit after flipping:" + fitnessOfCurrAssignment);
            if(fitnessOfCurrAssignment == formula.getNumberOfClauses()){
                out = Optional.of(initialAssignment);
                System.out.println(out.get());
                break;
            }
            tryCount++;
        }
        System.out.println("Returning best:");
        return out;
    }

    private int fitnessFunction(BitVector initialAssignment) {
        int counter = 0;
        for(int i=0; i<formula.getNumberOfClauses(); i++){
            if(formula.getClause(i).isSatisfied(initialAssignment)){
                counter++;
            }
        }
        return counter;
    }
}
