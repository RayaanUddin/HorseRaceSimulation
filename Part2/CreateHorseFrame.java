import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CreateHorseFrame extends JFrame{
    Color color = new Color(160, 82, 45);
    public CreateHorseFrame(LanePanel lane) {
        setSize(300, 350);
        getContentPane().setLayout(new BorderLayout());
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        // Horse name
        panel.add(new JLabel("Horse Name: "), c);
        JTextField horseName = new JTextField(15);
        c.gridx = 1;
        panel.add(horseName, c);

        // Confidence level between 0 and 1 to 1dp
        c.gridy = 1;
        c.gridx = 0;
        panel.add(new JLabel("Horse Confidence Level: "), c);
        JSlider horseConfidenceLevel = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        horseConfidenceLevel.setMajorTickSpacing(1);
        horseConfidenceLevel.setPaintTicks(true);
        horseConfidenceLevel.setPaintLabels(true);
        horseConfidenceLevel.setSnapToTicks(true);
        c.gridx = 1;
        panel.add(horseConfidenceLevel, c);

        // Horse color
        c.gridy = 2;
        c.gridx = 0;
        panel.add(new JLabel("Horse Color: "), c);
        JButton colorPickerButton = new JButton("Pick a Color");
        c.gridx = 1;
        panel.add(colorPickerButton, c);
        colorPickerButton.addActionListener(e -> {
            color = JColorChooser.showDialog(this, "Choose a color", new Color(160, 82, 45));
            if (color == null) {
                color = new Color(160, 82, 45);
            } else {
                colorPickerButton.setForeground(color);
            }
        });

        // Horse accessories
        c.gridy = 3;
        c.gridx = 0;
        panel.add(new JLabel("Horse Accessories: "), c);
        HashMap<String, JCheckBox> accessories = new HashMap<>();
        JCheckBox saddle = new JCheckBox("Saddle");
        JCheckBox horseshoes = new JCheckBox("Horseshoes");
        JCheckBox helmet = new JCheckBox("Helmet");
        JCheckBox sword = new JCheckBox("Sword");
        accessories.put("Saddle", saddle);
        accessories.put("Horseshoes", horseshoes);
        accessories.put("Helmet", helmet);
        accessories.put("Sword", sword);
        JPanel accessoryPanel = new JPanel();
        accessoryPanel.setLayout(new BoxLayout(accessoryPanel, BoxLayout.Y_AXIS));
        for (JCheckBox accessory : accessories.values()) {
            accessoryPanel.add(accessory);
        }
        c.gridx = 1;
        panel.add(accessoryPanel, c);
        
        // Create title for the frame
        setTitle("Horse Creator");
        JLabel title = new JLabel("Horse Creator");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(title, BorderLayout.NORTH);

        // Add add horse button
        JButton addHorseButton = new JButton("Add Horse");
        addHorseButton.addActionListener(e -> {
            if (horseName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a horse name", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println("Horse Name: " + horseName.getText());
            System.out.println("Horse Confidence Level: " + horseConfidenceLevel.getValue());
            System.out.println("Horse Color: " + colorPickerButton.getBackground());
            for (String accessory : accessories.keySet()) {
                System.out.println(accessory + ": " + accessories.get(accessory).isSelected());
            }
            // Confirmation
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to add this horse to lane?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                double confidence;
                try {
                    confidence = (double) horseConfidenceLevel.getValue() / 10.0;
                } catch (NumberFormatException ex) {
                    confidence = 0.0;
                }
                lane.addHorse(horseName.getText(), confidence, color, accessories);
                dispose();
            }
        });
        getContentPane().add(addHorseButton, BorderLayout.SOUTH);

        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}