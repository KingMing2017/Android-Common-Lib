package com.king.android.common.exception;

/**
 * 异常日志类
 * @author jda
 *
 */
public class ExceptionInfo {
	//本实体记录的唯一标识符 --不填
	private String exceptionId = "";
	//本实体记录的唯一标识�? --不填
	private String serviceId = "";
	//服务编号  --写apk应用英文名
	private String serviceNO = "";
	//服务名称  --写apk应用名
	private String serviceName = "";
	//版本号 ---apk版本号
	private String version = "";
	//过滤条件--不填
	private String filterCond = "";
	//请求�?��到结束的时间间隔，以ms为单位�?--不填
	private String requestMs = "";
	//请求参数--不填
	private String requestParam = "";
	//日志发生时间 --异常发生时间
	private String logTime = "";
	//异常信息--异常日志
	private String exceptionInfo = "";
	//服务器IP地址--终端imei�?
	private String serviceIp = "";
	
	public ExceptionInfo() {
		super();
	}

	public ExceptionInfo(String exceptionId, String serviceId,
			String serviceNO, String serviceName, String version,
			String filterCond, String requestMs, String requestParam,
			String logTime, String exceptionInfo, String serviceIp) {
		super();
		this.exceptionId = exceptionId;
		this.serviceId = serviceId;
		this.serviceNO = serviceNO;
		this.serviceName = serviceName;
		this.version = version;
		this.filterCond = filterCond;
		this.requestMs = requestMs;
		this.requestParam = requestParam;
		this.logTime = logTime;
		this.exceptionInfo = exceptionInfo;
		this.serviceIp = serviceIp;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceNO() {
		return serviceNO;
	}

	public void setServiceNO(String serviceNO) {
		this.serviceNO = serviceNO;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFilterCond() {
		return filterCond;
	}

	public void setFilterCond(String filterCond) {
		this.filterCond = filterCond;
	}

	public String getRequestMs() {
		return requestMs;
	}

	public void setRequestMs(String requestMs) {
		this.requestMs = requestMs;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	@Override
	public String toString() {
		return "ExceptionInfo [exceptionId=" + exceptionId + ", exceptionInfo="
				+ exceptionInfo + ", filterCond=" + filterCond + ", logTime="
				+ logTime + ", requestMs=" + requestMs + ", requestParam="
				+ requestParam + ", serviceId=" + serviceId + ", serviceIp="
				+ serviceIp + ", serviceNO=" + serviceNO + ", serviceName="
				+ serviceName + ", version=" + version + "]";
	}

}
