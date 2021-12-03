package cn.korostudio.shouhoupetspring;

import cn.korostudio.shouhoupetspring.system.SoftSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShouhouPet {

    static public void main(String[] arg0) {
        System.setProperty("sun.java2d.opengl", "true");
        SoftSystem.launch();
        SpringApplication.run(ShouhouPet.class, arg0);
        SoftSystem.Start();
    }
}
