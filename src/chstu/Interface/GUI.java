package chstu.Interface;

import chstu.db.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by linni on 5/13/2017.
 */
public class GUI {
    public GUI() {
        bottomCenterPanel = new JPanel(null);

        jScrollPanelCenterBottomPanel = new JScrollPane(bottomCenterPanel);
        jScrollPanelCenterBottomPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPanelCenterBottomPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPanelCenterBottomPanel.getVerticalScrollBar().setUnitIncrement(13);
        jScrollPanelCenterBottomPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        jScrollPanelCenterBottomPanel.setBounds(250,190,650,500);
        projectFrame.add(jScrollPanelCenterBottomPanel);
    }

    JFrame projectFrame = new JFrame();
    DBAdapter dataBase = DBAdapter.getInstance();

    JScrollPane jScrollPanelCenterBottomPanel;
    JPanel bottomCenterPanel;

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
        comment.setBounds(170,0,150,40);
        centerTopMenu.add(comment);

        namePickedSubject = new JLabel();
        namePickedSubject.setText("NAME OF SUBJECT");
        namePickedSubject.setBounds(10,0,150,40);
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
                                          Tasks tasks = new Tasks();
                                          tasks.setLabs(subjectId,Integer.parseInt(numberOfLabs.getText()));
                                          drawLabs(subjectId);
                                      }
                                  }
        );
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
        jScrollPaneLeftPanel.getVerticalScrollBar().setUnitIncrement(13);
        jScrollPaneLeftPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        jScrollPaneLeftPanel.setBounds(0,80,250,600);
        projectFrame.add(jScrollPaneLeftPanel);

        JLabel labelSubjectName = new JLabel("Предмети");
        labelSubjectName.setBounds(60,40,250,40);
        labelSubjectName.setFont(new Font("Times New Roman", Font.BOLD, 30));
        projectFrame.add(labelSubjectName);




//CENTER PANEL
        JPanel TopCenterPanel = new JPanel();
        TopCenterPanel.setBounds(250,40,650,100);
        TopCenterPanel.setBackground(new Color(214, 195, 244));
        projectFrame.add(TopCenterPanel);

        JLabel labelSubjectNumber = new JLabel("№");
            labelSubjectNumber.setVerticalAlignment(JLabel.CENTER);
            labelSubjectNumber.setHorizontalAlignment(JLabel.CENTER);
            labelSubjectNumber.setBounds(250,140,35,50);
            labelSubjectNumber.setFont(new Font("Times New Roman", Font.BOLD, 30));
            labelSubjectNumber.setForeground(new Color(113, 74, 176));
            projectFrame.add(labelSubjectNumber);


        JLabel labelSubjectComment = new JLabel("Коментар");
            labelSubjectComment.setBounds(285,140,365,50);
            labelSubjectComment.setVerticalAlignment(JLabel.CENTER);
            labelSubjectComment.setHorizontalAlignment(JLabel.CENTER);
            labelSubjectComment.setFont(new Font("Times New Roman", Font.BOLD, 30));
            labelSubjectComment.setForeground(new Color(113, 74, 176));
            projectFrame.add(labelSubjectComment);

        JLabel labelSubjectDate = new JLabel("Дата сдачі");
            labelSubjectDate.setVerticalAlignment(JLabel.CENTER);
            labelSubjectDate.setHorizontalAlignment(JLabel.CENTER);
            labelSubjectDate.setBounds(650,140,200,50);
            labelSubjectDate.setFont(new Font("Times New Roman", Font.BOLD, 30));
            labelSubjectDate.setForeground(new Color(113, 74, 176));
            projectFrame.add(labelSubjectDate);

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
            panelSubjectInSelectDate.setLayout(new GridLayout(5,1));
            panelSubjectInSelectDate.setBounds(900,270,300,190);
            panelSubjectInSelectDate.setBackground(new Color(137, 114, 176));
            projectFrame.add(panelSubjectInSelectDate);

        JLabel lbSelectSubjectRightPanel = new JLabel("№");
            lbSelectSubjectRightPanel.setForeground(new Color(137, 114, 176));
            lbSelectSubjectRightPanel.setBounds(900,230 ,300,43);
            lbSelectSubjectRightPanel.setFont(new Font("Times new roman", Font.BOLD, 25));
            projectFrame.add(lbSelectSubjectRightPanel);

        JLabel lbSelectNameRightPanel = new JLabel("Назва");
            lbSelectNameRightPanel.setForeground(new Color(137, 114, 176));
            lbSelectNameRightPanel.setBounds(1000,230 ,300,43);
            lbSelectNameRightPanel.setFont(new Font("Times new roman", Font.BOLD, 25));
            projectFrame.add(lbSelectNameRightPanel);

        JLabel lbSelectTypeRightPanel = new JLabel("Тип");
            lbSelectTypeRightPanel.setForeground(new Color(137, 114, 176));
            lbSelectTypeRightPanel.setBounds(1100,230 ,300,43);
            lbSelectTypeRightPanel.setFont(new Font("Times new roman", Font.BOLD, 25));
            projectFrame.add(lbSelectTypeRightPanel);



        datePanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDate = datePicker.getJFormattedTextField().getText();
                List<Timetable> subjectInSelectDate = dataBase.getLessonsInDay(selectedDate);
                List<Lesson> selectLabs = dataBase.getLessonsToShow(selectedDate);
                JLabel [] selectDayTimetableLable = new JLabel[subjectInSelectDate.size()];

                JPanel subjects [] = new JPanel[3];
                panelSubjectInSelectDate.removeAll();
                panelSubjectInSelectDate.setLayout(new GridLayout(5,3));
                lbSelectSubject.setText("Розклад на "+selectedDate);

                for (int i = 0; i < subjectInSelectDate.size(); i++){
                    subjects[i] = new JPanel();
                    subjects[i].setLayout(new GridLayout(0,4));
                    subjects[i].setBackground(new Color(137, 114, 176));
                    JLabel labelNumberOfSubject = new JLabel();
                    labelNumberOfSubject.setPreferredSize(new Dimension(50,30));

                    labelNumberOfSubject.setForeground(new Color(32, 16, 58));

                    labelNumberOfSubject.setFont(new Font("Calibri", Font.ITALIC, 26));
                    labelNumberOfSubject.setText(subjectInSelectDate.get(i).getNumberLesson() + "");

                    JLabel labelNameOfSubject = new JLabel();
                        labelNameOfSubject.setPreferredSize(new Dimension(100,30));
                        labelNameOfSubject.setText(selectLabs.get(i).getName()+"");
                        labelNameOfSubject.setFont(new Font("Calibri", Font.ITALIC, 26));
                        labelNameOfSubject.setForeground(new Color(32, 16, 58));

                    JLabel labelTypeOfSubject = new JLabel();
                        labelTypeOfSubject.setPreferredSize(new Dimension(50,30));
                        labelTypeOfSubject.setText(selectLabs.get(i).getType().substring(0,3)+"");
                        labelTypeOfSubject.setFont(new Font("Calibri", Font.ITALIC, 26));
                        labelTypeOfSubject.setForeground(new Color(32, 16, 58));

                    subjects[i].add(labelNumberOfSubject);
                    subjects[i].add(labelNameOfSubject);
                    subjects[i].add(labelTypeOfSubject);
                    panelSubjectInSelectDate.add(subjects[i]);
                }

                JScrollPane jScrollPaneRightBottomPanel = new JScrollPane(panelSubjectInSelectDate,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                jScrollPaneRightBottomPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
                jScrollPaneRightBottomPanel.getVerticalScrollBar().setUnitIncrement(13);
                jScrollPaneRightBottomPanel.setBounds(900,270,300,190);
                panelSubjectInSelectDate.revalidate();
                projectFrame.add(jScrollPaneRightBottomPanel);
            }
        });


        JLabel lbSelectSubjectRightBottomPanel = new JLabel("№");
            lbSelectSubjectRightBottomPanel.setForeground(new Color(137, 114, 176));
            lbSelectSubjectRightBottomPanel.setBounds(900,490 ,300,43);
            lbSelectSubjectRightBottomPanel.setFont(new Font("Times new roman", Font.BOLD, 25));
            projectFrame.add(lbSelectSubjectRightBottomPanel);

        JLabel lbSelectNameRightBottomPanel = new JLabel("Назва");
            lbSelectNameRightBottomPanel.setForeground(new Color(137, 114, 176));
            lbSelectNameRightBottomPanel.setBounds(1000,490 ,300,43);
            lbSelectNameRightBottomPanel.setFont(new Font("Times new roman", Font.BOLD, 25));
            projectFrame.add(lbSelectNameRightBottomPanel);

        JLabel lbSelectTypeRightBottomPanel = new JLabel("Тип");
            lbSelectTypeRightBottomPanel.setForeground(new Color(137, 114, 176));
            lbSelectTypeRightBottomPanel.setBounds(1100,490 ,300,43);
            lbSelectTypeRightBottomPanel.setFont(new Font("Times new roman", Font.BOLD, 25));
            projectFrame.add(lbSelectTypeRightBottomPanel);




        JPanel bottomRightPanel = new JPanel();
            bottomRightPanel.setLayout(null);
            bottomRightPanel.setLayout(new GridLayout(5,1));
            bottomRightPanel.setBounds(900,530,300,200);
            bottomRightPanel.setBackground(new Color(177, 148, 226));
            projectFrame.add(bottomRightPanel);

        DateUtil dates = new DateUtil();
        String strDate = dates.getCurrentDate();

        JLabel lbSubjectNextDay = new JLabel("Розклад на сьогодні");
            lbSubjectNextDay.setForeground(new Color(137, 114, 176));
            lbSubjectNextDay.setBounds(900,450 ,300,50);
            lbSubjectNextDay.setFont(new Font("Times new roman", Font.BOLD, 25));
            projectFrame.add(lbSubjectNextDay);

        List <Timetable> nextDaySubjects = dataBase.getLessonsInDay(strDate);
        List<Lesson> selectLabs = dataBase.getLessonsToShow(strDate);
        JPanel subjects [] = new JPanel[3];
        bottomRightPanel.setLayout(new GridLayout(5,3));
        for (int i = 0; i < nextDaySubjects.size(); i++){
            subjects[i] = new JPanel();
            subjects[i].setLayout(new GridLayout(0,3));
            subjects[i].setBackground(new Color(177, 148, 226));
            JLabel labelNumberOfSubject = new JLabel();
            labelNumberOfSubject.setPreferredSize(new Dimension(50,30));

            labelNumberOfSubject.setForeground(new Color(32, 16, 58));

            labelNumberOfSubject.setFont(new Font("Calibri", Font.ITALIC, 26));
            labelNumberOfSubject.setText(nextDaySubjects.get(i).getNumberLesson() + "");

            JLabel labelNameOfSubject = new JLabel();
                labelNameOfSubject.setPreferredSize(new Dimension(100,30));
                labelNameOfSubject.setText(selectLabs.get(i).getName()+"");
                labelNameOfSubject.setFont(new Font("Calibri", Font.ITALIC, 26));
                labelNameOfSubject.setForeground(new Color(32, 16, 58));

            JLabel labelTypeOfSubject = new JLabel();
                labelTypeOfSubject.setPreferredSize(new Dimension(50,30));
                labelTypeOfSubject.setText(selectLabs.get(i).getType().substring(0,3)+"");
                labelTypeOfSubject.setFont(new Font("Calibri", Font.ITALIC, 26));
                labelTypeOfSubject.setForeground(new Color(32, 16, 58));

            subjects[i].add(labelNumberOfSubject);
            subjects[i].add(labelNameOfSubject);
            subjects[i].add(labelTypeOfSubject);
            bottomRightPanel.add(subjects[i]);
        }

        JScrollPane jScrollPaneRightBottomPanel = new JScrollPane(bottomRightPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneRightBottomPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        jScrollPaneRightBottomPanel.setBounds(900,530,300,200);
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
        List <Labs> labsForSubject = dataBase.getLabsBySubject(subject);
        Color backgroundColor = new Color(113, 74, 176);
        Border border = BorderFactory.createLineBorder(new Color(177, 148, 226),1);

        bottomCenterPanel.setPreferredSize(new Dimension(650,100*labsForSubject.size()));
        bottomCenterPanel.setBackground(Color.LIGHT_GRAY);

        JPanel labPanel [] = new JPanel[labsForSubject.size()];

        for(int labNumber = 0, panelHeight = 0; labNumber<labsForSubject.size(); labNumber++, panelHeight += 100) {
            Labs labwork = labsForSubject.get(labNumber);
            labPanel[labNumber] = new JPanel(null);
            labPanel[labNumber].setBounds(0,panelHeight,650,100);

            JLabel labelNumberLab = new JLabel("" + labwork.getLabNumber());
                labelNumberLab.setBorder(border);
                labelNumberLab.setBounds(0,0,35,100);
                labelNumberLab.setVerticalAlignment(JLabel.CENTER);
                labelNumberLab.setHorizontalAlignment(JLabel.CENTER);
                labelNumberLab.setFont(new Font("Times New Roman", Font.ITALIC, 30));
                labelNumberLab.setOpaque(true);
                labelNumberLab.setBackground(backgroundColor);

            JTextArea labCommentArea = new JTextArea();
                labCommentArea.setFont(new Font("Times New Roman", Font.ITALIC, 30));
                labCommentArea.setBorder(border);
                labCommentArea.setBackground(backgroundColor);
                labCommentArea.setBounds(35,0,365,100);
                labCommentArea.setLineWrap(true);
                labCommentArea.setWrapStyleWord(true);
                if(labwork.getComment().equals("")) labCommentArea.setText(" Додайте свій коментар");
                else labCommentArea.setText(labwork.getComment());
                labCommentArea.getDocument().addDocumentListener(createCommentAreaListener(labCommentArea,labwork));

            UtilDateModel model = new UtilDateModel();
            model.setSelected(true);
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");

            JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
            datePanel.setPreferredSize(new Dimension(300,200));
            datePanel.setBackground(new Color(113, 74, 176));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Calendar calendar = new GregorianCalendar();
            try {
                Date date = sdf.parse( labwork.getDeadline());
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);

                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                model.setDate(year,month,day);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new Calendars());
                datePicker.setBackground(new Color(113, 74, 176));
                datePicker.setBounds(400,0,200,100);
                datePicker.setToolTipText(labwork.getDeadline());
                datePicker.addActionListener(createDatePickerListener(datePicker,labwork));

            JCheckBox statBox = new JCheckBox();
                statBox.setBorder(border);
                statBox.setBackground(backgroundColor);
                statBox.setVerticalAlignment(SwingConstants.CENTER);
                statBox.setHorizontalAlignment(SwingConstants.CENTER);
                statBox.setBounds(600,0,50,100);
                statBox.setSelected(labwork.getStatus() == 1);
                statBox.setBackground(getColorForLabStatus(labwork));
                statBox.addItemListener(getCheckBoxEvent(statBox,labwork));


            labPanel[labNumber].add(labelNumberLab);
            labPanel[labNumber].add(labCommentArea);
            labPanel[labNumber].add(datePicker);
            labPanel[labNumber].add(statBox);
            bottomCenterPanel.add(labPanel[labNumber]);
        }
    }

    private ItemListener getCheckBoxEvent(JCheckBox jCheckBox ,Labs lab){
        return  new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jCheckBox.isSelected()) {
                    System.out.println("selected");
                    dataBase.updateLabStatus(1,lab.getIdSubject(),lab.getLabNumber());
                    jCheckBox.setBackground(Color.GREEN);
                }
                else {
                    System.out.println("not selected");
                    dataBase.updateLabStatus(0,lab.getIdSubject(),lab.getLabNumber());
                    jCheckBox.setBackground(new Color(113, 74, 176));
                }
                projectFrame.validate();
            }
        };
    }

    private ActionListener createDatePickerListener(JDatePickerImpl datePicker ,Labs lab){
        return  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataBase.updateLabDeadline(datePicker.getJFormattedTextField().getText(),lab.getIdSubject(),lab.getLabNumber());
            }
        };
    }

    private DocumentListener createCommentAreaListener(JTextArea commentArea, Labs lab){
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

    private Color getColorForLabStatus(Labs lab){
        Color statusColor = null;

        switch (lab.getStatus()){
            case 0: statusColor = new Color(113, 74, 176); break;
            case 1: statusColor = new Color(63, 171, 57); break;
            case 2: statusColor = new Color(211, 81, 71);
        }

        return statusColor;
    }
}