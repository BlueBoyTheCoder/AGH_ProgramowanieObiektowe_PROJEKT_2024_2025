package org.example;

import javafx.application.Application;
import project.evolution.entities.traits.Genotype;
import project.evolution.simulation.SimulationApp;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try {
            Application.launch(SimulationApp.class, args);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

//        Genotype gen = new Genotype(4);
//        System.out.println(gen.getGenes().toString());
//        System.out.println(gen.getCurrentGene());
//        gen.mutate(2,2);
//        System.out.println(gen.getGenes().toString());
//        System.out.println(gen.getCurrentGene());
//        gen.use();
//        System.out.println(gen.getGenes().toString());
//        System.out.println(gen.getCurrentGene());
//        gen.use();
//        System.out.println(gen.getGenes().toString());
//        System.out.println(gen.getCurrentGene());
//        gen.use();
//        System.out.println(gen.getGenes().toString());
//        System.out.println(gen.getCurrentGene());
//        gen.use();
//        System.out.println(gen.getGenes().toString());
//        System.out.println(gen.getCurrentGene());

    }
}