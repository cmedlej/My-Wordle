import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import java.util.Random;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.Node;
/**
 * @author cmedlej3 GTID:903733083
 * @version 1
 */

public class Jordle extends Application {
    private int column = 0;
    private int row = 0;
    private String guess = "";
    private String word;
    private boolean won = false;
    private String instructions = "Hey you! Jordle is an interactive game in which\n "
        + "you have to find a 5 letter word in 6 guesses.\n"
        + "The game is color coded, so if you're color blind, I apologize.\n"
        + "The box will turn green if you found the right letter at the right position.\n"
        + "It will turn yellow if the letter is in the word\nbut at a different "
        + "position and it will "
        + "turn grey if the letter is not in the word at all.\nGood Luck!";

    //Creates random object
    private Random rand = new Random();
    /**
    * Gets nodes based on coordinates in grid.
    * @param grid the grid
    * @param row the row
    * @param column the column
    * @return the Label at the position
    */
    public static Label getLabel(GridPane grid, Integer row, Integer column) {
        Label result = null;
        for (Node label: grid.getChildren()) {
            if (GridPane.getColumnIndex(label) == column && GridPane.getRowIndex(label) == row) {
                result = (Label) label;
            }
        }
        return result;
    }
    /**
    * Createa the empty panel.
    * @param grid the grid
    */
    public void initialize(GridPane grid) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                grid.add(createLabel("", Color.WHITE), i, j);

            }
        }
    }
    /**
    * Creates the list of colors.
    * @param word the word to guess
    * @param guess the current guess
    * @return array of colors
    */
    public static Color[] findCommons(String word, String guess) {
        String common = "";
        String gletter;
        String wletter;
        Color[] result = new Color[5];
        for (int i = 0; i < word.length(); i++) {
            String letter = guess.substring(i, i + 1);
            if (word.contains(letter)) {
                common += letter;
            }
        }
        for (int i = 0; i < word.length(); i++) {
            gletter = guess.substring(i, i + 1);
            wletter = word.substring(i, i + 1);
            if (wletter.equals(gletter)) {
                result[i] = Color.GREEN;
                common = common.replace(gletter, "");
            }
        }
        for (int i = 0; i < word.length(); i++) {
            gletter = guess.substring(i, i + 1);
            wletter = word.substring(i, i + 1);
            if (result[i] == null) {
                if (common.contains(gletter)) {
                    result[i] = Color.YELLOW;
                    common = common.replace(gletter, "");
                } else {
                    result[i] = Color.GREY;
                }
            }
        }
        return result;
    }


    /**
    * Creates Labels.
    * @param text the wanted text in label
    * @param color the wanted bg color
    * @return the Label at the position
    */
    public static Label createLabel(String text, Color color) {
        Label box = new Label(text);
        box.setFont(new Font("Arial", 30));
        box.setPrefWidth(70);
        box.setPrefHeight(70);
        box.setAlignment(Pos.CENTER);
        box.setBackground(new Background(new BackgroundFill(color, new CornerRadii(0), new Insets(5.0))));
        return box;
    }

    /**
    * Changes label color.
    * @param label the label
    * @param color the wanted bg color
    */
    public static void changeColor(Label label, Color color) {
        label.setBackground(new Background(new BackgroundFill(color, new CornerRadii(0), new Insets(5.0))));
    }

    /**
    * Changes label text.
    * @param label the label
    * @param text the wanted text
    */
    public static void changeText(Label label, String text) {
        label.setText(text);
    }
    /**
    * Creates a new window.
    * @param instructions the game intructions
    * @param title the title
    */
    public static void newWindow(String title, String instructions) {
        Label instructionLabel = new Label(instructions);
        StackPane instrLayout = new StackPane();
        instrLayout.getChildren().add(instructionLabel);
        Scene instrScene = new Scene(instrLayout, 200, 100);
        Stage instrWindow = new Stage();
        instrWindow.setMinWidth(500);
        instrWindow.setMinHeight(500);
        instrWindow.setTitle(title);
        instrWindow.setScene(instrScene);
        instrWindow.show();
    }

    /**
    * Starts the Program.
    * @param theStage the stage
    */
    public void start(Stage theStage) {
        word = Words.list.get(rand.nextInt(Words.list.size())).toUpperCase();
        //Title
        theStage.setTitle("Jordle");
        //Width and Height
        theStage.setMinWidth(600);
        theStage.setMinHeight(770);
        //BorderPane
        BorderPane bord = new BorderPane();
        bord.setPadding(new Insets(20, 20, 20, 20));
        //Top

        //Create title
        Text title = new Text("JORDLE");
        title.setFont(Font.font("Impact", FontWeight.NORMAL, 50));
        title.setFill(Color.PERU);
        //Set the title to the top
        bord.setTop(title);
        bord.setAlignment(title, Pos.CENTER);

        //Center

        //Create Grid Pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Set center
        bord.setCenter(grid);
        bord.setAlignment(grid, Pos.CENTER);

        //Bottom

        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(3.0);
        bottom.setPadding(new Insets(40, 40, 40, 40));
        bord.setBottom(bottom);
        bord.setAlignment(bottom, Pos.CENTER);

        //Message
        Label btext = new Label("Try guessing a word!");

        //Give Up  Button
        Button gubtn = new Button();
        gubtn.setText("Give Up");
        gubtn.setPadding(new Insets(5, 5, 5, 5));

        gubtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newWindow("Give Up", "It's okay to give up sometimes! the word was " + word);
                column = 0;
                row = 0;
                guess = "";
                won = false;
                btext.setText("Try guessing a word!");
                initialize(grid);
                word = Words.list.get(rand.nextInt(Words.list.size())).toUpperCase();
            }
        });


        //Instruction Button
        Button instrbtn = new Button();
        instrbtn.setText("Instructions");
        instrbtn.setPadding(new Insets(5, 5, 5, 5));
        //lambda expression
        instrbtn.setOnAction(e -> newWindow("Instructions", instructions));

        //Restart Button
        Button rbtn = new Button();
        rbtn.setText("Restart");
        rbtn.setPadding(new Insets(5, 5, 5, 5));
        //anonymous inner class

        rbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                column = 0;
                row = 0;
                guess = "";
                won = false;
                btext.setText("Try guessing a word!");
                initialize(grid);
                word = Words.list.get(rand.nextInt(Words.list.size())).toUpperCase();
            }
        });


        //Add elements
        bottom.getChildren().addAll(btext, instrbtn, rbtn, gubtn);

        //Set Scene
        Scene scene = new Scene(bord, 300, 275);
        theStage.setScene(scene);
        theStage.show();

        //Create Alert
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Not enough Letters");

        //Initial Set up
        initialize(grid);

        //User input
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!won) {
                    if (column >= 1 && event.getCode() == KeyCode.BACK_SPACE) {
                        changeText(getLabel(grid, row, column - 1), "");
                        guess = guess.substring(0, guess.length() - 1);
                        column -= 1;
                    } else if (event.getText().length() > 0 && Character.isLetter(event.getText().charAt(0))) {
                        if (column >= 0 && column < 5) {
                            changeText(getLabel(grid, row, column), event.getText().toUpperCase());
                            guess += event.getText().toUpperCase();
                            column += 1;
                        }
                    } else if (event.getCode() == KeyCode.ENTER) {
                        if (guess.length() < 5) {
                            alert.show();
                        } else {
                            if (guess.equals(word)) {
                                for (int i = 0; i <= 4; i++) {
                                    changeColor(getLabel(grid, row, i), Color.GREEN);
                                    changeText(btext, "YOU WON!");
                                    won = true;
                                }
                            } else {
                                Color[] current = findCommons(word, guess);
                                for (int i = 0; i < current.length; i++) {
                                    changeColor(getLabel(grid, row, i), current[i]);
                                }
                            }
                            guess = "";
                            column = 0;
                            row += 1;
                            if (row == 6 && !won) {
                                changeText(btext, "You Lost! The word was " + word.toLowerCase());
                            }
                        }
                    }
                }
            }

        });
    }
    /**
    * Driver for Program.
    * @param args the args
    */
    public static void main(String[] args) {
        launch(args);
    }
}
