package cn.korostudio.shouhoupetspring.defparts.shouhou.panel;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class RightToolPanel {
    @Getter
    protected JPanel toolPanel;

    public RightToolPanel() {
        toolPanel = new JPanel();
        toolPanel.setPreferredSize(new Dimension(200, 600));
        toolPanel.setBackground(new Color(0, 0, 0, 63));
    }
}
