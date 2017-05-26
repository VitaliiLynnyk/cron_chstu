package chstu.gui.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Ar-Krav on 26.05.2017.
 */

public class ViewportStyle {
    public ViewportStyle() {
        debtRed = new Color(211, 81, 71 );
        passedGreen = new Color(63, 171, 57);
        violet1 = new Color(113, 74, 176);
        violet2 = new Color(177, 148, 226);
        violet3 = new Color(32, 16, 58);
        violet4 = new Color(137, 114, 176);
        violet5 = new Color(214, 195, 244);

        tnr15 = new Font("Times new roman", Font.BOLD, 15);
        tnr25 = new Font("Times new roman", Font.BOLD, 25);
        tnr30 = new Font("Times new roman", Font.BOLD, 30);
        chl30 = new Font("Chiller", Font.ITALIC, 30);
        clb26 = new Font("Calibri", Font.ITALIC, 26);

        border = BorderFactory.createLineBorder(violet2,1);
    }

    ViewportStyle instance;
    public ViewportStyle getInstance(){
        if (instance == null){
            instance = new ViewportStyle();
        }
        return instance;
    }

    public final Color debtRed, passedGreen,
                       violet1, violet2, violet3, violet4, violet5;

    public final Font tnr15, tnr25, tnr30,
                      chl30, clb26;

    public final Border border;
}
