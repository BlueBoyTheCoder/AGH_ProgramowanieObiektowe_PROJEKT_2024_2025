package project.evolution.Presenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.evolution.Statistics.Statistics;
import project.evolution.engine.SimulationEngine;
import project.evolution.entities.Animal;
import project.evolution.entities.WorldElementBox;
import project.evolution.entities.traits.Vector2d;
import project.evolution.map.DarwinWorld;
import project.evolution.map.WorldMap;
import project.evolution.simulation.Simulation;
import project.evolution.simulation.SimulationApp;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;
    private Simulation simulation;
    private int size=10;
    private int day=0;
    private Animal spiedAnimal=null;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    private final int maxMapSize = 400;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Button pauseFX;

    @FXML
    private Label genotypeFX;
    @FXML
    private Label genotypePartFX;
    @FXML
    private Label energyFX;
    @FXML
    private Label eatenPlantsAmountFX;
    @FXML
    private Label childrenAmountFX;
    @FXML
    private Label descendantsAmountFX;
    @FXML
    private Label ageFX;
    @FXML
    private Label deathAgeFX;
    @FXML
    private TextField xFX;
    @FXML
    private TextField yFX;

    @FXML
    private Label dayFX;
    @FXML
    private Label animalsAmountFX;
    @FXML
    private Label plantsAmountFX;
    @FXML
    private Label freeTilesAmountFX;
    @FXML
    private Label mostPopularGenotypeFX;
    @FXML
    private Label averageEnergyFX;
    @FXML
    private Label deadAnimalsAverageAgeFX;
    @FXML
    private Label averageChildrenAmountFX;


    @FXML
    private TextField startingAnimalAmountFX;
    @FXML
    private TextField startingAnimalEnergyFX;
    @FXML
    private TextField startingPlantAmountFX;
    @FXML
    private TextField widthFX;
    @FXML
    private TextField heightFX;
    @FXML
    private TextField energyForPlantFX;
    @FXML
    private TextField everydayPlantAmountFX;
    @FXML
    private TextField thrivingAnimaEnergyFX;
    @FXML
    private TextField breedingEnergyFX;
    @FXML
    private TextField minMutationsFX;
    @FXML
    private TextField maxMutationsFX;
    @FXML
    private TextField genotypeSizeFX;
    @FXML
    private TextField saveDataFX;

    @FXML
    private Label notificationFX;



    public void setWorldMap(WorldMap map) {
        this.map = map;
        this.maxX=map.getWidth()-1;
        this.maxY=map.getHeight()-1;
        this.minX=0;
        this.minY=0;
        this.size = Math.round((float) maxMapSize /(Math.max(maxX-minX+1,maxY-minY+1)+1f));
    }

    public void setGridAxes(){
        Label label = new Label("y/x");
        GridPane.setHalignment(label, HPos.CENTER);

        mapGrid.getColumnConstraints().add(new ColumnConstraints(Math.round((float) size)));
        mapGrid.getRowConstraints().add(new RowConstraints(Math.round((float) size)));
        mapGrid.add(label, 0, 0);

        for(int i=minX; i<maxX+1; i++){
            label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);

            mapGrid.getColumnConstraints().add(new ColumnConstraints(size));
            mapGrid.add(label, i-minX+1, 0);
        }

        for(int i=minY; i<maxY+1; i++){
            label = new Label(Integer.toString(i));
            GridPane.setHalignment(label, HPos.CENTER);

            mapGrid.getRowConstraints().add(new RowConstraints(size));
            mapGrid.add(label, 0, maxY-i+1);
        }
    }

    public void replaceElements(int option){
        ArrayList<Integer> mostPopularGenotype = Objects.requireNonNull(Statistics.getMostPopularGenotype(map, 1)).getFirst();
        Label label;
        for (int i=minX; i < maxX+1; i++){
            for (int j = minY; j < maxY+1; j++){
                Vector2d position = new Vector2d(i, j);
                Animal animal = map.animalAt(position);
                if (animal!=null){
                    label=new Label();
                    VBox vbox;
                    if(option==1 && map.hasAnimalWithGenotypeOnPosition(position,mostPopularGenotype)){
                        vbox = new WorldElementBox(animal,map.getBreedingEnergy(), animal.getEnergy(),0.8 * size, 1).getContainer();

                    }
                    else {
                        vbox = new WorldElementBox(animal, map.getBreedingEnergy(), animal.getEnergy(), 0.8 * size, 0).getContainer();
                    }
                    label.setGraphic(vbox);
                    mapGrid.add(label, i-minX+1, maxY-j+1);
                }
                else if (map.plantAt(position)!=null){
                    label=new Label();
                    VBox vbox = new WorldElementBox(map.plantAt(position),map.getBreedingEnergy(), -1,0.8 * size, option).getContainer();
                    label.setGraphic(vbox);
                    mapGrid.add(label, i-minX+1, maxY-j+1);
                }
                else{
                    label=new Label(" ");
                    mapGrid.add(label, i-minX+1, maxY-j+1);
                }
                GridPane.setHalignment(label, HPos.CENTER);

            }
        }
    }

    private void drawMap(int option) {
        setGridAxes();
        replaceElements(option);
        mapGrid.setGridLinesVisible(true);
    }

    @Override
    public void mapChanged(WorldMap map, ArrayList<String> notification){
        setWorldMap(map);
        Platform.runLater(() -> {
            clearGrid();
            drawMap(0);
            spyAnimalData();
            setStatistics(notification);
            dayFX.setText(Integer.toString(day));
            this.day+=1;
        });
    }

    public void setStatistics(ArrayList<String> statistics){
        animalsAmountFX.setText(statistics.get(0));
        plantsAmountFX.setText(statistics.get(1));
        freeTilesAmountFX.setText(statistics.get(2));
        mostPopularGenotypeFX.setText(statistics.get(3));
        averageEnergyFX.setText(statistics.get(4));
        deadAnimalsAverageAgeFX.setText(statistics.get(5));
        averageChildrenAmountFX.setText(statistics.get(6));
    }

    public void onSimulationStartClickedNew(String path,int startingAnimalAmount, int startingAnimalEnergy, int startingPlantAmount,int width, int height, int equatorHeight, int energyForPlant, int everydayPlantAmount, int thrivingAnimaEnergy, int breedingEnergy, int minMutations, int maxMutations, int genotypeSize) {
        map = new DarwinWorld(width, height, equatorHeight, energyForPlant, everydayPlantAmount, thrivingAnimaEnergy, breedingEnergy, minMutations, maxMutations, genotypeSize);

        map.registerObserver(this);
        this.simulation = new Simulation(startingAnimalAmount, startingAnimalEnergy, startingPlantAmount, map, path);
        if(path!=null) {
            simulation.setSaveToCsv(path);
        }
        SimulationEngine symEng = new SimulationEngine(List.of(simulation));
        Thread thread = new Thread(symEng::runAsyncInThreadPool);
        thread.start();

    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @FXML
    private void onSimulationStartClicked(ActionEvent actionEvent){
        try {
            int startingAnimalAmount = Integer.parseInt(startingAnimalAmountFX.getText());
            int startingAnimalEnergy = Integer.parseInt(startingAnimalEnergyFX.getText());
            int startingPlantAmount = Integer.parseInt(startingPlantAmountFX.getText());
            int width = Integer.parseInt(widthFX.getText());
            int height = Integer.parseInt(heightFX.getText());
            int energyForPlant = Integer.parseInt(energyForPlantFX.getText());
            int everydayPlantAmount = Integer.parseInt(everydayPlantAmountFX.getText());
            int thrivingAnimaEnergy = Integer.parseInt(thrivingAnimaEnergyFX.getText());
            int breedingEnergy = Integer.parseInt(breedingEnergyFX.getText());
            int minMutations = Integer.parseInt(minMutationsFX.getText());
            int maxMutations = Integer.parseInt(maxMutationsFX.getText());
            int genotypeSize = Integer.parseInt(genotypeSizeFX.getText());
            if (checkIntervalOfDate(startingAnimalAmount, startingAnimalEnergy, startingPlantAmount, width, height, energyForPlant, everydayPlantAmount, thrivingAnimaEnergy, breedingEnergy, minMutations, maxMutations, genotypeSize)) {
                SimulationApp simulationApp = new SimulationApp();
                try {
                    simulationApp.startNew(new Stage(), saveDataFX.getText(), startingAnimalAmount, startingAnimalEnergy, startingPlantAmount, width, height, (int) Math.ceil(0.2 * height), energyForPlant, everydayPlantAmount, thrivingAnimaEnergy, breedingEnergy, minMutations, maxMutations, genotypeSize);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e){
            notificationFX.setText("String occured");
            e.printStackTrace();
        }
    }

    private boolean checkIntervalOfDate(   int startingAnimalAmount,
                                        int startingAnimalEnergy,
                                        int startingPlantAmount,
                                        int width,
                                        int height,
                                        int energyForPlant,
                                        int everydayPlantAmount,
                                        int thrivingAnimaEnergy,
                                        int breedingEnergy,
                                        int minMutations,
                                        int maxMutations,
                                        int genotypeSize){
        if( startingAnimalAmount < 1 || startingAnimalAmount > 50){
            notificationFX.setText("change Starting animal amount value");
            return false;
        }
        else if( startingAnimalEnergy < 1 || startingAnimalEnergy > 100000){
            notificationFX.setText("change Starting animal energy value");
            return false;
        }
        else if( startingPlantAmount < 1 || startingPlantAmount > 50){
            notificationFX.setText("change Starting plant amount value");
            return false;
        }
        else if( width < 1 || width > 25){
            notificationFX.setText("change Map width value");
            return false;
        }
        else if( height < 1 || height > 25){
            notificationFX.setText("change Map height value");
            return false;
        }
        else if( energyForPlant < 1 || energyForPlant > 100000){
            notificationFX.setText("change Consumption energy value");
            return false;
        }
        else if( everydayPlantAmount < 1 || everydayPlantAmount > 50){
            notificationFX.setText("change Plants growth rate value");
            return false;
        }
        else if( thrivingAnimaEnergy < 1 || thrivingAnimaEnergy > 100000){
            notificationFX.setText("change Thriving animal energy value");
            return false;
        }
        else if( breedingEnergy < 1 || breedingEnergy > 100000){
            notificationFX.setText("change Energy spent for breeding value");
            return false;
        }
        else if( minMutations < 0 || minMutations > 10){
            notificationFX.setText("change Minimal mutations amount value");
            return false;
        }
        else if( maxMutations < 0 || maxMutations > 10){
            notificationFX.setText("change Maximal mutations amount value");
            return false;
        }
        else if( genotypeSize < 1 || genotypeSize > 10){
            notificationFX.setText("change Genotype size value");
            return false;
        }
        return true;

    }

    @FXML
    private void onSimulationPauseClicked(ActionEvent actionEvent){
        simulation.setPause();
        if(pauseFX.getText().equals("Start")){
            pauseFX.setText("Pause");
        }
        else{
            pauseFX.setText("Start");
        }
    }

    @FXML
    private void onSimulationEnhancedAnimalsClicked(ActionEvent actionEvent){
        Platform.runLater(() -> {
            clearGrid();
            drawMap(1);
        });
    }

    private void spyAnimalData(){
        if(spiedAnimal!=null) {
            genotypeFX.setText(spiedAnimal.getGenotype().getGenes().toString());
            genotypePartFX.setText(Integer.toString(spiedAnimal.getGenotype().getCurrentGene()));
            energyFX.setText(Integer.toString(spiedAnimal.getEnergy()));
            eatenPlantsAmountFX.setText(Integer.toString(spiedAnimal.getEatenPlants()));
            childrenAmountFX.setText(Integer.toString(spiedAnimal.getChildrenAmount()));
            descendantsAmountFX.setText(Integer.toString(spiedAnimal.getDescendantsAmount()));
            if (spiedAnimal.getEnergy() == 0) {
                ageFX.setText("dead");
                deathAgeFX.setText(Integer.toString(spiedAnimal.getAge()));
            } else {
                ageFX.setText(Integer.toString(spiedAnimal.getAge()));
                deathAgeFX.setText("not dead");
            }
        }
    }

    @FXML
    private void onSimulationChooseClicked(ActionEvent actionEvent){
        int x = Integer.parseInt(xFX.getText());
        int y = Integer.parseInt(yFX.getText());
        if(0 <= x && x < map.getWidth() && 0 <= y && y < map.getHeight()){
            spiedAnimal = map.animalAt(new Vector2d(x,y));
            spyAnimalData();
        }
    }



}
