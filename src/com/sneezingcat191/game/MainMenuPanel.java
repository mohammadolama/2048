package com.sneezingcat191.game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends GuiPanel {

    private Font titleFont = new Font("Serif", Font.PLAIN, 90);
    private Font creatorFont = new Font("Serif", Font.PLAIN, 25);
    private String title = "2048";
    private String creator = "OLAMA";
    private int spacing = 90;
    private int buttonWidth = 220;
    private int buttonHeight = 60;

    public MainMenuPanel() {
        super();
        GuiButton playButton = new GuiButton(Game.WIDTH/2-buttonWidth/2,220,buttonWidth,buttonHeight);
        GuiButton ScoresButton = new GuiButton(Game.WIDTH/2-buttonWidth/2,playButton.getY()+spacing,buttonWidth,buttonHeight);
        GuiButton quitButton = new GuiButton(Game.WIDTH/2-buttonWidth/2, ScoresButton.getY()+spacing,buttonWidth,buttonHeight);


        playButton.setText("Play");
        ScoresButton.setText("Scores");
        quitButton.setText("Quit");


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GuiScreen.getInstance().setCurrentPanel("Play");

            }
        });

        ScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiScreen.getInstance().setCurrentPanel("Leaderboards");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(playButton);
        add(ScoresButton);
        add(quitButton);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(titleFont);
        g.setColor(Color.BLACK);
        g.drawString(title,Game.WIDTH/2-DrawUtils.getMessageWidth(title,titleFont,g)/2,150);
        g.setFont(creatorFont);
        g.drawString(creator,20,Game.HEIGHT-10);


    }
}
