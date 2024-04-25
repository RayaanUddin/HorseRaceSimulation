import java.awt.*;
import javax.swing.*;

public class StatsFrame {
    public StatsFrame(HorsePanel[] horsePanels) {
        JFrame frame = new JFrame("Horse Statistics");
        frame.setSize(400, 500);
        frame.getContentPane().setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(horsePanels.length, 1));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(panel);
        for (HorsePanel horsePanel : horsePanels) {
            if (horsePanel == null) {
                continue;
            }
            JPanel horseStatsPanel = new JPanel(new BorderLayout());
            horseStatsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(horseStatsPanel);

            // Horse Name as Title
            JLabel horseName = new JLabel(horsePanel.horse.getName() + " Stats");
            horseName.setFont(new Font("Arial", Font.BOLD, 20));
            horseName.setHorizontalAlignment(JLabel.CENTER);
            horseStatsPanel.add(horseName, BorderLayout.NORTH);

            // Horse Stats
            JPanel statsPanel = new JPanel(new GridLayout(3, 1));
            horseStatsPanel.add(statsPanel, BorderLayout.CENTER);
            statsPanel.add(new JLabel("Distance Travelled: " + horsePanel.horse.getDistanceTravelled() + " meters"));
            statsPanel.add(new JLabel("Speed: " + ((int)(((horsePanel.horse.getDistanceTravelled()/ horsePanel.getFinishingTime())*100.0)+0.5)/100.0) + " m/s"));
            statsPanel.add(new JLabel("Time: " + horsePanel.getFinishingTime() + " seconds"));

            // Horse Won?
            JLabel wonLabel = new JLabel("Horse " + (horsePanel.won ? "won" : "lost"));
            horseStatsPanel.add(wonLabel, BorderLayout.SOUTH);
        }
        frame.setVisible(true);
    }
}
