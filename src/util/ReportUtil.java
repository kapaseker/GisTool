package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

class FileBean {
	public String fileName;
	public String fileContent;

	public FileBean(String fileName, String fileContent) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
}

public class ReportUtil {
	private static final String EXIT = "!!!---!!!";
	private static BufferedWriter mCsvWriter = null;
	private static String mFilePath = "./" + TimeUtil.getTime();
	private static int CLOSE_FLAG = 0;
	private static BlockingQueue<String> mReportBeanList = new LinkedBlockingDeque<String>();
	private static BlockingQueue<FileBean> mFileBeanList = new LinkedBlockingDeque<FileBean>();

	/**
	 * 设置报告路径,默认为当前路径
	 * 
	 * @param filePath
	 */

	public static void setPath(String filePath) {
		if (filePath != null) {
			mFilePath = "./" + filePath;
		}
	}

	/**
	 * 打开报告
	 */
	public static void openReport() {

		File path = new File(mFilePath);
		path.mkdirs();
		CLOSE_FLAG=0;
		ExecutorService exSer = Executors.newCachedThreadPool();
		exSer.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!Thread.interrupted()) {
					try {
						String strContent = mReportBeanList.take();
						if (strContent.equals(EXIT)) {
							close();
							break;
						}
						writeReport(strContent);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						PrintMessage("意外终止了程序,请重新启动..."
								+ ReportUtil.class.getCanonicalName());
					}
				}
			}
		});
		exSer.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!Thread.interrupted()) {

					try {
						FileBean fben = mFileBeanList.take();
						if (fben.fileName.equals(EXIT)) {
							close();
							break;
						}
						writeResultFile(fben.fileName, fben.fileContent);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						PrintMessage("意外终止了程序,请重新启动..."
								+ ReportUtil.class.getCanonicalName());
					}
				}
			}
		});

		exSer.shutdown();
		try {

			mCsvWriter = new BufferedWriter(new FileWriter(new File(mFilePath
					+ "/" + TimeUtil.getTime() + ".csv")));
			mCsvWriter
					.write("SerialID,StartTime,HandleTime,State,ReturnResult,URL\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("report文件创建失败!!!");
		}
	}

	public static void addReprot(String strContent) {
		try {
			mReportBeanList.put(strContent);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			PrintMessage("意外终止程序...");
		}
	}

	public static void addResultFile(String name, String content) {
		try {
			mFileBeanList.put(new FileBean(name, content));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			PrintMessage("意外终止程序...");
		}
	}

	/**
	 * 写入报告
	 * 
	 * @param 报告内容
	 */
	private synchronized static void writeReport(String strContent) {
		try {
			mCsvWriter.write(strContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			PrintMessage("报告文件写入失败,可能突然丢失文件" + strContent);
		}
	}

	/**
	 * 保存结果文件到指定目录
	 * 
	 * @param name
	 * @param content
	 */
	private synchronized static void writeResultFile(String name, String content) {
		File file = new File(mFilePath + "/" + name + ".txt");
		BufferedWriter filewriter;
		try {
			filewriter = new BufferedWriter(new FileWriter(file));
			filewriter.write(content);
			filewriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			PrintMessage("结果文件写入失败,可能突然丢失文件" + content);
		}

	}

	public static void PrintMessage(String message) {
		System.out.println(message);
	}

	/**
	 * 关闭报告
	 */

	private static void close() {
		CLOSE_FLAG++;
		if (CLOSE_FLAG == 2) {
			if (mCsvWriter != null) {
				try {
					mCsvWriter.flush();
					mCsvWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("文件关闭失败");
				}
			}
		}

	}

	public static void closeReport() {

		addReprot(EXIT);
		addResultFile(EXIT, EXIT);
	}
}
