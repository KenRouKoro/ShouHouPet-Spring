package cn.korostudio.shouhoupetspring.view.window;

import cn.hutool.core.map.BiMap;
import cn.hutool.core.thread.ThreadUtil;
import cn.korostudio.shouhoupetspring.err.Error;
import cn.korostudio.shouhoupetspring.parts.Parts;
import cn.korostudio.shouhoupetspring.parts.PartsSystem;
import cn.korostudio.shouhoupetspring.view.parts.WindowIndicator;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class WindowSystem {

    protected static Logger logger = LoggerFactory.getLogger(WindowSystem.class);

    protected static BiMap<String, ReWindow> mainWindows;
    @Getter
    protected static BiMap<String, CopyOnWriteArrayList<JInternalFrame>> ScreenComponentLists;
    @Getter
    protected static CopyOnWriteArrayList<String> DisPlayIDs;
    @Getter
    protected static String defScreen;
    @Getter
    protected static boolean OnTOP = false;

    static {
        ScreenComponentLists = new BiMap<>(new LinkedHashMap<>());
        DisPlayIDs = new CopyOnWriteArrayList<>();
        mainWindows = new BiMap<>(new LinkedHashMap<>());
    }

    @Synchronized
    static public void replaceScreen(Parts parts, String screen) {
        SwingUtilities.invokeLater(() -> {
            ScreenComponentLists.get(parts.getScreen()).remove(PartsSystem.getParts().get(parts).getJInternalFrame());
            ScreenComponentLists.get(screen).add(PartsSystem.getParts().get(parts).getJInternalFrame());
            mainWindows.get(parts.getScreen()).getDesktopPanel().remove(PartsSystem.getParts().get(parts).getJInternalFrame());
            mainWindows.get(screen).getDesktopPanel().add(PartsSystem.getParts().get(parts).getJInternalFrame());
            parts.setScreen(screen);
        });
    }

    static public void setOnTOP(boolean OnTOP){
        if (!WindowSystem.OnTOP){
            for(ReWindow window: mainWindows.values()){
                window.setAlwaysOnTop(false);
            }
        }
        WindowSystem.OnTOP = OnTOP;
    }

    static public void start() {
        logger.info("WindowSystem start.");
        SwingUtilities.invokeLater(() -> {
            for (String ids : mainWindows.keySet()) {
                ReWindow reWindow = mainWindows.get(ids);
                logger.debug("Window " + reWindow.getScreenID() + " is showing.");
                reWindow.setVisible(true);
            }
            if(!OnTOP){
                setToBack();
            }else{
                setToTop();
            }
        });
        ThreadUtil.execAsync(() -> {
            while (true) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice[] gd = ge.getScreenDevices();
                int screen = gd.length;
                defScreen = gd[0].getIDstring();
                for (int i = 0; i < screen; i++) {
                    boolean find = false;
                    for (String ids : DisPlayIDs) {
                        if (ids.equals(gd[i].getIDstring())) {
                            find = true;
                        }
                    }
                    if (!find) {
                        ReWindow reWindow = new ReWindow(i);
                        Rectangle thisRectangle = gd[i].getDefaultConfiguration().getBounds();
                        reWindow.setBounds(thisRectangle);
                        mainWindows.put(gd[i].getIDstring(), reWindow);
                        ScreenComponentLists.put(gd[i].getIDstring(), new CopyOnWriteArrayList<>());
                        DisPlayIDs.add(gd[i].getIDstring());
                        logger.info(gd[i].getIDstring());
                        reWindow.setVisible(true);
                        logger.debug("Window " + reWindow.getScreenID() + " is showing.");
                    } else {
                        int finalI = i;
                        SwingUtilities.invokeLater(() -> {
                            mainWindows.get(gd[finalI].getIDstring()).setScreenID(finalI);
                        });
                    }
                }
                for (String ids : DisPlayIDs) {
                    boolean find = false;
                    for (GraphicsDevice gdo : gd) {
                        if (gdo.getIDstring().equals(ids)) {
                            find = true;
                        }
                    }
                    if (!find) {
                        mainWindows.get(ids).setVisible(false);
                        mainWindows.get(ids).removeAll();
                        DisPlayIDs.remove(ids);
                        for (JInternalFrame component : ScreenComponentLists.get(ids)) {
                            Parts parts = PartsSystem.getPartsComponentMap().getKey(component);
                            replaceScreen(parts, defScreen);
                        }
                        ScreenComponentLists.remove(ids);
                        mainWindows.remove(ids);
                    }
                }
                Thread.sleep(5000);
            }
        });

    }

    static public void setToTop(){
        for(ReWindow window: mainWindows.values()){
            window.setAlwaysOnTop(true);
        }
    }

    static public void setToBack(){
        for(ReWindow window: mainWindows.values()){
            window.toBack();
        }
    }

    static public void setIndicatorShow(boolean set) {
        SwingUtilities.invokeLater(() -> {
            for (String ids : mainWindows.keySet()) {
                ReWindow reWindow = mainWindows.get(ids);
                reWindow.setIndicatorShow(set);
            }
        });
    }

    static public void stop() {
        SwingUtilities.invokeLater(() -> {
            for (String ids : mainWindows.keySet()) {
                ReWindow reWindow = mainWindows.get(ids);
                reWindow.setVisible(false);
            }
        });
    }


    static public void Init() {
        logger.info("WindowSystem Init.");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        int screen = gd.length;
        defScreen = gd[0].getIDstring();
        for (int i = 0; i < screen; i++) {
            ReWindow reWindow = new ReWindow(i);
            Rectangle thisRectangle = gd[i].getDefaultConfiguration().getBounds();
            reWindow.setBounds(thisRectangle);
            mainWindows.put(gd[i].getIDstring(), reWindow);
            ScreenComponentLists.put(gd[i].getIDstring(), new CopyOnWriteArrayList<>());
            DisPlayIDs.add(gd[i].getIDstring());
            logger.info(gd[i].getIDstring());
        }
    }

    @Synchronized
    static public void add(Parts parts) {
        SwingUtilities.invokeLater(() -> {
            ScreenComponentLists.get(parts.getScreen()).add(PartsSystem.getParts().get(parts).getJInternalFrame());
            try {
                mainWindows.get(parts.getScreen()).getDesktopPanel().add(PartsSystem.getParts().get(parts).getJInternalFrame());
            } catch (Exception e) {
                mainWindows.get(defScreen).getDesktopPanel().add(PartsSystem.getParts().get(parts).getJInternalFrame());
                parts.setScreen(defScreen);
            }
            repaint();
        });
    }

    @Synchronized
    static public void remove(Parts parts) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                ScreenComponentLists.get(parts.getScreen()).remove(PartsSystem.getParts().get(parts).getJInternalFrame());
                mainWindows.get(parts.getScreen()).getDesktopPanel().remove(PartsSystem.getParts().get(parts).getJInternalFrame());
            });
        } catch (InterruptedException | InvocationTargetException e) {
            Error.error(WindowSystem.class, e);
        }
        repaint();
    }

    static public void repaint() {
        SwingUtilities.invokeLater(() -> {
            for (String id : mainWindows.keySet()) {
                mainWindows.get(id).repaint();
            }
        });
    }


    public static class ReWindow extends JWindow {
        @Getter
        protected int ScreenID;

        protected WindowIndicator indicator;

        protected Logger logger;
        @Getter
        JDesktopPane desktopPanel ;

        public ReWindow(int ID) {
            logger = LoggerFactory.getLogger("ReWindow-" + ID);

            ScreenID = ID;

            indicator = new WindowIndicator(ID);

            desktopPanel = new JDesktopPane();
            desktopPanel.setOpaque(false);

            setAlwaysOnTop(false);

            setBackground(new Color(0, 0, 0, 0));

            setLayout(new BorderLayout());

            add(desktopPanel,BorderLayout.CENTER);

            desktopPanel.add(indicator);

            logger.info("ReWindow ID=" + ID + " Created.");
        }

        public void setScreenID(int screenID) {
            ScreenID = screenID;
            indicator.setNumber(screenID);
        }

        public void setIndicatorShow(boolean show) {
            indicator.setVisible(show);
        }
    }
}
