package hr.fer.zemris.trisat;

public class Clause {
    private int[] indexes;
    public Clause(int[] indexes) {
        //System.out.println("nova klauzula");
        //for(int a : indexes){
          //  System.out.println(a);
        //}
        this.indexes = indexes;
    }
    // vraća broj literala koji čine klauzulu
    public int getSize() {
        return indexes.length;
    }

    // vraća indeks varijable koja je index-ti član ove klauzule
    public int getLiteral(int index) {
        return Math.abs(indexes[index]);
    }

    // vraća true ako predana dodjela zadovoljava ovu klauzulu
    public boolean isSatisfied(BitVector assignment) {
        for(int lit = 0; lit<getSize(); lit++){
            boolean b = assignment.get(getLiteral(lit)-1);
            if (b && indexes[lit] > 0) return true;
            if (!b && indexes[lit] < 0) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String out = "";
        for(int lit : indexes){
          out += (""+ lit + " ");
        }
        return out;
    }
}
