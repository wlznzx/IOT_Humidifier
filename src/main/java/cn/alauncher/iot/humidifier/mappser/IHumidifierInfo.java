package cn.alauncher.iot.humidifier.mappser;

import cn.alauncher.iot.humidifier.modle.HumidifierInfo;

public interface IHumidifierInfo {
	public void insertHumidifierInfo(HumidifierInfo info);
	public void updateHumidifierInfo(HumidifierInfo info);
	public void deletev(int infoId);
	public HumidifierInfo getHumidifierInfo(int infoId);
}
