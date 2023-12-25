import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadSystem {
    private static final Logger logger = LoggerFactory.getLogger(SaveLoadSystem.class);


    public static void saveGame(String[][][] allMaps, Player player, String saveFileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFileName));

            // Write map states
            for (String[][] allMap : allMaps) {
                for (String[] strings : allMap) {
                    for (String string : strings) {
                        writer.write(string + ";");
                    }
                    writer.newLine();
                }
                writer.newLine();
            }

            // Write player state
            writer.write(player.getPosition().getX() + "," + player.getPosition().getY());
            writer.newLine();
            writer.write("Health:" + Script.health);
            writer.newLine();
            writer.write("Lives:" + MainGameWindow.GamePanel.lifeNumber);
            writer.newLine();
            writer.write("Keys:" + Script.gameMap.getPlayer().getItemCount("k"));
            writer.newLine();
            writer.write("HealthPotions:" + Script.gameMap.getPlayer().getItemCount("k"));
            writer.newLine();
            MainGameWindow.GamePanel panel = MainGameWindow.panel;
            writer.write(panel.getIconX() + "," + panel.getIconY());
            writer.newLine();
            writer.write("MapIndex:" + GameMap.currentMapIndex);
            writer.newLine();
            writer.write("MapState:" + GameMap.mapState);
            writer.newLine();

            writer.close();

        } catch (IOException e) {
            logger.error("error with file ",e);

        }
    }

    public static void loadGame(String saveFileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(saveFileName));

            // Read map states
            String[][][] loadedMaps = new String[4][][];
            for (int m = 0; m < 4; m++) {
                List<String[]> mapList = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    String[] values = line.split(";");
                    mapList.add(values);
                }
                loadedMaps[m] = mapList.toArray(new String[0][]);
            }

            // Read player state
            String[] playerCoords = reader.readLine().split(",");
            int x = Integer.parseInt(playerCoords[0]);
            int y = Integer.parseInt(playerCoords[1]);

            int health = Integer.parseInt(reader.readLine().split(":")[1]);
            int lifeCount = Integer.parseInt(reader.readLine().split(":")[1]);
            int keysCount = Integer.parseInt(reader.readLine().split(":")[1]);
            int healthPotionsCount = Integer.parseInt(reader.readLine().split(":")[1]);
            String[] iconCoords = reader.readLine().split(",");
            int index = Integer.parseInt(reader.readLine().split(":")[1]);
            GameMap.mapState = Integer.parseInt(reader.readLine().split(":")[1]);
            GameMap.maps = loadedMaps;


            int ix = Integer.parseInt(iconCoords[0]);
            int iy = Integer.parseInt(iconCoords[1]);

            // Set player's attributes using the read values

            Script.gameMap.initializePlayer(x,y);
            Script.updateHealth(health);
            MainGameWindow.GamePanel.lifeNumber = lifeCount;
            Script.gameMap.getPlayer().setItemCount("k",keysCount);
            Script.gameMap.getPlayer().setItemCount("he",healthPotionsCount);
            Script.gameMap.switchMap(index);
            MainGameWindow.setIconPosition(ix,iy);


            reader.close();
        } catch (IOException e) {
            logger.error("error with file ",e);
        }
    }
}