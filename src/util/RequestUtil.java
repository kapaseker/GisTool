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

public class RequestUtil {

	private String mUrl = null;
	private String mResult = null;
	private String mRqContent = null;
	private static final RequestUtil mSelf = new RequestUtil();
	private String mContentEncoding = "GBK";
	private onRequestListener mRequestListener = null;
	
	public static int RETR_SUCCESS = 0x001;
	public static int RETR_FAIL = 0x002;

	public static interface onRequestListener {
		public void onRead(String str,int rtrcode);
	}

	private RequestUtil() {

	}

	public static RequestUtil newInstace() {
		
		return mSelf;
	}

	public void addRequestListener(onRequestListener rl) {
		this.mRequestListener = rl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public String getUrl() {
		return mUrl;
	}
	
	public void setRequestContent(String content){
		this.mRqContent = content;
	}
	public String getRequestContent(){
		return mRqContent;
	}
	
	public void setContentEncoding(String en){
		this.mContentEncoding = en;
	}
	
	public String getContentEncoding(){
		return this.mContentEncoding;
	}

	public void sendGet() {
		if(false == checkParam()){
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

				String strContent = new String(bao.toByteArray(), "utf-8");
				sendMessage(strContent,this.RETR_SUCCESS);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				sendMessage(e.toString(),this.RETR_FAIL);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			sendMessage(e.toString(),this.RETR_FAIL);
		}

	}

	public void sendPost() {

		if(false == checkParam()){
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

				// httpUrlConnection.setRequestProperty("content-type", "text/xml");

				OutputStream output = new BufferedOutputStream(
						urlcon.getOutputStream());

				OutputStreamWriter writer = new OutputStreamWriter(output,mContentEncoding);

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

				String strContent = new String(bao.toByteArray(), "GBK");
				sendMessage(strContent,RETR_SUCCESS);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				sendMessage(e.toString(),RETR_FAIL);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			sendMessage(e.toString(),RETR_FAIL);
		}
	}
	private boolean checkParam(){
		
		if((mUrl == null) || (mUrl.trim().equals(""))){
			sendMessage("please set your url",RETR_FAIL);
			return false;
		}
		
		
		return true;
	}
	private void sendMessage(String str,int code){
		if(mRequestListener != null){
			mRequestListener.onRead(str, code);
		}
	}
	
	public void sendRequest(String sEND_METHOD) {
		// TODO Auto-generated method stub
		String method = sEND_METHOD.toLowerCase();
		if (method.equals("get")) {
			sendGet();
		} else if (method.equals("post")) {
			sendPost();
		}
	}
}
