package cn.korostudio.shouhoupetspring.data.sql;

import cn.korostudio.shouhoupetspring.parts.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PartsRepository extends JpaRepository<Parts, String> {
    Parts findByPartsID(String partsID);
    Parts findByPartsUUID(String uuid);
    Collection<Parts> findAllByScreen(String screen);
}
