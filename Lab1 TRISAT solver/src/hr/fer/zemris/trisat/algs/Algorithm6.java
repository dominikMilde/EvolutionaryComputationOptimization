package hr.fer.zemris.trisat.algs;

import hr.fer.zemris.trisat.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Algorithm6 implements IOptAlgorithm{
    private SATFormula formula;
    private SATFormulaStats statistics;
    private int iterationMAX = 100000;
    private double percentage = 0.5;

    public Algorithm6(SATFormula formula) {
        //System.out.println("konstruktor");
        this.formula = formula;
        this.statistics = new SATFormulaStats(formula);
    }
    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        Random random = new Random();
        Optional<BitVector> out = Optional.empty();
        BitVector initialAssignment = new BitVector(random, this.formula.getNumberOfVariables());
        MutableBitVector assignment = initialAssignment.copy();
        int iterations = 0;
        while (iterations < iterationMAX){
            System.out.println("Iteration: "+ iterations);
            int fitnessOfCurrAssignment = fitnessFunction(assignment);
            System.out.println("cur fit:" + fitnessOfCurrAssignment);
            if(fitnessOfCurrAssignment == formula.getNumberOfClauses()){
                out = Optional.of(assignment);
                System.out.println(out.get());
                break;
            }
            //System.out.println(iterations);
            //BitVector[] neigbourhood = new BitVector[formula.getNumberOfVariables()];
            ArrayList<MutableBitVector> neighbourhood = new ArrayList<>();
            BitVectorNGenerator gen = new BitVectorNGenerator(assignment);
            //System.out.println(iterations);
            for(MutableBitVector n : gen) {

                // radi nešto sa susjedom
                if (fitnessFunction(n) > fitnessOfCurrAssignment) {
                    //System.out.println("Ovaj je bolji.");
                    neighbourhood.add(n.copy());
                }
            }
            //uvjet zaustavljanja
            if(neighbourhood.isEmpty()){
                System.out.println("Stuck in local optimum, doing changes on variables.");
                for(int i=0; i<formula.getNumberOfVariables(); i++) {
                    double randomDbl = Math.random();
                    if (randomDbl < percentage) {
                        assignment.set(i, !assignment.get(i));
                    }
                }
                continue;
            }

            int nextIndex = (int) ((Math.random() * ( neighbourhood.size()- 0)) + 0);
            assignment = neighbourhood.get(nextIndex);
            iterations++;
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
