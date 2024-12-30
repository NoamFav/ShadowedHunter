package com.ShadowedHunter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadSystem {
    private static final Logger logger = LoggerFactory.getLogger(SaveLoadSystem.class);

    public static void saveGame(String[][][] allMaps, Player player, String saveFileName) {
        File saveDir = new File("Save/");
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Save/" + saveFileName))) {
            for (String[][] map : allMaps) {
                for (String[] row : map) {
                    writer.write(String.join(";", row));
                    writer.newLine();
                }
                writer.newLine();
            }

            writer.write(player.getPosition().getX() + "," + player.getPosition().getY());
            writer.newLine();
            writer.write("Health:" + Script.health);
            writer.newLine();
            writer.write("Lives:" + MainGameWindow.GamePanel.lifeNumber);
            writer.newLine();
            writer.write("Keys:" + Script.gameMap.getPlayer().getItemCount("k"));
            writer.newLine();
            writer.write("HealthPotions:" + Script.gameMap.getPlayer().getItemCount("he"));
            writer.newLine();
            writer.write(
                    "IconPosition:"
                            + MainGameWindow.panel.getIconX()
                            + ","
                            + MainGameWindow.panel.getIconY());
            writer.newLine();
            writer.write("MapIndex:" + GameMap.currentMapIndex);
            writer.newLine();
            writer.write("MapState:" + GameMap.mapState);
        } catch (IOException e) {
            logger.error("Error saving the game: ", e);
        }
    }

    public static void loadGame(String saveFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Save/" + saveFileName))) {
            List<String[][]> maps = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                List<String[]> map = new ArrayList<>();
                while (line != null && !line.isEmpty()) {
                    map.add(line.split(";"));
                    line = reader.readLine();
                }
                maps.add(map.toArray(new String[0][]));
            }

            GameMap.maps = maps.toArray(new String[0][][]);

            String[] playerCoords = reader.readLine().split(",");
            int x = Integer.parseInt(playerCoords[0]);
            int y = Integer.parseInt(playerCoords[1]);

            Script.updateHealth(Integer.parseInt(reader.readLine().split(":")[1]));
            MainGameWindow.GamePanel.lifeNumber = Integer.parseInt(reader.readLine().split(":")[1]);
            Script.gameMap
                    .getPlayer()
                    .setItemCount("k", Integer.parseInt(reader.readLine().split(":")[1]));
            Script.gameMap
                    .getPlayer()
                    .setItemCount("he", Integer.parseInt(reader.readLine().split(":")[1]));

            String[] iconCoords = reader.readLine().split(",");
            MainGameWindow.setIconPosition(
                    Integer.parseInt(iconCoords[0]), Integer.parseInt(iconCoords[1]));

            GameMap.currentMapIndex = Integer.parseInt(reader.readLine().split(":")[1]);
            GameMap.mapState = Integer.parseInt(reader.readLine().split(":")[1]);

            Script.gameMap.initializePlayer(x, y);
        } catch (IOException | NumberFormatException e) {
            logger.error("Error loading the game: ", e);
        }
    }
}
