package com.sneezingcat191.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class PlayPanel extends GuiPanel {

    private GameBoard board;
    private BufferedImage info;
    private ScoreManager scores;
    private Font scoreFont;
    private String timeF;
    private String bestTimeF;

    private GuiButton tryAgain;
    private GuiButton mainMenu;
    private GuiButton screenShot;

    private int smallButtonWidth = 160;
    private int spacing = 20;
    private int largeButtonWidth = smallButtonWidth * 2 + spacing;
    private int buttonHeight=50;
    private boolean added;
    private int alpha;
    private Font gameOverFont;
    private boolean screenshot;

    public PlayPanel() {
        scoreFont = Game.main.deriveFont(24f);
        gameOverFont = Game.main.deriveFont(70f);
        board = new GameBoard(Game.WIDTH / 2 - GameBoard.BOARD_WIDTH / 2, Game.HEIGHT - GameBoard.BOARD_HEIGHT - 20);
        scores = board.getScores();
        info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);

        mainMenu = new GuiButton(Game.WIDTH / 2 - largeButtonWidth / 2, 450, largeButtonWidth, buttonHeight);
        tryAgain = new GuiButton(mainMenu.getX() / 2 - smallButtonWidth/2 +120, mainMenu.getY() - spacing - buttonHeight, smallButtonWidth, buttonHeight);
        screenShot = new GuiButton(tryAgain.getX() + tryAgain.getWidth() + spacing, tryAgain.getY(), smallButtonWidth, buttonHeight);

        tryAgain.setText("Try Again");
        screenShot.setText("Screenshot");
        mainMenu.setText("Back to Main Menu");

        tryAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.getScores().reset();
                board.reset();
                alpha = 0;
                remove(tryAgain);
                remove(screenShot);
                remove(mainMenu);

                added = false;
            }
        });

        screenShot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screenshot = true;
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Menu");
            }
        });

    }


    private void drawGui(Graphics2D g) {
        timeF = scores.getTime() / 1000000 + "";
        bestTimeF = scores.getBestTime() / 1000000 + "";

        Graphics2D g2d = (Graphics2D) info.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, info.getWidth(), info.getHeight());
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(scoreFont);
        g2d.drawString("" + scores.getCurrentScore(), 30, 40);
        g2d.setColor(Color.red);
        g2d.drawString("Best: " + scores.getCurrentTopScore(),
                Game.WIDTH-DrawUtils.getMessageWidth("Best: " + scores.getCurrentTopScore(),scoreFont,g2d)-20,40);

        g2d.drawString("Fastest: " + bestTimeF,Game.WIDTH-DrawUtils.getMessageWidth("Fastest: " + bestTimeF,scoreFont,g2d)-20,90);
        g2d.drawString("Time: " + timeF,30,90);
        g2d.dispose();

        g.drawImage(info,0,0,null);
    }

    public void drawGameOver(Graphics2D g){
        g.setColor(new Color(222,222,222,alpha));
        g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
        g.setColor(Color.RED);
        g.setFont(gameOverFont);
        g.drawString("Game Over :(" , Game.WIDTH/2 - DrawUtils.getMessageWidth("Game Over :(",gameOverFont,g)/2,250);

    }

    @Override
    public void update(){
        board.update();
        if (board.isDead()){
            alpha++;
            if (alpha>170) alpha=170;
        }
    }

    @Override
    public void render(Graphics2D g){
        drawGui(g);
        board.render(g);
        if (screenshot){
            BufferedImage bi=new BufferedImage(Game.WIDTH,Game.HEIGHT,BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d=(Graphics2D) bi.getGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
            drawGui(g2d);
            board.render(g2d);
            try {
                ImageIO.write(bi,"gif",new File(System.getProperty("user.home")+"\\Desktop","screenshot"+System.nanoTime()+".gif"));

            }catch (Exception e){
                e.printStackTrace();
            }
            screenshot=false;

        }
        if (board.isDead()){
            if (!added){
                added=true;
                add(mainMenu);
                add(screenShot);
                add(tryAgain);
            }
            drawGameOver(g);
        }
        super.render(g);
    }

}
