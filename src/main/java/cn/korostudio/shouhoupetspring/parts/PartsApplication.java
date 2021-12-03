package cn.korostudio.shouhoupetspring.parts;

import javax.swing.*;
import java.awt.*;

public interface PartsApplication {
    JInternalFrame getJInternalFrame();

    void partsInit(Parts parts);

    void exit();

}
