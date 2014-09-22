import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;

import util.AnalysisBox;
import util.ReportUtil;
import util.XMLAnalysis;
import util.Interface.GerneralAnalysis;

public class GisTool {
	/**
	 * 
	 * @param args
	 * @throws DocumentException
	 * @throws IOException
	 * @category 函数入口,打开报告,读取配置列表和xml文件,完成分析报告
	 */
	public static void main(String[] args) throws DocumentException,
			IOException {
		// 打开report报告
		ReportUtil.openReport();
		// 通过分析xml来获得需要分析的参数
		XMLAnalysis ana = new XMLAnalysis();

		for (Map<String, String> hashmap : ana) {
			// 获得编号
			String SerialID = (String) hashmap.get("SerialID");
			// 获得请求的URL
			String ServiceURL = (String) hashmap.get("ServiceURL");
			// 获得相关的分析服务
//			if (SerialID.equals("G1021")) {
//				continue;
//			}
			GerneralAnalysis gan = AnalysisBox.getService(SerialID);

			if (gan != null) {
				// 初始化
				gan.init();
				// 设置请求编号,这个对于分析貌似没什么用
				gan.setID(SerialID);
				gan.setUrl(ServiceURL);
				// 设置请求方式
				gan.sendRequest("get");
				// 写入报告

				System.out.print(SerialID);
				ReportUtil.addReprot("\"" + gan.getRequestID() + "\",\""
						+ gan.getRequestTime() + "\",\"" + gan.getHandleTime()
						+ "\",\"" + gan.getResultState() + "\",\""
						+ gan.getRequestID() + ".txt\",\"" + gan.getUrl()
						+ "\"\n");
				// 写入结果文件
				ReportUtil.addResultFile(gan.getRequestID(),
						gan.getResultFile());
				System.out.println("----Done");
			} else {
				System.out
						.println(SerialID
								+ " has not the service ,please change your system.properties");
			}
			// }
		}
		// 关闭report报告
		ReportUtil.closeReport();

		System.out.println("YES ALL DONE");
	}
}
