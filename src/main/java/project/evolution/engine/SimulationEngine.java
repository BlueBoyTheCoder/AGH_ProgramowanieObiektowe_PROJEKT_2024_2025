package project.evolution.engine;


import project.evolution.simulation.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine{
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync(){
        for(Simulation simulation: simulations){
            simulation.run();
            }
        }


    public void runAsync(){
        for(Simulation simulation: simulations){
            Thread thread = new Thread(simulation);
            thread.start();
            threads.add(thread);
        }
        awaitSimulationsEnd();
    }

    public void awaitSimulationsEnd(){
        try{
            for (Thread thread: threads){
                thread.join();
            }
            threadPool.shutdown();
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
                System.out.println("Timeout!");
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void runAsyncInThreadPool(){
        for(Simulation simulation: simulations){
            threadPool.submit(simulation);
        }
        awaitSimulationsEnd();
    }
}
