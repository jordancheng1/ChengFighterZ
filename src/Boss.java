import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Boss {
    private int health;
    private int xCoord;
    private int yCoord;
    private BufferedImage bossImg;
    private BufferedImage bossHealth;
    private boolean spawned;
    private double MOVE_AMT = 1.0;

    public Boss(String difficulty) {
        xCoord = 1255;
        spawned = false;
        if (difficulty.equals("e")) {
            try {
                bossImg = ImageIO.read(new File("src/BossEasy.png"));
                bossHealth = ImageIO.read(new File("src/bossHealth.png"));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            health = 10;
            yCoord = 300;
        }
        else if (difficulty.equals("m")) {
            try {
                bossImg = ImageIO.read(new File("src/BossMedium.png"));
                bossHealth = ImageIO.read(new File("src/bossHealth.png"));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            health = 15;
            yCoord = 260;
        }
        else if (difficulty.equals("h")) {
            try {
                bossImg = ImageIO.read(new File("src/BossHard.png"));
                bossHealth = ImageIO.read(new File("src/bossHealth.png"));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            health = 20;
            yCoord = 100;
        }
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public BufferedImage getImage() {
        return bossImg;
    }

    public BufferedImage getHealthImg() {
        return bossHealth;
    }

    public boolean bossSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawn) {
        spawned = spawn;
    }

    public void moveOntoScreen() {
        if (health == 50) {
            if (xCoord - MOVE_AMT >= 800) {
                xCoord -= MOVE_AMT;
            }
        }
        else {
            if (xCoord - MOVE_AMT >= 650) {
                xCoord -= MOVE_AMT;
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public void loseHealth() {
        health -= 5;
    }

    public Rectangle bossRect() {
        int imageHeight = getImage().getHeight();
        int imageWidth = getImage().getWidth();
        Rectangle rect = new Rectangle(xCoord, yCoord, imageWidth, imageHeight);
        return rect;
    }
}
