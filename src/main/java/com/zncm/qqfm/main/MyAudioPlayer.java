package com.zncm.qqfm.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;
public class MyAudioPlayer {
//    Player player;
    File music;

    private static Player player;

    public static Player getPlayer() {
        return player;
    }
    public MyAudioPlayer(File file) {
        this.music = file;
        BufferedInputStream buffer = null;
        try {
            if (player!=null){
                player.close();
                player = null;
            }
            buffer = new BufferedInputStream(new FileInputStream(music));
            player = new Player(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }

}