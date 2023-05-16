package happy.test.javafxte;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.EventType;
import java.lang.Thread;
import java.lang.RuntimeException;
import java.lang.InterruptedException;
import java.lang.IndexOutOfBoundsException;
import java.io.IOException;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {

    private VBox root;
    @FXML
    private Label welcomeText;
    private Button click;
    private GridPane pane;
    private Rectangle[][] recs;

    private Rectangle head;
    private Rectangle tail;
    private Rectangle seed;

    //Variables
    private static Stack<Rectangle> body = new Stack<>();
    private final static int ROWS = 25;
    private final static int COLS = 35;
    private static int[] headA = new int[]{ROWS / 2, COLS / 2};
    private static int[] tailA = new int[]{ROWS / 2, COLS / 2};

    private boolean going = false;
    private boolean goingUp = false;
    private boolean goingDown = false;
    private boolean goingRight = false;
    private boolean goingLeft = false;

    private static int moved = 0;
    private static int size = 1;


    public HelloApplication() {
        root = new VBox(20);
        welcomeText = new Label();
        click = new Button("Hello!");
        pane = new GridPane();
        recs = new Rectangle[ROWS][COLS];

    }
    @Override
    public void start(Stage stage) throws IOException {
        root.getChildren().addAll(pane);
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(e -> {
            if (!going) {
                runInNewThread(() -> go(), "");
            }
            if (e.getCode() == KeyCode.UP) {
                going = true;
                if (!goingUp && !goingDown) {
                    goingUp = true;
                    goingRight = false;
                    goingLeft = false;
                }
            }
            if (e.getCode() == KeyCode.DOWN) {
                going = true;
                if (!goingDown && !goingUp) {
                    goingDown = true;
                    goingRight = false;
                    goingLeft = false;
                }
            }
            if (e.getCode() == KeyCode.RIGHT) {
                going = true;
                if (!goingRight && !goingLeft) {
                    goingRight = true;
                    goingUp = false;
                    goingDown = false;
                }
            }
            if (e.getCode() == KeyCode.LEFT) {
                going = true;
                if (!goingLeft && !goingRight) {
                    goingLeft = true;
                    goingUp = false;
                    goingDown = false;
                }
            }
        });
    }

    public void init() {
        pane.setGridLinesVisible(true);
        pane.setHgap(0);
        pane.setVgap(0);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                recs[i][j] = new Rectangle(21, 21, Color.BLACK);
                pane.add(recs[i][j], j, i);
            }
        }
        recs[ROWS / 2][COLS / 2].setFill(Color.LIME);
        head = recs[ROWS / 2][COLS / 2];
        tail = recs[ROWS / 2][COLS / 2];
        body.push(head);
        seed = recs[5][7];
        seed.setFill((Color.RED));


        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));

    }

    public static void main(String[] args) {
        //System.out.println(tail[0]);
        launch();
    }

    public void go() {
        try {
            while (going) {
                body.elementAt(body.size() - 1).setFill(Color.BLACK);
                body.remove(body.size() - 1);
                if (goingUp) {
                    head = recs[pane.getRowIndex(head) - 1][pane.getColumnIndex(head)];
                }
                if (goingDown) {
                    head = recs[pane.getRowIndex(head) + 1][pane.getColumnIndex(head)];
                }
                if (goingRight) {
                    head = recs[pane.getRowIndex(head)][pane.getColumnIndex(head) + 1];
                }
                if (goingLeft) {
                    head = recs[pane.getRowIndex(head)][pane.getColumnIndex(head) - 1];
                }
                body.push(head);
                for (Rectangle item : body) {
                    item.setFill(Color.LIME);
                }
                Thread.sleep(120);

            }
        } catch (IndexOutOfBoundsException | InterruptedException e) {
            going = false;
            System.out.println("ERROR");
            if (goingUp) {
                goDown();
            }
            if (goingDown) {
                goUp();
            }
            if (goingRight) {
                goLeft();
            }
            if (goingLeft) {
                goRight();
            }
            head.setFill(Color.LIME);
        }

    }

    private void goUp() {
        headA[0]--;
        head = recs[headA[0]][headA[1]];
    }
    private void goDown() {
        headA[0]++;
        head = recs[headA[0]][headA[1]];

    }
    private void goRight() {
        headA[1]++;
        head = recs[headA[0]][headA[1]];
    }
    private void goLeft() {
        headA[1]--;
        head = recs[headA[0]][headA[1]];
    }

    public static void runInNewThread(Runnable task, String name) {
        Thread t = new Thread(task, name);
        t.setDaemon(true);
        t.start();
    } // runInNewThread

}