package Main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer extends Thread {

	private String filename;
	private static boolean isover = false;  //用来停止线程

	public AudioPlayer(String wavfilename) {
		filename = wavfilename;
	}
	
	public static void setisOver(boolean tisover) {
		isover = tisover;
	}

	@Override
	public void run() {
		while (true && !isover) { 
			File file = new File(filename);

			AudioInputStream audioInputStream = null;

			try {
				audioInputStream = AudioSystem.getAudioInputStream(file);//获得输入流

			} catch (Exception e1) {
				e1.printStackTrace();
				return;
			}

			AudioFormat format = audioInputStream.getFormat(); // 获得此音频输入流中声音数据的音频格式。
			SourceDataLine auline = null;
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);// 根据指定信息构造数据行的信息对象，这些信息包括单个音频格式。

			try {
				auline = (SourceDataLine) AudioSystem.getLine(info); // 获得与指定 Line.Info 对象中的描述匹配的行。
				auline.open(format);//打开具有指定格式的行，这样可使行获得所有所需的系统资源并变得可操作。

			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			auline.start();
			int nBytesRead = 0;

			byte[] abData = new byte[2048];

			try {
				while (nBytesRead != -1 && !isover) {
					nBytesRead = audioInputStream.read(abData, 0, abData.length);

					if (nBytesRead >= 0)
						auline.write(abData, 0, nBytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			} finally {//关闭资源
				auline.drain();
				auline.close();
			}
		}

	}
}
