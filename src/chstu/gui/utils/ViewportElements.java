package chstu.gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ar-Krav on 26.05.2017.
 */
public class ViewportElements {

    public JPanel getPanel(LayoutManager layout, Rectangle boundsValue, Color backgroundColor){
        JPanel panel = new JPanel(layout);
            panel.setBounds(boundsValue);
            panel.setBackground(backgroundColor);
        return panel;
    }

    public JLabel getLable(String text, Font font, Rectangle boundsValue, Color foregroundColor){
        JLabel label = new JLabel(text,SwingConstants.CENTER);
            label.setBounds(boundsValue);
            label.setForeground(foregroundColor);
            label.setFont(font);
        return label;
    }

    public JScrollPane getScrollPane(Component component, Rectangle boundsValue){
        JScrollPane scrollPane = new JScrollPane(component,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.getVerticalScrollBar().setUnitIncrement(13);
            scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
            scrollPane.setBounds(boundsValue);
        return scrollPane;
    }

    public void setJComponentCommonParametr(JComponent component, Font font, Rectangle boundsValue, Color foregroundColor, Color backgroundColor){
        component.setBounds(boundsValue);
        component.setForeground(foregroundColor);
        component.setBackground(backgroundColor);
        component.setFont(font);
    }
}
