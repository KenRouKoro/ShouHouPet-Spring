package cn.korostudio.shouhoupetspring.file;

import cn.hutool.core.io.FileUtil;
import cn.korostudio.shouhoupetspring.file.jarFile.JarModSystem;
import cn.korostudio.shouhoupetspring.file.jarFile.JarPartsSystem;

public class FileSystem {
    public static void Init() {
        touchDir();
        JarPartsSystem.Init();
        JarModSystem.Init();
    }

    protected static void touchDir() {
        FileUtil.mkdir(System.getProperty("user.home") + "=/parts/java");
        FileUtil.mkdir(System.getProperty("user.home") + "/shouhoupet/parts/js");
        FileUtil.mkdir(System.getProperty("user.home") + "/shouhoupet/parts/web");
        FileUtil.mkdir(System.getProperty("user.home") + "/shouhoupet/mods");
        FileUtil.mkdir(System.getProperty("user.home") + "/shouhoupet/save");
        FileUtil.touch(System.getProperty("user.home") + "/shouhoupet/setting");


    }
}
