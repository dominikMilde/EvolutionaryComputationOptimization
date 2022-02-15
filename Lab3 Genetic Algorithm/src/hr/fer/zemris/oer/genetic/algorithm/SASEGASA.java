package hr.fer.zemris.oer.genetic.algorithm;

import java.net.StandardSocketOptions;
import java.util.*;

public class SASEGASA {
    private static final int MAXNUMBEROFGENERATIONS = 1000;
    private final int POPULATIONSIZE = 40;
    private final int NUMBEROFVILLAGES = 5;
    private final double SUCCESSRATIO = 0.4;
    private final int FACTOR = 30;
    private final double FIRSTPARENTCROSSOVER = 0.8;
    private final double MUTATIONPROBABILITY = 0.3;

    private final double LOWERLIMIT = -20;
    private final double UPPERLIMIT = 20;

    private final IFunction function;
    private final int numOfParams;

    public SASEGASA(IFunction function, int n){
        this.function = function;
        this.numOfParams = n;
    }

    public void start(){
        ArrayList<Individual> population = new ArrayList<>(POPULATIONSIZE);
        generatePopulation(population);
        
        for(int numberofvillages = NUMBEROFVILLAGES; numberofvillages > 0; numberofvillages--){
            ArrayList<Individual> nextGenerationPopulation = new ArrayList<>();
            for(int num = 0; num < numberofvillages; num++){
                int lowerBoundary = num * POPULATIONSIZE / numberofvillages;
                int upperBoundary = (num + 1) * POPULATIONSIZE / numberofvillages;

                if (num == numberofvillages - 1){
                    upperBoundary = POPULATIONSIZE;
                }
                if(upperBoundary > POPULATIONSIZE){
                    upperBoundary = POPULATIONSIZE;
                }

                //System.out.println(lowerBoundary + " " + upperBoundary);
                ArrayList<Individual> villagePopulation = new ArrayList<>();
                for(int index = lowerBoundary; index < upperBoundary; index++){
                    villagePopulation.add(population.get(index));
                }
                System.out.println("Num of villages " + numberofvillages + " current:" + num);
                villagePopulation = offspringSelection(villagePopulation);
                nextGenerationPopulation.addAll(villagePopulation);
                System.out.println(getBestIndividual(villagePopulation));
            }
            population = nextGenerationPopulation;
        }
        Individual best = getBestIndividual(population);
        System.out.println(best);
    }

    private ArrayList<Individual> offspringSelection(ArrayList<Individual> villagePopulation) {
        //System.out.println("AAA" + villagePopulation.size());
        int generationCount = 0;
        while(generationCount < MAXNUMBEROFGENERATIONS){ //TODO neki uvjet
            //System.out.println("Generacija" + generationCount);
            int childCount = 0;
            Set<Individual> nextGenPopulation = new HashSet<>();
            Set<Individual> pool = new HashSet<>();
            nextGenPopulation.add(getBestIndividual(villagePopulation)); //elitism
            //System.out.println(nextGenPopulation.size());

            int wantedNumberOfBetterChildren = (int) (villagePopulation.size() * SUCCESSRATIO);
            while(nextGenPopulation.size() < wantedNumberOfBetterChildren && childCount < villagePopulation.size() * FACTOR){
                Individual firstParent = twoTournirSelection(villagePopulation);
                Individual secondParent = twoTournirSelection(villagePopulation);

                Individual offspring = Individual.nonDeterministicCrossover(firstParent, secondParent, FIRSTPARENTCROSSOVER);
                offspring = Individual.nonDeterministicMutation(offspring, MUTATIONPROBABILITY);

                offspring.setPunishment(function.getValueInPoint(offspring));
                childCount++;

                if(offspring.getPunishment() < firstParent.getPunishment() && offspring.getPunishment() < secondParent.getPunishment()){
                    //System.out.println("DOdajem bolje dijete.");
                    nextGenPopulation.add(offspring);
                }
                else{
                    //System.out.println("Dodajem NEEE bolje dijete.");
                    pool.add(offspring);
                }
            }
            int betterCount = nextGenPopulation.size();
            //System.out.println("prije it");
            Iterator<Individual> it = pool.iterator();
            //System.out.println("poslije it");
            while(nextGenPopulation.size() < villagePopulation.size()){
                if(it.hasNext()){
                    nextGenPopulation.add(it.next());
                }
                else break;
            }

            //System.out.println("Prije afere: " + nextGenPopulation.size());
            //punim preostalo, TODO uvjeti zaustavljanja?
            while(nextGenPopulation.size() < villagePopulation.size() && childCount < villagePopulation.size() * FACTOR){
                Individual firstParent = twoTournirSelection(villagePopulation);
                Individual secondParent = twoTournirSelection(villagePopulation);

                Individual offspring = Individual.nonDeterministicCrossover(firstParent, secondParent, FIRSTPARENTCROSSOVER);
                offspring = Individual.nonDeterministicMutation(offspring, MUTATIONPROBABILITY);

                offspring.setPunishment(function.getValueInPoint(offspring));

                if(offspring.getPunishment() < firstParent.getPunishment() && offspring.getPunishment() < secondParent.getPunishment()){
                    betterCount++;
                }
                nextGenPopulation.add(offspring);
                childCount++;
                //System.out.println("IZVANPETLJENO dijete");
            }
            if(nextGenPopulation.size() < villagePopulation.size()){
                //System.out.println("Konvergiralo u generaciji zbog manjka" + generationCount);
                //System.out.println(getBestIndividual(villagePopulation));
                return villagePopulation;
            }
            if(betterCount < SUCCESSRATIO * villagePopulation.size()){
                //System.out.println("Konvergiralo u generaciji zbog malo dobrih" + generationCount);
                //System.out.println(getBestIndividual(villagePopulation));
                return villagePopulation;
            }
            generationCount++;
            villagePopulation = new ArrayList<>(nextGenPopulation);
        }
        //System.out.println(getBestIndividual(villagePopulation));
        return villagePopulation;
    }

    private Individual twoTournirSelection(ArrayList<Individual> villagePopulation) {
        Random r = new Random();
        int firstIndex = r.nextInt(villagePopulation.size());
        int secondIndex = r.nextInt(villagePopulation.size());

        if(villagePopulation.get(firstIndex).getPunishment() < villagePopulation.get(secondIndex).getPunishment()){
            return villagePopulation.get(firstIndex);
        }
        return villagePopulation.get(secondIndex);
    }

    private Individual getBestIndividual(ArrayList<Individual> villagePopulation) {
        Individual bestSoFar = villagePopulation.get(0);
        for(Individual i : villagePopulation){
            if (i.getPunishment() < bestSoFar.getPunishment()){
                bestSoFar = i;
            }
        }
        return bestSoFar;
    }

    private void generatePopulation(ArrayList<Individual> population) {
        int counter = 0;
        while(counter < POPULATIONSIZE){
            Individual individual = new Individual(numOfParams, LOWERLIMIT, UPPERLIMIT);
            individual.setPunishment(function.getValueInPoint(individual));
            if (!population.contains(individual)){
                population.add(individual);
                counter++;
            }
        }
    }
}
