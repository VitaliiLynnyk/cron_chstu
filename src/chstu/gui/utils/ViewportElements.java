package chstu.gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ar-Krav on 26.05.2017.
 */
public class ViewportElements {

    public JLabel getLable(String text, Font font, BoundValue boundsValue, Color foregroundColor){
        JLabel label = new JLabel(text,SwingConstants.CENTER);
            label.setBounds(boundsValue.getX(),boundsValue.getY(),boundsValue.getWidth(),boundsValue.getHeight());
            label.setForeground(foregroundColor);
            label.setBounds(boundsValue.getX(),boundsValue.getY(),boundsValue.getWidth(),boundsValue.getHeight());
            label.setFont(font);
        return label;
    }

    /*public JTextArea getTextArea(){

    }*/
}
