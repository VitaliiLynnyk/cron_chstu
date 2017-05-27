package chstu.gui;

import chstu.db.*;
import chstu.db.entity.Laboratory;
import chstu.db.entity.Lesson;
import chstu.db.entity.LessonTimetable;
import chstu.db.entity.Subjects;
import chstu.gui.utils.ViewportElements;
import chstu.gui.utils.ViewportStyle;
import chstu.timetable.DateUtil;
import chstu.timetable.Tasks;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
    }
    JFrame projectFrame = new JFrame();
    DBAdapter dataBase = DBAdapter.getInstance();
    ViewportElements vElements = new ViewportElements();
    ViewportStyle vStyle = ViewportStyle.getInstance();

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
        JPanel leftTopMenu = vElements.getPanel(null,new Rectangle(0,0,250,40),vStyle.violet1);
        projectFrame.add(leftTopMenu);

        JLabel programName = vElements.getLable("CRON_CHSTU",vStyle.chl30,new Rectangle(45,0,150,40),vStyle.colorNameYellow);
        leftTopMenu.add(programName);

        JPanel centerTopMenu = vElements.getPanel(null,new Rectangle(251,0,648,40),vStyle.violet5);
        projectFrame.add(centerTopMenu);

        JLabel comment = vElements.getLable("введіть кількість лаб",vStyle.tnr15,new Rectangle(140,0,150,40),vStyle.violet1);
        centerTopMenu.add(comment);

        namePickedSubject = vElements.getLable("Назва ",vStyle.tnr15,new Rectangle(10,0,150,40),vStyle.violet1);
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
                                          if(numbers.contains(numberOfLabs.toString())){
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
                                              drawLabs(subjectId);
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

        List<Subjects> arrayList= dataBase.getAllSubjects();
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

        JLabel labelSubjectName = vElements.getLable("Предмети",vStyle.tnrB30,new Rectangle(0,40,250,40),vStyle.violet1);
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
        progressAllLabs = vElements.getLable("Всі лабораторні:"+db.getAllLabs().size(),vStyle.tnrB30,new Rectangle(0,0,300,100),vStyle.violet1);
        progressСompleted = vElements.getLable(" Виконані:"+completed,vStyle.tnrB30,new Rectangle(220,0,300,100),vStyle.passedGreen);
        progressDebt = vElements.getLable(" В боргах:"+debts,vStyle.tnrB30,new Rectangle(390,0,300,100),vStyle.debtRed);

        JPanel topCenterPanel = vElements.getPanel(null,new Rectangle(250,40,650,100),vStyle.violet5);
        topCenterPanel.add(progressAllLabs);
        topCenterPanel.add(progressСompleted);
        topCenterPanel.add(progressDebt);
        projectFrame.add(topCenterPanel);

        JLabel labelSubjectNumber = vElements.getLable("№",vStyle.tnrB30,new Rectangle(250,140,35,50),vStyle.violet1);
        projectFrame.add(labelSubjectNumber);

        JLabel labelSubjectComment = vElements.getLable("Коментар",vStyle.tnrB30,new Rectangle(285,140,365,50),vStyle.violet1);
        projectFrame.add(labelSubjectComment);

        JLabel labelSubjectDate = vElements.getLable("Дата сдачі",vStyle.tnrB30,new Rectangle(650,140,200,50),vStyle.violet1);
        projectFrame.add(labelSubjectDate);
//RIGHT PANEL
        JPanel topRightPanel = vElements.getPanel(new GridLayout(1,0),new Rectangle(900,0,300,190),vStyle.violet1);
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

        JLabel lbSelectSubject = vElements.getLable("Розклад  ",vStyle.tnr25,new Rectangle(900,193 ,300,43),vStyle.violet4);
        projectFrame.add(lbSelectSubject);

        panelSubjectInSelectDate = vElements.getPanel(null,new Rectangle(900,270,300,190),vStyle.violet4);
        projectFrame.add(panelSubjectInSelectDate);

        JLabel lbSelectSubjectRightPanel = vElements.getLable("№",vStyle.tnr25,new Rectangle(805,230 ,300,43),vStyle.violet4);
        projectFrame.add(lbSelectSubjectRightPanel);

        JLabel lbSelectNameRightPanel = vElements.getLable("Назва",vStyle.tnr25,new Rectangle(900,230 ,300,43),vStyle.violet4);
        projectFrame.add(lbSelectNameRightPanel);

        JLabel lbSelectTypeRightPanel = vElements.getLable("Тип",vStyle.tnr25,new Rectangle(1000,230 ,300,43),vStyle.violet4);
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

                    JLabel labelNumberOfSubject = vElements.getLable(subjectInSelectDate.get(subjectNumber).getNumberLesson() + "",vStyle.clb26,new Rectangle(0,0,100,25),Color.black);

                    JLabel labelNameOfSubject = vElements.getLable(selectLabs.get(subjectNumber).getName()+"",vStyle.clb26,new Rectangle(100,0,100,25),Color.black);

                    JLabel labelTypeOfSubject = vElements.getLable(selectLabs.get(subjectNumber).getType().substring(0,3)+"",vStyle.clb26,new Rectangle(200,0,100,25),Color.black);

                    subjects[subjectNumber].add(labelNumberOfSubject);
                    subjects[subjectNumber].add(labelNameOfSubject);
                    subjects[subjectNumber].add(labelTypeOfSubject);
                    panelSubjectInSelectDate.add(subjects[subjectNumber]);
                }
            }
        });


        JLabel lbSelectSubjectRightBottomPanel  = vElements.getLable("№",vStyle.tnr25,new Rectangle(805,490 ,300,43),vStyle.violet4);
        projectFrame.add(lbSelectSubjectRightBottomPanel);

        JLabel lbSelectNameRightBottomPanel = vElements.getLable("Назва",vStyle.tnr25,new Rectangle(900,490 ,300,43),vStyle.violet4);
        projectFrame.add(lbSelectNameRightBottomPanel);

        JLabel lbSelectTypeRightBottomPanel = vElements.getLable("Тип",vStyle.tnr25,new Rectangle(1000,490 ,300,43),vStyle.violet4);
        projectFrame.add(lbSelectTypeRightBottomPanel);

        JPanel bottomRightPanel = vElements.getPanel(null,new Rectangle(900,530,300,200),vStyle.violet2);
        bottomRightPanel.setLayout(new GridLayout(5,1));
        projectFrame.add(bottomRightPanel);

        DateUtil dates = new DateUtil();
        String strDate = dates.getCurrentDate();

        JLabel lbSubjectNextDay = vElements.getLable("Розклад на сьогодні",vStyle.tnr25,new Rectangle(900,450 ,300,50),vStyle.violet4);
        projectFrame.add(lbSubjectNextDay);

        List <LessonTimetable> nextDaySubjects = dataBase.getLessonsInDay(strDate);
        List<Lesson> selectLabs = dataBase.getLessonsToShow(strDate);
        JPanel subjects [] = new JPanel[3];
        bottomRightPanel.setLayout(new GridLayout(5,3));
        for (int subjectNumber = 0; subjectNumber < nextDaySubjects.size(); subjectNumber++){
            subjects[subjectNumber] = new JPanel();
            subjects[subjectNumber].setLayout(new GridLayout(0,3));
            subjects[subjectNumber].setBackground(new Color(177, 148, 226));

            JLabel labelNumberOfSubject = vElements.getLable(nextDaySubjects.get(subjectNumber).getNumberLesson() + "",vStyle.clb26,new Rectangle(0,0,100,25),Color.black);

            JLabel labelNameOfSubject = vElements.getLable(selectLabs.get(subjectNumber).getName()+"",vStyle.clb26,new Rectangle(100,0,100,25),Color.black);

            JLabel labelTypeOfSubject = vElements.getLable(selectLabs.get(subjectNumber).getType().substring(0,3)+"",vStyle.clb26,new Rectangle(200,0,100,25),Color.black);

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
                drawLabs(subject);
                subjectId = subject;

                namePickedSubject.setText(dataBase.getAllSubjects().get(subject-1).getName());
                projectFrame.validate();
            }
        };
        return action;
    }
    boolean isFirstShowInSession = true;
    private void drawLabs(int subject){
        if (isFirstShowInSession) {
            isFirstShowInSession = false;
        }
        else {
            bottomCenterPanel.removeAll();
        }
        List <Laboratory> laboratoryForSubject = dataBase.getLabsBySubject(subject);

        bottomCenterPanel.setPreferredSize(new Dimension(650,100* laboratoryForSubject.size()));
        bottomCenterPanel.setBackground(Color.LIGHT_GRAY);

        JPanel labPanel [] = new JPanel[laboratoryForSubject.size()];

        for(int labNumber = 0, panelHeight = 0; labNumber< laboratoryForSubject.size(); labNumber++, panelHeight += 100) {
            Laboratory labwork = laboratoryForSubject.get(labNumber);
            labPanel[labNumber] = vElements.getPanel(null,new Rectangle(0,panelHeight,650,100),null);

            JLabel labelNumberLab = new JLabel("" + labwork.getLabNumber(), SwingConstants.CENTER);
                labelNumberLab.setOpaque(true);
                vElements.setJComponentExtendedParametr(labelNumberLab,vStyle.tnrI30,new Rectangle(0,0,35,100),vStyle.violet5,vStyle.violet1);

            JTextArea labCommentArea = new JTextArea();
                vElements.setJComponentExtendedParametr(labCommentArea,vStyle.tnrI30,new Rectangle(35,0,365,100),vStyle.violet5,vStyle.violet1);
                labCommentArea.setLineWrap(true);
                labCommentArea.setWrapStyleWord(true);
                labCommentArea.setText(labwork.getComment().isEmpty() ? " Додайте свій коментар" : labwork.getComment());
                labCommentArea.getDocument().addDocumentListener(createCommentAreaListener(labCommentArea,labwork));

            UtilDateModel model = new UtilDateModel();
                model.setSelected(true);
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                DateUtil dateUtil = new DateUtil();
                Calendar calendar = dateUtil.convertStringInDate(labwork.getDeadline());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                model.setDate(year,month,day);

            JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new Calendars());
                datePicker.setBackground(new Color(113, 74, 176));
                datePicker.setBounds(400,0,200,100);
                datePicker.setToolTipText(labwork.getDeadline());
                datePicker.addActionListener(createDatePickerListener(datePicker,labwork));

            JCheckBox statBox = new JCheckBox();
                vElements.setJComponentExtendedParametr(statBox,null,new Rectangle(600,0,50,100),null,getColorForLabStatus(labwork));
                statBox.setVerticalAlignment(SwingConstants.CENTER);
                statBox.setHorizontalAlignment(SwingConstants.CENTER);
                statBox.setSelected(labwork.getStatus() == 1);
                statBox.addItemListener(getCheckBoxEvent(statBox,labwork));

            labPanel[labNumber].add(labelNumberLab);
            labPanel[labNumber].add(labCommentArea);
            labPanel[labNumber].add(datePicker);
            labPanel[labNumber].add(statBox);
            bottomCenterPanel.add(labPanel[labNumber]);
        }
    }
    private ItemListener getCheckBoxEvent(JCheckBox jCheckBox ,Laboratory lab){
        return  new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jCheckBox.isSelected()) {
                    System.out.println("selected");
                    dataBase.updateLabStatus(1,lab.getIdSubject(),lab.getLabNumber());
                    jCheckBox.setBackground(new Color(63, 171, 57));
                }
                else {
                    System.out.println("not selected");
                    dataBase.updateLabStatus(0,lab.getIdSubject(),lab.getLabNumber());
                    jCheckBox.setBackground(new Color( 113, 74, 176));
                }
                projectFrame.validate();
            }
        };
    }

    private ActionListener createDatePickerListener(JDatePickerImpl datePicker ,Laboratory lab){
        return  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataBase.updateLabDeadline(datePicker.getJFormattedTextField().getText(),lab.getIdSubject(),lab.getLabNumber());
            }
        };
    }

    private DocumentListener createCommentAreaListener(JTextArea commentArea, Laboratory lab){
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                dataBase.updateLabComment(commentArea.getText(),lab.getIdSubject(),lab.getLabNumber());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                dataBase.updateLabComment(commentArea.getText(),lab.getIdSubject(),lab.getLabNumber());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                dataBase.updateLabComment(commentArea.getText(),lab.getIdSubject(),lab.getLabNumber());
            }
        };
    }

    private Color getColorForLabStatus(Laboratory lab){
        Color statusColor = null;

        switch (lab.getStatus()){
            case 0: statusColor = new Color(113, 74, 176); break;
            case 1: statusColor = new Color(63, 171, 57); break;
            case 2: statusColor = new Color(211, 81, 71 );
        }

        return statusColor;
    }
}