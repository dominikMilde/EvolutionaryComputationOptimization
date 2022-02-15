package hr.fer.zemris.oer.pso;

import java.awt.desktop.SystemSleepEvent;

public class PSOGlobal extends PSO{

    public PSOGlobal(IFunction func, int n) {
        super(func, n);

    }

    protected void update(int iter){
        double w;
        if(iter > ITERATION_LIMIT_FOR_WMIN){
            w = wmin;
        }
        else{
            w = ((double) iter / ITERATION_LIMIT_FOR_WMIN) * (wmin - wmax) + wmax;
        }

        //System.out.println("w="+w);
        for(int i = 0; i < POPULATION_SIZE; i++){
            for(int d = 0; d < numOfParams; d++){
                double currPosition = positions[i][d];
                double velocityNext =   w * velocities[i][d] +
                                        c1 * r.nextDouble() * (pbest[i][d] - currPosition) +
                                        c2 * r.nextDouble() * (gbest[d] - currPosition);

                if (velocityNext < MIN_VEL) velocityNext = MIN_VEL;
                if (velocityNext > MAX_VEL) velocityNext = MAX_VEL;

                velocities[i][d] = velocityNext;
                positions[i][d] += velocityNext;
            }
        }
    }
}
