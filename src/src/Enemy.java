package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private int xCoord;
    private int yCoord;
    private BufferedImage right;
    private BufferedImage left;
    private double MOVE_AMT;
    private boolean leftSide;
    private boolean facingRight;

    public Enemy(String difficulty) {
        int spawn = (int) (Math.random() * 2);
        if (spawn == 0) {
            xCoord = -100;
            leftSide = true;
            facingRight = true;
        }
        else {
            xCoord = 1255;
            leftSide = false;
            facingRight = false;
        }
        if (difficulty.equals("h")) {
            yCoord = 445;
            MOVE_AMT = 7.0;
            try {
                right = ImageIO.read(new File("src/EnemyHard.png"));
                left = ImageIO.read(new File("src/EnemyHardLeft.png"));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (difficulty.equals("m")){
            yCoord = 510;
            MOVE_AMT = 6.0;
            try {
                right = ImageIO.read(new File("src/EnemyMedium.png"));
                left = ImageIO.read(new File("src/EnemyMediumLeft.png"));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (difficulty.equals("e")) {
            yCoord = 435;
            MOVE_AMT = 4.0;
            try {
                right = ImageIO.read(new File("src/EnemyEasy.png"));
                left = ImageIO.read(new File("src/EnemyEasyLeft.png"));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
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

    public boolean facingRight() {
        return facingRight;
    }
    public BufferedImage getImage() {
        if (leftSide) {
            return right;
        }
        return left;
    }

    public boolean getDirection() {
        return facingRight;
    }

    public Rectangle enemyRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle(xCoord + 30, yCoord, imageWidth  - 50, imageHeight);
        return rect;
    }
}
