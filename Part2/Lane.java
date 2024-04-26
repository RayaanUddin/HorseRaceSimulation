import java.awt.*;
import javax.swing.*;
import java.util.HashMap;

public class Lane extends JPanel {
    HorsePanel horsePanel;
    private JPanel trackPanel;
    private JLabel laneLabel;
    private int laneNumber;

    private JButton addHorseButton;

    public Lane(int laneNumber) {
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
            new CreateHorse(this);
        });
        trackPanel.add(addHorseButton, BorderLayout.SOUTH);
    }

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

    public void refreshLaneLabel() {
        if (horsePanel == null) {
            laneLabel.setText("Lane " + laneNumber);
            return;
        }
        laneLabel.setText("Lane " + laneNumber + " | " + horsePanel.horse.getName() + " (Current confidence " + ((int)((horsePanel.horse.getConfidence()*100.0)+0.5)/100.0) + ")");
    }
}
