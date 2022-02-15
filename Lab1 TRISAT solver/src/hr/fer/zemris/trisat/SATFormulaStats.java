package hr.fer.zemris.trisat;

public class SATFormulaStats {
    private SATFormula formula;
    private double post[];
    private BitVector assignment;

    private double percentageConstantUp = 0.01;
    private double percentageConstantDown = 0.1;
    private double percentageUnitAmount = 50;

    public SATFormulaStats(SATFormula formula) {
        this.formula = formula;
        this.post = new double[formula.getNumberOfClauses()];
    }

    // analizira se predano rješenje i pamte svi relevantni pokazatelji
    // primjerice, ažurira elemente polja post[...] ako drugi argument to dozvoli; računa Z; ...
    public void setAssignment(BitVector assignment, boolean updatePercentages) {
        this.assignment = assignment;

        if(updatePercentages){
            int m = this.formula.getNumberOfClauses();
            for(int i = 0; i<m; i++){
                if (formula.getClause(i).isSatisfied(assignment)){
                    post[i] += ((1-post[i])*percentageConstantUp);
                }
                else{
                    post[i] += (0-post[i])*percentageConstantDown;
                }
            }
        }
    }
    // vraća temeljem onoga što je setAssignment zapamtio: broj klauzula koje su zadovoljene
    public int getNumberOfSatisfied() {
        int counter = 0;
        for(int i = 0; i < this.formula.getNumberOfClauses(); i++){
            if (this.formula.getClause(i).isSatisfied(this.assignment)) counter++;
        }
        return counter;
    }

    // vraća temeljem onoga što je setAssignment zapamtio
    public boolean isSatisfied() {
        return this.formula.isSatisfied(this.assignment);
    }

    // vraća temeljem onoga što je setAssignment zapamtio: suma korekcija klauzula
    // to je korigirani Z iz algoritma 3
    public double getPercentageBonus() {
        double percentageBonus = 0;

        for(int i = 0; i < this.formula.getNumberOfClauses(); i++){
            if(formula.getClause(i).isSatisfied(assignment)){
                percentageBonus += percentageUnitAmount * (1-post[i]);
            }
            else{
                percentageBonus -= percentageUnitAmount * (1-post[i]);
            }
        }
        return percentageBonus;
    }

    // vraća temeljem onoga što je setAssignment zapamtio: procjena postotka za klauzulu
    // to su elementi polja post[...]
    public double getPercentage(int index) {
        if(index<0 || index > post.length) throw new IndexOutOfBoundsException("Cant access element on the given index.");
        return post[index];
    }

    // resetira sve zapamćene vrijednosti na početne (tipa: zapamćene statistike)
    public void reset() {
        for(int i = 0; i<post.length; i++){
            post[i] = 0;
        }
    }
}
