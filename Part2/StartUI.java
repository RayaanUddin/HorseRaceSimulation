import javax.swing.*;
import java.awt.*;

/**
 * StartUI is a class that represents the user interface for the start menu of the horse racing game. It allows the user to select the number of lanes and the length of the track before starting the race.
 * 
 * @author Rayaan Uddin
 * @version 1.0
 * @see RaceUI
 * 
 */
public class StartUI {

    static Integer laneNumber;
    static Integer trackLength = 1000;
    private static StartUI startFrame;
    private JFrame frame;
    private JPanel panel;

    /**
     * Constructor for StartUI objects. Creates a new StartUI with the frame passed as an argument. If no frame is passed, a new frame is created.
     * @param frame
     */
    private StartUI(JFrame frame) {
        if (frame == null) {
            frame = new JFrame("Horse Racing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        this.frame = frame;

        // Create Main Panel
        panel = new JPanel(new BorderLayout());
        frame.add(panel);

        // Create Title Panel
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.RED);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        JLabel title = new JLabel("Horse Race");
        titlePanel.add(title, c);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titlePanel, BorderLayout.NORTH);

        // Create scrollpane for form
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel formPanel = new JPanel(new GridBagLayout());
        scrollPane.setViewportView(formPanel);
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create lane number slider
        JLabel label = new JLabel("Number of lanes on track:");
        formPanel.add(label, gbc);
        gbc.gridx = 1;
        JSlider laneNumber_Slider = new JSlider(JSlider.HORIZONTAL, 2, 10, 2);
        laneNumber_Slider.setPaintLabels(true);
        laneNumber_Slider.setSnapToTicks(true);
        laneNumber_Slider.setLabelTable(laneNumber_Slider.createStandardLabels(1));
        if (laneNumber != null) {
            laneNumber_Slider.setValue(laneNumber);
        }
        formPanel.add(laneNumber_Slider, gbc);
        gbc.gridy = 1;

        // Create track length slider
        JLabel label2 = new JLabel("Length of the track ("+trackLength+" m):");
        gbc.gridx = 0;
        formPanel.add(label2, gbc);
        JSlider trackLen_Slider = new JSlider(JSlider.HORIZONTAL, 100, 1500, trackLength);
        trackLen_Slider.setMajorTickSpacing(100);
        trackLen_Slider.setSnapToTicks(true);
        trackLen_Slider.addChangeListener(e -> {
            label2.setText("Length of the track ("+trackLen_Slider.getValue()+" m):");
        });
        gbc.gridx = 1;
        formPanel.add(trackLen_Slider, gbc);

        // Create Button Start
        JButton startButton = new JButton("START");
        startButton.addActionListener(e -> {
            System.out.println("Start Button Clicked with lane number: " + laneNumber_Slider.getValue() + " and track length: " + trackLen_Slider.getValue());
            try {
                laneNumber = laneNumber_Slider.getValue();
                trackLength = trackLen_Slider.getValue();
                new RaceUI(this.frame);
                return;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.frame, "Invalid input for track length", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
        panel.add(startButton, BorderLayout.SOUTH);
    }

    // Display the panel for main menu. Skeleton class.
    public void display() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(400, 200);
        frame.setResizable(false);
        frame.add(panel);
        frame.setVisible(true);
    }

    // Create a new StartUI object if it doesn't exist, otherwise return the existing object
    public static StartUI startFrame(JFrame frame) {
        if (startFrame == null) {
            return new StartUI(frame);
        } else {
            return startFrame;
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        new StartUI(null).display();
    }
}
