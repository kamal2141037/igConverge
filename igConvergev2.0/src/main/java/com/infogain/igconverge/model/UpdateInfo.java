package com.infogain.igconverge.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UpdateInfo")
public class UpdateInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4555266774019271915L;
	String deviceType;
	String isMandatory;
	double version;
	
	public UpdateInfo() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}
	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * @return the isMandatory
	 */
	public String getIsMandatory() {
		return isMandatory;
	}
	/**
	 * @param isMandatory the isMandatory to set
	 */
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}
	/**
	 * @return the version
	 */
	public double getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(double version) {
		this.version = version;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateInfo [deviceType=");
		builder.append(deviceType);
		builder.append(", isMandatory=");
		builder.append(isMandatory);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}
}
//public class UpdateInfo implements Serializable
//{
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -4484629778247986223L;
//	String deviceType;
//	String appOlderVersion;
//	String isMandatory;
//	String appNewVersion;
//	
//	/**
//	 * 
//	 */
//	public UpdateInfo() 
//	{
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * @param deviceType
//	 * @param appOlderVersion
//	 * @param updateFlag
//	 * @param appNewVersion
//	 */
//	public UpdateInfo(String deviceType, String appOlderVersion,
//			String isMandatory, String appNewVersion) {
//		super();
//		this.deviceType = deviceType;
//		this.appOlderVersion = appOlderVersion;
//		this.isMandatory = isMandatory;
//		this.appNewVersion = appNewVersion;
//	}
//
//	/**
//	 * @return the deviceType
//	 */
//	public String getDeviceType() {
//		return deviceType;
//	}
//
//	/**
//	 * @param deviceType the deviceType to set
//	 */
//	public void setDeviceType(String deviceType) {
//		this.deviceType = deviceType;
//	}
//
//	/**
//	 * @return the appOlderVersion
//	 */
//	public String getAppOlderVersion() {
//		return appOlderVersion;
//	}
//
//	/**
//	 * @param appOlderVersion the appOlderVersion to set
//	 */
//	public void setAppOlderVersion(String appOlderVersion) {
//		this.appOlderVersion = appOlderVersion;
//	}
//
//	/**
//	 * @return the updateFlag
//	 */
//	public String getIsMandatory() {
//		return isMandatory;
//	}
//
//	/**
//	 * @param updateFlag the updateFlag to set
//	 */
//	public void setIsMandatory(String updateFlag) {
//		this.isMandatory = updateFlag;
//	}
//
//	/**
//	 * @return the appNewVersion
//	 */
//	public String getAppNewVersion() {
//		return appNewVersion;
//	}
//
//	/**
//	 * @param appNewVersion the appNewVersion to set
//	 */
//	public void setAppNewVersion(String appNewVersion) {
//		this.appNewVersion = appNewVersion;
//	}
//
//	/* (non-Javadoc)
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("UpdateInfo [deviceType=");
//		builder.append(deviceType);
//		builder.append(", appOlderVersion=");
//		builder.append(appOlderVersion);
//		builder.append(", updateFlag=");
//		builder.append(isMandatory);
//		builder.append(", appNewVersion=");
//		builder.append(appNewVersion);
//		builder.append("]");
//		return builder.toString();
//	}
//	
//}
