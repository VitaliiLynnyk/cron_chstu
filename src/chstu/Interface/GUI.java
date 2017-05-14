package chstu.Interface;

import chstu.db.DBAdapter;
import chstu.timetable.DateUtil;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by linni on 5/13/2017.
 */
public class GUI {

    public void makeForm(){
        JFrame projectFrame = new JFrame();
            projectFrame.setSize(1200,700);
            projectFrame.setTitle("frame");
            projectFrame.setResizable(false);

            projectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            projectFrame.setLocationRelativeTo(null);
            projectFrame.setLayout(null);
            projectFrame.setBackground(Color.white);

//topMenu
            JPanel leftTopMenu = new JPanel();
            leftTopMenu.setLayout(null);
            leftTopMenu.setBounds(0,0,250,40);
            leftTopMenu.setBackground(new Color(113, 74, 176));
            projectFrame.add(leftTopMenu);

            JLabel programName = new JLabel();
            programName.setText("CRON_CHSTU");
            programName.setForeground(Color.yellow);
            programName.setBounds(45,0,150,40);
            programName.setFont(new Font("Chiller", Font.ITALIC, 30));
            leftTopMenu.add(programName);





            JPanel centerTopMenu = new JPanel();
            centerTopMenu.setLayout(null);
            centerTopMenu.setBounds(251,0,648,40);
            projectFrame.add(centerTopMenu);


            JLabel comment = new JLabel();
            comment.setText("введіть кількість лаб");
            comment.setBounds(300,0,150,40);
            centerTopMenu.add(comment);

            JLabel namePickedSubject = new JLabel();
            namePickedSubject.setText("NAME OF SUBJECT");
            namePickedSubject.setBounds(10,0,150,40);
            centerTopMenu.add(namePickedSubject);



            JTextField numberOfSubject = new JTextField();
            numberOfSubject.setBounds(170,10,100,20);
            centerTopMenu.add(numberOfSubject);

            JButton btnOkey = new JButton("OK");
            btnOkey.setBounds(500,10,100,20);
            centerTopMenu.add(btnOkey);
//LEFT PANEL


        /*btnOkey.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    { DBAdapter dbAdapter;
                        dbAdapter = new DBAdapter();
                        int subject = 0;
                        for(int i=0; i<dbAdapter.getNamesOfSubjects().size(); i++){
                            if(namePickedSubject.getText() == dbAdapter.getNamesOfSubjects().get(i))
                                subject = i;
                        }

                        *//*Tasks tasks = new Tasks();
                        tasks.createNewLabs(subject,Integer.parseInt(numberOfSubject.getText()));*//*
                    }
                }
        );*/

        JPanel leftPanel = new JPanel();
            leftPanel.setBounds(0,40,250,630);
            leftPanel.setBackground(Color.BLACK);
            projectFrame.add(leftPanel);

            ArrayList<String>arrayList = new DBAdapter().getNamesOfSubjects();
            JButton[] btn = new JButton[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                btn[i] = new JButton(arrayList.get(i)+"");
                btn[i].setPreferredSize(new Dimension(100,100));
                leftPanel.setLayout(new GridLayout(0,1));
                btn[i].setFont(new Font("Calibri", Font.ITALIC, 26));
                btn[i].setVerticalAlignment(JLabel.CENTER);
                btn[i].setBorderPainted(false);
                btn[i].setFocusPainted(false);
                btn[i].setBackground(new Color(177, 148, 226));
                leftPanel.add(btn[i]);
            }

            JScrollPane jScrollPaneLeftPanel = new JScrollPane(leftPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            jScrollPaneLeftPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
            jScrollPaneLeftPanel.setBounds(0,40,250,630);
            projectFrame.add(jScrollPaneLeftPanel);

//CENTER PANEL
        JPanel TopCenterPanel = new JPanel();
            TopCenterPanel.setBounds(250,40,650,150);
            TopCenterPanel.setBackground(new Color(214, 195, 244));
            projectFrame.add(TopCenterPanel);

        JPanel BottomCenterPanel = new JPanel();
            BottomCenterPanel.setBounds(250,190,650,500);
            BottomCenterPanel.setBackground(new Color(157, 98, 255));
            projectFrame.add(BottomCenterPanel);

//RIGHT PANEL

            JPanel topRightPanel = new JPanel();
            topRightPanel.setBounds(900,-5,300,320);
            topRightPanel.setBackground(new Color(113, 74, 176));
            projectFrame.add(topRightPanel);

        UtilDateModel model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");

            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            datePanel.setPreferredSize(new Dimension(300,200));
            datePanel.setBounds(900,30,300,200);
            topRightPanel.add(datePanel);

        JPanel bottomRightPanel = new JPanel();
            bottomRightPanel.setLayout(new GridLayout(0,1));
            bottomRightPanel.setBounds(900,300,300,400);
            bottomRightPanel.setBackground(new Color(177, 148, 226));
            projectFrame.add(bottomRightPanel);

            DateUtil dates = new DateUtil();
                String strDate = dates.getCurrentDate();
                DBAdapter db = DBAdapter.getInstance();
                ArrayList<ArrayList<String>> nextDaySubjects = db.getAllLessonsOfDay(strDate);

                JLabel [] nextDayTimetableLable = new JLabel[nextDaySubjects.size()];
                for (int i = 0; i < nextDaySubjects.size(); i++){
                    nextDayTimetableLable[i] = new JLabel();
                    nextDayTimetableLable[i].setPreferredSize(new Dimension(300,50));
                    nextDayTimetableLable[i].setText(nextDaySubjects.get(i).get(0) + " | " + nextDaySubjects.get(i).get(1) + " | " + nextDaySubjects.get(i).get(2));
                    nextDayTimetableLable[i].setForeground(Color.GREEN);
                    nextDayTimetableLable[i].setVerticalAlignment(JLabel.CENTER);
                    nextDayTimetableLable[i].setFont(new Font("Calibri", Font.ITALIC, 26));
                    bottomRightPanel.add(nextDayTimetableLable[i]);
                }
            JScrollPane jScrollPaneRightPanel = new JScrollPane(bottomRightPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            jScrollPaneRightPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
            jScrollPaneRightPanel.setBounds(900,300,300,400);
            projectFrame.add(jScrollPaneRightPanel);

        projectFrame.setVisible(true);
    }
}