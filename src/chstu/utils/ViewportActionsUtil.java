package chstu.utils;

import chstu.db.DBAdapter;
import chstu.db.entity.Laboratory;
import chstu.bot.BotNewLabsPart;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Ar-Krav on 27.05.2017.
 */
public class ViewportActionsUtil {
    private ViewportLogicUtil vLogic;
    private DBAdapter dataBase;
    private ViewportStyleUtil vStyle;
    private int subjectId;

    public ViewportActionsUtil(JLabel progressAllLabs, JLabel progressСompleted, JLabel progressDebt) {
        vLogic = new ViewportLogicUtil(this,progressAllLabs, progressСompleted, progressDebt);
        dataBase = DBAdapter.getInstance();
        vStyle = ViewportStyleUtil.getInstance();
    }

    public ViewportLogicUtil getVLogic() {
        return vLogic;
    }

    public ItemListener getCheckBoxEvent(JCheckBox jCheckBox , Laboratory lab){
        return  new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jCheckBox.isSelected()) {
                    dataBase.updateLabStatus(1,lab.getIdSubject(),lab.getLabNumber());
                    jCheckBox.setBackground(vStyle.colorPassedGreen);
                }
                else {
                    dataBase.updateLabStatus(0,lab.getIdSubject(),lab.getLabNumber());
                    jCheckBox.setBackground(vStyle.colorViolet1);
                }

                vLogic.setLabStatistic();
            }
        };
    }

    public ActionListener createDatePickerUpdateLabListener(JDatePickerImpl datePicker , Laboratory lab){
        return  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataBase.updateLabDeadline(datePicker.getJFormattedTextField().getText(),lab.getIdSubject(),lab.getLabNumber());
            }
        };
    }

    public DocumentListener createCommentAreaListener(JTextArea commentArea, Laboratory lab){
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

    public ActionListener createDatePickerShowTimetableListener(JPanel panel, JDatePickerImpl datePicker, JLabel timetableDate){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timetableDate.setText("Розклад на " + datePicker.getJFormattedTextField().getText());
                vLogic.showTimetable(panel, datePicker.getJFormattedTextField().getText());
            }
        };
    }

    public ActionListener createOkButtonListener(JTextField numberLabsInputField, JPanel labsPanel){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numbers="1234567890";
                if(numbers.contains(numberLabsInputField.getText())){
                    BotNewLabsPart BotNewLabs = new BotNewLabsPart();
                    BotNewLabs.setLabs(subjectId,Integer.parseInt(numberLabsInputField.getText()));

                    vLogic.setLabStatistic();
                    vLogic.showLabs(labsPanel,subjectId);

                }else {
                    numberLabsInputField.setBackground(vStyle.colorDebtRed);
                    numberLabsInputField.setText("1-9");
                }
            }
        };
    }

    public ActionListener createSubjectButtonListener(int subject, JPanel labsPanel, JLabel subjectNameLabel){
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vLogic.showLabs(labsPanel,subject);
                subjectId = subject;

                subjectNameLabel.setText(dataBase.getAllSubjects().get(subject).getName());
                labsPanel.validate();
            }
        };
        return action;
    }

    public Color getColorForLabStatus(Laboratory lab){
        switch (lab.getStatus()){
            case 0: return vStyle.colorViolet1;
            case 1: return vStyle.colorPassedGreen;
            case 2: return vStyle.colorDebtRed;
        }
        return null;
    }
}