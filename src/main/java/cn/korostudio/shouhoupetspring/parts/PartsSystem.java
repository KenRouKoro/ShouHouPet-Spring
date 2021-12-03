package cn.korostudio.shouhoupetspring.parts;

import cn.hutool.core.map.BiMap;
import cn.hutool.core.util.ReflectUtil;
import cn.korostudio.shouhoupetspring.data.SQLPartsTool;
import cn.korostudio.shouhoupetspring.file.jarFile.jarannotations.JarParts;
import cn.korostudio.shouhoupetspring.view.window.WindowSystem;
import lombok.Getter;
import lombok.Synchronized;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PartsSystem {

    //** <ID , PartsClass>
    @Getter
    protected static BiMap<String, Class<?>> PartsClassMap;
    //** <Parts , SwingComponent>
    @Getter
    protected static BiMap<Parts, PartsApplication> Parts;
    @Getter
    protected static BiMap<Parts, JInternalFrame> PartsComponentMap;
    @Getter
    protected static CopyOnWriteArrayList<JarParts> jarPartses;

    static {
        PartsClassMap = new BiMap<>(new LinkedHashMap<>());
        PartsComponentMap = new BiMap<>(new LinkedHashMap<>());
        Parts = new BiMap<>(new LinkedHashMap<>());
        jarPartses = new CopyOnWriteArrayList<>();
    }

    @Synchronized
    public static void register(JarParts jarParts, Class<?> partsClass) {
        PartsClassMap.put(jarParts.ID(), partsClass);
        jarPartses.add(jarParts);
    }

    @Synchronized
    public static Parts findParts(PartsApplication partsApplication) {
        return Parts.getKey(partsApplication);
    }

    public static void InitParts() {
        for (String id : PartsClassMap.keySet()) {
            ReflectUtil.invokeStatic(ReflectUtil.getMethod(PartsClassMap.get(id), "Init"));
        }
        SQLPartsTool.LoadSQLParts();
        for (String id : PartsClassMap.keySet()) {
            ReflectUtil.invokeStatic(ReflectUtil.getMethod(PartsClassMap.get(id), "AutoRun"));
        }
    }

    public static void saveParts(Parts parts) {
        SQLPartsTool.saveParts(parts);
    }

    public static void creatParts(String id) {
        PartsFactory.creatPartsByClass(PartsClassMap.get(id));

    }

    public static void removeParts(Parts parts) {
        WindowSystem.remove(parts);
        Parts.get(parts).exit();
        Parts.remove(parts);
        PartsComponentMap.remove(parts);
        SQLPartsTool.removeParts(parts);
    }

    static public void Init() {

    }
}
