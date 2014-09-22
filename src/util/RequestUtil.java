package util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * 
 * @author i-xiepenggang
 * @category 用于发送HTTP请求
 */
public class RequestUtil {

	protected String mUrl = null;
	protected String mRqContent = null;
	protected String mContentEncoding = "GBK";
	protected String mResultContent = "";
	protected Date mRequestDate = null;
	protected String mHandleSpanTime = "";

	public RequestUtil() {

	}

	/**
	 * 设定url
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.mUrl = url;
	}

	/**
	 * 获得url
	 * 
	 * @return url
	 */
	public String getUrl() {
		return mUrl;
	}

	/**
	 * 
	 * @return 处理消耗消耗时间
	 */
	protected String getHandleSpanTime() {
		return mHandleSpanTime;
	}

	/**
	 * @return 返回请求时间
	 */
	protected String getRequestDate() {
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String reTime = format2.format(mRequestDate);
		return reTime;
	}

	/**
	 * 设定请求体
	 * 
	 * @param content
	 */
	public void setRequestContent(String content) {
		this.mRqContent = content;
	}

	/**
	 * 
	 * @return 请求体
	 */
	public String getRequestContent() {
		return mRqContent;
	}

	/**
	 * 设定请求体的编码
	 * 
	 * @param en
	 */
	public void setContentEncoding(String en) {
		this.mContentEncoding = en;
	}

	public String getContentEncoding() {
		return this.mContentEncoding;
	}

	/**
	 * 发送get请求
	 */
	public void sendGet() {
		if (false == checkParam()) {
			return;
		}

		try {
			URL url = new URL(mUrl);
			
			//url.set

			try {
				HttpURLConnection urlcon = (HttpURLConnection) url
						.openConnection();
				urlcon.setConnectTimeout(5000);
				urlcon.setReadTimeout(5000);
				urlcon.connect();
				DataInputStream dis = new DataInputStream(
						urlcon.getInputStream());

				byte[] readBt = new byte[1024];

				ByteArrayOutputStream bao = new ByteArrayOutputStream();

				int readLen = -1;
				while ((readLen = dis.read(readBt)) != -1) {
					bao.write(readBt, 0, readLen);
					Array.set(readBt, 0, (byte) 0);
				}

				dis.close();
				urlcon.disconnect();

				mResultContent = new String(bao.toByteArray(), "utf-8");

				try {
					Document doc = DocumentHelper.parseText(mResultContent);
					if (doc.getXMLEncoding().toLowerCase().equals("gbk")) {
						mResultContent = new String(bao.toByteArray(), "gbk");
					}
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					//printMessage(e.toString(), RequestUtil.RETR_FAIL);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				printMessage(e.toString());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			printMessage(e.toString());
		} catch(Exception ex){
			printMessage(ex.toString());
		}

	}

	/**
	 * 发送post请求
	 */
	public void sendPost() {

		if (false == checkParam()) {
			return;
		}

		try {
			URL url = new URL(mUrl);

			try {
				HttpURLConnection urlcon = (HttpURLConnection) url
						.openConnection();

				urlcon.setRequestMethod("POST");

				urlcon.setConnectTimeout(2000);
				urlcon.setDoOutput(true);
				urlcon.setDoInput(true);
				urlcon.connect();

				// httpUrlConnection.setRequestProperty("content-type",
				// "text/xml");

				OutputStream output = new BufferedOutputStream(
						urlcon.getOutputStream());

				OutputStreamWriter writer = new OutputStreamWriter(output,
						mContentEncoding);

				writer.write(mRqContent);
				writer.flush();
				writer.close();

				DataInputStream dis = new DataInputStream(
						urlcon.getInputStream());

				byte[] readBt = new byte[1024];
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				int readLen = -1;
				while ((readLen = dis.read(readBt)) != -1) {
					bao.write(readBt, 0, readLen);
					Array.set(readBt, 0, (byte) 0);
				}

				dis.close();
				urlcon.disconnect();

				mResultContent = new String(bao.toByteArray(), "utf-8");

				try {
					Document doc = DocumentHelper.parseText(mResultContent);
					if (doc.getXMLEncoding().toLowerCase().equals("gbk")) {
						mResultContent = new String(bao.toByteArray(), "gbk");
					}
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					printMessage(e.toString());
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				printMessage(e.toString());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			printMessage(e.toString());
		}
	}

	/**
	 * 校验参数,其实就是判断是否空
	 * 
	 * @return
	 */
	protected boolean checkParam() {

		if ((mUrl == null) || (mUrl.trim().equals(""))) {
			printMessage("please set your url");
			return false;
		}

		return true;
	}

	/**
	 * 答应相关结果,例如什么错误之类的
	 * 
	 * @param str
	 * @param code
	 */
	protected void printMessage(String str) {
		//System.out.println(str);
	}

	/**
	 * 发请求的方法
	 * 
	 * @param 你是用post啊
	 *            ???还是get
	 */
	public void sendRequest(String sEND_METHOD) {
		// TODO Auto-generated method stub
		mResultContent="";
		mRequestDate = new Date();
		String method = sEND_METHOD.toLowerCase();
		if (method.equals("get")) {
			sendGet();
		} else if (method.equals("post")) {
			sendPost();
		}
		Date endDate = new Date();
		mHandleSpanTime = ((endDate.getTime() - mRequestDate.getTime()))
				/ 1000.0 + "";
	}

	protected String getResult() {
		return mResultContent;
	}
}
