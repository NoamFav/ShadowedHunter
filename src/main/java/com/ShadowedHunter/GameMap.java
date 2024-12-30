package com.ShadowedHunter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public static String[][][] maps = new String[4][][];
    public static int currentMapIndex;
    private String[][] currentMap;
    private Player player;
    public static int mapState = 0;
    public static MainGameWindow gameWindow = MainGameWindow.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(GameMap.class);

    static {
        if (mapState == 0) {
            for (int i = 0; i < 4; i++) {
                maps[i] = loadMapFromFile("maps/floor" + (i) + ".csv");
            }
        }
    }

    public GameMap() {
        currentMapIndex = 0;
        this.currentMap = maps[currentMapIndex];
        initializePlayer(1, 17);
    }

    public void initializePlayer(int x, int y) {
        this.player = new Player(x, y);
    }

    public Player getPlayer() {
        return player;
    }

    public void switchMap(int newIndex) {
        if (newIndex < 0 || newIndex >= 4) {
            return;
        }
        currentMapIndex = newIndex;
        this.currentMap = maps[newIndex];
        gameWindow.repaint();
    }

    public String[][] getCurrentMap() {
        return currentMap;
    }

    private static String[][] loadMapFromFile(String filePath) {
        List<String[]> list = new ArrayList<>();

        try (InputStream is = GameLauncher.class.getClassLoader().getResourceAsStream(filePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + filePath);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                list.add(values);
            }
        } catch (IOException e) {
            logger.error("Error loading the file: ", e);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }

        return list.toArray(new String[0][]);
    }

    public void movePlayerUp() {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int newY = y - 1;
        if (isValidMove(x, newY, false)) {
            player.moveUp();
            MainGameWindow.moveIcon(0, -17);
        }
    }

    public void movePlayerDown() {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int newY = y + 1;
        if (isValidMove(x, newY, false)) {
            player.moveDown();
            MainGameWindow.moveIcon(0, 17);
        }
    }

    public void movePlayerLeft() {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int newX = x - 1;
        if (isValidMove(newX, y, false)) {
            player.moveLeft();
            MainGameWindow.moveIcon(-17, 0);
        }
    }

    public void movePlayerRight() {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int newX = x + 1;
        if (isValidMove(newX, y, false)) {
            player.moveRight();
            MainGameWindow.moveIcon(17, 0);
        }
    }

    public void openUp() {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int newY = y - 1;
        if (isValidMove(x, newY, true)) {
            player.moveUp();
            player.moveUp();
            MainGameWindow.moveIcon(0, -17);
            MainGameWindow.moveIcon(0, -17);
        }
    }

    public void openDown() {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int newY = y + 1;
        if (isValidMove(x, newY, true)) {
            player.moveDown();
            player.moveDown();
            MainGameWindow.moveIcon(0, 17);
            MainGameWindow.moveIcon(0, 17);
        }
    }

    public void openLeft() {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int newX = x - 1;
        if (isValidMove(newX, y, true)) {
            player.moveLeft();
            player.moveLeft();
            MainGameWindow.moveIcon(-17, 0);
            MainGameWindow.moveIcon(-17, 0);
        }
    }

    public void openRight() {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        int newX = x + 1;
        if (isValidMove(newX, y, true)) {
            player.moveRight();
            player.moveRight();
            MainGameWindow.moveIcon(17, 0);
            MainGameWindow.moveIcon(17, 0);
        }
    }

    public void stairsUp() {

        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        if (currentMap[y - 1][x].equals("stu")) {
            movePlayerUp();
            switchMap(currentMapIndex + 1);
            gameWindow.setOutputText("Going up the stairs");
        } else if (currentMap[y + 1][x].equals("stu")) {
            movePlayerDown();
            switchMap(currentMapIndex + 1);
            gameWindow.setOutputText("Going up the stairs");
        } else if (currentMap[y][x - 1].equals("stu")) {
            movePlayerLeft();
            switchMap(currentMapIndex + 1);
            gameWindow.setOutputText("Going up the stairs");
        } else if (currentMap[y][x + 1].equals("stu")) {
            movePlayerRight();
            switchMap(currentMapIndex + 1);
            gameWindow.setOutputText("Going up the stairs");
        } else {
            gameWindow.setOutputText("there is no stairs here");
        }
    }

    public void stairsDown() {

        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        if (currentMap[y - 1][x].equals("std")) {
            movePlayerUp();
            switchMap(currentMapIndex - 1);
            gameWindow.setOutputText("Going down the stairs");
        } else if (currentMap[y + 1][x].equals("std")) {
            movePlayerDown();
            switchMap(currentMapIndex - 1);
            gameWindow.setOutputText("Going down the stairs");
        } else if (currentMap[y][x - 1].equals("std")) {
            movePlayerLeft();
            switchMap(currentMapIndex - 1);
            gameWindow.setOutputText("Going down the stairs");
        } else if (currentMap[y][x + 1].equals("std")) {
            movePlayerRight();
            switchMap(currentMapIndex - 1);
            gameWindow.setOutputText("Going down the stairs");
        } else {
            gameWindow.setOutputText("there is no stairs here");
        }
    }

    private boolean isValidMove(int x, int y, boolean isOpeningDoor) {

        if (x < 0 || x >= currentMap.length || y < 0 || y >= currentMap[x].length) {
            return false;
        } else if (currentMap[y][x].equals("x")) {
            gameWindow.setOutputText("You can't, there is a wall there");
            return false;
        } else if (currentMap[y][x].equals("s")) {
            return true;
        } else if (currentMap[y][x].matches("[dlt]")) {
            if (isOpeningDoor) {
                return true;
            }
            gameWindow.setOutputText("You can't, there is a door there");
            return false;
        }
        return true;
    }
}
