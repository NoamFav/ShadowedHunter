package com.ShadowedHunter;

import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

public class MainGameWindow extends JFrame {

    private static MainGameWindow instance;
    public static GamePanel panel = new GamePanel();

    public static AtomicInteger counterValue = new AtomicInteger();

    GameLauncher BackgroundMusic;

    private MainGameWindow() {
        setTitle("Main Game Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setUndecorated(true);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        panel = new GamePanel();
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setVisible(true);

        BackgroundMusic = new GameLauncher();
        BackgroundMusic.play("ThemeMusic.mp3", 20);

        int delay = 1000;
        ActionListener taskPerformer =
                evt -> {
                    counterValue.getAndIncrement();
                    panel.repaint();
                };
        new Timer(delay, taskPerformer).start();
    }

    public void setOutputText(String text) {
        panel.setTextFieldText(text);
    }

    public static void setIconPosition(int newX, int newY) {
        panel.setIconPosition(newX, newY);
    }

    public static void moveIcon(int deltaX, int deltaY) {
        panel.moveIcon(deltaX, deltaY);
    }

    public static class GamePanel extends JPanel {
        JTextArea textArea1;
        JTextArea textArea2;
        Image bgImage1;
        Image bgImage2;
        Image bgImage3;
        Image bgImage4;
        Image iconImage;
        Image[] maps;
        Image background;
        Image sword;
        Image key;
        Image shield;
        Image health;
        Image cheatSheet;

        public int iconX = 429;
        public int iconY = 301;
        public static int lifeNumber = 0;
        private boolean showCheatSheet = false;
        Font customFont = GameLauncher.loadCustomFont();

        public void incrementLifeNumber() {
            lifeNumber++;
            this.repaint();
        }

        public int getIconX() {
            return iconX;
        }

        public int getIconY() {
            return iconY;
        }

        public void setTextFieldText(String text) {
            textArea1.setText(text);
        }

        public GamePanel() {
            setLayout(null);
            setOpaque(false);

            maps =
                    new Image[] {
                        bgImage1 =
                                new ImageIcon(
                                                Objects.requireNonNull(
                                                        getClass()
                                                                .getResource(
                                                                        "/maps/playerFloor1.png")))
                                        .getImage(),
                        bgImage2 =
                                new ImageIcon(
                                                Objects.requireNonNull(
                                                        getClass()
                                                                .getResource(
                                                                        "/maps/playerFloor2.png")))
                                        .getImage(),
                        bgImage3 =
                                new ImageIcon(
                                                Objects.requireNonNull(
                                                        getClass()
                                                                .getResource(
                                                                        "/maps/playerFloor3.png")))
                                        .getImage(),
                        bgImage4 =
                                new ImageIcon(
                                                Objects.requireNonNull(
                                                        getClass()
                                                                .getResource(
                                                                        "/maps/playerFloor4.png")))
                                        .getImage(),
                    };
            iconImage =
                    new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.png")))
                            .getImage();
            background =
                    new ImageIcon(
                                    Objects.requireNonNull(
                                            getClass().getResource("/BackgroundGame.png")))
                            .getImage();
            cheatSheet =
                    new ImageIcon(Objects.requireNonNull(getClass().getResource("/CheatSheet.png")))
                            .getImage();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double screenWidth = screenSize.getWidth();
            double screenHeight = screenSize.getHeight();
            int fontSizeForTextArea1 = 50;

            if (screenWidth == 1440 && screenHeight == 900) {
                fontSizeForTextArea1 = 40;
            }

            textArea1 = createRoundedTextArea(false);
            textArea1.setText("Type 'intro' to start");
            textArea1.setFont(
                    new Font(
                            customFont.getFontName(), customFont.getStyle(), fontSizeForTextArea1));
            add(textArea1);

            textArea2 = createRoundedTextArea(true);
            textArea2.setFont(new Font(customFont.getFontName(), customFont.getStyle(), 50));
            add(textArea2);

            textArea2.addKeyListener(
                    new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                String text = textArea2.getText();
                                textArea2.setText("");
                                e.consume();
                                String action = text.trim();
                                if (action.equalsIgnoreCase("help")) {
                                    showCheatSheet = true;
                                    repaint();

                                    Timer timer =
                                            new Timer(
                                                    10000,
                                                    e1 -> {
                                                        showCheatSheet = false;
                                                        repaint();
                                                    });
                                    timer.setRepeats(false);
                                    timer.start();
                                }
                                Script.action(action);
                            }
                        }
                    });

            if (screenWidth == 1440 && screenHeight == 900) {
                // Adjustments for 1280x720 resolution
                iconX = 278; // Example scale factor
                iconY = 252;
            } else if (screenWidth == 1920 && screenHeight == 1080) {
                // Adjustments for 1920x1080 resolution (Full HD)
                iconX = 429;
                iconY = 301;
            } /*else if (screenWidth <= 2560 && screenHeight <= 1440) {
                  // Adjustments for 2560x1440 resolution (Quad HD)
                  iconX = (int) (429 * 1.25); // Example scale factor
                  iconY = (int) (301 * 1.25);
              } else {
                  // Adjustments for other resolutions
                  iconX = (int) (429 * 1.5); // Example scale factor
                  iconY = (int) (301 * 1.5);
              }*/
        }

        public void setIconPosition(int newX, int newY) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double screenWidth = screenSize.getWidth();
            double screenHeight = screenSize.getHeight();

            System.out.println(screenHeight + " " + screenWidth);

            if (screenWidth == 1440 && screenHeight == 900) {
                // Adjustments for 1280x720 resolution
                iconX = 278; // Example scale factor
                iconY = 252;
            } else if (screenWidth == 1920 && screenHeight == 1080) {
                iconX = newX;
                iconY = newY;
            } /* else if (screenWidth <= 2560 && screenHeight <= 1440) {
                  iconX = (int) (newX * 1.25); // Example scale factor
                  iconY = (int) (newY * 1.25);
              } else {
                  // Adjustments for other resolutions
                  iconX = (int) (newX * 1.5); // Example scale factor
                  iconY = (int) (newY * 1.5);
              }*/

            repaint();
        }

        public void moveIcon(int deltaX, int deltaY) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double screenWidth = screenSize.getWidth();
            double screenHeight = screenSize.getHeight();

            if (screenWidth == 1440 && screenHeight == 900) {
                // Adjustments for 1280x720 resolution
                iconX += (int) (deltaX * 0.88); // Example scale factor
                iconY += (int) (deltaY * 0.88);
            } else if (screenWidth == 1920 && screenHeight == 1080) {
                // Adjustments for 1920x1080 resolution (Full HD)
                iconX += deltaX;
                iconY += deltaY;
            } /*else if (screenWidth <= 2560 && screenHeight <= 1440) {
                  // Adjustments for 2560x1440 resolution (Quad HD)
                  iconX += (int) (deltaX * 1.25); // Example scale factor
                  iconY += (int) (deltaY * 1.25);
              } else {
                  // Adjustments for other resolutions
                  iconX += (int) (deltaX * 1.5); // Example scale factor
                  iconY += (int) (deltaY * 1.5);
              }*/

            repaint();
        }

        private JTextArea createRoundedTextArea(boolean editable) {
            JTextArea textArea =
                    new JTextArea() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(
                                    RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                            g2.setColor(Color.GRAY);
                            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                            super.paintComponent(g2);
                            g2.dispose();
                        }
                    };
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setOpaque(false);
            textArea.setEditable(editable);
            textArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            return textArea;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Load and draw background image
            g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

            int fullRectWidth = getWidth() - 20;
            int topRectHeight = (int) (getHeight() * 0.80);

            // scale computation
            double xScale = (double) fullRectWidth / bgImage1.getWidth(this);
            double yScale = (double) topRectHeight / bgImage1.getHeight(this);

            // maintain the aspect ratio
            double scale = Math.min(xScale, yScale);

            int scaledWidth = (int) (bgImage1.getWidth(this) * scale);
            int scaledHeight = (int) (bgImage1.getHeight(this) * scale);

            int x = (fullRectWidth - scaledWidth) / 2 + 10;
            int y = (topRectHeight - scaledHeight) / 2 + 10;

            g2d.setClip(new RoundRectangle2D.Float(x, y, scaledWidth, scaledHeight, 20, 20));

            g2d.drawImage(maps[GameMap.currentMapIndex], x, y, scaledWidth, scaledHeight, this);
            g2d.setClip(null);

            int newIconWidth = iconImage.getWidth(this) / 2;
            int newIconHeight = iconImage.getHeight(this) / 2;

            g2d.setColor(Color.BLACK);
            g2d.drawImage(iconImage, iconX, iconY, newIconWidth, newIconHeight, this);

            g2d.drawRoundRect(x, y, scaledWidth, scaledHeight, 20, 20);

            int textArea1Height = (int) ((getHeight() - topRectHeight) * 0.57) - 10;
            int textArea1StartY = topRectHeight + 20;

            int textArea2Height = getHeight() - textArea1StartY - textArea1Height - 20;
            int textArea2StartY = textArea1StartY + textArea1Height + 10;

            int halfWidth = (getWidth() / 2) - 10;
            int startXHalf = (getWidth() - halfWidth) / 2;

            int textArea1Width = (int) (fullRectWidth / 1.3);
            int textArea1X = (fullRectWidth - textArea1Width) / 2 + 10;
            textArea1.setBounds(textArea1X, textArea1StartY, textArea1Width, textArea1Height);
            textArea2.setBounds(startXHalf, textArea2StartY, halfWidth, textArea2Height);

            BufferedImage fogImage =
                    new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D fogGraphics = fogImage.createGraphics();

            applyFogEffect(fogGraphics, getWidth(), getHeight(), iconX + 4, iconY + 4);

            g2d.drawImage(fogImage, 0, 0, this);

            fogGraphics.dispose();

            drawHealthBar(g2d);
            drawCounters(g2d);
            drawInventory(g2d);

            if (showCheatSheet) {
                g2d.setClip(new RoundRectangle2D.Float(x, y, scaledWidth, scaledHeight, 20, 20));
                g2d.drawImage(cheatSheet, x, y, scaledWidth, scaledHeight, this);
                g2d.setClip(null);
            }
        }

        private void applyFogEffect(
                Graphics2D fogGraphics, int width, int height, int playerX, int playerY) {

            fogGraphics.setComposite(AlphaComposite.SrcOver.derive(1f));
            fogGraphics.setColor(new Color(0, 0, 0, 255));
            fogGraphics.fillRect(0, 0, width, height);

            fogGraphics.setComposite(AlphaComposite.DstOut);
            RadialGradientPaint p =
                    new RadialGradientPaint(
                            playerX,
                            playerY,
                            (float) getWidth() / 30,
                            new float[] {0.0f, 1.0f},
                            new Color[] {new Color(0, 0, 0, 255), new Color(0, 0, 0, 0)});
            fogGraphics.setPaint(p);
            fogGraphics.fillOval(
                    (int) (playerX - (float) getWidth() / 30),
                    (int) (playerY - ((float) getWidth() / 30)),
                    (int) ((float) getWidth() / 30 * 2),
                    (int) ((float) getWidth() / 30 * 2));
        }

        private void drawInventory(Graphics2D g2d) {

            Font customFont = GameLauncher.loadCustomFont();
            int imageWidth = (int) (getWidth() / 17.1);
            int imageHeight = (int) (getHeight() / 9.6);
            int padding = getWidth() / 100;
            int interPadding = getWidth() / 50;
            int titleHeight = getHeight() / 20;
            int barX = (int) (getWidth() * 0.0104);
            int barY = titleHeight + padding + getHeight() / 15;

            key =
                    new ImageIcon(
                                    Objects.requireNonNull(
                                            getClass().getResource("/inventoryItems/key.png")))
                            .getImage();
            health =
                    new ImageIcon(
                                    Objects.requireNonNull(
                                            getClass()
                                                    .getResource(
                                                            "/inventoryItems/healthPotion.png")))
                            .getImage();
            sword =
                    new ImageIcon(
                                    Objects.requireNonNull(
                                            getClass().getResource("/inventoryItems/Sword.png")))
                            .getImage();
            shield =
                    new ImageIcon(
                                    Objects.requireNonNull(
                                            getClass().getResource("/inventoryItems/Shield.png")))
                            .getImage();

            Player player = Script.gameMap.getPlayer();
            int[] itemCounts = {player.getItemCount("k"), player.getItemCount("he"), 1, 1};

            int totalWidth = (padding + imageWidth) * 2 + interPadding;
            int totalHeight = (padding + imageHeight) * 2 + interPadding + titleHeight;

            float scaleFactor = (float) getHeight() / 1080;
            int scaleFont = (int) (30 * scaleFactor);
            g2d.setFont(new Font(customFont.getFontName(), Font.PLAIN, scaleFont));

            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRoundRect(barX, barY, totalWidth, totalHeight, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.drawRoundRect(barX, barY, totalWidth, totalHeight, 20, 20);

            g2d.drawString("Inventory:", barX + padding, barY + padding + titleHeight - 35);

            String[] itemTitles = {"Key", "HP Potion", "Sword", "Shield"};
            Image[] images = {key, health, sword, shield};
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 2; col++) {
                    int index = row * 2 + col;
                    int itemX = barX + padding + (imageWidth + interPadding) * col;
                    int itemY = barY + titleHeight + padding + (imageHeight + interPadding) * row;
                    int scaleFont2 = (int) (23 * scaleFactor);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRoundRect(itemX, itemY, imageWidth, imageHeight, 10, 10);
                    g2d.setColor(Color.WHITE);
                    g2d.drawRoundRect(itemX, itemY, imageWidth, imageHeight, 10, 10);
                    g2d.drawString(itemTitles[index], itemX + padding / 2, itemY);
                    g2d.drawImage(images[index], itemX, itemY, imageWidth, imageHeight, this);
                    g2d.setFont(new Font(customFont.getFontName(), Font.PLAIN, scaleFont2));
                    g2d.drawString(
                            String.valueOf(itemCounts[index]),
                            itemX + imageWidth - padding,
                            itemY + imageHeight - padding);
                    g2d.setFont(new Font(customFont.getFontName(), Font.PLAIN, scaleFont));
                }
            }
        }

        private void drawHealthBar(Graphics2D g2d) {

            Font customFont = GameLauncher.loadCustomFont();
            int screenWidth = getWidth();
            int screenHeight = getHeight();

            int barWidth = screenWidth / 6;
            int barHeight = screenHeight / 27;

            int containerPadding = (int) (screenWidth * 0.0052);
            int titleHeight = (int) (screenHeight * 0.0389);

            int barX = (int) (screenWidth * 0.0154);
            int barY = (int) (screenHeight * 0.02) + titleHeight + containerPadding;

            float scaleFactor = (float) getHeight() / 1080;
            int scaleFont = (int) (30 * scaleFactor);

            int containerWidth = barWidth + 2 * containerPadding;
            int containerHeight = barHeight + titleHeight + 2 * containerPadding;
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRoundRect(
                    barX - containerPadding,
                    barY - titleHeight - containerPadding,
                    containerWidth,
                    containerHeight,
                    20,
                    20);
            g2d.setColor(Color.WHITE);
            g2d.drawRoundRect(
                    barX - containerPadding,
                    barY - titleHeight - containerPadding,
                    containerWidth,
                    containerHeight,
                    20,
                    20);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font(customFont.getFontName(), customFont.getStyle(), scaleFont));
            g2d.drawString("Health", barX, barY - containerPadding);

            g2d.setColor(Color.GRAY);
            g2d.fillRoundRect(barX, barY, barWidth, barHeight, 20, 20);

            GradientPaint gradient =
                    new GradientPaint(
                            barX, barY, Color.RED, barX + barWidth, barY, Color.GREEN, false);
            g2d.setPaint(gradient);

            int healthWidth = (int) ((Script.health / 100.0) * barWidth);
            g2d.fillRoundRect(barX, barY, healthWidth, barHeight, 20, 20);

            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(barX, barY, barWidth, barHeight, 20, 20);
        }

        private void drawCounters(Graphics2D g2d) {
            Font customFont = GameLauncher.loadCustomFont();

            int barWidth = (int) getWidth() / 12;
            int counterX = getWidth() - barWidth - 20;
            int counterY = (int) getHeight() / 15;
            int lifeCounterX = getWidth() - barWidth - 20;
            int lifeCounterY = (int) getHeight() / 9;

            float scaleFactor = (float) getHeight() / 1080;
            int scaleFont = (int) (30 * scaleFactor);

            g2d.setFont(new Font(customFont.getFontName(), customFont.getStyle(), scaleFont));

            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRoundRect(counterX - 10, counterY - 30, barWidth, 40, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.drawRoundRect(counterX - 10, counterY - 30, barWidth, 40, 20, 20);

            g2d.setColor(Color.WHITE);
            AtomicInteger totalSeconds = counterValue;
            int hours = totalSeconds.get() / 3600;
            int minutes = (totalSeconds.get() % 3600) / 60;
            int seconds = totalSeconds.get() % 60;
            String timeString = String.format("Time: %02d:%02d:%02d", hours, minutes, seconds);
            g2d.drawString(timeString, counterX, counterY);

            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRoundRect(lifeCounterX - 10, lifeCounterY - 30, barWidth, 40, 20, 20);
            g2d.setColor(Color.WHITE);
            g2d.drawRoundRect(lifeCounterX - 10, lifeCounterY - 30, barWidth, 40, 20, 20);

            g2d.setColor(Color.WHITE);
            g2d.drawString("Lives: " + lifeNumber, lifeCounterX, lifeCounterY);
        }
    }

    public static MainGameWindow getInstance() {
        if (instance == null) {
            instance = new MainGameWindow();
        }
        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> {
                    MainGameWindow game = new MainGameWindow();
                    game.setVisible(true);
                    Script.setGameWindow(game);
                });
    }
}
