package chstu.gui.utils;

import chstu.db.DBAdapter;
import chstu.db.entity.Laboratory;
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
public class ViewportActions {
    public ViewportActions() {
        vStyle = ViewportStyle.getInstance();
        vLogic = new ViewportLogic();
        dataBase = DBAdapter.getInstance();
    }

    ViewportStyle vStyle;
    ViewportLogic vLogic;
    DBAdapter dataBase;

    public ItemListener getCheckBoxEvent(JCheckBox jCheckBox , Laboratory lab){
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

    public Color getColorForLabStatus(Laboratory lab){
        switch (lab.getStatus()){
            case 0: return vStyle.violet1;
            case 1: return vStyle.passedGreen;
            case 2: return vStyle.debtRed;
        }
        return null;
    }
}