package com.zncm.qqfm.main;

import com.zncm.qqfm.decoder.AbstractDecoder;
import com.zncm.qqfm.decoder.IAudio;

import java.io.FileInputStream;
import java.io.IOException;

public class MiniPlayer extends AbstractDecoder {
    private FileInputStream fileStream;

	/**
	 * 用指定的音频输出audio创建一个MiniPlayer。
	 * @param audio 音频输出对象。若为 <b>null</b> 只解码不产生输出。
	 */
	public MiniPlayer(IAudio audio) {
		super(audio);
	}

	/**
	 * 打开输入流并初始化解码器。
	 * @param name MP3文件名。
	 * @return MP3帧头简短信息。
	 * @throws IOException 发生I/O错误。
	 */
	public String open(String name) throws IOException {
		fileStream = new FileInputStream(name);
		return super.openDecoder();
	}

	@Override
	protected int fillBuffer(byte[] b, int off, int len) {
		try {
			return fileStream.read(b, off, len);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	protected void done() {
		if (fileStream != null) {
			try { fileStream.close(); } catch (IOException e) { }
		}
	}

}