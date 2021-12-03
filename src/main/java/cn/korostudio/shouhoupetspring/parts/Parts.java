package cn.korostudio.shouhoupetspring.parts;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Parts {
    @Id
    protected String partsUUID;
    protected String screen;
    protected String partsID;
    protected String data;

}
