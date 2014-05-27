package util.Interface;
/**
 * 
 * @author i-xiepenggang
 *	@category 统一的处理接口,主要用于返回结果的定义
 */
public interface IGisVerify {
	public String getRequestIdentifier();
	public String getRequestTime();
	public String getHandleTime();
	public String getState();
	public String getResult();
}
