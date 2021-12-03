package cn.korostudio.shouhoupetspring.defparts.shouhou.panel;

import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BasePanel {
    @Getter
    protected JInternalFrame mainPanel;

    protected Point origin = new Point();

    protected boolean isTuo = false;

    @Getter
    protected RightToolPanel rightToolPanel;

    public BasePanel() {
        mainPanel = new JInternalFrame();
        mainPanel.setTitle("ShouhouPet");
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setSize(600, 600);
        mainPanel.setBackground(new Color(0, 0, 0, 0));
        mainPanel.setLocation(600, 600);
        ((BasicInternalFrameUI)mainPanel.getUI()).setNorthPane(null);
        mainPanel.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.setVisible(true);

        rightToolPanel = new RightToolPanel();
        mainPanel.add(rightToolPanel.getToolPanel(), BorderLayout.EAST);

        mainPanel.addMouseMotionListener(new MouseMotionListener() {
            // 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
            public void mouseDragged(MouseEvent e) {
                // 当鼠标拖动时获取窗口当前位置
                java.awt.Point p = mainPanel.getLocation();
                // 设置窗口的位置
                // 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
                mainPanel.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO 自动生成的方法存根

            }
        });

        mainPanel.addMouseListener(new MouseListener() {
            // 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
            public void mousePressed(MouseEvent e) {
                // 当鼠标按下的时候获得窗口当前的位置
                origin.x = e.getX();
                origin.y = e.getY();
                isTuo = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isTuo = false;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO 自动生成的方法存根
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO 自动生成的方法存根

            }
        });
    }
}
