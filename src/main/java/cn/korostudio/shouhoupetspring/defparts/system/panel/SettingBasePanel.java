package cn.korostudio.shouhoupetspring.defparts.system.panel;

import cn.hutool.core.util.ClassLoaderUtil;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import lombok.Getter;
import lombok.Synchronized;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SettingBasePanel {
    @Getter
    protected JInternalFrame  basePanel;

    protected JPanel title;

    protected JPanel settingPanel;

    protected Point origin = new Point();

    protected boolean isTuo = false;

    protected static Color baseColor = new Color(251, 251, 251);

    public SettingBasePanel(){
        basePanel = new JInternalFrame ();
        basePanel.setSize(600,500);
        basePanel.setLayout(new BorderLayout());
        basePanel.setLocation(200,200);
        ((BasicInternalFrameUI)basePanel.getUI()).setNorthPane(null);
        basePanel.setBorder(BorderFactory.createEmptyBorder());
        basePanel.setVisible(true);

        title = new JPanel();
        title.setLayout(null);
        title.setPreferredSize(new Dimension(600,30));
        title.setBackground(Color.white);

        FlatSVGIcon icon = new FlatSVGIcon("zondicons/cog.svg",10,10);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(10,10,10,10);

        title.add(iconLabel);

        JLabel titleLabel = new JLabel("Setting");
        titleLabel.setBackground(new Color(0,0,0,0));
        titleLabel.setForeground(Color.darkGray);
        titleLabel.setLocation(30,0);
        titleLabel.setSize(300,30);

        title.add(titleLabel);

        JPanel closePanel = new JPanel();
        closePanel.setLayout(null);
        closePanel.setSize(50,30);
        closePanel.setLocation(550,0);
        closePanel.setBackground(Color.white);

        FlatSVGIcon closeICON = new FlatSVGIcon("zondicons/close.svg",10,10);
        FlatSVGIcon.ColorFilter closeFilter = new FlatSVGIcon.ColorFilter(color -> Color.darkGray);

        closeICON.setColorFilter(closeFilter);

        closePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closePanel.setBackground(Color.red);
                closeFilter.setMapper(color -> Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closePanel.setBackground(Color.white);
                closeFilter.setMapper(color -> Color.DARK_GRAY);
            }
        });

        JLabel closeLabel = new JLabel(closeICON); //ClassLoaderUtil.getClassLoader()));
        closeLabel.setLocation(20,10);
        closeLabel.setSize(10,10);

        closePanel.add(closeLabel);

        title.add(closePanel);

        title.addMouseListener(new MouseListener() {
            // ?????????mousePressed ???????????????????????????????????????????????????
            public void mousePressed(MouseEvent e) {
                // ???????????????????????????????????????????????????
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
                // TODO ???????????????????????????
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO ???????????????????????????

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO ???????????????????????????

            }
        });

        title.addMouseMotionListener(new MouseMotionListener() {
            // ?????????mouseDragged ???????????????????????????????????????????????????????????????
            public void mouseDragged(MouseEvent e) {
                // ??????????????????????????????????????????
                java.awt.Point p =basePanel.getLocation();
                // ?????????????????????
                // ????????????????????? + ?????????????????????????????? - ???????????????????????????????????????
                basePanel.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
                //basePanel.paintImmediately(basePanel.getBounds());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO ???????????????????????????

            }
        });



        basePanel.add(title,BorderLayout.NORTH);

        settingPanel = new JPanel();
        settingPanel.setBackground(baseColor);
        settingPanel.setLayout(new BorderLayout());

        basePanel.add(settingPanel,BorderLayout.CENTER);


    }
}
