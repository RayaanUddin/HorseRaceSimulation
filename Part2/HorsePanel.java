import javax.swing.*;
import java.awt.*;

public class HorsePanel extends JPanel {
    private int horseX;
    public Horse horse;
    static boolean racing = false;
    static double maxSpeed = 20.0; // Maximum speed of any horse is 20m/s
    static int refreshRate = 20; // Refresh rate of the panel (ms)
    private JLabel laneLabel;
    private String laneName;

    public HorsePanel(JLabel laneLabel, Horse horse) {
        setBackground(Color.GREEN);
        racing = false;
        this.laneLabel = laneLabel;
        laneName = laneLabel.getText();
        this.horse = horse;
        refreshLaneLabel();
    }

    private void refreshLaneLabel() {
        laneLabel.setText(laneName + " | " + horse.getName() + " (Current confidence " + ((int)((horse.getConfidence()*100.0)+0.5)/100.0) + ")");
    }

    public void startRace() {
        racing = true;
        horseX = 0;
        horse.goBackToStart();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (racing) {
                    move();
                    try {
                        Thread.sleep(refreshRate);
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
        // Draw horse body
        g.setColor(new Color(160, 82, 45)); // Brown color
        g.fillRect(horseX + 5, getHeight() / 2 - 10, 40, 20);

        // Draw horse head
        g.setColor(new Color(160, 82, 45)); // Brown color
        g.fillRect(horseX + 45, getHeight() / 2 - 17, 20, 20);

        // Draw horse ears
        g.setColor(new Color(139, 69, 19)); // Saddle brown color
        int[] earX = {horseX + 65, horseX + 65, horseX + 75};
        int[] earY = {getHeight() / 2 - 17, getHeight() / 2 - 25, getHeight() / 2 - 25};
        g.fillPolygon(earX, earY, 3);

        // Draw horse eyes
        g.setColor(Color.BLACK);
        g.fillOval(horseX + 55, getHeight() / 2 - 13, 5, 5);

        // Draw horse legs
        if (horse.hasFallen()) {
            g.setColor(Color.RED);
            g.fillRect((horseX + 5)-20, getHeight() / 2 + 10, 30, 10);
            g.fillRect(horseX + 35, getHeight() / 2 + 10, 30, 10);
        } else {
            g.setColor(new Color(160, 82, 45)); // Brown color
            g.fillRect(horseX + 5, getHeight() / 2 + 10, 10, 30);
            g.fillRect(horseX + 35, getHeight() / 2 + 10, 10, 30);
        }

        // Draw horse tail
        g.setColor(new Color(160, 82, 45)); // Brown color
        g.fillRect(horseX, getHeight() / 2 - 5, 5, 10);
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
                System.out.println("And the winner is "+ horse.getName());
                refreshLaneLabel();
                horse.setConfidence(horse.getConfidence() * 1.1);
                if (horse.getConfidence() > 1.0) {
                    horse.setConfidence(1.0);
                } else if (horse.getConfidence() == 0.0) {
                    horse.setConfidence(0.1);
                }
                //JOptionPane.showMessageDialog(frame, horse.getName() + "has won");
                racing = false;
            }
        }
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
}