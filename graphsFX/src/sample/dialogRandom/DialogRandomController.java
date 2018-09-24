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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
                    DecimalFormat decimalFormat = new DecimalFormat("#");

                    int pointCount = Integer.parseInt(pointsNumber.getText());
                    pointCount *= 2;
                    double minRandomValue = Double.parseDouble(minRandomNumber.getText());
                    double maxRandomValue = Double.parseDouble(maxRandomNumber.getText());

                    ArrayList<Double> arrayList = new ArrayList<>();

                    for(int i = 0; i < pointCount; i++){
                        arrayList.add(ThreadLocalRandom.current().nextDouble(minRandomValue, maxRandomValue));
                        System.out.print(decimalFormat.format(arrayList.get(i)) + " ");
                    }
                    System.out.println();

                    for(int i = 0; i < arrayList.size(); i++){
                        bufferedWriter.write("Point : " + "(" + decimalFormat.format(arrayList.get(i)) +
                                ", " + decimalFormat.format(arrayList.get(i + 1)) + "), ");
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
