package hr.fer.zemris.oer.pso;

public class PSOLocalChain extends PSO{
    protected final int ns = 2;
    public PSOLocalChain(IFunction func, int n) {
        super(func, n);
    }

    @Override
    protected void update(int iter) {
        double w;
        if (iter > ITERATION_LIMIT_FOR_WMIN) {
            w = wmin;
        } else {
            w = ((double) iter / ITERATION_LIMIT_FOR_WMIN) * (wmin - wmax) + wmax;
        }

        //System.out.println("w="+w);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            double[] lbest = getBestNeighbour(i);
            for (int d = 0; d < numOfParams; d++) {
                double currPosition = positions[i][d];
                double velocityNext = w * velocities[i][d] +
                        c1 * r.nextDouble() * (pbest[i][d] - currPosition) +
                        c2 * r.nextDouble() * (lbest[d] - currPosition);

                if (velocityNext < MIN_VEL) velocityNext = MIN_VEL;
                if (velocityNext > MAX_VEL) velocityNext = MAX_VEL;

                velocities[i][d] = velocityNext;
                positions[i][d] += velocityNext;
            }
        }
    }
    protected double[] getBestNeighbour(int currIndex){
        double [] bestNeighbour = new double[numOfParams];
        double bestFSoFar = Double.MAX_VALUE;

        for(int i = -ns; i <= ns; i++){
            int indexToTest = currIndex + i;
            if(indexToTest >= 0 && indexToTest < POPULATION_SIZE){
                if(f[indexToTest] < bestFSoFar){
                    bestFSoFar = f[indexToTest];
                    bestNeighbour = pbest[indexToTest];
                }
            }
        }
        return bestNeighbour;
    }
}
