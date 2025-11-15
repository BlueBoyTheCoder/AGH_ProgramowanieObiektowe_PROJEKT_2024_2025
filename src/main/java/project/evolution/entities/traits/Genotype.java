package project.evolution.entities.traits;
import java.util.Random;
import java.util.ArrayList;


public class Genotype {
    private final ArrayList<Integer> genes;
    private int currentGene;

    public Genotype(int currentGene, ArrayList<Integer> genes) {
        this.currentGene = currentGene % genes.size();
        this.genes = genes;
    }

    public Genotype(ArrayList<Integer> genes) {
        this.currentGene = 0;
        this.genes = genes;
    }

    public Genotype(int genotypeSize) {
        this.currentGene = 0;
        this.genes = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < genotypeSize; i++) {
            genes.add(rand.nextInt(8));
        }
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public int getCurrentGene() {
        return currentGene;
    }

    public int use() {
        int currentGeneSaved = currentGene;
        this.currentGene = (this.currentGene + 1) % genes.size();
        return genes.get(currentGeneSaved);
    }

    public Genotype getChildGenotype(Genotype genotype, double genesProportion){
        int genesAmount = (int) Math.ceil(genesProportion*this.genes.size());
        Random rand = new Random();
        ArrayList<Integer> newGenotype=new ArrayList<>();
        if(rand.nextBoolean()){
            newGenotype.addAll(this.genes.subList(0,genesAmount));
            newGenotype.addAll(genotype.genes.subList(genesAmount,this.genes.size()));
        }
        else{
            newGenotype.addAll(genotype.genes.subList(0,this.genes.size()-genesAmount));
            newGenotype.addAll(this.genes.subList(this.genes.size()-genesAmount,this.genes.size()));
        }
        return new Genotype(newGenotype);
    }

    public void mutate(int minimalMutationsAmount, int maximalMutationsAmount){
        if(minimalMutationsAmount<=maximalMutationsAmount && maximalMutationsAmount<=this.genes.size()){
            Random rand = new Random();
            int number0fGenes = rand.nextInt(maximalMutationsAmount-minimalMutationsAmount+1)+minimalMutationsAmount;
            for(int i=0; i<this.genes.size(); i++){
                if(rand.nextInt(genes.size()-i)<number0fGenes){
                    this.genes.set(i,rand.nextInt(8));
                    number0fGenes-=1;
                }
            }
        }
    }

}
