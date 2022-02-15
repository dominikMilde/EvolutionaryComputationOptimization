package hr.fer.zemris.oer.pso;

import java.util.Arrays;
import java.util.Random;

public abstract class PSO {
    protected Random r = new Random();

    protected final IFunction function;
    protected final int numOfParams;

    protected final double c1 = 2.0;
    protected final double c2 = 2.0;

    protected final int POPULATION_SIZE = 40;
    protected final double THRESHOLD = 10E-5;
    protected final int ITERATION_LIMIT = 2000;
    protected final int ITERATION_LIMIT_FOR_WMIN = 1500;

    protected final double MIN_POS = -10;
    protected final double MAX_POS = 10;
    protected final double MIN_VEL = -3;
    protected final double MAX_VEL = 3;

    protected double [][] positions;
    protected double [][] velocities;

    protected double [][] pbest;
    protected double [] gbest;
    protected double [] f;
    protected double minimum;

    protected double wmin = 0.3;
    protected double wmax = 0.9;

    public PSO(IFunction func, int n) {
        this.function = func;
        this.numOfParams = n;

        positions = new double[POPULATION_SIZE][numOfParams];
        velocities = new double[POPULATION_SIZE][numOfParams];
        f = new double[POPULATION_SIZE];
        gbest = new double[numOfParams];
        pbest = new double[POPULATION_SIZE][numOfParams];

        initValues();
    }

    private void initValues() {
        minimum = Double.POSITIVE_INFINITY;
        for(int i = 0; i < POPULATION_SIZE; i++){
            f[i] = Double.POSITIVE_INFINITY;
            for(int p = 0; p < numOfParams; p++){
                positions[i][p] = MIN_POS + (r.nextDouble() * (MAX_POS - MIN_POS));
                velocities[i][p] = MIN_VEL + (r.nextDouble() * (MAX_VEL - MIN_VEL));
            }
        }
    }

    public void start() {
        //double[] a = {6.133750498648252, -0.3978223060588416, 2.4668941786656102, 0.416038380570644, -1.6564433196478645, -0.32510780003505957};
        //System.out.println("a" + function.getValueInPoint(a));
        for(int iter = 0; iter < ITERATION_LIMIT; iter++){

            if(minimum <= THRESHOLD){
                System.out.println("Idem van: " + minimum);
                break;
            }

            for(int particle = 0; particle < POPULATION_SIZE; particle++){
                double tempSolution = function.getValueInPoint(positions[particle]);

                if(tempSolution < f[particle]){
                    f[particle] = tempSolution;
                    pbest[particle] = Arrays.copyOf(positions[particle], numOfParams);
                }

                if(tempSolution < minimum){
                    minimum = tempSolution;
                    gbest = positions[particle];
                }

            }
            update(iter);
            System.out.println("Iteration: " + iter);
            for(int p = 0; p < numOfParams; p++){
                System.out.print(gbest[p] + " ");
            }
            System.out.print(" --->");
            System.out.println(minimum);
        }
    }

    protected abstract void update(int iter);

}
