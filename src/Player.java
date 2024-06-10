import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    private final double MOVE_AMT = 8.0;
    private BufferedImage right;
    private BufferedImage left;
    private double xCoord;
    private double yCoord;
    private int health;
    private boolean alive;
    private int difficulty;
    private boolean facingRight;

    public Player(String imageRight, String imageLeft, String difficulty) {
        xCoord = 50;
        facingRight = true;
        alive = true;
        if (difficulty.equals("h")) {
            health = 1;
            yCoord = 495;
        }
        else if (difficulty.equals("m")){
            health = 3;
            yCoord = 565;
        }
        else if (difficulty.equals("e")) {
            health = 5;
            yCoord = 465;
        }
        try {
            right = ImageIO.read(new File(imageRight));
            left = ImageIO.read(new File(imageLeft));
            healthImg = ImageIO.read(new File());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }

    public int getHealth() {
        return health;
    }
    public boolean getAlive() {
        return alive;
    }

    public void loseHealth() {
        health--;
    }

    public void faceRight() {
        facingRight = true;
    }

    public void faceLeft() {
        facingRight = false;
    }

    public void moveRight() {
        if (xCoord + MOVE_AMT <= 1155) {
            xCoord += MOVE_AMT;
        }
    }

    public void moveLeft() {
        if (xCoord - MOVE_AMT >= 0) {
            xCoord -= MOVE_AMT;
        }
    }

    public boolean getDirection() {
        return facingRight;
    }

    public void turn() {
        if (facingRight) {
            faceLeft();
        }
        else {
            faceRight();
        }
    }

    public BufferedImage getPlayerImage() {
        if (facingRight) {
            return right;
        }
        else {
            return left;
        }
    }

    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord, (int) yCoord, imageWidth, imageHeight);
        return rect;
    }
}
