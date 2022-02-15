package hr.fer.zemris.trisat;

import java.util.Iterator;

public class BitVectorNGenerator implements Iterable<MutableBitVector>{
    private BitVector assignment;

    public BitVectorNGenerator(BitVector assignment){
        this.assignment = assignment;
    }

    @Override
    public Iterator<MutableBitVector> iterator() {
        return new MyIteratorMutable(assignment);
    }

    public MutableBitVector[] createNeighborhood() {
        MutableBitVector[] neighbours = new MutableBitVector[this.assignment.getSize()];

        int j = 0;
        Iterator<MutableBitVector> iterator = this.iterator();
        while(iterator.hasNext()){
            neighbours[j] = iterator.next();
            j++;
        }
        return neighbours;
    }


    private class MyIteratorMutable implements Iterator<MutableBitVector>{
        private MutableBitVector temp;
        private short i = 0;

        public MyIteratorMutable(BitVector assignment){
            this.temp = assignment.copy();
        }

        @Override
        public boolean hasNext() {
            if (i < temp.getSize()) return true;
            return false;
        }

        @Override
        public MutableBitVector next() {
            if(!hasNext()){
                throw new IndexOutOfBoundsException("Can't get next element. Has no next.");
            }
            MutableBitVector copy = temp.copy();
            //System.out.println(copy + " " + i);
            toggleAt(i, copy);
            //System.out.println(copy);
            i++;
            //System.out.println("next: " + copy);
            return copy;
        }
        private void toggleAt(int i, MutableBitVector mbv){
            mbv.set(i, !mbv.get(i));
        }

    }
}
