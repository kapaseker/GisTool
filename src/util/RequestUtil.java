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
	public static int RETR_SUCCESS = 0x001;
	public static int RETR_FAIL = 0x002;
	protected Date mRequestDate = null;
	protected String mHandleSpanTime = "";

	public RequestUtil() {

	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public String getUrl() {
		return mUrl;
	}

	protected String getHandleSpanTime() {
		return mHandleSpanTime;
	}

	protected String getRequestDate() {
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String reTime = format2.format(mRequestDate);
		return reTime;
	}

	public void setRequestContent(String content) {
		this.mRqContent = content;
	}

	public String getRequestContent() {
		return mRqContent;
	}

	public void setContentEncoding(String en) {
		this.mContentEncoding = en;
	}

	public String getContentEncoding() {
		return this.mContentEncoding;
	}

	public void sendGet() {
		if (false == checkParam()) {
			return;
		}

		try {
			URL url = new URL(mUrl);

			try {
				HttpURLConnection urlcon = (HttpURLConnection) url
						.openConnection();

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

			} catch (IOException e) {
				// TODO Auto-generated catch block
				printMessage(e.toString(), RequestUtil.RETR_FAIL);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			printMessage(e.toString(), RequestUtil.RETR_FAIL);
		}

	}

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

				mResultContent = new String(bao.toByteArray(), "GBK");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				printMessage(e.toString(), RETR_FAIL);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			printMessage(e.toString(), RETR_FAIL);
		}
	}

	protected boolean checkParam() {

		if ((mUrl == null) || (mUrl.trim().equals(""))) {
			printMessage("please set your url", RETR_FAIL);
			return false;
		}

		return true;
	}

	protected void printMessage(String str, int code) {
		//System.out.println(str);
	}

	public void sendRequest(String sEND_METHOD) {
		// TODO Auto-generated method stub
		mRequestDate = new Date();
		String method = sEND_METHOD.toLowerCase();
		if (method.equals("get")) {
			sendGet();
		} else if (method.equals("post")) {
			sendPost();
		}
		Date endDate = new Date();
		mHandleSpanTime = ((endDate.getTime() - mRequestDate.getTime()))/1000.0 + "";
	}

	protected String getResult() {
		return mResultContent;
	}
}
