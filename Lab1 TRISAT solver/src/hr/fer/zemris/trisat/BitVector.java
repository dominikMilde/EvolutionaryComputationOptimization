package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

public class BitVector {

    protected boolean[] bitVector;

    private void initializeVector(int n){
        bitVector = new boolean[n];
    }

    public BitVector(Random rand, int numberOfBits){
           initializeVector(numberOfBits);

           for(int i = 0; i<numberOfBits; i++){
               this.bitVector[i] = rand.nextBoolean();
           }
    }

    public BitVector(boolean ... bits) {
        initializeVector(bits.length);

        boolean[] copiedVector = Arrays.copyOf(this.bitVector, this.bitVector.length);
    }

    public BitVector(int n) { initializeVector(n); }

    public boolean get(int index) {
        return this.bitVector[index];
    }

    public int getSize() {
        return bitVector.length;
    }

    @Override
    public String toString() {
        String str = "";
        for (boolean b : bitVector){
            str += (b ? "1" : "0");
        }
        return str;
    }

    public MutableBitVector copy() {
        boolean[] copiedVector = Arrays.copyOf(this.bitVector, this.bitVector.length);
        return new MutableBitVector(copiedVector);
    }
}
