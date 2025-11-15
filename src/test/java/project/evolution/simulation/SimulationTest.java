package project.evolution.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.evolution.map.World;
import project.evolution.map.WorldMap;
import java.lang.reflect.Field;

class SimulationTest {
    private WorldMap map;
    private Simulation simulation;
    private String testPath;

    @BeforeEach
    void setUp() {
        int width = 10;
        int height = 10;
        int energyForPlant = 10;
        int everydayPlantAmount = 2;
        int thrivingAnimalEnergy = 50;
        int breedingEnergy = 20;
        int minMutations = 1;
        int maxMutations = 3;
        int genotypeSize = 8;

        map = new World(width, height, energyForPlant, everydayPlantAmount, thrivingAnimalEnergy, breedingEnergy, minMutations, maxMutations, genotypeSize);
        testPath = "";
        simulation = new Simulation(5, 100, 10, map, testPath);
    }

    @Test
    void testPauseToggle() throws NoSuchFieldException, IllegalAccessException {
        Field pauseField = Simulation.class.getDeclaredField("pause");
        pauseField.setAccessible(true);

        assert !pauseField.getBoolean(simulation);
        simulation.setPause();
        assert pauseField.getBoolean(simulation);
        simulation.setPause();
        assert !pauseField.getBoolean(simulation);
    }

    @Test
    void testRunMethod() throws InterruptedException {
        Thread simulationThread = new Thread(simulation);
        simulationThread.start();
        simulation.setPause();
        simulationThread.interrupt();

        assert true;
    }
}