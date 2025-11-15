package project.evolution.Statistics;

import project.evolution.entities.Animal;
import project.evolution.entities.Plant;
import project.evolution.entities.traits.Vector2d;
import project.evolution.map.WorldMap;

import java.util.*;

public class Statistics {

    public static int getAnimalsAmount(WorldMap map) {
        return map.getAnimalsSet().size();
    }

    public static int getPlantsAmount(WorldMap map) {
        return map.getPlantsMap().size();
    }

    public static int getFreeTilesAmount(WorldMap map) {
        Set<Vector2d> occupiedTiles = new HashSet<>();
        for (Animal a : map.getAnimalsSet()) {
            occupiedTiles.add(a.getPosition());
        }
        for (Map.Entry<Vector2d, Plant> a : map.getPlantsMap().entrySet()) {
            occupiedTiles.add(a.getKey());
        }
        return map.getHeight() * map.getWidth() - occupiedTiles.size();
    }

    public static ArrayList<ArrayList<Integer>> getMostPopularGenotype(WorldMap map, int numberOfGenotypes) {
        numberOfGenotypes = Math.min(numberOfGenotypes, map.getAnimalsSet().size());
        if (numberOfGenotypes == 1) {
            if (!map.getAnimalsMap().isEmpty()) {
                //Trick to get genotype from map. For loop runs only once
                for (Animal a : map.getAnimalsSet()) {
                    return new ArrayList<>(List.of(a.getGenotype().getGenes()));
                }
            } else {
                return null;
            }
        }

        Map<ArrayList<Integer>, Integer> popularGenes = new HashMap<>();
        int maxNumber = 1;
        for (Animal a : map.getAnimalsSet()) {
            if (popularGenes.containsKey(a.getGenotype().getGenes())) {
                popularGenes.put(a.getGenotype().getGenes(), popularGenes.get(a.getGenotype().getGenes()) + 1);
                maxNumber = Math.max(maxNumber, popularGenes.get(a.getGenotype().getGenes()) + 1);
            } else {
                popularGenes.put(a.getGenotype().getGenes(), 1);
            }
        }
        TreeMap<Integer, ArrayList<ArrayList<Integer>>> sortedPopularGenes = new TreeMap<>();

        for (Map.Entry<ArrayList<Integer>, Integer> a : popularGenes.entrySet()) {
            if (sortedPopularGenes.containsKey(-a.getValue())) {
                sortedPopularGenes.get(-a.getValue()).add(a.getKey());
            } else {
                sortedPopularGenes.put(-a.getValue(), new ArrayList<>(List.of(a.getKey())));
            }
        }

        ArrayList<ArrayList<Integer>> popularGenesList = new ArrayList<>();
        for (Map.Entry<Integer, ArrayList<ArrayList<Integer>>> s : sortedPopularGenes.entrySet()) {
            while (numberOfGenotypes > 0 && !s.getValue().isEmpty()) {
                numberOfGenotypes -= 1;
                popularGenesList.add(s.getValue().getLast());
                s.getValue().removeLast();
            }
        }
        return popularGenesList;
    }

    public static double averageAnimalsEnergyLevel(WorldMap map) {
        double avarageEnergy = 0;
        for (Animal a : map.getAnimalsSet()) {
            avarageEnergy += a.getEnergy();
        }
        return avarageEnergy / map.getAnimalsSet().size();
    }

    public static double averageDeadAnimalsAge(WorldMap map) {
        double avarageAge = 0;
        for (Animal a : map.getDeadAnimalsList()) {
            avarageAge += a.getAge();
        }
        return avarageAge / map.getDeadAnimalsList().size();
    }

    public static double averageChildrenAmount(WorldMap map) {
        double avarageChildrenAmount = 0;
        for (Animal a : map.getAnimalsSet()) {
            avarageChildrenAmount += a.getChildrenAmount();
        }
        return avarageChildrenAmount / map.getAnimalsSet().size();
    }

    public static ArrayList<String> getStatistics(WorldMap map, int numberOfGenotypes) {
        ArrayList<String> statistics = new ArrayList<>();
        statistics.add(Integer.toString(getAnimalsAmount(map)));
        statistics.add(Integer.toString(getPlantsAmount(map)));
        statistics.add(Integer.toString(getFreeTilesAmount(map)));

        ArrayList<ArrayList<Integer>> mostPopularGenotypes = getMostPopularGenotype(map, numberOfGenotypes);
        String statistic = "";
        assert mostPopularGenotypes != null;
        for (ArrayList<Integer> g : mostPopularGenotypes) {
            statistic = statistic + g.toString() + " ";
        }
        statistics.add(statistic);
        statistics.add(Double.toString(averageAnimalsEnergyLevel(map)));
        statistics.add(Double.toString(averageDeadAnimalsAge(map)));
        statistics.add(Double.toString(averageChildrenAmount(map)));

        return statistics;
    }
}
