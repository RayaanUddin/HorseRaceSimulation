import javax.swing.*;
import java.awt.*;

public class RaceFrame {
    private JFrame frame;
    private JPanel racePanel;
    Lane[] lanes;

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
            startRaceGUI();
        });
        JMenu optionMenu = new JMenu("Options");
        menuBar.add(optionMenu);
        JMenuItem startFrame = new JMenuItem("Main Menu");
        JMenuItem getStats = new JMenuItem("Get Stats");
        optionMenu.add(getStats);
        getStats.addActionListener(e -> {
            new StatsFrame(lanes);
        });
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
            lanes = new Lane[StartFrame.laneNumber];
            for (int i = 0; i < StartFrame.laneNumber; i++) {
                lanes[i] = new Lane(i+1);
                racePanel.add(lanes[i]);
            }
            return racePanel;
        }
    }

    private void startRaceGUI() {
        for (Lane lane : lanes) {
            if (lane.horsePanel != null) {
                System.out.println("Starting Race");
                lane.horsePanel.startRace();
                lane.horsePanel.revalidate();
            }
        }
    }

    public static void main(String[] args) {
        new RaceFrame(null);
    }
}
