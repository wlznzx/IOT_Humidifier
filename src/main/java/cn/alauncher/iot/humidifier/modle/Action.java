package cn.alauncher.iot.humidifier.modle;


/*
	create table if not exists action(device_id varchar(32) NOT NULL,action varchar(32) NOT NULL,extra varchar(32))CHARACTER SET utf8 COLLATE utf8_general_ci;
*/
public class Action {
	public String device_id;
	public String action;
	public String extra;
	
	public Action(String device_id, String action, String extra) {
		super();
		this.device_id = device_id;
		this.action = action;
		this.extra = extra;
	}
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
}
