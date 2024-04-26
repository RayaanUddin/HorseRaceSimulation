import java.awt.*;
import javax.swing.*;

public class StatsFrame {
    public StatsFrame(LanePanel[] lanes) {
        JFrame frame = new JFrame("Horse Statistics");
        frame.setSize(400, 500);
        frame.getContentPane().setLayout(new BorderLayout());

        // Get horses panels count from lanes
        int horsePanelsCount = 0;
        for (LanePanel lane : lanes) {
            if (lane.horsePanel != null) {
                horsePanelsCount++;
            }
        }

        // Create Stats panel
        JPanel panel = new JPanel(new GridLayout(horsePanelsCount, 1));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(panel);

        // Add horse stats to panel
        for (LanePanel lane : lanes) {
            if (lane.horsePanel == null) {
                continue;
            }

            // Create a panel for a horse's stats
            JPanel horseStatsPanel = new JPanel(new BorderLayout());
            horseStatsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(horseStatsPanel);

            // Horse Name as Title
            JLabel horseName = new JLabel(lane.horsePanel.horse.getName() + " Stats");
            horseName.setFont(new Font("Arial", Font.BOLD, 20));
            horseName.setHorizontalAlignment(JLabel.CENTER);
            horseStatsPanel.add(horseName, BorderLayout.NORTH);

            // Horse Stats
            JPanel statsPanel = new JPanel(new GridLayout(3, 1));
            horseStatsPanel.add(statsPanel, BorderLayout.CENTER);
            statsPanel.add(new JLabel("Distance Travelled: " + lane.horsePanel.horse.getDistanceTravelled() + " meters"));
            statsPanel.add(new JLabel("Speed: " + ((int)(((lane.horsePanel.horse.getDistanceTravelled()/ lane.horsePanel.getFinishingTime())*100.0)+0.5)/100.0) + " m/s"));
            statsPanel.add(new JLabel("Time: " + lane.horsePanel.getFinishingTime() + " seconds"));

            // Horse Won?
            JLabel wonLabel = new JLabel("Horse " + (lane.horsePanel.won ? "won" : "lost"));
            horseStatsPanel.add(wonLabel, BorderLayout.SOUTH);
        }
        frame.setVisible(true);
    }
}
