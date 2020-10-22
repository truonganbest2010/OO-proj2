package view;


import controller.KeyController;
import controller.TimerListener;
import model.EnemyComposite;
import model.Shooter;
import model.ShooterElement;

import java.awt.*;
import javax.swing.*;

public class GameBoard {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FPS = 60;
    public static final int DELAY = 1000/FPS;
    
    private JFrame window;
    private MyCanvas canvas;
    private Shooter shooter;
    private EnemyComposite enemyComposite;
    private Timer timer;
    private TimerListener timerListener;

    private JButton startBtn = new JButton("Start");
    private JButton pauseBtn = new JButton("Pause");
    private JButton quitBtn = new JButton("Quit");

    private JLabel scoreLabel = new JLabel();
    private int score = 0;

    public GameBoard(JFrame window) {
        this.window = window;
    }

    public void init() {
        Container cp = window.getContentPane();

        JPanel northPanel = new JPanel();
        cp.add(BorderLayout.NORTH, northPanel);
        JLabel label = new JLabel("Score: ");
        scoreLabel.setText("" + score);
        northPanel.add(label);
        northPanel.add(scoreLabel);

        canvas = new MyCanvas(this, WIDTH, HEIGHT);
        cp.add(BorderLayout.CENTER, canvas);
        canvas.addKeyListener(new KeyController(this));
        canvas.requestFocusInWindow();
        canvas.setFocusable(true);

            pauseBtn.setEnabled(false);

        startBtn.setFocusable(false);
        pauseBtn.setFocusable(false);
        quitBtn.setFocusable(false);

        JPanel southPanel = new JPanel();
        southPanel.add(startBtn);
        southPanel.add(pauseBtn);
        southPanel.add(quitBtn);
        cp.add(BorderLayout.SOUTH, southPanel);

        canvas.getGameElements().add(new TextDraw("Click <Start> to play", WIDTH/3 - 50, HEIGHT/2, Color.yellow, 30));
        
        timerListener = new TimerListener(this);
        timer = new Timer(DELAY, timerListener);
    
        startBtn.addActionListener(e -> {
            pauseBtn.setEnabled(true);
            startBtn.setEnabled(false);
            shooter = new Shooter(GameBoard.WIDTH/2, GameBoard.HEIGHT-ShooterElement.SIZE);
            enemyComposite = new EnemyComposite();
            canvas.getGameElements().clear();
            canvas.getGameElements().add(shooter);
            canvas.getGameElements().add(enemyComposite);
            score = 0;
            timer.start();
        });

        pauseBtn.addActionListener(e -> {
            String Btnlabel = pauseBtn.getText();
            if (Btnlabel.equals("Pause")) {
                pauseBtn.setText("Resume");
                startBtn.setEnabled(true);
                startBtn.setText("New Game");
                timer.stop();
            }
            else {
                pauseBtn.setText("Pause");
                startBtn.setEnabled(false);
                timer.start();
            }
        });

        quitBtn.addActionListener(e -> {
            System.exit(0);
        });
    }

    public MyCanvas getCanvas() {
        return canvas;
    }
    public Timer getTimer() {
        return timer;
    }
    public TimerListener getTimerListener() {
        return timerListener;
    }
    public Shooter getShooter() {
        return shooter;
    }
    public EnemyComposite getEnemyComposite() {
        return enemyComposite;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public JLabel getScoreLabel() {
        return scoreLabel;
    }
    public JButton getStartBtn() {
        return startBtn;
    }
    public JButton getPauseBtn() {
        return pauseBtn;
    }
}
