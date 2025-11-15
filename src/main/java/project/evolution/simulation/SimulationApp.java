package project.evolution.simulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.evolution.Presenter.SimulationPresenter;

import java.io.IOException;

public class SimulationApp extends Application {



    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        configureStage(primaryStage, viewRoot);

        primaryStage.show();
    }

    public void startNew(Stage primaryStage, String path, int startingAnimalAmount, int startingAnimalEnergy, int startingPlantAmount,int width, int height, int equatorHeight, int energyForPlant, int everydayPlantAmount, int thrivingAnimaEnergy, int breedingEnergy, int minMutations, int maxMutations, int genotypeSize) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulationNew.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        configureStage(primaryStage, viewRoot);

        presenter.onSimulationStartClickedNew(path,startingAnimalAmount, startingAnimalEnergy, startingPlantAmount, width, height,equatorHeight, energyForPlant, everydayPlantAmount, thrivingAnimaEnergy, breedingEnergy, minMutations, maxMutations, genotypeSize);

        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
