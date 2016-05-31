package com.infogain.igconverge.service;

import java.util.List;

import com.infogain.igconverge.model.DeviceInfo;

public interface DeviceInfoHandler 
{
	public DeviceInfo findByTokenIdAndDeviceType(String tokenId, String deviceType);
	
	public void saveInfo(DeviceInfo deviceInfo);
	
	public List<DeviceInfo> findAllDevice();
	
	public List<DeviceInfo> findAllDeviceByDeviceType(String deviceType);
	
	public void removeUnregisteredDevice(String tokenId, String deviceType);
	
	public DeviceInfo findByEmployeeId(String empId);
}
