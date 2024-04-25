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
        frame.setSize(800, 600);
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
                JPanel lanePanel = new JPanel(new BorderLayout());
                lanePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel laneLabel = new JLabel("Lane " + (i + 1));
                lanePanel.add(laneLabel, BorderLayout.NORTH);
                JPanel trackPanel = new JPanel(new BorderLayout());
                trackPanel.setBackground(Color.GREEN);
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
                    horsePanels[i] = new HorsePanel(laneLabel, new Horse('H', horseName, horseConfidence));
                }
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

    public static void main(String[] args) {
        new RaceFrame(null);
    }
}
