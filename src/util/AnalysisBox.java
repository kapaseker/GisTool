package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import util.Interface.GerneralAnalysis;

public class AnalysisBox {

	private static Properties mServiceProperty = new Properties();

	private static Map<Object, GerneralAnalysis> mClassMap = new HashMap<Object, GerneralAnalysis>();
	static {
		mServiceProperty = new Properties();
		try {
			FileReader filereader = new FileReader(new File(
					"cfg/system.properties"));
			mServiceProperty.load(filereader);
			filereader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("没有找到配置文件");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("没有文件权限");
		}

		Set<Object> stritem = mServiceProperty.keySet();

		for (Object obj : stritem) {
			Object objKey = mServiceProperty.get(obj);
			if (mClassMap.containsKey(objKey)) {
				continue;
			} else {
				Class<GerneralAnalysis> cls;
				try {
					cls = (Class<GerneralAnalysis>) Class.forName(objKey
							.toString());
					GerneralAnalysis ana = (GerneralAnalysis) cls.newInstance();
					mClassMap.put(objKey, ana);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("");
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	public static GerneralAnalysis getService(String key) {

		if (mServiceProperty.containsKey(key)) {
			return mClassMap.get(mServiceProperty.get(key));
		}

		return null;
	}
}
