package chstu.Interface;


import chstu.db.DBAdapter;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

        //topMenu
        JPanel leftTopMenu = new JPanel();
        leftTopMenu.setBounds(0,0,250,40);
        leftTopMenu.setBackground(Color.YELLOW);
        projectFrame.add(leftTopMenu);

        //НЕ ЗАБУТИ ЛЕЙБЕЛ//НЕ ЗАБУТИ ЛЕЙБЕЛ//НЕ ЗАБУТИ ЛЕЙБЕЛ//НЕ ЗАБУТИ ЛЕЙБЕЛ//НЕ ЗАБУТИ ЛЕЙБЕЛ//НЕ ЗАБУТИ ЛЕЙБЕЛ//НЕ ЗАБУТИ ЛЕЙБЕЛ//НЕ ЗАБУТИ ЛЕЙБЕЛ


        JPanel centerTopMenu = new JPanel();
        centerTopMenu.setLayout(null);
        centerTopMenu.setBounds(251,0,650,40);
        centerTopMenu.setBackground(Color.pink);
        projectFrame.add(centerTopMenu);



        JLabel namePickedSubject = new JLabel();
        namePickedSubject.setText("NAME OF SUBJECT");
        namePickedSubject.setBounds(10,0,150,40);
        centerTopMenu.add(namePickedSubject);



        JTextField numberOfSubject = new JTextField();
        numberOfSubject.setBounds(170,10,100,20);
        centerTopMenu.add(numberOfSubject);

        JButton btnOkey = new JButton("OK");
        btnOkey.setBounds(400,10,100,20);
        centerTopMenu.add(btnOkey);
        //LEFT PANEL


        btnOkey.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {   DBAdapter dbAdapter;
                        dbAdapter = new DBAdapter();
                        int subject = 0;
                        for(int i=0; i<dbAdapter.getNamesOfSubjects().size(); i++){
                            if(namePickedSubject.getText() == dbAdapter.getNamesOfSubjects().get(i))
                                    subject = i;
                        }

                        /*Tasks tasks = new Tasks();
                        tasks.createNewLabs(subject,Integer.parseInt(numberOfSubject.getText()));*/
                    }
                }
        );


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
        JPanel TopCenterPanel = new JPanel();
        TopCenterPanel.setBounds(250,40,650,150);
        TopCenterPanel.setBackground(Color.RED);
        projectFrame.add(TopCenterPanel);




        JPanel BottomCenterPanel = new JPanel();
        BottomCenterPanel.setBounds(250,190,650,500);
        BottomCenterPanel.setBackground(Color.LIGHT_GRAY);
        projectFrame.add(BottomCenterPanel);



















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

        datePanel.setPreferredSize(new Dimension(300,200));
        topRightPanel.add(datePanel);



        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setBounds(900,340,300,300);
        bottomRightPanel.setBackground(Color.blue);
        projectFrame.add(bottomRightPanel);





        projectFrame.setVisible(true);
    }
}