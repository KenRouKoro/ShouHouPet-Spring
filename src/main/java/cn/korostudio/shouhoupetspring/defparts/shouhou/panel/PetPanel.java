package cn.korostudio.shouhoupetspring.defparts.shouhou.panel;

import lombok.Getter;

import javax.swing.*;

public class PetPanel {
    @Getter
    protected JPanel petPanel;

    public PetPanel() {
        petPanel = new JPanel();
        petPanel.setLocation(0, 100);

    }
}
