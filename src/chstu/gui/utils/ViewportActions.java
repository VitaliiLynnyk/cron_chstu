package chstu.gui.utils;

import chstu.db.DBAdapter;
import chstu.db.entity.Laboratory;
import chstu.timetable.Tasks;
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
public class ViewportActions extends ViewportLogic{
    public ViewportActions(JLabel progressAllLabs, JLabel progressСompleted, JLabel progressDebt) {
        super(progressAllLabs, progressСompleted, progressDebt);
    }

    private int subjectId;

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

                setLabStatistic();
            }
        };
    }

    public ActionListener createDatePickerListener(JDatePickerImpl datePicker , Laboratory lab){
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

    public ActionListener createDatePickerListener(JPanel panel, JDatePickerImpl datePicker){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTimetable(panel, datePicker.getJFormattedTextField().getText());
            }
        };
    }

    public ActionListener createOkButtonListener(JTextField numberLabsInputField, JPanel labsPanel){
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numbers="1234567890";
                if(numbers.contains(numberLabsInputField.getText())){
                    Tasks tasks = new Tasks();
                    tasks.setLabs(subjectId,Integer.parseInt(numberLabsInputField.getText()));

                    setLabStatistic();
                    showLabs(labsPanel,subjectId);

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
                showLabs(labsPanel,subject);
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