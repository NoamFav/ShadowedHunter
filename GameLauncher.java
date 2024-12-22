import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Objects;
import javazoom.jl.player.Player;
import javax.sound.sampled.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLauncher {

    private FloatControl volumeControl = null;
    private Player player;

    private volatile boolean keepPlaying = true;
    private static final Logger logger = LoggerFactory.getLogger(GameLauncher.class);

    public static Font loadCustomFont() {
        try {
            Font loadedFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(GameLauncher.class.getResourceAsStream("/Resources/Dungeon.TTF"))).deriveFont(40.0f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(loadedFont);
            return loadedFont;
        } catch (IOException | FontFormatException e) {
            logger.error("Error loading font: ",e);
            return new Font("Arial", Font.PLAIN, 40);
        }
    }

    public void play(String path, float volume) {
        new Thread(() -> {
            while (keepPlaying) {
                try {
                    FileInputStream fis = new FileInputStream(path);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    player = new Player(bis);
                    player.play();

                    Mixer.Info[] mixers = AudioSystem.getMixerInfo();
                    for (Mixer.Info mixerInfo : mixers) {
                        Mixer mixer = AudioSystem.getMixer(mixerInfo);
                        Line.Info[] lineInfos = mixer.getSourceLineInfo();
                        for (Line.Info lineInfo : lineInfos) {
                            if (lineInfo.getLineClass().equals(Clip.class)) {
                                try {
                                    Clip clip = (Clip) mixer.getLine(lineInfo);
                                    if (clip.isControlSupported(FloatControl.Type.VOLUME)) {
                                        volumeControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
                                        setVolume(volume);
                                    }
                                } catch (LineUnavailableException lue) {
                                    logger.error("An unexpected error occurred: ", lue);
                                }
                            }
                        }
                    }

                    player.play();
                } catch (Exception e) {
                    logger.error("Error loading the music: ", e);
                }
            }
        }).start();
    }

    public void stopMusic() {
        keepPlaying = false;
        if (player != null) {
            player.close();
        }
    }
    public void setVolume(float volume) {
        if (volumeControl != null) {
            volumeControl.setValue(volume);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
