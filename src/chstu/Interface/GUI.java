package chstu.Interface;


import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

/**
 * Created by linni on 5/13/2017.
 */
public  class GUI {

    public void makeForm(){
        JFrame projectFrame = new JFrame();
        projectFrame.setSize(1200,700);
        projectFrame.setTitle("frame");
        projectFrame.setResizable(false);

        projectFrame.setDefaultCloseOperation(projectFrame.EXIT_ON_CLOSE);
        projectFrame.setLocationRelativeTo(null);
        projectFrame.setLayout(null);
        projectFrame.setBackground(Color.white);

        String[] firstMenuItem = {
                "Lecce_Pen",
                "Parker_Pen",
                "AURORA"
        };
        JMenuBar firstMenu = new JMenuBar();
        firstMenu.setBounds(0,0,950,40);
        projectFrame.add(firstMenu);

        JMenuItem item = new JMenuItem("firstMenuItem");
        item.setBounds(0,5,100,40);
        firstMenu.add(item);

        JButton btSearch = new JButton("search");
        btSearch.setBounds(1100,5,100,30);
        projectFrame.add(btSearch);

        JTextField textSearch = new JTextField();
        textSearch.setBounds(950,5,150,30);
        projectFrame.add(textSearch);



        //LEFT PANEL

        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0,40,250,630);
        leftPanel.setBackground(Color.BLACK);
        projectFrame.add(leftPanel);




        JButton[] btn = new JButton[20];
        for (int i = 0; i < 20; i++) {
            btn[i] = new JButton(i +"");
            btn[i].setPreferredSize(new Dimension(100,80));
            leftPanel.setLayout(new GridLayout(0,1));
            btn[i].setBorderPainted(false);
            btn[i].setFocusPainted(false);
            btn[i].setBackground(Color.cyan);
            leftPanel.add(btn[i]);
        }


        JScrollPane jScrollPaneLeftPanel  = new JScrollPane(leftPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneLeftPanel.setBounds(0,40,250,630);
        projectFrame.add(jScrollPaneLeftPanel);


        //CENTER PANEL
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(250,40,650,700);
        centerPanel.setBackground(Color.RED);
        projectFrame.add(centerPanel);





















        //RIGHT PANEL
        JPanel topRightPanel = new JPanel();
        topRightPanel.setBounds(900,40,300,300);
        topRightPanel.setBackground(Color.YELLOW);
        projectFrame.add(topRightPanel);


        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        datePanel.setPreferredSize(new Dimension(300,300));
        topRightPanel.add(datePanel);



        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setBounds(900,340,300,300);
        bottomRightPanel.setBackground(Color.LIGHT_GRAY);






        projectFrame.setVisible(true);
    }
}