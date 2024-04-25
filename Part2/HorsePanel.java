import javax.swing.*;
import java.awt.*;

public class HorsePanel extends JPanel {
    private int horseX;
    public Horse horse;
    static boolean racing = false;
    static double maxSpeed = 20.0; // Maximum speed of any horse is 20m/s
    private double time = 0; // Time in seconds.
    private JLabel laneLabel;
    private String laneName;
    public boolean won = false;
    private JFrame frame;
    private Color horseColor;

    public HorsePanel(JLabel laneLabel, Horse horse, JFrame frame, Color color) {
        setBackground(Color.GREEN);
        racing = false;
        this.laneLabel = laneLabel;
        laneName = laneLabel.getText();
        this.horse = horse;
        this.frame = frame;
        refreshLaneLabel();
        horseColor = color;
    }

    private void refreshLaneLabel() {
        laneLabel.setText(laneName + " | " + horse.getName() + " (Current confidence " + ((int)((horse.getConfidence()*100.0)+0.5)/100.0) + ")");
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
                        Thread.sleep(20);
                        if (!horse.hasFallen()) {
                            time = time + 0.5;
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
        g.setColor(new Color(153, 153, 102)); // Saddle brown color
        g.fillRect(horseX + 15, getHeight() / 2 - 7, 30, 10);
    }

    // Move the horse
    public void move() {
        if (!racing || horse.hasFallen()) {
            return;
        }

        horseX += (((double) getWidth()/ (double) StartFrame.trackLength) * maxSpeed) * Math.random() * horse.getConfidence();

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
                refreshLaneLabel();
                racing = false;
                JOptionPane.showMessageDialog(frame, horse.getName() + " has won");
                System.out.println(horse.getName() + " has won");
            }
        }
        horse.setDistance((int) getHorseDistance());
        double controller = 0.1;
        if (getWidth() < StartFrame.trackLength) {
            controller = ((double) getWidth()*0.01/ (double) StartFrame.trackLength);
        }
        if (Math.random() < (controller*horse.getConfidence()*horse.getConfidence())) {
            horse.fall();
            System.out.println(horse.getName() + " has fallen!");
            refreshLaneLabel();
        }

        repaint();
    }

    public double getHorseDistance() {
        if (won) {
            return StartFrame.trackLength;
        }
        return ((double) horseX / (double) getWidth())* StartFrame.trackLength;
    }

    public double getFinishingTime() {
        return time;
    }
}