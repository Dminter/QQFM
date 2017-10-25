package com.zncm.qqfm.main;

import com.zncm.qqfm.decoder.IAudio;

public class Audio implements IAudio {
    @Override
    public boolean open(int rate, int channels, int bufferSize) {
        return false;
    }

    @Override
    public int write(byte[] b, int off, int size) {
        return 0;
    }

    @Override
    public void start(boolean started) {

    }

    @Override
    public void drain() {

    }

    @Override
    public void close() {

    }
}
