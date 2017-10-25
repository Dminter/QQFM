package com.zncm.qqfm.data.model;


import com.zncm.qqfm.data.Program;
import com.zncm.qqfm.utils.Xutils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaomx on 2017/7/26.
 */
public class ProgramModel extends AbstractListModel {
    List<Program> programs;

    public ProgramModel(List<Program> programs) {
        this.programs = programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    @Override
    public int getSize() {
        if (Xutils.listNotNull(programs)){
            return programs.size();
        }else {
            return 0;
        }

    }

    @Override
    public Object getElementAt(int index) {
        if (Xutils.listNotNull(programs)){
            return programs.get(index);
        }else {
            return null;
        }
    }
}
