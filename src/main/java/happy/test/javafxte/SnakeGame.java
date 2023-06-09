package happy.test.javafxte;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.lang.IndexOutOfBoundsException;
import java.io.IOException;
import java.util.Stack;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SnakeGame extends Application {
    private VBox root;
    private final GridPane pane;
    private final Rectangle[][] recs;
    private Rectangle head;
    private Rectangle seed;

    private Text text;


    //Variables
    private static Stack<Rectangle> body = new Stack<>();
    private final static int ROWS = 25;
    private final static int COLS = 35;

    private static int length = 1;
    private static int highscore = 1;

    private boolean going = false;
    private boolean goingUp = false;
    private boolean goingDown = false;
    private boolean goingRight = false;
    private boolean goingLeft = false;

    private boolean previousGoingUp = false;
    private boolean previousGoingDown = false;
    private boolean previousGoingRight = false;
    private boolean previousGoingLeft = false;

    int count = 0;
    boolean counting = false;

    public SnakeGame() {
        root = new VBox(0);
        pane = new GridPane();
        recs = new Rectangle[ROWS][COLS];
        text = new Text("Length: ( 1 )");

    }
    @Override
    public void start(Stage stage) throws IOException {
        text.setFont(new Font(25));
        text.setFill(Color.WHITESMOKE);
        text.setTextAlignment(TextAlignment.LEFT);
        BackgroundFill b = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        pane.setBackground(new Background(b));
        root.getChildren().addAll(pane, text);
        Scene scene = new Scene(root);
        scene.setFill(Color.CADETBLUE);
        stage.setResizable(false);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(e -> {
            if (!going) {
                runInNewThread(() -> go(), "");
            }
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
                if (!previousGoingDown || body.size() == 1) {
                    going = true;
                    goingUp = true;
                    goingDown = false;
                    goingRight = false;
                    goingLeft = false;
                }
            }
            if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
                if (!previousGoingUp || body.size() == 1) {
                    going = true;
                    goingUp = false;
                    goingDown = true;
                    goingRight = false;
                    goingLeft = false;
                }
            }
            if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                if (!previousGoingLeft || body.size() == 1) {
                    going = true;
                    goingUp = false;
                    goingDown = false;
                    goingRight = true;
                    goingLeft = false;
                }
            }
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                if (!previousGoingRight || body.size() == 1) {
                    going = true;
                    goingUp = false;
                    goingDown = false;
                    goingRight = false;
                    goingLeft = true;
                }
            }
        });
    }

    public void init() {
        pane.setHgap(.75);
        pane.setVgap(.75);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                recs[i][j] = new Rectangle(21, 21, Color.BLACK);
                pane.add(recs[i][j], j, i);
            }
        }

        recs[ROWS / 2][COLS / 2].setFill(Color.LIME);
        head = recs[ROWS / 2][COLS / 2];
        body.push(head);
        getSeed();
        seed.setFill(Color.RED);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(6,6,13,6));

    }

    public static void main(String[] args) {
        launch();
    }

    public void go() {
        try {
            while (going) {
                if (count == 3) {
                    counting = false;
                    count = 0;
                }
                int headRow = GridPane.getRowIndex(head);
                int headCol = GridPane.getColumnIndex(head);

                int newRow = headRow;
                int newCol = headCol;

                if (goingUp) {
                    newRow--;
                } else if (goingDown) {
                    newRow++;
                } else if (goingRight) {
                    newCol++;
                } else if (goingLeft) {
                    newCol--;
                }

                Rectangle newHead = recs[newRow][newCol];
                if (newHead != seed && body.contains(newHead)) {
                    restartGame();
                } else {
                    if (newHead != seed) {
                        if (!counting) {
                            Rectangle tail = body.pop();
                            tail.setFill(Color.BLACK);
                        }
                    } else {
                        counting = true;
                        seed.setFill(Color.BLACK);
                        getSeed();
                        seed.setFill(Color.RED);
                        length += 3;
                        text.setText("Length: ( " + length + " )");

                    }

                    newHead.setFill(Color.LIME);
                    body.add(0, newHead);
                    head = newHead;
                }
                if (counting) {
                    count++;
                }
                previousGoingUp = goingUp;
                previousGoingDown = goingDown;
                previousGoingRight = goingRight;
                previousGoingLeft = goingLeft;
                Thread.sleep(100);
            }
        } catch (IndexOutOfBoundsException | InterruptedException e) {
            going = false;
            System.out.println("ERROR");
            restartGame();
        }

    }

    private void getSeed() {
        int seedX = (int)(Math.random() * ROWS);
        int seedY = (int)(Math.random() * COLS);
        seed = recs[seedX][seedY];
        if (seed.getFill() == Color.LIME) {
            getSeed();
        }
    }

    private void restartGame() {
        if (highscore < length) {
            highscore = length;
        }
        for (Rectangle item : body) {
            item.setFill(Color.BLACK);
        }
        body.clear();
        head = recs[ROWS / 2][COLS / 2];
        body.push(head);
        going = false;
        goingRight = false;
        goingLeft = false;
        goingUp = false;
        goingDown = false;
        head.setFill(Color.LIME);
        length = 1;
        text.setText("Length: ( 1 )");
        System.out.println(highscore);
    }
    
    public static void runInNewThread(Runnable task, String name) {
        Thread t = new Thread(task, name);
        t.setDaemon(true);
        t.start();
    } // runInNewThread
    //END HERE
}