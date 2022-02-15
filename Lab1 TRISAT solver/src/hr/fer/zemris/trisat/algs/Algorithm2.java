package hr.fer.zemris.trisat.algs;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Algorithm2 implements IOptAlgorithm{
    private SATFormula formula;
    private SATFormulaStats statistics;
    private int iterationMAX = 100000;

    public Algorithm2(SATFormula formula) {
        //System.out.println("konstruktor");
        this.formula = formula;
        this.statistics = new SATFormulaStats(formula);
    }
    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        Random random = new Random();
        Optional<BitVector> out = Optional.empty();
        BitVector initialAssignment = new BitVector(random, this.formula.getNumberOfVariables());

        int iterations = 0;
        while (iterations < iterationMAX){
            System.out.println("Iteration: "+ iterations);
            int fitnessOfCurrAssignment = fitnessFunction(initialAssignment);
            System.out.println("cur fit:" + fitnessOfCurrAssignment);
            if(fitnessOfCurrAssignment == formula.getNumberOfClauses()){
                out = Optional.of(initialAssignment);
                System.out.println(out.get());
                break;
            }
            //System.out.println(iterations);
            //BitVector[] neigbourhood = new BitVector[formula.getNumberOfVariables()];
            ArrayList<BitVector> neighbourhood = new ArrayList<>();
            BitVectorNGenerator gen = new BitVectorNGenerator(initialAssignment);
            //System.out.println(iterations);
            for(BitVector n : gen) {

                // radi nešto sa susjedom
                if (fitnessFunction(n) > fitnessOfCurrAssignment) {
                    //System.out.println("Ovaj je bolji.");
                    neighbourhood.add(n.copy());
                }
            }
            //uvjet zaustavljanja
            if(neighbourhood.isEmpty()){
                System.out.println("Search was not successful, local optimum.");
                break;
            }

            int nextIndex = (int) ((Math.random() * ( neighbourhood.size()- 0)) + 0);
            initialAssignment = neighbourhood.get(nextIndex);
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
