package chstu.gui.utils;

import chstu.db.DBAdapter;
import chstu.db.entity.Laboratory;
import chstu.db.entity.Lesson;
import chstu.db.entity.LessonTimetable;
import chstu.gui.Calendars;
import chstu.gui.Viewport;
import chstu.timetable.DateUtil;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ar-Krav on 27.05.2017.
 */
public class ViewportLogic {

    public ViewportLogic() {
        vStyle = ViewportStyle.getInstance();
        vElements = new ViewportElements();
        vActions = new ViewportActions();
        viewport = new Viewport();
        dataBase = DBAdapter.getInstance();
    }

    private ViewportStyle vStyle;
    private ViewportElements vElements;
    private ViewportActions vActions;
    private Viewport viewport;
    private DBAdapter dataBase;
    
    
    public void showLabs(JPanel panel, int subject){
        if (panel.getComponentCount() > 0){
            panel.removeAll();
        }

        List<Laboratory> laboratoryForSubject = dataBase.getLabsBySubject(subject);
        panel.setPreferredSize(new Dimension(650,100* laboratoryForSubject.size()));

        for(int labNumber = 0, blockHeight = 0; labNumber< laboratoryForSubject.size(); labNumber++, blockHeight += 100) {
            Laboratory labwork = laboratoryForSubject.get(labNumber);

            JLabel labelNumberLab = new JLabel("" + labwork.getLabNumber(), SwingConstants.CENTER);
                labelNumberLab.setOpaque(true);
                vElements.setJComponentExtendedProperties(labelNumberLab,vStyle.fontTnrI30,new Rectangle(0,blockHeight,35,100),vStyle.colorViolet5,vStyle.colorViolet1);

            JTextArea labCommentArea = new JTextArea();
                vElements.setJComponentExtendedProperties(labCommentArea,vStyle.fontTnrI30,new Rectangle(35,blockHeight,365,100),vStyle.colorViolet5,vStyle.colorViolet1);
                labCommentArea.setLineWrap(true);
                labCommentArea.setWrapStyleWord(true);
                labCommentArea.setText(labwork.getComment().isEmpty() ? " Додайте свій коментар" : labwork.getComment());
                labCommentArea.getDocument().addDocumentListener(vActions.createCommentAreaListener(labCommentArea,labwork));

            JDatePanelImpl datePanel = vElements.getDataPanel();
                DateUtil dateUtil = new DateUtil();
                Calendar calendar = dateUtil.convertStringInDate(labwork.getDeadline());
                datePanel.getModel().setDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DAY_OF_MONTH));
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new Calendars());
                datePicker.setBackground(new Color(113, 74, 176));
                datePicker.setBounds(400,blockHeight,200,100);
                datePicker.setToolTipText(labwork.getDeadline());
                datePicker.addActionListener(vActions.createDatePickerListener(datePicker,labwork));

            JCheckBox statBox = new JCheckBox();
                vElements.setJComponentExtendedProperties(statBox,null,new Rectangle(600,blockHeight,50,100),null,vActions.getColorForLabStatus(labwork));
                statBox.setVerticalAlignment(SwingConstants.CENTER);
                statBox.setHorizontalAlignment(SwingConstants.CENTER);
                statBox.setSelected(labwork.getStatus() == 1);
                statBox.addItemListener(vActions.getCheckBoxEvent(statBox,labwork));

            panel.add(labelNumberLab);
            panel.add(labCommentArea);
            panel.add(datePicker);
            panel.add(statBox);
        }
    }

    public void showTimetable(JPanel panel, String date){
        if (panel.getComponentCount() > 0){
            panel.removeAll();
        }

        List<Lesson> lessonsTimetable = dataBase.getLessonsToShow(date);
        panel.setSize(new Dimension(300, 25*lessonsTimetable.size() < 200 ? 200 : 35*lessonsTimetable.size()));
        int blockHeight = 0;

        for (Lesson lesson : lessonsTimetable){
            JLabel labelNumberOfSubject = vElements.getLable("" + lesson.getNumber(),vStyle.fofntClb26,new Rectangle(0,blockHeight,35,35),Color.black);
            JLabel labelNameOfSubject = vElements.getLable(lesson.getName(),vStyle.fofntClb26,new Rectangle(35,blockHeight,190,35),Color.black);
            JLabel labelTypeOfSubject = vElements.getLable("" + lesson.getType().substring(0,3),vStyle.fofntClb26,new Rectangle(225,blockHeight,75,35),Color.black);

            panel.add(labelNumberOfSubject);
            panel.add(labelNameOfSubject);
            panel.add(labelTypeOfSubject);

            blockHeight += 35;
        }
    }

    public void setLabStatistic(){
        int completed = 0;
        int debts = 0;

        for (Laboratory lab : dataBase.getAllLabs()){
            if(lab.getStatus() == 1 ){
                completed++;
            }
            if(lab.getStatus() == 2 ){
                debts++;
            }
        }

        viewport.getProgressAllLabs().setText("Всі лабораторні:"+dataBase.getAllLabs().size());
        viewport.getProgressСompleted().setText(" Виконані:"+completed);
        viewport.getProgressDebt().setText(" В боргах:"+debts);
    }
}
