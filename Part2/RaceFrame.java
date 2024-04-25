import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceFrame {
    private JFrame frame;
    private JPanel racePanel;
    private HorsePanel[] horsePanels;

    public RaceFrame(JFrame frame) {
        if (frame == null) {
            frame = new JFrame("Horse Racing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        this.frame = frame;
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(400, 500);
        frame.setResizable(false);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenuItem startRace = new JMenuItem("Start Race");
        menuBar.add(startRace);
        startRace.addActionListener(e -> {
            startRace();
        });
        JMenu optionMenu = new JMenu("Options");
        menuBar.add(optionMenu);
        JMenuItem startFrame = new JMenuItem("Main Menu");
        optionMenu.add(startFrame);
        startFrame.addActionListener(e -> {
            System.out.println("Going to Main Menu");
            menuBar.setVisible(false);
            goBackToStart();
        });

        // Race
        racePanel = createRace();
        if (racePanel != null) {
            System.out.println(racePanel.getComponentCount());
        }
        frame.add(racePanel);

        frame.setVisible(true);
    }

    private void goBackToStart() {
        StartFrame.startFrame(this.frame).display();
    }

    private JPanel createRace() {
        if (StartFrame.laneNumber == null || StartFrame.trackLength == null) {
            goBackToStart();
            return null;
        } else {
            JPanel racePanel = new JPanel(new GridLayout(StartFrame.laneNumber, 1));
            horsePanels = new HorsePanel[StartFrame.laneNumber];
            for (int i = 0; i < StartFrame.laneNumber; i++) {
                String horseName = JOptionPane.showInputDialog("Enter Horse Name for track " + (i + 1) + ": (Leave blank to skip)");
                double horseConfidence = 0.5;
                if (!horseName.isEmpty()) {
                    System.out.println("Horse Name: " + horseName);
                    boolean run = true;
                    while (run) {
                        run = false;
                        String horseConfidence_str = JOptionPane.showInputDialog("Enter Horse Confidence for track " + (i + 1) + ": (Leave blank for 0.5)");
                        if (!horseConfidence_str.isEmpty()) {
                            try {
                                horseConfidence = Double.parseDouble(horseConfidence_str);
                                if (horseConfidence < 0 || horseConfidence > 1) {
                                    JOptionPane.showMessageDialog(this.frame, "Invalid confidence level. Must be a decimal between 0 and 1.", "Error", JOptionPane.ERROR_MESSAGE);
                                    run = true;
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this.frame, "Invalid confidence level. Must be a decimal between 0 and 1.", "Error", JOptionPane.ERROR_MESSAGE);
                                run = true;
                            }
                        }
                    }
                    horsePanels[i] = new HorsePanel();
                    horsePanels[i].horse = new Horse('H', horseName, horseConfidence);
                }
                JPanel lanePanel = new JPanel(new BorderLayout());
                lanePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel laneLabel = (horseName.isEmpty() ? (new JLabel("Lane " + (i + 1))) : (new JLabel("Lane " + (i + 1) + " | " + horseName + " (Confidence Level " + horseConfidence + ")")));
                lanePanel.add(laneLabel, BorderLayout.NORTH);
                JPanel trackPanel = new JPanel(new BorderLayout());
                trackPanel.setBackground(Color.GREEN);
                if (horsePanels[i] != null) {
                    trackPanel.add(horsePanels[i], BorderLayout.CENTER);
                    horsePanels[i].setVisible(true);
                }
                lanePanel.add(trackPanel, BorderLayout.CENTER);
                racePanel.add(lanePanel);
            }
            return racePanel;
        }
    }

    private void startRace() {
        for (HorsePanel horsePanel : horsePanels) {
            if (horsePanel != null) {
                System.out.println("Starting Race");
                horsePanel.startRace();
                horsePanel.revalidate();
            }
        }
    }

    private class HorsePanel extends JPanel {
        private int horseX;
        public Horse horse;
        static boolean racing = false;
        static int maxSpeed = 20; // Maximum speed of any horse is 20m/s
        static int refreshRate = 20; // Refresh rate of the panel (ms)

        public HorsePanel() {
            setBackground(Color.GREEN);
            horseX = 0;
            racing = false;
        }

        public void startRace() {
            racing = true;
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
            g.setColor(new Color(160, 82, 45)); // Brown color
            g.fillRect(horseX + 5, getHeight() / 2 + 10, 10, 30);
            g.fillRect(horseX + 35, getHeight() / 2 + 10, 10, 30);

            // Draw horse tail
            g.setColor(new Color(160, 82, 45)); // Brown color
            g.fillRect(horseX, getHeight() / 2 - 5, 5, 10);
        }

        // Move the horse
        public void move() {
            System.out.println("Moving horse");
            horseX += ((maxSpeed) * horse.getConfidence());
            if (horseX > getWidth()) {
                // if position is at the end of the track
                if (horseX > getWidth()) {
                    JOptionPane.showMessageDialog(frame, horse.getName() + "has won");
                    racing = false;
                }
                horseX = 0; // Reset position when horse goes off screen
            }
            repaint();
        }
    }
    public static void main(String[] args) {
        new RaceFrame(null);
    }
}
