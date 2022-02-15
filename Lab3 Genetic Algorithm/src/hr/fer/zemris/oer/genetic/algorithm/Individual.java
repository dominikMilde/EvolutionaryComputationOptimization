package hr.fer.zemris.oer.genetic.algorithm;

import java.util.Arrays;
import java.util.Random;

public class Individual {
    private double[] params;
    private double punishment;
    Random r = new Random();
    public Individual(double[] p){
        this.params = p;
        this.punishment = Double.MAX_VALUE;
    }
    public Individual(double[] p, double punishment){
        this.params = p;
        this.punishment = punishment;
    }

    public Individual(int numofparameters, double lowerlimit, double upperlimit) {
        this.params = new double[numofparameters];


        for(int i = 0; i<numofparameters; i++){
            params[i] = lowerlimit + (r.nextDouble() * (upperlimit - lowerlimit));
        }
    }

    public double[] getParams() {
        return params;
    }

    public void setParams(double[] params) {
        this.params = params;
    }

    public double getPunishment() {
        return punishment;
    }

    public void setPunishment(double punishment) {
        this.punishment = punishment;
    }

    public static Individual nonDeterministicCrossover(Individual firstParent, Individual secondParent, double firstparentcrossover) {
        Random r = new Random();
        double coinFlip = r.nextDouble();
        int size = firstParent.getParams().length;
        double[] newParams = new double[size];
        if(coinFlip < 0.5){
            for(int i = 0; i < size; i++){
               newParams[i] = (firstParent.getParams()[i] + secondParent.getParams()[i]) / 2;
            }
        }
        else{
            for(int i = 0; i < size; i++){
                coinFlip = r.nextDouble();
                if(coinFlip < firstparentcrossover){
                    newParams[i] = firstParent.getParams()[i];
                }
                else{
                    newParams[i] = (firstParent.getParams()[i] + secondParent.getParams()[i]) / 2;
                }
            }
        }
        return new Individual(newParams);
    }

    public static Individual nonDeterministicMutation(Individual offspring, double mutationprobability) {
        Random r = new Random();
        double coinFlip = r.nextDouble();
        int size = offspring.getParams().length;
        double[] newParams = new double[size];
        if(coinFlip < 0.5){
            for(int i = 0; i < size; i++){
                coinFlip = r.nextDouble();
                if(coinFlip < mutationprobability){
                    newParams[i] = offspring.getParams()[i] + r.nextGaussian(0, 0.9);
                }
                else{
                    newParams[i] = offspring.getParams()[i];
                }
            }
        }
        else{
            for(int i = 0; i < size; i++){
                coinFlip = r.nextDouble();
                if(coinFlip < mutationprobability){
                    newParams[i] = offspring.getParams()[i] + r.nextGaussian(0, 0.1);
                }
                else{
                    newParams[i] = offspring.getParams()[i];
                }
            }
        }
        return new Individual(newParams);
    }

    @Override
    public String toString() {
        return "Individual{" +
                "params=" + Arrays.toString(params) +
                ", punishment=" + punishment +
                '}';
    }
}
