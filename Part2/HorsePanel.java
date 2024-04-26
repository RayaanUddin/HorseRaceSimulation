import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class HorsePanel extends JPanel {
    private int horseX;
    public Horse horse;
    static boolean racing = false;
    static int frameRate = 20; // 20ms
    static double maxSpeed = 20.0; // Maximum speed of any horse is 20m/s
    private double time = 0; // Time in seconds.
    public boolean won = false;
    private Color horseColor;
    private LanePanel lane;
    private HashMap<String, JCheckBox> accessories;

    public HorsePanel(LanePanel lane, Horse horse, Color color, HashMap<String, JCheckBox> accessories) {
        setBackground(Color.GREEN);
        this.horse = horse;
        horseColor = color;
        this.lane = lane;
        this.accessories = accessories;
        reset();
        setVisible(true);
    }

    public void reset() {
        horseX = 0;
        time = 0;
        horse.goBackToStart();
        won = false;
        racing = false;
        repaint();
    }

    public void startRace() {
        reset();
        racing = true;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (racing) {
                    move();
                    try {
                        Thread.sleep(frameRate);
                        if (!horse.hasFallen()) {
                            time++;
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    // Draw horse
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHorse(g);
    }

    private void drawHorse(Graphics g) {
        // Body
        g.setColor(horseColor);
        g.fillRect(horseX + 5, getHeight() / 2 - 10, 40, 20);

        // Head
        g.setColor(horseColor);
        g.fillRect(horseX + 45, getHeight() / 2 - 17, 20, 20);

        // Ears
        g.setColor(horseColor);
        int[] earX = {horseX + 65, horseX + 65, horseX + 75};
        int[] earY = {getHeight() / 2 - 17, getHeight() / 2 - 25, getHeight() / 2 - 25};
        g.fillPolygon(earX, earY, 3);

        // Eyes
        g.setColor(Color.BLACK);
        g.fillOval(horseX + 55, getHeight() / 2 - 13, 5, 5);

        // Legs
        if (horse.hasFallen()) {
            g.setColor(Color.RED);
            g.fillRect((horseX + 5)-20, getHeight() / 2 + 10, 30, 10);
            g.fillRect(horseX + 35, getHeight() / 2 + 10, 30, 10);
        } else {
            g.setColor(horseColor);
            g.fillRect(horseX + 5, getHeight() / 2 + 10, 10, 30);
            g.fillRect(horseX + 35, getHeight() / 2 + 10, 10, 30);
        }

        // Tail
        g.setColor(horseColor);
        g.fillRect(horseX, getHeight() / 2 - 5, 5, 10);

        // Saddle
        if (accessories.get("Saddle").isSelected()) {
            g.setColor(new Color(153, 153, 102)); // Saddle brown color
            g.fillRect(horseX + 15, getHeight() / 2 - 7, 30, 10);
        }

        // Horseshoes
        if (accessories.get("Horseshoes").isSelected()) {
            g.setColor(Color.BLACK);
            if (horse.hasFallen()) {
                g.fillOval(horseX-25, getHeight() / 2 + 10, 10, 10);
                g.fillOval(horseX + 65, getHeight() / 2 + 10, 10, 10);
            } else {
                g.fillOval(horseX + 5, getHeight() / 2 + 40, 10, 10);
                g.fillOval(horseX + 35, getHeight() / 2 + 40, 10, 10);
            }
        }

        // Helmet
        if (accessories.get("Helmet").isSelected()) {
            g.setColor(Color.BLUE);
            g.fillRect(horseX + 45, getHeight() / 2 - 17, 20, 5);
        }

        // Sword
        if (accessories.get("Sword").isSelected()) {
            g.setColor(Color.GRAY);
            g.fillRect(horseX + 65, getHeight() / 2 - 17, 5, 20);
        }
    }

    // Move the horse
    public void move() {
        if (!racing || horse.hasFallen()) {
            return;
        }

        // Move horse (based on confidence and speed and random)
        if (Math.random() < horse.getConfidence()) {
            horseX += ((double) getWidth() / (double) StartUI.trackLength) * horse.getConfidence() * maxSpeed;
        } else {
            horseX += ((double) getWidth() / (double) StartUI.trackLength) * horse.getConfidence() * maxSpeed * Math.random();
        }
        horseX += ((double) getWidth()/ (double) StartUI.trackLength) * horse.getConfidence() * maxSpeed;

        if (horseX > getWidth()) {
            // if position is at the end of the track
            if (horseX > getWidth()) {
                won = true;
                horse.setConfidence(horse.getConfidence() * 1.1);
                if (horse.getConfidence() > 1.0) {
                    horse.setConfidence(1.0);
                } else if (horse.getConfidence() == 0.0) {
                    horse.setConfidence(0.1);
                }
                lane.refreshLaneLabel();
                racing = false;
                JOptionPane.showMessageDialog(lane.getTopLevelAncestor(), horse.getName() + " has won");
                System.out.println(horse.getName() + " has won");
            }
        }
        horse.setDistance((int) getHorseDistance());
        if (Math.random() < (0.01*horse.getConfidence()*horse.getConfidence())) {
            horse.fall();
            System.out.println(horse.getName() + " has fallen!");
            lane.refreshLaneLabel();
        }

        repaint();
    }

    public double getHorseDistance() {
        if (won) {
            return StartUI.trackLength;
        }
        return ((double) horseX / (double) getWidth())* StartUI.trackLength;
    }

    public double getFinishingTime() {
        return time;
    }
}