import javax.swing.*;
import java.awt.*;

public class StartUI {

    static Integer laneNumber;
    static Integer trackLength = 100;
    private static StartUI startFrame;
    private JFrame frame;
    private JPanel panel;

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
        JLabel label2 = new JLabel("Length of the track (meters):");
        gbc.gridx = 0;
        formPanel.add(label2, gbc);
        JTextField trackLen_textField = new JTextField(15);
        if (trackLength != null) {
            trackLen_textField.setText(trackLength.toString());
        }
        gbc.gridx = 1;
        formPanel.add(trackLen_textField, gbc);
        // Create Button Start
        JButton startButton = new JButton("START");
        startButton.addActionListener(e -> {
            System.out.println("Start Button Clicked with lane number: " + laneNumber_Slider.getValue() + " and track length: " + trackLen_textField.getText());
            try {
                laneNumber = laneNumber_Slider.getValue();
                trackLength = Integer.parseInt(trackLen_textField.getText());
                new RaceUI(this.frame);
                return;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this.frame, "Invalid input for track length", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
        panel.add(startButton, BorderLayout.SOUTH);
    }

    public void display() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(400, 200);
        frame.setResizable(false);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static StartUI startFrame(JFrame frame) {
        if (startFrame == null) {
            return new StartUI(frame);
        } else {
            return startFrame;
        }
    }
    public static void main(String[] args) {
        new StartUI(null).display();
    }
}
