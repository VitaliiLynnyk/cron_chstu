package chstu.gui;

import chstu.db.*;
import chstu.db.entity.Laboratory;
import chstu.db.entity.Lesson;
import chstu.db.entity.LessonTimetable;
import chstu.db.entity.Subject;
import chstu.gui.utils.ViewportActions;
import chstu.gui.utils.ViewportElements;
import chstu.gui.utils.ViewportLogic;
import chstu.gui.utils.ViewportStyle;
import chstu.timetable.DateUtil;
import chstu.timetable.Tasks;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.util.List;
/**
 * Created by linni on 5/13/2017.
 */
public class Viewport {
    public Viewport() {
        bottomCenterPanel = new JPanel(null);
        jScrollPanelCenterBottomPanel = vElements.getScrollPane(bottomCenterPanel,new Rectangle(250,190,650,500));
        projectFrame.add(jScrollPanelCenterBottomPanel);
        bottomCenterPanel.setBackground(vStyle.colorMPanelGray);
    }
    JFrame projectFrame = new JFrame("CRON_CHSTU");
    DBAdapter dataBase = DBAdapter.getInstance();
    ViewportElements vElements = new ViewportElements();
    ViewportStyle vStyle = ViewportStyle.getInstance();
    ViewportActions vActions = new ViewportActions();
    ViewportLogic vLogic = new ViewportLogic();

    JScrollPane jScrollPanelCenterBottomPanel;
    JPanel bottomCenterPanel;
    JPanel panelSubjectInSelectDate;
    private int subjectId = 0;
    private JLabel namePickedSubject;

    public void makeForm(){
        projectFrame.setSize(1200,700);
        projectFrame.setResizable(false);
        projectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        projectFrame.setLocationRelativeTo(null);
        projectFrame.setLayout(null);
        projectFrame.setBackground(Color.white);

//topMenu
        JLabel programName = new JLabel("CRON_CHSTU", SwingConstants.CENTER);
            programName.setOpaque(true);
            vElements.setJComponentExtendedProperties(programName, vStyle.fontChl30,new Rectangle(0,0,250,40),vStyle.colorNameYellow,vStyle.colorViolet1);
            projectFrame.add(programName);

        JPanel centerTopMenu = vElements.getPanel(null,new Rectangle(251,0,648,40),vStyle.colorViolet5);
            projectFrame.add(centerTopMenu);

        JLabel comment = vElements.getLable("введіть кількість лаб",vStyle.fontTnr15,new Rectangle(140,0,150,40),vStyle.colorViolet1);
            centerTopMenu.add(comment);

        namePickedSubject = vElements.getLable("Назва ",vStyle.fontTnr15,new Rectangle(10,0,150,40),vStyle.colorViolet1);
            centerTopMenu.add(namePickedSubject);

        JTextField numberOfLabs = new JTextField();
            numberOfLabs.setBounds(300,10,100,20);
            centerTopMenu.add(numberOfLabs);

        JButton btnOkey = new JButton("Додати");
            btnOkey.setBounds(500,10,100,20);
            centerTopMenu.add(btnOkey);
            btnOkey.addActionListener(new ActionListener() {
                                          public void actionPerformed(ActionEvent e) {
                                              int completed = 0;
                                              int debts = 0;
                                              String numbers="1234567890";
                                              if(numbers.contains(numberOfLabs.getText())){
                                                  DBAdapter db = DBAdapter.getInstance();
                                                  for (Laboratory lab : db.getAllLabs()){
                                                      if(lab.getStatus() == 1 ){
                                                          completed++;
                                                      }
                                                      if(lab.getStatus() == 2 ){
                                                          debts++;
                                                      }
                                                  }
                                                  Tasks tasks = new Tasks();
                                                  tasks.setLabs(subjectId,Integer.parseInt(numberOfLabs.getText()));
                                                  /*progressAllLabs.setText("Всі лабораторні:"+db.getAllLabs().size());
                                                  progressСompleted.setText(" Виконані:"+completed);
                                                  progressDebt.setText(" В боргах:"+debts);*/

                                                  vLogic.showLabs(bottomCenterPanel,subjectId);
                                                  panelSubjectInSelectDate.repaint();
                                              }else {
                                                  numberOfLabs.setBackground(new Color(211, 81, 71));
                                                  numberOfLabs.setText("1-9");
                                              }
                                          }
                                      }
            );

//LEFT PANEL
        JPanel leftPanel = vElements.getPanel(new GridLayout(0,1),new Rectangle(0,80,250,600),null);
        projectFrame.add(leftPanel);

        List<Subject> subjectsList= dataBase.getAllSubjects();
        for (Subject subject : subjectsList) {
            JButton sButton = new JButton(subject.getName());
            sButton.setPreferredSize(new Dimension(100,100));
            sButton.setFont(vStyle.fofntClb26);
            sButton.setVerticalAlignment(SwingConstants.CENTER);
            sButton.setBorderPainted(false);
            sButton.setFocusPainted(false);
            sButton.setBackground(vStyle.colorViolet2);
            sButton.addActionListener(getActionForButtons(subject.getId()));

            leftPanel.add(sButton);
        }

        JScrollPane jScrollPaneLeftPanel = vElements.getScrollPane(leftPanel,new Rectangle(0,80,250,600));
            projectFrame.add(jScrollPaneLeftPanel);

        JLabel labelSubjectName = vElements.getLable("Предмети",vStyle.fontTnrB30,new Rectangle(0,40,250,40),vStyle.colorViolet1);
            projectFrame.add(labelSubjectName);

//CENTER PANEL
        int completed = 0;
        int debts = 0;
        DBAdapter db = DBAdapter.getInstance();
        for (Laboratory lab : db.getAllLabs()){
            if(lab.getStatus() == 1 ){
                completed++;
            }
            if(lab.getStatus() == 2 ){
                debts++;
            }
        }
        JLabel progressAllLabs = vElements.getLable("Всі лабораторні:"+db.getAllLabs().size(),vStyle.fontTnrB30,new Rectangle(0,0,300,100),vStyle.colorViolet1);
        JLabel progressСompleted = vElements.getLable(" Виконані:"+completed,vStyle.fontTnrB30,new Rectangle(220,0,300,100),vStyle.colorPassedGreen);
        JLabel progressDebt = vElements.getLable(" В боргах:"+debts,vStyle.fontTnrB30,new Rectangle(390,0,300,100),vStyle.colorDebtRed);

        JPanel topCenterPanel = vElements.getPanel(null,new Rectangle(250,40,650,100),vStyle.colorViolet5);
            topCenterPanel.add(progressAllLabs);
            topCenterPanel.add(progressСompleted);
            topCenterPanel.add(progressDebt);
            projectFrame.add(topCenterPanel);


        JLabel labelSubjectNumber = vElements.getLable("№",vStyle.fontTnrB30,new Rectangle(250,140,35,50),vStyle.colorViolet1);
            projectFrame.add(labelSubjectNumber);
        JLabel labelSubjectComment = vElements.getLable("Коментар",vStyle.fontTnrB30,new Rectangle(285,140,365,50),vStyle.colorViolet1);
            projectFrame.add(labelSubjectComment);
        JLabel labelSubjectDate = vElements.getLable("Дата сдачі",vStyle.fontTnrB30,new Rectangle(650,140,200,50),vStyle.colorViolet1);
            projectFrame.add(labelSubjectDate);

//RIGHT PANEL
        JPanel topRightPanel = vElements.getPanel(new GridLayout(1,0),new Rectangle(900,0,300,190),vStyle.colorViolet1);
            projectFrame.add(topRightPanel);

        JDatePanelImpl datePanel = vElements.getDataPanel();
            datePanel.setBounds(900,30,300,100);
            topRightPanel.add(datePanel);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new Calendars());

        JLabel lbSelectSubject = vElements.getLable("Розклад  ",vStyle.fontTnr25,new Rectangle(900,193 ,300,43),vStyle.colorViolet4);
            projectFrame.add(lbSelectSubject);

        panelSubjectInSelectDate = vElements.getPanel(null,new Rectangle(900,270,300,190),vStyle.colorViolet4);
        JScrollPane jScrollPaneRightTopPanel = vElements.getScrollPane(panelSubjectInSelectDate,new Rectangle(900,270,300,190));
            projectFrame.add(jScrollPaneRightTopPanel);

        JLabel lbSelectSubjectRightPanel = vElements.getLable("№",vStyle.fontTnr25,new Rectangle(805,230 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectSubjectRightPanel);

        JLabel lbSelectNameRightPanel = vElements.getLable("Назва",vStyle.fontTnr25,new Rectangle(900,230 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectNameRightPanel);

        JLabel lbSelectTypeRightPanel = vElements.getLable("Тип",vStyle.fontTnr25,new Rectangle(1000,230 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectTypeRightPanel);

        datePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vLogic.showTimetable(panelSubjectInSelectDate, datePicker.getJFormattedTextField().getText());
            }
        });


        JLabel lbSelectSubjectRightBottomPanel  = vElements.getLable("№",vStyle.fontTnr25,new Rectangle(805,490 ,300,43),vStyle.colorViolet4);
            projectFrame.add(lbSelectSubjectRightBottomPanel);

        JLabel lbSelectNameRightBottomPanel = vElements.getLable("Назва",vStyle.fontTnr25,new Rectangle(900,490 ,300,43),vStyle.colorViolet4);
            projectFrame.add(lbSelectNameRightBottomPanel);

        JLabel lbSelectTypeRightBottomPanel = vElements.getLable("Тип",vStyle.fontTnr25,new Rectangle(1000,490 ,300,43),vStyle.colorViolet4);
            projectFrame.add(lbSelectTypeRightBottomPanel);

        JLabel lbSubjectNextDay = vElements.getLable("Розклад на сьогодні",vStyle.fontTnr25,new Rectangle(900,450 ,300,50),vStyle.colorViolet4);
            projectFrame.add(lbSubjectNextDay);

        JPanel bottomRightPanel = vElements.getPanel(null,new Rectangle(900,530,300,200),vStyle.colorViolet2);
            JScrollPane jScrollPaneRightBottomPanel = vElements.getScrollPane(bottomRightPanel,new Rectangle(900,530,300,200));
            vLogic.showTimetable(bottomRightPanel,new DateUtil().getCurrentDate());

        projectFrame.add(jScrollPaneRightBottomPanel);


        projectFrame.setVisible(true);
    }

    private ActionListener getActionForButtons(int subject){
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vLogic.showLabs(bottomCenterPanel,subject);
                subjectId = subject;

                namePickedSubject.setText(dataBase.getAllSubjects().get(subject-1).getName());
                projectFrame.validate();
            }
        };
        return action;
    }
}