package cn.korostudio.shouhoupetspring.file.jarFile;

import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.korostudio.shouhoupetspring.file.jarFile.jarannotations.JarParts;
import cn.korostudio.shouhoupetspring.parts.PartsSystem;
import cn.korostudio.shouhoupetspring.tools.OtherClassScanner;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class JarPartsSystem {

    static protected Logger logger = LoggerFactory.getLogger(JarPartsSystem.class);
    @Getter
    static protected JarClassLoader PartsJarClassLoader;
    @Getter
    static protected Set<Class<?>> PartsClassSet;

    static {
    }

    static public void Init() {
        PartsJarClassLoader = ClassLoaderUtil.getJarClassLoader(new File(System.getProperty("user.home") + "/shouhoupet/parts/java"));
        loadParts();
    }

    static protected void loadParts() {
        logger.info("Scanning Package By Annotation\"@JarParts\".");
        //扫描所有路径
        OtherClassScanner otherScanner = new OtherClassScanner("", clazz -> clazz.isAnnotationPresent(JarParts.class));
        otherScanner.setClassLoader(PartsJarClassLoader);
        Set<Class<?>> otherClassSet = otherScanner.scan(true);
        PartsClassSet = new HashSet<>();
        PartsClassSet.addAll(otherClassSet);
        for (Class<?> part : PartsClassSet) {
            logger.debug("Find: " + part.toString());
            JarParts parts = part.getAnnotation(JarParts.class);
            PartsSystem.register(parts, part);
        }
    }
}
