package GUI.Components;

import GUI.MainScreen;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private float borderRadius;

    public RoundedButton(String text, float borderRadius) {
        super(text);
        this.borderRadius = borderRadius;

        setOpaque(false); // Make sure it's non-opaque
        setBorder(null);
        setPreferredSize(new Dimension((int) borderRadius, (int) borderRadius));

    }

    @Override
    protected void paintComponent(Graphics g) {

        int drawRadius = (int)( MainScreen.getInstance().getZoomAmount() * borderRadius);

        if (!isOpaque() && getBorder() instanceof RoundedButtonBorder) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, drawRadius, drawRadius);
            g2.dispose();
        } else if (!isOpaque() ) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, (int) drawRadius, (int) drawRadius);
            g2.dispose();
        }

        super.paintComponent(g);
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        int drawRadius = (int)( MainScreen.getInstance().getZoomAmount() * borderRadius);

        setPreferredSize(new Dimension((int) drawRadius, (int) drawRadius));
    }
}
