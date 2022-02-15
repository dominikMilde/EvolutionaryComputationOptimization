package hr.fer.zemris.trisat.algs;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

import java.util.*;

public class Algorithm3 implements IOptAlgorithm {
    private SATFormula formula;
    private SATFormulaStats statistics;
    private int iterationMAX = 10000;
    private int numberOfBest = 2;

    public Algorithm3(SATFormula formula) {
        //System.out.println("konstruktor");
        this.formula = formula;
        this.statistics = new SATFormulaStats(formula);
    }

    public Optional<BitVector> solve(Optional<BitVector> initial) {
        Random random = new Random();
        Optional<BitVector> out = Optional.empty();
        BitVector initialAssignment = new BitVector(random, this.formula.getNumberOfVariables());

        int iterations = 0;

        while (iterations < iterationMAX) {
            System.out.println("Itejavaration: " + iterations);
            statistics.setAssignment(initialAssignment.copy(), true);
            System.out.println(statistics.getNumberOfSatisfied());

            if (statistics.getNumberOfSatisfied() == formula.getNumberOfClauses()) {
                out = Optional.of(initialAssignment);
                System.out.println(out.get());
                break;
            }
            //System.out.println(iterations);
            //BitVector[] neigbourhood = new BitVector[formula.getNumberOfVariables()];
            SortedMap<Double, BitVector> neighbourhood = new TreeMap<>(Collections.reverseOrder());
            BitVectorNGenerator gen = new BitVectorNGenerator(initialAssignment);
            //System.out.println(iterations);
            for (BitVector n : gen) {
                // radi nešto sa susjedom
                neighbourhood.put(fitnessFunction(n), n.copy());
            }
            ArrayList<BitVector> nextGen = new ArrayList<>();
            for(BitVector bitVector : neighbourhood.values()){
                if(nextGen.size()<numberOfBest){
                    nextGen.add(bitVector);
                }
                else{
                    break;
                }
            }

            int nextIndex = (int) ((Math.random() * (nextGen.size() - 0)) + 0);
            initialAssignment = nextGen.get(nextIndex);
            iterations++;
        }
        System.out.println("Returning best:");
        return out;
    }

    private Double fitnessFunction(BitVector n) {
        statistics.setAssignment(n, true);
        double out = statistics.getNumberOfSatisfied() + statistics.getPercentageBonus();
        return out;
    }
}