package hr.fer.zemris.trisat.algs;

import hr.fer.zemris.trisat.*;

import java.util.*;

public class Algorithm5 implements IOptAlgorithm{
    private SATFormula formula;
    private SATFormulaStats statistics;
    private int MAXFLIPS = 10000;
    private int MAXTRIES = 10000;

    private double percentage = 0.6;

    public Algorithm5(SATFormula formula) {
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
                int randIndex = (int) ((Math.random() * (formula.getNumberOfClauses() - 0)) + 0);
                while(formula.getClause(randIndex).isSatisfied(initialAssignment)){
                    randIndex = (int) ((Math.random() * (formula.getNumberOfClauses() - 0)) + 0);
                }
                System.out.println(randIndex);
                double randomDbl = Math.random();
                int randVariableInClause;
                int varIndex;
                if(randomDbl<percentage){ //TODO promijeniti
                    //System.out.println("Idemm u prvbi");
                    randVariableInClause = (int)((Math.random() * (3 - 0)) + 0);
                    varIndex = formula.getClause(randIndex).getLiteral(randVariableInClause);
                    Clause[] newClauses = new Clause[formula.getNumberOfClauses()];
                    //System.out.println("Prvi prije i");
                    for(int i=0; i<formula.getNumberOfClauses(); i++){
                        int indexes[] = new int[formula.getClause(i).getSize()];
                        int count = 0;
                        //System.out.println("Prvi prije j");
                        for (int j = 0; j < formula.getClause(i).getSize(); j++){
                            if(Math.abs(formula.getClause(i).getLiteral(j)) == varIndex){
                                indexes[count] = -(formula.getClause(i).getLiteral(j));
                                count++;
                            }
                            else{
                                indexes[count] = (formula.getClause(i).getLiteral(j));
                                count++;
                            }
                        }
                        //System.out.println("Van iz j");
                        Clause inner = new Clause(indexes);
                        newClauses[i] = inner;
                        //System.out.println("Van iz jjjj");
                    }
                    SATFormula newFormula = new SATFormula(formula.getNumberOfVariables(), newClauses);
                    this.formula = newFormula;
                }
                //System.out.println("Prije drugog randoma");
                double randomDbl1 = Math.random();
                if(randomDbl1>=percentage){
                    //System.out.println("Idem u drugi");
                    Clause[] newClauses = new Clause[formula.getNumberOfClauses()];
                    SortedMap<Integer, Integer> mapFitnessToVariableFlip = new TreeMap<>();
                    for(int v = 1; v <= formula.getNumberOfVariables(); v++){
                        for(int i=0; i<formula.getNumberOfClauses(); i++){
                            int indexes[] = new int[formula.getClause(i).getSize()];
                            int count = 0;
                            for (int j = 0; j < formula.getClause(i).getSize(); j++){
                                if(Math.abs(formula.getClause(i).getLiteral(j)) == v){
                                    indexes[count] = -(formula.getClause(i).getLiteral(j));
                                    count++;
                                }
                                else{
                                    indexes[count] = (formula.getClause(i).getLiteral(j));
                                    count++;
                                }
                            }
                            Clause inner = new Clause(indexes);
                            newClauses[i] = inner;
                        }
                        SATFormula temporaryFormula = new SATFormula(formula.getNumberOfVariables(), newClauses);
                        SATFormulaStats stats = new SATFormulaStats(temporaryFormula);
                        stats.setAssignment(initialAssignment, true);
                        mapFitnessToVariableFlip.put(stats.getNumberOfSatisfied(), v);
                    }
                    int bestVar = 0;
                    boolean passed = false;
                    for(int var : mapFitnessToVariableFlip.values()){
                        if(!passed){
                            bestVar = var;
                            passed = true;
                        }
                        else{
                            break;
                        }

                    }
                    Clause[] newClausesFinal = new Clause[formula.getNumberOfClauses()];
                    for(int i=0; i<formula.getNumberOfClauses(); i++){
                        int indexes[] = new int[formula.getClause(i).getSize()];
                        int count = 0;
                        for (int j = 0; j < formula.getClause(i).getSize(); j++){
                            if(Math.abs(formula.getClause(i).getLiteral(j)) == bestVar){
                                indexes[count] = -(formula.getClause(i).getLiteral(j));
                                count++;
                            }
                            else{
                                indexes[count] = (formula.getClause(i).getLiteral(j));
                                count++;
                            }
                        }
                        Clause inner = new Clause(indexes);
                        newClausesFinal[i] = inner;
                    }
                    SATFormula newFormula = new SATFormula(formula.getNumberOfVariables(), newClausesFinal);
                    this.formula = newFormula;
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
