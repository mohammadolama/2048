package com.sneezingcat191.game;

import java.io.*;
import java.util.ArrayList;

public class Leaderboards {

    private static Leaderboards lBoards;
    private String filePath;
    private String highScores;

    private ArrayList<Integer> topScores;
    private ArrayList<Integer> topTiles;
    private ArrayList<Long> topTime;

    private Leaderboards() {
        filePath = new File("").getAbsolutePath();
        highScores = "Scores";
        topScores = new ArrayList<Integer>();
        topTiles = new ArrayList<Integer>();
        topTime = new ArrayList<Long>();
    }

    public static Leaderboards getInstance() {
        if (lBoards == null) {
            lBoards = new Leaderboards();
        }
        return lBoards;
    }

    public void addScore(int score) {
        for (int i = 0; i < topScores.size(); i++) {
            if (score >= topScores.get(i)) {
                topScores.add(i, score);
                topScores.remove(topScores.size() - 1);
                return;
            }
        }
    }

    public void addTile(int tileValue) {
        for (int i = 0; i < topTiles.size(); i++) {
            if (tileValue >= topTiles.get(i)) {
                topTiles.add(i, tileValue);
                topTiles.remove(topScores.size() - 1);
                return;
            }
        }
    }

    public void addTime(long mills) {
        for (int i = 0; i < topTime.size(); i++) {
            if (mills <= topTime.get(i)) {
                topTime.add(i, mills);
                topTime.remove(topScores.size() - 1);
                return;
            }
        }
    }

    public void loadScores() {
        try {
            File f = new File(filePath, highScores);
            if (!f.isFile()) {
                createSaveData();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            topTime.clear();
            topTiles.clear();
            topScores.clear();

            String[] scores = reader.readLine().split("-");
            String[] tiles = reader.readLine().split("-");
            String[] times = reader.readLine().split("-");

            for (int i = 0; i < scores.length; i++) {
                topScores.add(Integer.parseInt(scores[i]));
            }
            for (int i = 0; i < tiles.length; i++) {
                topTiles.add(Integer.parseInt(tiles[i]));

            }
            for (int i = 0; i < times.length; i++) {
                topTime.add(Long.parseLong(times[i]));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveScores() {
        FileWriter output = null;

        try {
            File f = new File(filePath, highScores);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write(topScores.get(0) + "-" + topScores.get(1) + "-" + topScores.get(2) + "-" + topScores.get(3) + "-" + topScores.get(4));
            writer.newLine();
            writer.write(topTiles.get(0) + "-" + topTiles.get(1) + "-" + topTiles.get(2) + "-" + topTiles.get(3) + "-" + topTiles.get(4));
            writer.newLine();
            writer.write(topTime.get(0) + "-" + topTime.get(1) + "-" + topTime.get(2) + "-" + topTime.get(3) + "-" + topTime.get(4));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createSaveData(){
        FileWriter output = null;

        try {
            File f = new File(filePath, highScores);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("0-0-0-0-0");
            writer.newLine();
            writer.write("0-0-0-0-0");
            writer.newLine();
            writer.write(Integer.MAX_VALUE+"-"+Integer.MAX_VALUE+"-"+Integer.MAX_VALUE+"-"+Integer.MAX_VALUE+"-"+Integer.MAX_VALUE);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHighScore(){
        return topScores.get(0);
    }
    public long getFasestTime(){
        return topTime.get(0);
    }
    public int getHighTile(){
        return topTiles.get(0);
    }

    public ArrayList<Integer> getTopScores() {
        return topScores;
    }



    public ArrayList<Integer> getTopTiles() {
        return topTiles;
    }



    public ArrayList<Long> getTopTime() {
        return topTime;
    }


}
