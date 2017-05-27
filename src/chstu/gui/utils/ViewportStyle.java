package chstu.gui.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Ar-Krav on 26.05.2017.
 */

public class ViewportStyle {
    public ViewportStyle() {
        colorDebtRed = new Color(211, 81, 71 );
        colorPassedGreen = new Color(63, 171, 57);
        colorNameYellow = Color.yellow;
        colorMPanelGray = Color.LIGHT_GRAY;
        colorViolet1 = new Color(113, 74, 176);
        colorViolet2 = new Color(177, 148, 226);
        colorViolet3 = new Color(32, 16, 58);
        colorViolet4 = new Color(137, 114, 176);
        colorViolet5 = new Color(214, 195, 244);

        fontTnr15 = new Font("Times new roman", Font.BOLD, 15);
        fontTnr25 = new Font("Times new roman", Font.BOLD, 25);
        fontTnrB30 = new Font("Times new roman", Font.BOLD, 30);
        fontTnrI30 = new Font("Times new roman", Font.ITALIC, 30);
        fontChl30 = new Font("Chiller", Font.ITALIC, 30);
        fofntClb26 = new Font("Calibri", Font.ITALIC, 26);

        border = BorderFactory.createLineBorder(colorViolet2,1);
    }

    private static ViewportStyle instance;
    public static ViewportStyle getInstance(){
        if (instance == null){
            instance = new ViewportStyle();
        }
        return instance;
    }

    public final Color colorDebtRed, colorPassedGreen, colorNameYellow, colorMPanelGray,
                       colorViolet1, colorViolet2, colorViolet3, colorViolet4, colorViolet5;

    public final Font fontTnr15, fontTnr25, fontTnrB30, fontTnrI30,
                      fontChl30, fofntClb26;

    public final Border border;
}
