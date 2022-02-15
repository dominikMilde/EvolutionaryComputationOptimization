package hr.fer.zemris.trisat;

public class MutableBitVector extends BitVector{

    public MutableBitVector(boolean ... copiedVector) {
        this.bitVector = copiedVector;
    }

    public MutableBitVector(int n){
        this.bitVector = new boolean[n];
    }


    public void set(int index, boolean value){
        bitVector[index] = value;
    }
}
