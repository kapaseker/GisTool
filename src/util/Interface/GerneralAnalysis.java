package util.Interface;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import util.RequestUtil;

/**
 * 常规分析,所有的分析工具均继承这个玩意儿
 * 
 * @author i-xiepenggang
 * 
 */
public abstract class GerneralAnalysis extends RequestUtil implements
		IGisAnalysis {
	protected String mID = "";
	public static String RETR_YES = "YES";
	public static String RETR_BAD = "NG";
	public static String RETR_WAR = "WARNING";
	
	/**
	 * 设定id
	 */
	@Override
	public void setID(String str) {
		// TODO Auto-generated method stub
		this.mID = str;
	}

	/**
	 * 初始化
	 */
	public void init() {
		mUrl = null;
		mRqContent = null;
		mContentEncoding = "GBK";
		mResultContent = "";
		mRequestDate = null;
		mHandleSpanTime = "";
		mID = "";
	}

	/**
	 * 获得ID
	 */

	@Override
	public String getRequestID() {
		// TODO Auto-generated method stub
		return mID;
	}

	/**
	 * 获的请求时间
	 */
	public String getRequestTime() {
		// TODO Auto-generated method stub
		return getRequestDate();
	}

	/**
	 * 获得处理时间
	 */
	@Override
	public String getHandleTime() {
		// TODO Auto-generated method stub
		return getHandleSpanTime();
	}

	/**
	 * 获得处理结果
	 */
	@Override
	public String getResultFile() {
		// TODO Auto-generated method stub
		return getResult();
	}

	protected boolean checkisXML() {

		try {
			DocumentHelper.parseText(mResultContent);
		} catch (DocumentException e) {
			return false;
		}
		return true;
	}
}
