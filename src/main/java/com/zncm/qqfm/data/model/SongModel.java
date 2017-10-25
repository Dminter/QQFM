package com.zncm.qqfm.data.model;


import com.zncm.qqfm.data.Song;
import com.zncm.qqfm.utils.Xutils;

import javax.swing.*;
import java.util.List;

/**
 * Created by jiaomx on 2017/7/26.
 */
public class SongModel extends AbstractListModel {
    List<Song> songs;

    public SongModel(List<Song> programs) {
        this.songs = programs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public int getSize() {
        if (Xutils.listNotNull(songs)){
            return songs.size();
        }else {
            return 0;
        }

    }

    @Override
    public Object getElementAt(int index) {
        if (Xutils.listNotNull(songs)){
            return songs.get(index);
        }else {
            return null;
        }
    }
}
