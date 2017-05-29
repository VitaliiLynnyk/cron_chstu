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
        dataBase = DBAdapter.getInstance();
        vElements = new ViewportElements();
        vStyle = ViewportStyle.getInstance();

        bottomCenterPanel = vElements.getPanel(null, new Rectangle(0,0,0,0), vStyle.colorMPanelGray);
        progressAllLabs = vElements.getLable("",vStyle.fontTnrB30,new Rectangle(0,0,300,100),vStyle.colorViolet1);
        progressСompleted = vElements.getLable("",vStyle.fontTnrB30,new Rectangle(220,0,300,100),vStyle.colorPassedGreen);
        progressDebt = vElements.getLable("",vStyle.fontTnrB30,new Rectangle(390,0,300,100),vStyle.colorDebtRed);

        vActions = new ViewportActions(progressAllLabs, progressСompleted, progressDebt);
    }

    private DBAdapter dataBase;
    private ViewportElements vElements;
    private ViewportStyle vStyle;
    private ViewportActions vActions;

    private JPanel bottomCenterPanel;
    private JLabel progressAllLabs;
    private JLabel progressСompleted;
    private JLabel progressDebt;

    public void makeForm(){
        JFrame projectFrame = new JFrame("CRON_CHSTU");
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

        JLabel namePickedSubject = vElements.getLable("Назва ",vStyle.fontTnr15,new Rectangle(10,0,150,40),vStyle.colorViolet1);
            centerTopMenu.add(namePickedSubject);

        JTextField numberOfLabs = new JTextField();
            numberOfLabs.setBounds(300,10,100,20);
            centerTopMenu.add(numberOfLabs);

        JButton btnOkey = new JButton("Додати");
            btnOkey.setBounds(500,10,100,20);
            centerTopMenu.add(btnOkey);
            btnOkey.addActionListener(vActions.createOkButtonListener(numberOfLabs,bottomCenterPanel));

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
            sButton.addActionListener(vActions.createSubjectButtonListener(subject.getId(), bottomCenterPanel, namePickedSubject));

            leftPanel.add(sButton);
        }

        JScrollPane jScrollPaneLeftPanel = vElements.getScrollPane(leftPanel,new Rectangle(0,80,250,600));
            projectFrame.add(jScrollPaneLeftPanel);

        JLabel labelSubjectName = vElements.getLable("Предмети",vStyle.fontTnrB30,new Rectangle(0,40,250,40),vStyle.colorViolet1);
            projectFrame.add(labelSubjectName);

//CENTER PANEL
        JPanel topCenterPanel = vElements.getPanel(null,new Rectangle(250,40,650,100),vStyle.colorViolet5);
            topCenterPanel.add(progressAllLabs);
            topCenterPanel.add(progressСompleted);
            topCenterPanel.add(progressDebt);
            projectFrame.add(topCenterPanel);

        vActions.setLabStatistic();

        JLabel labelSubjectNumber = vElements.getLable("№",vStyle.fontTnrB30,new Rectangle(250,140,35,50),vStyle.colorViolet1);
            projectFrame.add(labelSubjectNumber);
        JLabel labelSubjectComment = vElements.getLable("Коментар",vStyle.fontTnrB30,new Rectangle(285,140,365,50),vStyle.colorViolet1);
            projectFrame.add(labelSubjectComment);
        JLabel labelSubjectDate = vElements.getLable("Дата сдачі",vStyle.fontTnrB30,new Rectangle(650,140,200,50),vStyle.colorViolet1);
            projectFrame.add(labelSubjectDate);

//CENTRAL BOTTOM PANEL
        JScrollPane jScrollPanelCenterBottomPanel = vElements.getScrollPane(bottomCenterPanel,new Rectangle(250,190,650,500));
            projectFrame.add(jScrollPanelCenterBottomPanel);

//RIGHT PANEL
        JPanel topRightPanel = vElements.getPanel(new GridLayout(1,0),new Rectangle(900,0,300,190),vStyle.colorViolet1);
            projectFrame.add(topRightPanel);

        JPanel panelSubjectInSelectDate = vElements.getPanel(null,new Rectangle(900,270,300,190),vStyle.colorViolet4);
            JScrollPane jScrollPaneRightTopPanel = vElements.getScrollPane(panelSubjectInSelectDate,new Rectangle(900,270,300,190));
        projectFrame.add(jScrollPaneRightTopPanel);

        JDatePanelImpl datePanel = vElements.getDataPanel();
            datePanel.setBounds(900,30,300,100);
            topRightPanel.add(datePanel);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new Calendars());
            datePanel.addActionListener(vActions.createDatePickerListener(panelSubjectInSelectDate,datePicker));

        JLabel lbSelectSubject = vElements.getLable("Розклад  ",vStyle.fontTnr25,new Rectangle(900,193 ,300,43),vStyle.colorViolet4);
            projectFrame.add(lbSelectSubject);

        JPanel topTimetableInfoPanel = vElements.getTimetableInfoPanel(new Rectangle(900,230,300,43));
            projectFrame.add(topTimetableInfoPanel);


        JLabel lbSubjectNextDay = vElements.getLable("Розклад на сьогодні",vStyle.fontTnr25,new Rectangle(900,450 ,300,50),vStyle.colorViolet4);
            projectFrame.add(lbSubjectNextDay);

        JPanel bottomTimetableInfoPanel = vElements.getTimetableInfoPanel(new Rectangle(900,490,300,43));
            projectFrame.add(bottomTimetableInfoPanel);

        JPanel bottomRightPanel = vElements.getPanel(null,new Rectangle(900,530,300,200),vStyle.colorViolet4);
            JScrollPane jScrollPaneRightBottomPanel = vElements.getScrollPane(bottomRightPanel,new Rectangle(900,530,300,200));
            vActions.showTimetable(bottomRightPanel,new DateUtil().getCurrentDate());
        projectFrame.add(jScrollPaneRightBottomPanel);


        projectFrame.setVisible(true);
    }
}