package hr.fer.zemris.trisat;

public class SATFormula {
    private int numberOfVariables;
    private Clause[] clauses;

    public SATFormula(int numberOfVariables, Clause[] clauses) {
        this.numberOfVariables = numberOfVariables;
        this.clauses = clauses;
        if(this.clauses == null){
            System.out.println("kreiran NULLLLLL");
        }
    }
    public int getNumberOfVariables() {
        return this.numberOfVariables;
    }
    public int getNumberOfClauses() {
        return this.clauses.length;
    }
    public Clause getClause(int index) {
        return this.clauses[index];
    }
    public boolean isSatisfied(BitVector assignment) {
        for (int i=0; i<clauses.length; i++){
            //System.out.println("printam klauzulu: " + clauses[i]);
            if (!clauses[i].isSatisfied(assignment)){
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        String out = "";

        for(Clause c : clauses){
            out += ( " (" + c + ")" );
        }
        return out;
    }
}

