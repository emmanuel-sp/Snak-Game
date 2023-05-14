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
    private final static int ROWS = 19;
    private final static int COLS = 19;
    private static int[] headA = new int[]{ROWS / 2, COLS / 2};
    private static int[] tailA = new int[]{ROWS / 2, COLS / 2};



    private boolean goingUp = false;
    private boolean goingDown = false;
    private boolean goingRight = false;
    private boolean goingLeft = false;

    private boolean wentLeft = false;
    private boolean wentRight = false;
    private boolean wentUp = false;
    private boolean wentDown = false;

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
            if (e.getCode() == KeyCode.UP) {
                if (!goingUp && !goingDown) {
                    runInNewThread(() -> this.goUp(), "Up");
                }
            }
            if (e.getCode() == KeyCode.DOWN) {
                if (!goingDown && !goingUp) {
                    runInNewThread(() -> this.goDown(), "Down");
                }
            }
            if (e.getCode() == KeyCode.RIGHT) {
                if (!goingRight && !goingLeft) {
                    runInNewThread(() -> this.goRight(), "Right");
                }
            }
            if (e.getCode() == KeyCode.LEFT) {
                if (!goingLeft && !goingRight) {
                    runInNewThread(() -> this.goLeft(), "Left");
                }
            }
        });
    }

    public void init() {
        pane.setGridLinesVisible(true);
        pane.setHgap(.75);
        pane.setVgap(.75);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                recs[i][j] = new Rectangle(27, 27, Color.BLACK);
                pane.add(recs[i][j], j, i);
            }
        }
        recs[ROWS / 2][COLS / 2].setFill(Color.GREEN);
        head = recs[ROWS / 2][COLS / 2];
        tail = recs[ROWS / 2][COLS / 2];
        seed = recs[5][7];
        seed.setFill((Color.RED));


        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));
        click.setOnAction((e) -> onHelloButtonClick());


    }

    public static void main(String[] args) {
        //System.out.println(tail[0]);
        launch();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome!");
    }

    public void goUp() {
        if (head == seed) {
            size++;
        }
        //goingUp = !goingUp;
        //goingDown = false;
        //goingLeft = false;
        //goingRight = false;
       // while(goingUp) {
            try {
                tail.setFill(Color.BLACK);
                tailA[0]--;
                tail = recs[tailA[0]][tailA[1]];
                headA[0]--;
                head = recs[headA[0]][headA[1]];
                moved++;
            } catch (IndexOutOfBoundsException e) {
                goingUp = false;
            }
            head.setFill(Color.GREEN);
            wentUp = true;

            //           try {
            //             Thread.sleep(150);
            //       } catch (InterruptedException ex) {
            //         throw new RuntimeException(ex);
            //   }
        }
  //  }
    public void goDown() {
        if (head == seed) {
            size++;
        }
        //goingDown = !goingDown;
        //goingUp = false;
        //goingLeft = false;
        //goingRight = false;
      //  while(goingDown) {
            try {
                tail.setFill(Color.BLACK);
                tailA[0]++;
                tail = recs[tailA[0]][tailA[1]];
                headA[0]++;
                head = recs[headA[0]][headA[1]];
                moved++;
            } catch (IndexOutOfBoundsException e) {
                goingDown = false;
            }
            head.setFill(Color.GREEN);
            wentDown = true;

 //           try {
   //             Thread.sleep(150);
     //       } catch (InterruptedException ex) {
       //         throw new RuntimeException(ex);
         //   }
    //    }
    }

    public void goRight() {
        if (head == seed) {
            size++;
        }
       // goingRight = !goingRight;
        //goingDown = false;
        //goingLeft = false;
        //goingUp = false;
     //   while(goingRight) {
            try {
                tail.setFill(Color.BLACK);
                tailA[1]++;
                tail = recs[tailA[0]][tailA[1]];
                headA[1]++;
                head = recs[headA[0]][headA[1]];
                moved++;
            } catch (IndexOutOfBoundsException e) {
                goingRight = false;
            }
            head.setFill(Color.GREEN);
            wentRight = true;

        //           try {
        //             Thread.sleep(150);
        //       } catch (InterruptedException ex) {
        //         throw new RuntimeException(ex);
        //   }
      //  }
    }

    public void goLeft() {
        //goingLeft = !goingLeft;
        //goingDown = false;
        //goingUp = false;
        //goingRight = false;
      //  while(goingLeft) {
            if (head == seed) {
                size++;
            }
            try {
                tail.setFill(Color.BLACK);
                tailA[1]--;
                tail = recs[tailA[0]][tailA[1]];
                headA[1]--;
                head = recs[headA[0]][headA[1]];
                moved++;
            } catch (IndexOutOfBoundsException e) {
                goingLeft = false;
            }
            head.setFill(Color.GREEN);
            wentLeft = true;

        //           try {
        //             Thread.sleep(150);
        //       } catch (InterruptedException ex) {
        //         throw new RuntimeException(ex);
        //   }
      //  }
    }

    private void grow() {

    }
    public static void runInNewThread(Runnable task, String name) {
        Thread t = new Thread(task, name);
        t.setDaemon(true);
        t.start();
    } // runInNewThread

}