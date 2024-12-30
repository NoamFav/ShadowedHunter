package com.ShadowedHunter;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

import javax.swing.*;

public class MainMenu extends JFrame {

    private static final Color BUTTON_COLOR = new Color(71, 18, 18);
    private static final int ROUNDNESS = 25;
    // Images and font used in the main menu
    private final Image backgroundImage;
    private final Image titleImage;
    GameLauncher bgMusic;

    Font customFont = GameLauncher.loadCustomFont();

    public MainMenu() {
        backgroundImage = loadImage("/ground.jpg");
        titleImage = loadImage("/Title.png");

        setTitle("Shadowed Hunter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // This will remove the title bar, ensuring the window goes fully fullscreen.
        setUndecorated(true);

        // Get the default screen device and set the JFrame to fullscreen.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            // Fallback to maximize if true fullscreen isn't supported
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        ImagePanel imagePanel = new ImagePanel();
        setLayout(new BorderLayout());
        add(imagePanel, BorderLayout.CENTER);

        bgMusic = new GameLauncher();
        bgMusic.play("VoidBgMusic.mp3", 20f);

        setVisible(true);
    }

    private Image loadImage(String path) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            throw new RuntimeException("Resource not found: " + path);
        }
        return new ImageIcon(imageUrl).getImage();
    }

    private JButton createColoredButton(String text, boolean isFixedSize) {
        JButton button =
                new JButton(text) {
                    @Override
                    public Dimension getPreferredSize() {
                        if (!isFixedSize) {
                            int width = (int) (MainMenu.this.getWidth() * 0.5 * 0.7);
                            int height = (int) (MainMenu.this.getHeight() / 6 * 0.7);
                            return new Dimension(width, height);
                        } else return super.getPreferredSize();
                    }

                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setColor(getBackground());
                        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ROUNDNESS, ROUNDNESS);
                        super.paintComponent(g);
                    }

                    @Override
                    protected void paintBorder(Graphics g) {
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2d.setColor(Color.WHITE);
                        g2d.drawRoundRect(
                                0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
                    }

                    @Override
                    public boolean contains(int x, int y) {
                        return new RoundRectangle2D.Float(
                                        0, 0, getWidth(), getHeight(), ROUNDNESS, ROUNDNESS)
                                .contains(x, y);
                    }
                };

        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(customFont);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        return button;
    }

    private class ImagePanel extends JPanel {
        private final JButton leftButton, rightButton, closeButton;

        public ImagePanel() {
            setLayout(null);

            leftButton = createColoredButton("NEW GAME", false);
            rightButton = createColoredButton("LOAD GAME", false);
            leftButton.addActionListener(
                    e -> {
                        bgMusic.stopMusic();
                        MainGameWindow.getInstance();
                        MainMenu.this.dispose();
                    });
            rightButton.addActionListener(
                    e -> {
                        bgMusic.stopMusic();
                        MainGameWindow.getInstance();
                        SaveLoadSystem.loadGame("Save.txt");
                        MainMenu.this.dispose();
                    });

            Font currentFont = leftButton.getFont();
            leftButton.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), 80));

            currentFont = rightButton.getFont();
            rightButton.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), 80));

            closeButton = createColoredButton("CLOSE", true);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int closeButtonWidth = (int) (screenSize.width * 0.1);
            int closeButtonHeight = (int) (0.2 * closeButtonWidth);
            closeButton.setPreferredSize(new Dimension(closeButtonWidth, closeButtonHeight));
            closeButton.addActionListener(e -> System.exit(0));

            add(leftButton);
            add(rightButton);
            add(closeButton);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            if (titleImage != null) {
                double titleScaleFactor = (double) getWidth() / titleImage.getWidth(this);
                int scaledTitleHeight = (int) (titleImage.getHeight(this) * titleScaleFactor);
                g.drawImage(titleImage, 0, 80, getWidth(), scaledTitleHeight + 30, this);
            }

            int leftButtonX = (getWidth() / 4) - (leftButton.getPreferredSize().width / 2);
            int rightButtonX = (3 * getWidth() / 4) - (rightButton.getPreferredSize().width / 2);
            int buttonY = (5 * getHeight() / 6) - (leftButton.getPreferredSize().height / 2);

            leftButton.setBounds(
                    leftButtonX,
                    buttonY,
                    leftButton.getPreferredSize().width,
                    leftButton.getPreferredSize().height);
            rightButton.setBounds(
                    rightButtonX,
                    buttonY,
                    rightButton.getPreferredSize().width,
                    rightButton.getPreferredSize().height);

            int closeButtonX = getWidth() - closeButton.getPreferredSize().width - 10;
            int closeButtonY = 10;
            closeButton.setBounds(
                    closeButtonX,
                    closeButtonY,
                    closeButton.getPreferredSize().width,
                    closeButton.getPreferredSize().height);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
