package main.java.view;

import javax.swing.*;
import java.awt.*;

public class HelpTooltip extends JLabel {

    public HelpTooltip(String tooltipText) {
        super();
        setIcon(createHelpIcon());
        setToolTipText(tooltipText);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private ImageIcon createHelpIcon() {
        ImageIcon originalIcon = new ImageIcon("src/main/resources/images/help_icon.png");
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}