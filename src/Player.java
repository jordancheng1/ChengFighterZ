import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    private final double MOVE_AMT = 8.0;
    private BufferedImage right;
    private BufferedImage left;
    private BufferedImage deadRight;
    private BufferedImage deadLeft;
    private double xCoord;
    private double yCoord;
    private BufferedImage healthImg;
    private int health;
    private int score;
    private int scoreInc;
    private int difficulty;
    private boolean facingRight;

    public Player(String imageRight, String imageLeft, String deadR, String deadL, String difficulty) {
        xCoord = 550;
        facingRight = true;
        score = 0;
        if (difficulty.equals("h")) {
            health = 1;
            yCoord = 495;
            scoreInc = 3;
        }
        else if (difficulty.equals("m")){
            health = 2;
            yCoord = 565;
            scoreInc = 2;
        }
        else if (difficulty.equals("e")) {
            health = 3;
            yCoord = 465;
            scoreInc = 1;
        }
        try {
            right = ImageIO.read(new File(imageRight));
            left = ImageIO.read(new File(imageLeft));
            deadRight = ImageIO.read(new File(deadR));
            deadLeft = ImageIO.read(new File(deadL));
            healthImg = ImageIO.read(new File("src/Health.png"));
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

    public BufferedImage getHealthImg() {
        return healthImg;
    }

    public boolean getAlive() {
        if (health != 0) {
            return true;
        }
        return false;
    }

    public void loseHealth() {
        health--;
    }

    public int getScore() {
        return score;
    }

    public void enemySlain() {
        score += scoreInc;
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
        if (getAlive()) {
            if (facingRight) {
                return right;
            }
            else {
                return left;
            }
        }
        else {
            if (facingRight) {
                return deadRight;
            }
            else {
                return deadLeft;
            }
        }
    }

    public Rectangle playerRect() {
        int imageHeight = getPlayerImage().getHeight();
        int imageWidth = getPlayerImage().getWidth();
        Rectangle rect = new Rectangle((int) xCoord + 20, (int) yCoord, 120, 90);
        return rect;
    }
}
