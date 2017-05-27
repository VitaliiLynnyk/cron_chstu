package chstu.gui.utils;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

/**
 * Created by Ar-Krav on 26.05.2017.
 */
public class ViewportElements {
    public ViewportElements() {

    }
    ViewportStyle vStyle = ViewportStyle.getInstance();

    public JPanel getPanel(LayoutManager layout, Rectangle boundsValue, Color backgroundColor){
        JPanel panel = new JPanel(layout);
            panel.setBounds(boundsValue);
            panel.setBackground(backgroundColor);
        return panel;
    }

    public JLabel getLable(String text, Font font, Rectangle boundsValue, Color foregroundColor){
        JLabel label = new JLabel(text,SwingConstants.CENTER);
            setJComponentCommonProperties(label,font,boundsValue,foregroundColor);
        return label;
    }

    public JScrollPane getScrollPane(Component component, Rectangle boundsValue){
        JScrollPane scrollPane = new JScrollPane(component,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.getVerticalScrollBar().setUnitIncrement(13);
            scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
            scrollPane.setBounds(boundsValue);
        return scrollPane;
    }

    public void setJComponentExtendedProperties(JComponent component, Font font, Rectangle boundsValue, Color foregroundColor, Color backgroundColor){
        setJComponentCommonProperties(component,font,boundsValue,foregroundColor);
        component.setBorder(vStyle.border);
        component.setBackground(backgroundColor);
    }

    public void setJComponentCommonProperties(JComponent component, Font font, Rectangle boundsValue, Color foregroundColor){
        component.setBounds(boundsValue);
        component.setForeground(foregroundColor);
        component.setFont(font);
    }

    public JDatePanelImpl getDataPanel(){
        UtilDateModel model = new UtilDateModel();
            model.setSelected(true);
        Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
            datePanel.setPreferredSize(new Dimension(300,200));

        return datePanel;
    }
}
