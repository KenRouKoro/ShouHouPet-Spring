package cn.korostudio.shouhoupetspring.file.jarFile;

import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.korostudio.shouhoupetspring.file.jarFile.jarannotations.JarMods;
import cn.korostudio.shouhoupetspring.tools.OtherClassScanner;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class JarModSystem {
    static protected Logger logger;
    @Getter
    static protected JarClassLoader ModsJarClassLoader;
    @Getter
    static protected Set<Class<?>> ModsClassSet;


    static {
        logger = LoggerFactory.getLogger(JarModSystem.class);
    }

    static public void Init() {
        ModsJarClassLoader = ClassLoaderUtil.getJarClassLoader(new File(System.getProperty("user.home") + "/shouhoupet/mods"));
        loadMods();
    }

    static protected void loadMods() {
        logger.info("Scanning Package By Annotation\"@JarMods\".");
        //扫描所有路径
        OtherClassScanner otherScanner = new OtherClassScanner("", clazz -> clazz.isAnnotationPresent(JarMods.class));
        otherScanner.setClassLoader(ModsJarClassLoader);
        Set<Class<?>> otherClassSet = otherScanner.scan(true);
        ModsClassSet = new HashSet<>();
        ModsClassSet.addAll(otherClassSet);
        for (Class<?> part : ModsClassSet) {
            logger.debug("Find: " + part.toString());
            ReflectUtil.invokeStatic(ReflectUtil.getMethod(part, "Init"));
        }
    }

}
