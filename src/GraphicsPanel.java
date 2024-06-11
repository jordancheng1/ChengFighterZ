import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private BufferedImage background;
    private Player player;
    private boolean[] pressedKeys;
    private Timer timer;
    private int time;
    private ArrayList<BufferedImage> aangAttacks = new ArrayList<BufferedImage>();
    private ArrayList<BufferedImage> aangAttacksLeft = new ArrayList<BufferedImage>();
    private ArrayList<Enemy> enemies;
    private Boss boss;
    private Animator aangAttackAnimation;
    private Animator aangAttackAnimationLeft;
    private JFrame enclosingFrame;
    private BufferedImage select;
    private BufferedImage character1;
    private BufferedImage locked;
    private BufferedImage difficulty;
    private BufferedImage gameOver;
    private BufferedImage win;
    private Rectangle character1Button;
    private Rectangle selectButton;
    private Rectangle easyButton;
    private Rectangle mediumButton;
    private Rectangle hardButton;
    private String charName = "";
    private String charName2 = "";
    private String character1DeadRight = "";
    private String character1DeadLeft = "";
    private int characterSelected;
    private boolean attacking;
    private boolean charSelected;
    private boolean gameStarted;
    private boolean diffSelected;
    private boolean won;
    private String diff;

    public GraphicsPanel() {
        try {
            select = ImageIO.read(new File("src/SelectButton.png"));
            character1 = ImageIO.read(new File("src/Aang.png"));
            locked = ImageIO.read(new File("src/LockedCharacter.png"));
            difficulty = ImageIO.read(new File("src/Difficulty.png"));
            gameOver = ImageIO.read(new File("src/GameOver.png"));
            win = ImageIO.read(new File("src/Win.png"));
            aangAttacks.add(ImageIO.read(new File("src/AangAttack1.png")));
            aangAttacks.add(ImageIO.read(new File("src/AangAttack2.png")));
            aangAttacks.add(ImageIO.read(new File("src/AangAttack3.png")));
            aangAttacks.add(ImageIO.read(new File("src/AangAttack4.png")));
            aangAttacks.add(ImageIO.read(new File("src/AangAttack5.png")));
            aangAttacksLeft.add(ImageIO.read(new File("src/AangAttack1Left.png")));
            aangAttacksLeft.add(ImageIO.read(new File("src/AangAttack2Left.png")));
            aangAttacksLeft.add(ImageIO.read(new File("src/AangAttack3Left.png")));
            aangAttacksLeft.add(ImageIO.read(new File("src/AangAttack4Left.png")));
            aangAttacksLeft.add(ImageIO.read(new File("src/AangAttack5Left.png")));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        character1Button = new Rectangle(100, 50, character1.getWidth(), character1.getHeight());
        selectButton = new Rectangle(480, 550, select.getWidth(), select.getHeight());
        easyButton = new Rectangle(305, 375, 183, 130);
        mediumButton = new Rectangle(520, 375, 183, 130);
        hardButton = new Rectangle(735, 375, 183, 130);
        pressedKeys = new boolean[128];
        time = 0;
        timer = new Timer(1000, this);
        enemies = new ArrayList<>();
        aangAttackAnimation = new Animator(aangAttacks);
        aangAttackAnimation.setSpeed(100);
        aangAttackAnimation.play();
        aangAttackAnimationLeft = new Animator(aangAttacksLeft);
        aangAttackAnimationLeft.setSpeed(100);
        aangAttackAnimationLeft.play();
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        charSelected = false;
        gameStarted = false;
        diffSelected = false;
        won = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameStarted) {
            if (charSelected) {
                g.drawImage(difficulty,270, 350, null);
                g.drawRect(95, 45, character1.getWidth() + 10, character1.getHeight() + 10);
            }
            if (diffSelected) {
                if (diff.equals("e")) {
                    g.drawRect(305, 375, 183, 130);
                    g.drawString("Reach a score of 10 to summon the boss", 305, 360);
                }
                else if (diff.equals("m")) {
                    g.drawRect(520, 375, 183, 130);
                    g.drawString("Faster enemies!", 520, 350);
                    g.drawString("Reach a score of 20 to summon the boss", 520, 360);
                }
                else if (diff.equals("h")) {
                    g.drawRect(735, 375, 183, 130);
                    g.drawString("Fastest enemies!", 735, 350);
                    g.drawString("Reach a score of 30 to summon the boss", 735, 360);
                }
                g.drawImage(select, 480, 550, null);
            }
            g.drawImage(character1, 100, 50, null);
            int x = 100;
            for (int i = 0; i < 6; i++) {
                x += 155;
                g.drawImage(locked, x, 50, null);
            }
        }
        else {
            if (diff.equals("e")) {
                try {
                    background = ImageIO.read(new File("src/EasyLevel.png"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                g.drawImage(background, 0, 0, null);
            }
            else if (diff.equals("m")) {
                try {
                    background = ImageIO.read(new File("src/MediumLevel.png"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                g.drawImage(background, 0, 0, null);
            }
            else if (diff.equals("h")) {
                try {
                    background = ImageIO.read(new File("src/HardLevel.png"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                g.drawImage(background, 0, 0, null);
            }
            if (!won) {
                g.setFont(new Font("Courier New", Font.BOLD, 72));
                g.setColor(Color.white);
                if (player.getAlive()) {
                    g.setFont(new Font("Courier New", Font.BOLD, 72));
                    g.setColor(Color.white);
                    g.drawString("SCORE : " + player.getScore(), 20, 175);
                    int spaceBwtnHearts = 20;
                    for (int i = 1; i <= player.getHealth(); i++) {
                        g.drawImage(player.getHealthImg(), spaceBwtnHearts, 20, null);
                        spaceBwtnHearts += 120;
                    }
                    if (attacking) {
                        if (player.getDirection()) {
                            aangAttackAnimation.update(System.currentTimeMillis());
                            g.drawImage(aangAttackAnimation.sprite, player.getxCoord(), player.getyCoord(), null);
                        } else {
                            aangAttackAnimationLeft.update(System.currentTimeMillis());
                            g.drawImage(aangAttackAnimationLeft.sprite, player.getxCoord(), player.getyCoord(), null);
                        }
                    } else {
                        g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
                    }
                    if (!boss.bossSpawned()) {
                        for (int i = 0; i < enemies.size(); i++) {
                            Enemy enemy = enemies.get(i);
                            g.drawImage(enemy.getImage(), enemy.getxCoord(), enemy.getyCoord(), null);
                            if (enemy.facingRight()) {
                                enemy.moveRight();
                            } else {
                                enemy.moveLeft();
                            }
                            if (player.playerRect().intersects(enemy.enemyRect()) && attacking && player.getDirection() && !enemy.getDirection()) {
                                enemies.remove(i);
                                i--;
                                player.enemySlain();
                            } else if (player.playerRect().intersects(enemy.enemyRect()) && attacking && !player.getDirection() && enemy.getDirection()) {
                                enemies.remove(i);
                                i--;
                                player.enemySlain();
                            } else if (player.playerRect().intersects(enemy.enemyRect()) && attacking && player.getDirection() && enemy.getDirection()) {
                                player.loseHealth();
                                enemies.remove(i);
                                i--;
                            } else if (player.playerRect().intersects(enemy.enemyRect()) && attacking && !player.getDirection() && !enemy.getDirection()) {
                                player.loseHealth();
                                enemies.remove(i);
                                i--;
                            } else if (player.playerRect().intersects(enemy.enemyRect()) && !attacking) {
                                player.loseHealth();
                                enemies.remove(i);
                                i--;
                            }
                        }
                        if (player.getScore() >= 10 && diff.equals("e")) {
                            boss.setSpawned(true);
                        } else if (player.getScore() >= 20 && diff.equals("m")) {
                            boss.setSpawned(true);
                        } else if (player.getScore() >= 30 && diff.equals("h")) {
                            boss.setSpawned(true);
                        }
                    } else {
                        g.drawImage(boss.getImage(), boss.getxCoord(), boss.getyCoord(), null);
                        boss.moveOntoScreen();
                    }
                } else {
                    g.drawImage(gameOver, 365, 60, null);
                    g.drawString("SCORE : " + player.getScore(), 365, 550);
                    g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord() + 20, null);
                }
                if (boss.getHealth() == 0) {
                    won = true;
                }
            }
            else {
                g.drawImage(win, 340, 250, null);
                g.drawImage(player.getPlayerImage(), player.getxCoord(), player.getyCoord(), null);
            }
        }

        // moves left (A)
        if (pressedKeys[65] && gameStarted && player.getAlive() && !won) {
            player.faceLeft();
            player.moveLeft();
        }
        // moves right (D)
        if (pressedKeys[68] && gameStarted && player.getAlive() && !won) {
            player.faceRight();
            player.moveRight();
        }
    }

    public void mousePressed(MouseEvent e) {
        Point mouseClickLocation = e.getPoint();
        if (!gameStarted) {
            if (selectButton.contains(mouseClickLocation) && !charName.isEmpty() && diffSelected) {
                player = new Player(charName, charName2, character1DeadRight, character1DeadLeft, diff);
                timer.start();
                gameStarted = true;
                boss = new Boss(diff);
            } else if (character1Button.contains(mouseClickLocation)) {
                charName = "src/AangCharRight.png";
                charName2 = "src/AangCharLeft.png";
                character1DeadRight = "src/AangDeadRight.png";
                character1DeadLeft = "src/AangDeadLeft.png";
                charSelected = true;
                characterSelected = 1;
            } else if (easyButton.contains(mouseClickLocation)) {
                diffSelected = true;
                diff = "e";
            } else if (mediumButton.contains(mouseClickLocation)) {
                diffSelected = true;
                diff = "m";
            } else if (hardButton.contains(mouseClickLocation)) {
                diffSelected = true;
                diff = "h";
            }
        }
        else if (player.getAlive()) {
            attacking = true;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (gameStarted) {
            int key = e.getKeyCode();
            pressedKeys[key] = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (gameStarted) {
            int key = e.getKeyCode();
            pressedKeys[key] = false;
        }
    }
    public void mouseReleased(MouseEvent e) {
        if (gameStarted && attacking) {
            attacking = false;
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer && gameStarted) {
            time++;
            if (!boss.bossSpawned()) {
                if (diff.equals("e")) {
                    if (time % 3 == 0) {
                        Enemy enemy = new Enemy(diff);
                        enemies.add(enemy);
                    }
                } else if (diff.equals("m")) {
                    if (time % 2 == 0) {
                        Enemy enemy = new Enemy(diff);
                        enemies.add(enemy);
                    }
                } else if (diff.equals("h")) {
                    Enemy enemy = new Enemy(diff);
                    enemies.add(enemy);
                }
            }
            else if (boss.bossSpawned()) {
                if (player.playerRect().intersects(boss.bossRect()) && attacking) {
                    if (time % 2 == 0) {
                        boss.loseHealth();
                    }
                }
            }
        }
    }
}
