package GUI.Components;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private final int borderRadius;

    public RoundedButton(String text, int borderRadius) {
        super(text);
        this.borderRadius = borderRadius;

        setOpaque(false); // Make sure it's non-opaque
        setBorder(null);
        setPreferredSize(new Dimension(borderRadius, borderRadius));

    }

    @Override
    protected void paintComponent(Graphics g) {

        if (!isOpaque() && getBorder() instanceof RoundedButtonBorder) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, ((RoundedButtonBorder)getBorder()).getRadius(), ((RoundedButtonBorder)getBorder()).getRadius());
            g2.dispose();
        } else if (!isOpaque() ) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, borderRadius, borderRadius);
            g2.dispose();
        }

        super.paintComponent(g);
    }
}
