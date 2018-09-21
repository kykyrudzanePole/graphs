package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.dialogRandom.DialogRandomController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    File file;

    public static double start_radius = 0;
    public static ArrayList<Point> arrayList = new ArrayList<>();
    public static double pointX;
    public static double pointY;
    public static DecimalFormat decimalFormat = new DecimalFormat("#.#");


    @FXML
    private Button clear;

    @FXML
    private Button findRadius;

    @FXML
    private Button show;

    @FXML
    private Text radius;

    @FXML
    private Pane pane;

    @FXML
    private Button findAdress;

    @FXML
    private TextField fileAdress;

    @FXML
    private Button randomButton;

    @FXML
    void initialize(){
        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Color color = Color.WHITE;
                for(int i = 0; i < arrayList.size(); i++){
                    if(arrayList.get(i).frequency == "black"){
                        color = Color.BLACK;
                    }
                    if(arrayList.get(i).frequency == "white"){
                        color = Color.WHITE;
                    }
                    pane.getChildren().add(new Circle(arrayList.get(i).pointX, arrayList.get(i).pointY, start_radius, color));
                }
            }
        });

        findAdress.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Window stage = null;
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                file = fileChooser.showOpenDialog(stage);
                String path = file.toString();
                System.out.println(path);
                fileAdress.setText(path);
            }
        });

        randomButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/dialogRandom/dialogRandom.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Random Points");
                    stage.setScene(new Scene(root));
                    stage.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
                fileAdress.setText("/Users/Couple/Documents/GitHub/graphs/graphsFX/RandomFile.txt");
            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileAdress.setText(" ");
                file = null;
                start_radius = 0;
                pane.getChildren().clear();
                radius.setText(" ");
                arrayList.clear();
            }
        });

        findRadius.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    handleFile();
                    decimalFormat.format(start_radius);
                    radius.setText(String.valueOf(decimalFormat.format(start_radius)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void handleFile() throws IOException{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileAdress.getText())));

            String data = bufferedReader.readLine();

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher1 = pattern.matcher(data);
            Matcher matcher2 = pattern.matcher(data);

            int count2 = 0;
            int count1 = 0;
            int mas_length = 0;

            while(matcher1.find()){
                if(count1 == 0){
                    count1++;
                }else{
                    count1 = 0;
                    mas_length++;
                }
            }

            while(matcher2.find()){
                if(count2 == 0){
                    pointX = Double.parseDouble(matcher2.group());
                    count2++;
                }else {
                    if(count2 == 1){
                        pointY = Double.parseDouble(matcher2.group());
                        count2 = 0;
                        arrayList.add(new Point(pointX, pointY, "black", new double[mas_length], true));
                    }
                }
            }

            bufferedReader.close();

            for(int i = 0; i < arrayList.size(); i++){
                lengthCounter(arrayList.get(i));
            }

            start_radius /= 2;
            System.out.println(decimalFormat.format(start_radius));

            for(int i = 0; i < arrayList.size(); i++) {
                mainLogic(arrayList.get(i));
            }

            for(int i = 0; i < arrayList.size(); i++){
                System.out.print(arrayList.get(i).frequency + " ");
            }
        }

    public static void mainLogic(Point point){
        try {
            ArrayList<Integer> integerArrayList = new ArrayList<>();

            boolean flag = true;
            while (flag) {
                integerArrayList.clear();
                for (int i = 0; i < point.points_length.length; i++) {
                    if (point.points_length[i] != 0 && start_radius > point.points_length[i] / 2 && point.frequency == arrayList.get(i).frequency) {
                        integerArrayList.add(i);
                    }
                }
                integerArrayList.add(integerArrayList.get(0));
                System.out.print("in while circle : ");
                for (int i = 0; i < integerArrayList.size(); i++) {
                    System.out.print(integerArrayList.get(i) + " ");
                }
                System.out.println();
                if (checkCrossing(point, integerArrayList)) {
                    flag = false;
                    System.out.println("true change : " + start_radius);
                }
            }
        }catch (IndexOutOfBoundsException error){
            System.out.println("empty collection");
        }
    }


    public static boolean checkCrossing(Point point, ArrayList<Integer> integerArrayList) {

        double maxLength = Double.NEGATIVE_INFINITY;

        if(integerArrayList.isEmpty()){
            System.out.println("empty in second circle");
        }

        System.out.print("in checkCrossing method " );
        for(int i = 0; i < integerArrayList.size(); i++){
            System.out.print(integerArrayList.get(i) + " ");
        }
        System.out.println();
        boolean boolFlag = true;

        for(int i = 0; i < integerArrayList.size() - 1; i++) {
            if(integerArrayList.size() == 2){
                break;
            }
            if(start_radius > arrayList.get(integerArrayList.get(i)).points_length[integerArrayList.get(i + 1)] / 2) {
                System.out.println("work check 1");
                if(arrayList.get(integerArrayList.get(i)).points_length[integerArrayList.get(i + 1)] >= point.points_length[integerArrayList.get(i)]) {
                    System.out.println("work check 2.1");
                    if(maxLength < arrayList.get(integerArrayList.get(i)).points_length[integerArrayList.get(i + 1)] / 2){
                        System.out.println("work check 3.1");
                        maxLength = arrayList.get(integerArrayList.get(i)).points_length[integerArrayList.get(i + 1)] / 2;
                    }
                } else {
                    System.out.println("work check 2.2");
                    if(maxLength < point.points_length[integerArrayList.get(i)] / 2){
                        System.out.println("work check 3.2");
                        maxLength = point.points_length[integerArrayList.get(i)] / 2;
                    }
                }
                boolFlag = false;
            }
        }
        if(boolFlag){
            point.checkFrequency = false;
            for(int i = 0; i < integerArrayList.size() - 1; i++){
                if(!arrayList.get(integerArrayList.get(i)).checkFrequency) {
                    double temp = start_radius - point.points_length[integerArrayList.get(i)] / 2;
                    start_radius -= temp;
                }else {
                    if (point.frequency == "black") {
                        arrayList.get(integerArrayList.get(i)).frequency = "white";
                        arrayList.get(integerArrayList.get(i)).checkFrequency = false;
                        System.out.println(arrayList.get(integerArrayList.get(i)).frequency + " " + integerArrayList.get(i));
                    } else {
                        arrayList.get(integerArrayList.get(i)).frequency = "black";
                        arrayList.get(integerArrayList.get(i)).checkFrequency = false;
                        System.out.println(arrayList.get(integerArrayList.get(i)).frequency + " " + integerArrayList.get(i));
                    }
                }
            }
        }else{
            start_radius = start_radius - (start_radius - maxLength);
            System.out.println("radius in while : " + start_radius);
        }
        return boolFlag;
    }


    public static void lengthCounter(Point point){

        for(int i = 0; i < point.points_length.length; i++){
            point.points_length[i] = Math.sqrt(Math.pow(arrayList.get(i).pointX - point.pointX,2) + Math.pow(arrayList.get(i).pointY - point.pointY,2));
        }

        for(int i = 0; i < point.points_length.length; i++){
            System.out.print( decimalFormat.format(point.points_length[i]) + "   ");
            if(start_radius < point.points_length[i]){
                start_radius = point.points_length[i];
            }
        }
        System.out.println();
    }
}

