package sample.dialogRandom;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DialogRandomController{

    @FXML
    private TextField pointsNumber;

    @FXML
    private TextField minRandomNumber;

    @FXML
    private TextField maxRandomNumber;

    @FXML
    private Button okButton;

    @FXML
    void initialize(){
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Start Random Class");
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("/Users/Couple/Documents/GitHub/graphs/graphsFX/RandomFile.txt")));
                    Random random = new Random();

                    int pointCount = Integer.parseInt(pointsNumber.getText());
                    pointCount *= 2;
                    int minRandomValue = Integer.parseInt(minRandomNumber.getText());
                    int maxRandomValue = Integer.parseInt(maxRandomNumber.getText());

                    ArrayList<Integer> arrayList = new ArrayList<>();

                    for(int i = 0; i < pointCount; i++){
                        arrayList.add(random.nextInt(maxRandomValue + 1 - minRandomValue) + minRandomValue);
                        System.out.print(arrayList.get(i) + " ");
                    }
                    System.out.println();

                    for(int i = 0; i < arrayList.size() - 1; i++){
                        bufferedWriter.write("A" + i +"(" + arrayList.get(i) + ", " + arrayList.get(i + 1) + "), ");
                        i++;
                    }

                    bufferedWriter.flush();
                    bufferedWriter.close();

                }catch (IOException e){
                    e.printStackTrace();
                }
                okButton.getScene().getWindow().hide();
            }
        });
    }
}
