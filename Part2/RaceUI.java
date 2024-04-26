import javax.swing.*;
import java.awt.*;

/**
 * RaceUI is a class that represents the user interface for the horse racing game. It displays all the lanes and options to initiate the race, view stats, or go back to main menu.
 * 
 * @author Rayaan Uddin
 * @version 1.0
 * @see StartUI
 * @see StatsFrame
 * @see LanePanel
 * 
 */
public class RaceUI {
    private JFrame frame;
    private JPanel racePanel;
    LanePanel[] lanes;

    /**
     * Constructor for RaceUI objects. Creates a new RaceUI with the frame passed as an argument. If no frame is passed, a new frame is created. This should not occur as the frame should be passed from StartUI.
     * @param frame
     */
    public RaceUI(JFrame frame) {
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
            new StatsFrame(StatsData.createStatsData(lanes));
            
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

    // Go back to the start screen (Main Menu)
    private void goBackToStart() {
        StartUI.startFrame(this.frame).display();
    }

    // Create race with lanes
    private JPanel createRace() {
        if (StartUI.laneNumber == null || StartUI.trackLength == null) {
            goBackToStart();
            return null;
        } else {
            JPanel racePanel = new JPanel(new GridLayout(StartUI.laneNumber, 1));
            lanes = new LanePanel[StartUI.laneNumber];
            for (int i = 0; i < StartUI.laneNumber; i++) {
                lanes[i] = new LanePanel(i+1);
                racePanel.add(lanes[i]);
            }
            return racePanel;
        }
    }

    // Starts the race
    private void startRaceGUI() {
        for (LanePanel lane : lanes) {
            if (lane.horsePanel != null) {
                System.out.println("Starting Race");
                lane.horsePanel.startRace();
                lane.horsePanel.revalidate();
            }
        }
    }
}
