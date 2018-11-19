package cn.alauncher.iot.humidifier.mappser;

import java.util.List;

import cn.alauncher.iot.humidifier.modle.RegistrationID;


public interface IRegistrationID {
	public void insertRegistrationID(RegistrationID id);
	public void updateRegistrationID(RegistrationID id);
	public int deleteRegistrationID(RegistrationID id);
	public RegistrationID getRegistrationIDBy(RegistrationID id);
	public List<RegistrationID> getRegistrationID(String id);
	public List<RegistrationID> getRegistrationIDByDevID(String dev_id);
}
