package chstu.Interface;

import chstu.db.DBAdapter;
import chstu.db.Labs;
import chstu.db.Subjects;
import chstu.db.Timetable;
import chstu.timetable.DateUtil;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.List;

/**
 * Created by linni on 5/13/2017.
 */
public class GUI {
    JFrame projectFrame = new JFrame();
    DBAdapter dataBase = DBAdapter.getInstance();

    public void makeForm(){
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

        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0,80,250,600);
        leftPanel.setBackground(Color.BLACK);
        projectFrame.add(leftPanel);

        List<Subjects> arrayList= dataBase.getAllSubjects();
        ArrayList<JButton>btn = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            btn.add(i,new JButton(arrayList.get(i).getName()+""));
            btn.get(i).setPreferredSize(new Dimension(100,100));
            leftPanel.setLayout(new GridLayout(0,1));
            btn.get(i).setFont(new Font("Calibri", Font.ITALIC, 26));
            btn.get(i).setVerticalAlignment(JLabel.CENTER);
            btn.get(i).setBorderPainted(false);
            btn.get(i).setFocusPainted(false);
            btn.get(i).setBackground(new Color(177, 148, 226));
            btn.get(i).addActionListener(getActionForButtons(i+1));
            leftPanel.add(btn.get(i));
        }

        JScrollPane jScrollPaneLeftPanel = new JScrollPane(leftPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneLeftPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        jScrollPaneLeftPanel.setBounds(0,80,250,600);
        projectFrame.add(jScrollPaneLeftPanel);

        JLabel labelSubjectName = new JLabel("Предмети");
        labelSubjectName.setBounds(60,40,250,40);
        labelSubjectName.setFont(new Font("Times New Roman", Font.BOLD, 30));
        projectFrame.add(labelSubjectName);




//CENTER PANEL
        JPanel TopCenterPanel = new JPanel();
        TopCenterPanel.setBounds(250,40,650,150);
        TopCenterPanel.setBackground(new Color(214, 195, 244));
        projectFrame.add(TopCenterPanel);

//RIGHT PANEL

        JPanel topRightPanel = new JPanel();
        topRightPanel.setBounds(900,-5,300,207);
        topRightPanel.setBackground(new Color(113, 74, 176));
        projectFrame.add(topRightPanel);
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        datePanel.setPreferredSize(new Dimension(300,200));
        datePanel.setBounds(900,30,300,100);
        topRightPanel.add(datePanel);

        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new Calendars());




        JLabel lbSelectSubject = new JLabel("Розклад  ");
        lbSelectSubject.setForeground(new Color(137, 114, 176));
        lbSelectSubject.setBounds(900,193 ,300,43);
        lbSelectSubject.setFont(new Font("Times new roman", Font.BOLD, 25));
        projectFrame.add(lbSelectSubject);


        JPanel panelSubjectInSelectDate = new JPanel();
        panelSubjectInSelectDate.setLayout(new GridLayout(3,1));
        panelSubjectInSelectDate.setBounds(900,235,300,170);
        panelSubjectInSelectDate.setBackground(new Color(137, 114, 176));
        projectFrame.add(panelSubjectInSelectDate);






        datePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDate = datePicker.getJFormattedTextField().getText();
                panelSubjectInSelectDate.removeAll();
                lbSelectSubject.setText("Розклад на "+selectedDate);

                //System.out.println(dataBase.getLessonsInDay(selectedDate));
                List<Timetable> subjectInSelectDate = dataBase.getLessonsInDay(selectedDate);
                JLabel [] selectDayTimetableLable = new JLabel[subjectInSelectDate.size()];
                for (int i = 0; i < subjectInSelectDate.size(); i++){
                    selectDayTimetableLable[i] = new JLabel();
                    selectDayTimetableLable[i].setPreferredSize(new Dimension(300,80));
                    selectDayTimetableLable[i].setText(subjectInSelectDate.get(i).getNumberLesson() +" | " + subjectInSelectDate.get(i).getTypeLesson() + " | " + subjectInSelectDate.get(i).getLessonDate());
                    selectDayTimetableLable[i].setFont(new Font("Calibri", Font.ITALIC, 26));
                    panelSubjectInSelectDate.add(selectDayTimetableLable[i]);
                }
                if(selectDayTimetableLable.size() = 0){

                }

                JScrollPane jScrollPaneRightBottomPanel = new JScrollPane(panelSubjectInSelectDate,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jScrollPaneRightBottomPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
                jScrollPaneRightBottomPanel.setBounds(900,235,300,170);
                projectFrame.add(jScrollPaneRightBottomPanel);
            }
        });


        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(null);
        bottomRightPanel.setLayout(new GridLayout(3,1));
        bottomRightPanel.setBounds(900,452,300,220);
        bottomRightPanel.setBackground(new Color(177, 148, 226));
        projectFrame.add(bottomRightPanel);

        DateUtil dates = new DateUtil();
        String strDate = dates.getCurrentDate();

        JLabel lbSubjectNextDay = new JLabel("Розклад на сьогодні");
        lbSubjectNextDay.setForeground(new Color(137, 114, 176));
        lbSubjectNextDay.setBounds(900,380 ,300,100);
        lbSubjectNextDay.setFont(new Font("Times new roman", Font.BOLD, 25));
        projectFrame.add(lbSubjectNextDay);

        List <Timetable> nextDaySubjects = dataBase.getLessonsInDay(strDate);
        JLabel [] nextDayTimetableLable = new JLabel[nextDaySubjects.size()];
        for (int i = 0; i < nextDaySubjects.size(); i++){
            nextDayTimetableLable[i] = new JLabel();
            nextDayTimetableLable[i].setPreferredSize(new Dimension(300,80));
            nextDayTimetableLable[i].setText(nextDaySubjects.get(i).getNumberLesson() +" | " + nextDaySubjects.get(i).getTypeLesson() + " | " + nextDaySubjects.get(i).getLessonDate());
            nextDayTimetableLable[i].setForeground(new Color(32, 16, 58));
            nextDayTimetableLable[i].setVerticalAlignment(JLabel.CENTER);
            nextDayTimetableLable[i].setFont(new Font("Calibri", Font.ITALIC, 26));
            bottomRightPanel.add(nextDayTimetableLable[i]);
        }
        JScrollPane jScrollPaneRightBottomPanel = new JScrollPane(bottomRightPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneRightBottomPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        jScrollPaneRightBottomPanel.setBounds(900,452,300,220);
        projectFrame.add(jScrollPaneRightBottomPanel);

        projectFrame.setVisible(true);
    }

    private ActionListener getActionForButtons(int subject){
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLabs(subject);
                System.out.println(subject);

                projectFrame.validate();
            }
        };

        return action;
    }

    private void drawLabs(int subject){
        JPanel BottomCenterPanel = new JPanel();
        BottomCenterPanel.setBounds(250,190,650,500);
        BottomCenterPanel.setBackground(Color.LIGHT_GRAY);
        projectFrame.add(BottomCenterPanel);

        GridLayout gbl = new GridLayout(dataBase.getLabsBySubject(subject).size(),1);
        BottomCenterPanel.setLayout(gbl);

        JPanel labs [] = new JPanel[dataBase.getLabsBySubject(subject).size()];

        List <Labs> ar = dataBase.getLabsBySubject(subject);

        for(int i=0; i<ar.size(); i++) {
            labs[i] = new JPanel();
            //  labs[i].setPreferredSize(new Dimension(300,50));
            GridLayout g = new GridLayout(1,4);
            labs[i].setLayout(g);

            JLabel lb = new JLabel("             " + ar.get(i).getLabNumber());
            lb.setFont(new Font("Times New Roman", Font.ITALIC, 30));
            lb.setOpaque(true);
            lb.setBackground(new Color(113, 74, 176));
            lb.setPreferredSize(new Dimension(50,100));

            JTextArea textArea = new JTextArea();
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            if(ar.get(i) != null) textArea.setText(ar.get(i).getComment());
            else textArea.setText("Додайте свій коментар");
            textArea.setFont(new Font("Times New Roman", Font.ITALIC, 30));
            textArea.setBackground(new Color(113, 74, 176));

            textArea.setPreferredSize(new Dimension(200,100));

            JTextField txField = new JTextField(ar.get(i).getDeadline());
            txField.setFont(new Font("Times New Roman", Font.ITALIC, 30));
            txField.setBackground(new Color(113, 74, 176));

            txField.setPreferredSize(new Dimension(150,100));

            JCheckBox statBox = new JCheckBox();
            statBox.setBackground(new Color(113, 74, 176));

            statBox.setPreferredSize(new Dimension(50,100));

            labs[i].add(lb);
            labs[i].add(textArea);
            labs[i].add(txField);
            labs[i].add(statBox);
            BottomCenterPanel.add(labs[i]);
        }
        JScrollPane jScrollPanelCenterBottomPanel = new JScrollPane(BottomCenterPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPanelCenterBottomPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        jScrollPanelCenterBottomPanel.setBounds(250,190,650,500);
        projectFrame.add(jScrollPanelCenterBottomPanel);

    }
}