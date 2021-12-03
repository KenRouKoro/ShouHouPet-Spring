package cn.korostudio.shouhoupetspring.data;

import cn.hutool.extra.spring.SpringUtil;
import cn.korostudio.shouhoupetspring.data.sql.PartsRepository;
import cn.korostudio.shouhoupetspring.parts.Parts;
import cn.korostudio.shouhoupetspring.parts.PartsFactory;

import java.util.List;


public class SQLPartsTool {

    protected static PartsRepository partsRepository;

    static public void Init() {
        partsRepository = SpringUtil.getBean(PartsRepository.class);
    }

    static public Parts findByPartsID(String partsID) {
        return partsRepository.findByPartsID(partsID);
    }

    static public void LoadSQLParts() {
        List<Parts> parts = partsRepository.findAll();
        for (Parts partsObj : parts) {
            PartsFactory.creatPartsByParts(partsObj);
        }
    }

    static public void removeParts(Parts parts) {
        partsRepository.delete(parts);
    }

    static public void saveParts(Parts parts) {
        partsRepository.save(parts);
    }
}
