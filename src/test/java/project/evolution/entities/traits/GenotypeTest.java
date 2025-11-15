package project.evolution.entities.traits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenotypeTest {

    private Genotype genotype;
    private ArrayList<Integer> testGenes;

    @BeforeEach
    void setUp() {
        testGenes = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7));
        genotype = new Genotype(testGenes);
    }

    @Test
    void testConstructorWithGeneList() {
        assertEquals(testGenes, genotype.getGenes());
        assertEquals(0, genotype.getCurrentGene());
    }

    @Test
    void testConstructorWithSize() {
        int genotypeSize = 10;
        Genotype randomGenotype = new Genotype(genotypeSize);
        assertEquals(genotypeSize, randomGenotype.getGenes().size());
        for (int gene : randomGenotype.getGenes()) {
            assertTrue(gene >= 0 && gene < 8);
        }
    }

    @Test
    void testGetCurrentGene() {
        assertEquals(0, genotype.getCurrentGene());
    }

    @Test
    void testUse() {
        assertEquals(0, genotype.use());
        assertEquals(1, genotype.getCurrentGene());
        assertEquals(1, genotype.use());
        assertEquals(2, genotype.getCurrentGene());
    }

    @Test
    void testUseWrapAround() {
        for (int i = 0; i < testGenes.size(); i++) {
            genotype.use();
        }
        assertEquals(0, genotype.getCurrentGene());
    }

    @Test
    void testGetChildGenotype() {
        ArrayList<Integer> parentGenes = new ArrayList<>(List.of(7, 6, 5, 4, 3, 2, 1, 0));
        Genotype parentGenotype = new Genotype(parentGenes);
        double proportion = 0.5;
        Genotype childGenotype = genotype.getChildGenotype(parentGenotype, proportion);

        assertEquals(testGenes.size(), childGenotype.getGenes().size());
    }

    @Test
    void testMutateWithinRange() {
        ArrayList<Integer> originalGenes = new ArrayList<>(genotype.getGenes());
        genotype.mutate(2, 4);

        int mutations = 0;
        for (int i = 0; i < originalGenes.size(); i++) {
            if (!originalGenes.get(i).equals(genotype.getGenes().get(i))) {
                mutations++;
            }
        }
        assertTrue(mutations >= 2 && mutations <= 4);
    }

    @Test
    void testMutateEdgeCases() {
        genotype.mutate(0, 0);
        assertEquals(testGenes, genotype.getGenes());
    }
}
