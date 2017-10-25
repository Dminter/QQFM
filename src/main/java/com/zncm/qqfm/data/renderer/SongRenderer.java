package com.zncm.qqfm.data.renderer;

import com.zncm.qqfm.data.Program;
import com.zncm.qqfm.data.Song;
import com.zncm.qqfm.utils.Xutils;

import javax.swing.*;
import java.awt.*;

public class SongRenderer extends JLabel implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        Song song = (Song) value;
        if (song==null){
            return null;
        }

        if (Xutils.isNotEmptyOrNull(song.getName())){
            setText(song.getName());
        }


        return this;
    }
}
