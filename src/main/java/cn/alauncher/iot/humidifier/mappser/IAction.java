package cn.alauncher.iot.humidifier.mappser;

import cn.alauncher.iot.humidifier.modle.Action;


public interface IAction {
	public void insertAction(Action action);
	public int deleteAction(Action action);
	public Action getAction(String device_id);
}
