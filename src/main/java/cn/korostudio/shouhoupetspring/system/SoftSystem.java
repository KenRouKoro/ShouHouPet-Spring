package cn.korostudio.shouhoupetspring.system;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import cn.korostudio.shouhoupetspring.data.SQLPartsTool;
import cn.korostudio.shouhoupetspring.event.EventSystem;
import cn.korostudio.shouhoupetspring.file.FileSystem;
import cn.korostudio.shouhoupetspring.parts.PartsSystem;
import cn.korostudio.shouhoupetspring.view.window.WindowSystem;
import com.formdev.flatlaf.FlatLightLaf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;

import javax.swing.*;

public class SoftSystem {

    public static final Setting SoftSetting;
    private static Logger logger = LoggerFactory.getLogger(SoftSystem.class);

    static {
        SoftSetting = new Setting(FileUtil.touch("soft.setting"), CharsetUtil.CHARSET_UTF_8, true);
    }

    static public void Init() {
        logger.info("SoftSystem is Init.");
        flatInit();
        vlcjInit();
        EventSystem.Init();
        WindowSystem.Init();
        PartsSystem.Init();
        FileSystem.Init();
    }

    static public void Start() {
        logger.info("SoftSystem Starting.");
        SQLPartsTool.Init();
        PartsSystem.InitParts();
        WindowSystem.start();

    }

    static public void Stop() {
        logger.info("SoftSystem Stop.");

    }

    private static void flatInit() {
        FlatLightLaf.install();
        UIManager.put("Button.arc", 0);
        UIManager.put("Component.arc", 0);
        logger.info("Flat is Init.");
    }

    private static void vlcjInit() {
        MediaPlayerFactory factory = new MediaPlayerFactory();
        logger.info("VLCJ lib:" + factory.nativeLibraryPath());
    }

    public static void launch() {
        Init();
    }
}
