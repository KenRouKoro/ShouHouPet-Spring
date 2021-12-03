package cn.korostudio.shouhoupetspring.defparts.system;

import cn.hutool.json.JSONUtil;
import cn.korostudio.shouhoupetspring.data.SQLPartsTool;
import cn.korostudio.shouhoupetspring.defparts.shouhou.data.Data;
import cn.korostudio.shouhoupetspring.defparts.system.panel.SettingBasePanel;
import cn.korostudio.shouhoupetspring.file.jarFile.jarannotations.JarParts;
import cn.korostudio.shouhoupetspring.parts.Parts;
import cn.korostudio.shouhoupetspring.parts.PartsApplication;
import cn.korostudio.shouhoupetspring.parts.PartsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

@JarParts(name = "System", author = "Koro", version = "0.1Alpha", ID = "system")
public class System implements PartsApplication {
    static protected Logger logger;

    static {
        logger = LoggerFactory.getLogger(System.class);
    }

    protected SettingBasePanel basePanel;
    protected Data data;
    protected Parts thisParts;

    public static void Init() {
        logger.info("Path System is Init.");
    }

    public static void AutoRun() {
        logger.info("Path System is AutoRun.");
        if (SQLPartsTool.findByPartsID("system") == null) {
            logger.info("Auto Creat System parts.");
            PartsFactory.creatPartsByClass(System.class);
        }
    }

    public Component getComponent() {
        return basePanel.getBasePanel();
    }

    @Override
    public JInternalFrame getJInternalFrame() {
        return basePanel.getBasePanel();
    }

    public void partsInit(Parts parts) {
        logger.info("Path Shouhou is Init.");
        basePanel = new SettingBasePanel();
        thisParts = parts;
        data = JSONUtil.toBean(parts.getData(), Data.class);
    }

    @Override
    public void exit() {

    }
}
