package hr.fer.zemris.trisat.algs;

import hr.fer.zemris.trisat.BitVector;

import java.util.Optional;

public interface IOptAlgorithm {
     public Optional<BitVector> solve(Optional<BitVector> initial);
}
