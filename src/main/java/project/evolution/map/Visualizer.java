package project.evolution.map;

import project.evolution.entities.Animal;
import project.evolution.entities.Plant;
import project.evolution.entities.traits.Vector2d;

import java.util.*;

public class Visualizer {


    public static void visualizer(int heigth, int width, HashSet<Animal> animals, Map<Vector2d,Plant> plants){
        Set<Vector2d> animalTile = new HashSet<>();
        Set<Vector2d> plantTile = new HashSet<>();

        for (Animal a : animals) {
            animalTile.add(a.getPosition());
        }

        for (Map.Entry<Vector2d,Plant> p : plants.entrySet()) {
            plantTile.add(p.getKey());
        }

        System.out.print("y\\x");
        for(int i=0; i<width; i++){
            System.out.print(" "+i+" ");
        }
        System.out.print("\n");
        for(int i=heigth-1; i>=0; i--){
            System.out.print(" "+i+" ");
            for (int j=0; j<width; j++){
                if(animalTile.contains(new Vector2d(j,i))){
                    System.out.print(" A ");
                }
                else if(plantTile.contains(new Vector2d(j,i))){
                    System.out.print(" P ");
                }
                else {
                    System.out.print(" . ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public static void visualizer(int heigth, int width, HashSet<Animal> animals, Map<Vector2d,Plant> plants, int equatorBottom, int equatorTop){
        Set<Vector2d> animalTile = new HashSet<>();
        Set<Vector2d> plantTile = new HashSet<>();

        for (Animal a : animals) {
            animalTile.add(a.getPosition());
        }

        for (Map.Entry<Vector2d,Plant> p : plants.entrySet()) {
            plantTile.add(p.getKey());
        }

        System.out.print("y\\x");
        for(int i=0; i<width; i++){
            System.out.print(" "+i%10+" ");
        }
        System.out.print("\n");
        for(int i=heigth-1; i>=0; i--){
            if(i<10){
                System.out.print(" ");
            }
            System.out.print(i+" ");
            for (int j=0; j<width; j++){
                if(animalTile.contains(new Vector2d(j,i))){
                    System.out.print("\u001B[33m" +" A "+"\u001b[0m");
                }
                else if(plantTile.contains(new Vector2d(j,i))){
                    System.out.print("\u001b[38;5;155m"+" P "+"\u001b[0m");
                }
                else if(equatorBottom<=i && i<equatorTop){
                    System.out.print("\u001B[32m"+" * "+"\u001b[0m");
                }
                else {
                    System.out.print(" . ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
