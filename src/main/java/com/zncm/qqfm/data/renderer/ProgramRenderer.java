package com.zncm.qqfm.data.renderer;

import com.zncm.qqfm.data.Program;
import com.zncm.qqfm.utils.Xutils;

import javax.swing.*;
import java.awt.*;

public class ProgramRenderer extends JLabel implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        Program program = (Program) value;
        if (program==null){
            return null;
        }

        if (Xutils.isNotEmptyOrNull(program.getName())){
            setText(program.getName());
        }


        return this;
    }
}
