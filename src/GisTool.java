import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;

import util.AnalysisBox;
import util.ReportUtil;
import util.XMLAnalysis;
import util.Interface.GerneralAnalysis;

public class GisTool {
	public static void main(String[] args) throws DocumentException,
			IOException {

		for (String arg : args) {
			System.out.println(arg);
		}
		ReportUtil.openReport();

		XMLAnalysis ana = new XMLAnalysis(null);

		for (Map<String,String> hashmap : ana) {
			String SerialID = (String) hashmap.get("SerialID");
			//String ServiceVersion = (String) hashmap.get("ServiceVersion");
			String ServiceURL = (String) hashmap.get("ServiceURL");

			GerneralAnalysis gan = AnalysisBox.getService(SerialID);
			gan.init();
			gan.setID(SerialID);
			gan.setUrl(ServiceURL);
			gan.sendRequest("get");
			ReportUtil.writeReport("\"" + gan.getRequestID() + "\",\""
					+ gan.getRequestTime() + "\",\"" + gan.getHandleTime()
					+ "\",\"" + gan.getResultState() + "\",\""
					+ gan.getRequestID() + ".txt\"\n");
			ReportUtil.writeResultFile(gan.getRequestID(), gan.getResultFile());
		}

		ReportUtil.closeReport();

		System.out.println("yes done");
	}
}
