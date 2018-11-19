package cn.alauncher.iot.humidifier.mappser;

import cn.alauncher.iot.humidifier.modle.Device;


public interface IDevice {
	public void insertDevice(Device device);
	public int updateDevice(Device device);
	public int deleteDevice(String id);
	public Device getDevice(String id);
}
