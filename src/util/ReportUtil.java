package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportUtil {

	private static BufferedWriter mWriter = null;
	private static String mFilePath = "./" + TimeUtil.getTime();

	public static void setPath(String filePath) {
		if (filePath != null) {
			mFilePath = "./" + filePath;
		}
	}

	public static void openReport() {

		File path = new File(mFilePath);
		path.mkdirs();
		try {

			mWriter = new BufferedWriter(new FileWriter(new File(mFilePath
					+ "/" + TimeUtil.getTime() + ".csv")));

			mWriter.write("SerialID,StartTime,HandleTime,State,ReturnResult,\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("report文件创建失败!!!");
		}
	}

	public synchronized static void writeReport(String strContent) {
		try {
			mWriter.write(strContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("报告文件写入失败,可能突然丢失文件");
		}
	}

	public synchronized static void writeResultFile(String name, String content) {
		File file = new File(mFilePath + "/" + name + ".txt");
		BufferedWriter filewriter;
		try {
			filewriter = new BufferedWriter(new FileWriter(file));
			filewriter.write(content);
			filewriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("结果文件写入失败,可能突然丢失文件");
		}

	}

	public static void closeReport() {
		if (mWriter != null) {
			try {
				mWriter.flush();
				mWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("文件关闭失败");
			}
		}
	}
}
