package com.infogain.igconverge.service;

import java.util.List;

import com.infogain.igconverge.model.UpdateInfo;

public interface UpdateInfoHandler 
{
	public UpdateInfo findByVersionAndDevice(String version, String deviceType);
	
	// serviceType = false, for getting devices with deviceType in sorted order according to version
	//serviceType = true, for getting devices with deviceType in sorted order according to version which having isMandatory = true 
	public List<UpdateInfo> findByDevice(String deviceType, boolean serviceType);
	
	public void saveUpdateInfo(UpdateInfo updateInfo);
	
}
