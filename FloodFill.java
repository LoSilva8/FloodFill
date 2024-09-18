import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class FloodFillApp extends Application {

    private final int LARGURA = 213;
    private final int ALTURA = 208;
    private final int TAMANHO_PIXEL = 10;
    private Color[][] image;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(213, 208);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image img = new Image(new FileInputStream("/home/lorenzob/Downloads/xadrez.png")); //Path da imagem
        loadImage(img);

        int startX = 0, startY = 0;
        Color newColor = Color.BLUE;

        floodFillWithStack(startX, startY, newColor);

        drawImage(gc);

        Scene scene = new Scene(canvas);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ENCHENTE CHEIO =D");
        primaryStage.show();
    }

    private void initializeImage(GraphicsContext gc) {
        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[0].length; y++) {
                if (x == y) {
                    image[x][y] = Color.BLACK; 
                } else {
                    image[x][y] = Color.WHITE;
                }
            }
        }
    }

    private void drawImage(GraphicsContext gc) {
        for (int x = 0; x < image.length; x++) {
            for (int y = 0; y < image[0].length; y++) {
                gc.setFill(image[x][y]);
                gc.fillRect(x * TAMANHO_PIXEL, y * TAMANHO_PIXEL, TAMANHO_PIXEL, TAMANHO_PIXEL);
            }
        }
    }

    private void floodFillWithStack(int x, int y, Color newColor) {
        Color targetColor = image[x][y];
        if (targetColor.equals(newColor)) return;

        Stack<Point> stack = new Stack<>();
        stack.push(new Point(x, y));

        while (!stack.isEmpty()) {
            Point p = stack.pop();
            int px = p.x;
            int py = p.y;

            if (px < 0 || py < 0 || px >= image.length || py >= image[0].length) continue;
            if (image[px][py] != targetColor) continue;

            image[px][py] = newColor;

            stack.push(new Point(px + 1, py));
            stack.push(new Point(px - 1, py));
            stack.push(new Point(px, py + 1));
            stack.push(new Point(px, py - 1));
        }
    }

    private void floodFillWithQueue(int x, int y, Color newColor) {
        Color targetColor = image[x][y];
        if (targetColor.equals(newColor)) return;

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int px = p.x;
            int py = p.y;

            if (px < 0 || py < 0 || px >= image.length || py >= image[0].length) continue;
            if (image[px][py] != targetColor) continue;

            image[px][py] = newColor;

            queue.add(new Point(px + 1, py));
            queue.add(new Point(px - 1, py));
            queue.add(new Point(px, py + 1));
            queue.add(new Point(px, py - 1));
        }
    }

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
