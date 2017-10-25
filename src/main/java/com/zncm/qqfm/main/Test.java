//package com.zncm.qqfm.main;
//
//import java.io.File;
//import java.io.IOException;
//import javax.media.Player;
//public class Test {
//    /**
//	 * 测试解码时间，只解码不产生音频输出。
//	 * @param args 指定的源文件，本地MP3文件。
//	 */
//	public static void main(String[] args) {
////		if (args.length == 0) {
////			System.err.println("Please specify a valid filename.");
////			return;
////		}
//		//System.out.println(args[0] + "\ndecoding...");
//
//		String filePath = "D:/Dev/QQFM/music/英语也是简化来的.mp3";
//
////		MiniPlayer player = new MiniPlayer(new Audio());	// null改为new Audio()可正常播放
////
////		try {
////			long t0 = System.nanoTime();
////
////			String msg = player.open(filePath);
////			player.run();
////
////			long t1 = System.nanoTime() - t0;
////
////			File file = new File(filePath);
////			long length = file.length();
////			int frames = player.getFrameCount();
////			System.out.println(msg);
////			System.out.printf("      length: %d bytes, %d frames\n", length, frames);
////			System.out.printf("elapsed time: %,dns (%.9fs, %.2f fps)\n", t1, t1/1e9, frames/(t1/1e9));
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//
//		Player player;
//
//	}
//
//}