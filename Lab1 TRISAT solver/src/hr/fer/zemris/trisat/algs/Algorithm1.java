package hr.fer.zemris.trisat.algs;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

import java.util.Optional;

public class Algorithm1 implements IOptAlgorithm {
    private SATFormula formula;
    public Algorithm1(SATFormula formula) {
        this.formula = formula;
    }

    @Override
    public Optional<BitVector> solve(Optional<BitVector> initial) {
        Optional<BitVector> out = null;
        int numOfVars = formula.getNumberOfVariables();
        int numOfAssignments = (int) Math.pow(2, numOfVars);

        MutableBitVector assignment = new MutableBitVector(numOfVars); //all "zeros"

        for(int i = 0; i<numOfAssignments; i++){
            //System.out.println("BROj cova: " + formula.getNumberOfClauses());
            if (formula.isSatisfied(assignment)){
                System.out.println(assignment);
                out = Optional.of(assignment.copy());
            }
            addOneBoolean(assignment);
        }

        return out;
    }

    private void addOneBoolean(MutableBitVector assignment){
        int i = 0; //binary count "backwards"
        while(assignment.get(i)){
            assignment.set(i, false);
            i++;
            if(i>=formula.getNumberOfVariables()){
                //System.out.println(assignment + "aadsadsadsasd");
                return;
            }
        }
        assignment.set(i, true);
    }
}
