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
    JFrame projectFrame = new JFrame();
    DBAdapter dataBase = DBAdapter.getInstance();
    ViewportElements vElements = new ViewportElements();
    ViewportStyle vStyle = ViewportStyle.getInstance();
    ViewportActions vActions = new ViewportActions();
    ViewportLogic vLogic = new ViewportLogic();

    JScrollPane jScrollPanelCenterBottomPanel;
    JPanel bottomCenterPanel;
    JLabel progressAllLabs;
    JLabel progressСompleted;
    JLabel progressDebt;
    JPanel panelSubjectInSelectDate;
    private int subjectId = 0;
    private JLabel namePickedSubject;

    public void makeForm(){
        projectFrame.setSize(1200,700);
        projectFrame.setTitle("frame");
        projectFrame.setResizable(false);

        projectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        projectFrame.setLocationRelativeTo(null);
        projectFrame.setLayout(null);
        projectFrame.setBackground(Color.white);

//topMenu
        JPanel leftTopMenu = vElements.getPanel(null,new Rectangle(0,0,250,40),vStyle.colorViolet1);
        projectFrame.add(leftTopMenu);

        JLabel programName = vElements.getLable("CRON_CHSTU",vStyle.fontChl30,new Rectangle(45,0,150,40),vStyle.colorNameYellow);
        leftTopMenu.add(programName);

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

        btnOkey.addActionListener(new ActionListener()
                                  {
                                      public void actionPerformed(ActionEvent e)
                                      {
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
                                              progressAllLabs.setText("Всі лабораторні:"+db.getAllLabs().size());
                                              progressСompleted.setText(" Виконані:"+completed);
                                              progressDebt.setText(" В боргах:"+debts);

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
        JPanel leftPanel = vElements.getPanel(new GridLayout(0,1),new Rectangle(0,80,250,600),Color.BLACK);
        projectFrame.add(leftPanel);

        List<Subject> arrayList= dataBase.getAllSubjects();
        ArrayList<JButton>btn = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            btn.add(i,new JButton(arrayList.get(i).getName()+""));
            btn.get(i).setPreferredSize(new Dimension(100,100));
            btn.get(i).setFont(new Font("Calibri", Font.ITALIC, 26));
            btn.get(i).setVerticalAlignment(SwingConstants.CENTER);
            btn.get(i).setBorderPainted(false);
            btn.get(i).setFocusPainted(false);
            btn.get(i).setBackground(new Color(177, 148, 226));
            btn.get(i).addActionListener(getActionForButtons(i+1));
            leftPanel.add(btn.get(i));
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
        progressAllLabs = vElements.getLable("Всі лабораторні:"+db.getAllLabs().size(),vStyle.fontTnrB30,new Rectangle(0,0,300,100),vStyle.colorViolet1);
        progressСompleted = vElements.getLable(" Виконані:"+completed,vStyle.fontTnrB30,new Rectangle(220,0,300,100),vStyle.colorPassedGreen);
        progressDebt = vElements.getLable(" В боргах:"+debts,vStyle.fontTnrB30,new Rectangle(390,0,300,100),vStyle.colorDebtRed);

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
        projectFrame.add(panelSubjectInSelectDate);

        JLabel lbSelectSubjectRightPanel = vElements.getLable("№",vStyle.fontTnr25,new Rectangle(805,230 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectSubjectRightPanel);

        JLabel lbSelectNameRightPanel = vElements.getLable("Назва",vStyle.fontTnr25,new Rectangle(900,230 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectNameRightPanel);

        JLabel lbSelectTypeRightPanel = vElements.getLable("Тип",vStyle.fontTnr25,new Rectangle(1000,230 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectTypeRightPanel);

        datePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDate = datePicker.getJFormattedTextField().getText();
                List<LessonTimetable> subjectInSelectDate = dataBase.getLessonsInDay(selectedDate);
                List<Lesson> selectLabs = dataBase.getLessonsToShow(selectedDate);
                JPanel subjects [] = new JPanel[selectLabs.size()];
                panelSubjectInSelectDate.removeAll();
                panelSubjectInSelectDate.repaint();
                panelSubjectInSelectDate.setLayout(new GridLayout(5,3));
                lbSelectSubject.setText("Розклад на "+selectedDate);

                for (int subjectNumber = 0; subjectNumber < subjectInSelectDate.size(); subjectNumber++){
                    subjects[subjectNumber] = new JPanel();
                    subjects[subjectNumber].setLayout(null);
                    subjects[subjectNumber].setBackground(new Color(137, 114, 176));

                    JLabel labelNumberOfSubject = vElements.getLable(subjectInSelectDate.get(subjectNumber).getNumberLesson() + "",vStyle.fofntClb26,new Rectangle(0,0,100,25),Color.black);

                    JLabel labelNameOfSubject = vElements.getLable(selectLabs.get(subjectNumber).getName()+"",vStyle.fofntClb26,new Rectangle(100,0,100,25),Color.black);

                    JLabel labelTypeOfSubject = vElements.getLable(selectLabs.get(subjectNumber).getType().substring(0,3)+"",vStyle.fofntClb26,new Rectangle(200,0,100,25),Color.black);

                    subjects[subjectNumber].add(labelNumberOfSubject);
                    subjects[subjectNumber].add(labelNameOfSubject);
                    subjects[subjectNumber].add(labelTypeOfSubject);
                    panelSubjectInSelectDate.add(subjects[subjectNumber]);
                }
            }
        });


        JLabel lbSelectSubjectRightBottomPanel  = vElements.getLable("№",vStyle.fontTnr25,new Rectangle(805,490 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectSubjectRightBottomPanel);

        JLabel lbSelectNameRightBottomPanel = vElements.getLable("Назва",vStyle.fontTnr25,new Rectangle(900,490 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectNameRightBottomPanel);

        JLabel lbSelectTypeRightBottomPanel = vElements.getLable("Тип",vStyle.fontTnr25,new Rectangle(1000,490 ,300,43),vStyle.colorViolet4);
        projectFrame.add(lbSelectTypeRightBottomPanel);

        JPanel bottomRightPanel = vElements.getPanel(null,new Rectangle(900,530,300,200),vStyle.colorViolet2);
        bottomRightPanel.setLayout(new GridLayout(5,1));
        projectFrame.add(bottomRightPanel);

        DateUtil dates = new DateUtil();
        String strDate = dates.getCurrentDate();

        JLabel lbSubjectNextDay = vElements.getLable("Розклад на сьогодні",vStyle.fontTnr25,new Rectangle(900,450 ,300,50),vStyle.colorViolet4);
        projectFrame.add(lbSubjectNextDay);

        List <LessonTimetable> nextDaySubjects = dataBase.getLessonsInDay(strDate);
        List<Lesson> selectLabs = dataBase.getLessonsToShow(strDate);
        JPanel subjects [] = new JPanel[3];
        bottomRightPanel.setLayout(new GridLayout(5,3));
        for (int subjectNumber = 0; subjectNumber < nextDaySubjects.size(); subjectNumber++){
            subjects[subjectNumber] = new JPanel();
            subjects[subjectNumber].setLayout(new GridLayout(0,3));
            subjects[subjectNumber].setBackground(new Color(177, 148, 226));

            JLabel labelNumberOfSubject = vElements.getLable(nextDaySubjects.get(subjectNumber).getNumberLesson() + "",vStyle.fofntClb26,new Rectangle(0,0,100,25),Color.black);

            JLabel labelNameOfSubject = vElements.getLable(selectLabs.get(subjectNumber).getName()+"",vStyle.fofntClb26,new Rectangle(100,0,100,25),Color.black);

            JLabel labelTypeOfSubject = vElements.getLable(selectLabs.get(subjectNumber).getType().substring(0,3)+"",vStyle.fofntClb26,new Rectangle(200,0,100,25),Color.black);

            subjects[subjectNumber].add(labelNumberOfSubject);
            subjects[subjectNumber].add(labelNameOfSubject);
            subjects[subjectNumber].add(labelTypeOfSubject);
            bottomRightPanel.add(subjects[subjectNumber]);
        }

        JScrollPane jScrollPaneRightBottomPanel = vElements.getScrollPane(bottomRightPanel,new Rectangle(900,530,300,200));
        projectFrame.add(jScrollPaneRightBottomPanel);

        projectFrame.setVisible(true);
    }

    private ActionListener getActionForButtons(int subject){
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vLogic.showLabs(bottomCenterPanel,subjectId);
                subjectId = subject;

                namePickedSubject.setText(dataBase.getAllSubjects().get(subject-1).getName());
                projectFrame.validate();
            }
        };
        return action;
    }
}