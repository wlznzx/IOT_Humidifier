package cn.alauncher.iot.humidifier.modle;


/*
 	create table if not exists registrationid(id varchar(32) NOT NULL,device_id varchar(32) NOT NULL)CHARACTER SET utf8 COLLATE utf8_general_ci;
 */
public class RegistrationID {
	public String id;
	public String device_id;
	public RegistrationID(String id, String device_id) {
		super();
		this.id = id;
		this.device_id = device_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	
}
