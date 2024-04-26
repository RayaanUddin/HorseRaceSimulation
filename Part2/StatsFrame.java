import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

public class StatsFrame extends JFrame{
    
    StatsData[] statsData;

    public StatsFrame(StatsData[] statsData) {
        super();
        this.statsData = statsData;
        setTitle("Horse Racing Stats");
        setSize(400, 500);
        getContentPane().setLayout(new BorderLayout());

        // Create Stats panel
        JPanel panel = new JPanel(new GridLayout(statsData.length, 1));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(panel);


        // Add horse stats to panel
        for (StatsData data : statsData) {
            // Create a panel for a horse's stats
            JPanel horseStatsPanel = new JPanel(new BorderLayout());
            horseStatsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(horseStatsPanel);

            // Horse Name as Title
            JLabel horseName = new JLabel(data.horseName + " Stats");
            horseName.setFont(new Font("Arial", Font.BOLD, 20));
            horseName.setHorizontalAlignment(JLabel.CENTER);
            horseStatsPanel.add(horseName, BorderLayout.NORTH);

            // Horse Stats
            JPanel statsPanel = new JPanel(new GridLayout(3, 1));
            horseStatsPanel.add(statsPanel, BorderLayout.CENTER);
            statsPanel.add(new JLabel("Distance Travelled: " + data.distanceTravelled + " meters"));
            statsPanel.add(new JLabel("Speed: " + data.speed + " m/s"));
            statsPanel.add(new JLabel("Time: " + data.time + " seconds"));

            // Horse Won?
            JLabel wonLabel = new JLabel("Horse " + (data.won ? "won" : "lost"));
            if (data.won) {
                wonLabel.setForeground(Color.GREEN);
            } else {
                wonLabel.setForeground(Color.RED);
            }
            horseStatsPanel.add(wonLabel, BorderLayout.SOUTH);
        }

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // File menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Save stats
        JMenuItem saveStats = new JMenuItem("Save Stats");
        fileMenu.add(saveStats);
        saveStats.addActionListener(e -> {
            if (statsData.length == 0) {
                JOptionPane.showMessageDialog(this, "No stats to save", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // Save stats to a file
            System.out.println("Saving stats to a file");
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                StringBuilder encodedStats = new StringBuilder();
                for (StatsData data : statsData) {
                    encodedStats.append(data.encodeData()).append(";");
                }
                System.out.println("Encoded stats: " + encodedStats);
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    writer.print(encodedStats);
                    JOptionPane.showMessageDialog(null, "File saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Open stats
        JMenuItem openStats = new JMenuItem("Open Stats");
        fileMenu.add(openStats);
        openStats.addActionListener(e -> {
            openStatsFromFile();
        });

        // Exit
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    // Open stats from a file
    public static StatsFrame openStatsFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try {
                // Read stats from file
                StringBuilder encodedStats = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        encodedStats.append(line);
                    }
                }
                System.out.println("Encoded stats: " + encodedStats);
                if (encodedStats.length() == 0) {
                    JOptionPane.showMessageDialog(null, "No stats to load", "Information", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
                String[] stats = encodedStats.toString().split(";");
                StatsData[] decodedStats = new StatsData[stats.length];
                for (int i = 0; i < stats.length; i++) {
                    decodedStats[i] = new StatsData(stats[i]);
                }
                return new StatsFrame(decodedStats);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }
}
