package cn.korostudio.shouhoupetspring.defparts.shouhou;

import cn.hutool.json.JSONUtil;
import cn.korostudio.shouhoupetspring.data.SQLPartsTool;
import cn.korostudio.shouhoupetspring.defparts.shouhou.data.Data;
import cn.korostudio.shouhoupetspring.defparts.shouhou.panel.BasePanel;
import cn.korostudio.shouhoupetspring.file.jarFile.jarannotations.JarParts;
import cn.korostudio.shouhoupetspring.parts.Parts;
import cn.korostudio.shouhoupetspring.parts.PartsApplication;
import cn.korostudio.shouhoupetspring.parts.PartsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

@JarParts(name = "ShouhouPet", author = "Koro", version = "0.1Alpha", ID = "shouhoupet")
public class Shouhou implements PartsApplication {
    static protected Logger logger;

    static {
        logger = LoggerFactory.getLogger(Shouhou.class);
    }

    protected BasePanel basePanel;
    protected Data data;
    protected Parts thisParts;

    public static void Init() {
        logger.info("Shouhou is Init.");
    }

    public static void AutoRun() {
        logger.info("Path Shouhou is AutoRun.");
        if (SQLPartsTool.findByPartsID("shouhoupet") == null) {
            logger.info("Auto Creat Shouhou pet parts.");
            PartsFactory.creatPartsByClass(Shouhou.class);
        }
    }

    public Component getComponent() {
        return basePanel.getMainPanel();
    }

    @Override
    public JInternalFrame getJInternalFrame() {
        return basePanel.getMainPanel();
    }

    public void partsInit(Parts parts) {
        logger.info("Path Shouhou is Init.");
        basePanel = new BasePanel();
        thisParts = parts;
        data = JSONUtil.toBean(parts.getData(), Data.class);
        logger.info("UUID is: " + parts.getPartsUUID());
    }

    @Override
    public void exit() {

    }
}
