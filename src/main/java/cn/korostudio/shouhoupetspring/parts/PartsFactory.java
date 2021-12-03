package cn.korostudio.shouhoupetspring.parts;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.korostudio.shouhoupetspring.err.Error;
import cn.korostudio.shouhoupetspring.file.jarFile.jarannotations.JarParts;
import cn.korostudio.shouhoupetspring.view.window.WindowSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartsFactory {

    protected static Logger logger = LoggerFactory.getLogger(PartsFactory.class);

    public static void creatPartsByClass(Class<?> parts) {
        PartsApplication partsApplication = (PartsApplication) ReflectUtil.newInstanceIfPossible(parts);
        Parts partsOBJ = new Parts();
        partsOBJ.setPartsID(parts.getAnnotation(JarParts.class).ID());
        partsOBJ.setScreen(WindowSystem.getDefScreen());
        partsOBJ.setPartsUUID(IdUtil.randomUUID());
        PartsSystem.getParts().put(partsOBJ, partsApplication);
        partsApplication.partsInit(partsOBJ);
        PartsSystem.getPartsComponentMap().put(partsOBJ, partsApplication.getJInternalFrame());
        WindowSystem.add(partsOBJ);
    }

    public static void creatPartsByParts(Parts parts) {
        logger.info("Creat Parts By Parts : "+parts.toString());
        PartsApplication partsApplication = null;
        try {
            partsApplication = (PartsApplication) ReflectUtil.newInstanceIfPossible(PartsSystem.getPartsClassMap().get(parts.getPartsID()));
        } catch (Exception e) {
            Error.error(PartsFactory.class, e);
            return;
        }
        PartsSystem.getParts().put(parts, partsApplication);
        partsApplication.partsInit(parts);
        WindowSystem.add(parts);
    }

}
