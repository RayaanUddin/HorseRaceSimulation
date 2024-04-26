import java.awt.*;
import javax.swing.*;
import java.util.HashMap;

/**
 * LanePanel is a subclass of JPanel that represents a lane on the race. It holds the horsePanel and trackPanel for the lane and allows the user to add and remove a horse to the lane using a button.
 * The lane has a lane number and a label to display the lane number and the horse's name and confidence level if a horse is added.
 * 
 * @author Rayaan Uddin
 * @version 1.0
 * @see JPanel
 * @see HorsePanel
 * @see CreateHorseFrame
 * 
 */
public class LanePanel extends JPanel {
    HorsePanel horsePanel;
    private JPanel trackPanel;
    private JLabel laneLabel;
    private int laneNumber;

    private JButton addHorseButton;

    /**
     * Constructor for LanePanel objects. Creates a new LanePanel with a lane number and a label for the lane number. The trackPanel is created to hold the horsePanel and addHorseButton.
     * @param laneNumber
     */
    public LanePanel(int laneNumber) {
        super();
        System.out.println("Creating lane " + laneNumber);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.laneNumber = laneNumber;
        laneLabel = new JLabel("Lane " + laneNumber);
        add(laneLabel, BorderLayout.NORTH);
        trackPanel = new JPanel(new BorderLayout());
        trackPanel.setBackground(Color.GREEN);
        add(trackPanel, BorderLayout.CENTER);

        // Add an add horse button to the track
        addHorseButton = new JButton("Add Horse");
        addHorseButton.addActionListener(e -> {
            new CreateHorseFrame(this);
        });
        trackPanel.add(addHorseButton, BorderLayout.SOUTH);
    }

    // Add a horse to the lane
    public void addHorse(String horseName, double horseConfidence, Color horseColor, HashMap<String, JCheckBox> accessories) {
        if (horsePanel == null) {
            trackPanel.remove(addHorseButton);
            horsePanel = new HorsePanel(this, new Horse('H', horseName, horseConfidence), horseColor, accessories);
            trackPanel.add(horsePanel, BorderLayout.CENTER);
            refreshLaneLabel();

            // Change add horse to remove horse
            JButton removeHorseButton = new JButton("Remove Horse");
            removeHorseButton.addActionListener(e -> {
                trackPanel.remove(horsePanel);
                trackPanel.remove(removeHorseButton);
                trackPanel.add(addHorseButton, BorderLayout.SOUTH);
                trackPanel.revalidate();
                trackPanel.repaint();
                horsePanel = null;
                refreshLaneLabel();
            });
            trackPanel.add(removeHorseButton, BorderLayout.SOUTH);
        }
    }

    // Refresh the lane label to show the horse's name and confidence level or just the lane number
    public void refreshLaneLabel() {
        if (horsePanel == null) {
            laneLabel.setText("Lane " + laneNumber);
            return;
        }
        laneLabel.setText("Lane " + laneNumber + " | " + horsePanel.horse.getName() + " (Current confidence " + ((int)((horsePanel.horse.getConfidence()*100.0)+0.5)/100.0) + ")");
    }
}
