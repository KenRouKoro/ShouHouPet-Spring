package cn.korostudio.shouhoupetspring.view.parts;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;

public class WindowIndicator extends JInternalFrame {
    @Setter
    @Getter
    protected int number;

    public WindowIndicator(int number) {
        setBackground(Color.BLACK);
        setFont(new Font("宋体", Font.BOLD, 50));
        setBounds(0, 0, 150, 150);
        this.number = number;
        ((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        setBorder(BorderFactory.createEmptyBorder());
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g2d.setColor(Color.white);
        g2d.drawString(number + "", 55, 85);
    }
}
