package cn.alauncher.iot.bussiness;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.alauncher.iot.humidifier.constant.Constants;
import cn.alauncher.iot.humidifier.mappser.IDevice;
import cn.alauncher.iot.humidifier.mappser.IRegistrationID;
import cn.alauncher.iot.humidifier.modle.Device;
import cn.alauncher.iot.humidifier.modle.RegistrationID;
import cn.alauncher.iot.humidifier.utils.JPushUtills;

public class DeviceService {
	
	String resource = "mybatis-config.xml";
	SqlSessionFactory sqlSessionFactory;
	Gson mGson;
	
	static Logger logger = LogManager.getLogger("DeviceService");
	
	public DeviceService(){
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mGson = new GsonBuilder().disableHtmlEscaping().create();
	}
	
	public JsonObject insertDevice(String json) {
		Device _info = mGson.fromJson(json, Device.class);
		return insertDevice(_info);
	}
	
	public JsonObject insertDevice(Device info) {
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		if(info == null){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, Constants.PARAMETER_ERROR);
			return resultObj;
		}
		SqlSession session = sqlSessionFactory.openSession();
		IDevice _IDevice = session.getMapper(IDevice.class);
		if(_IDevice.getDevice(info.getDevice_id()) == null){
			try{
				_IDevice.insertDevice(info);
				session.commit();
			}catch(Exception e){
				resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
				resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
			}
			session.close();
			return resultObj;
		}else{
			return updateDevice(info);
		}
	}
	
	public JsonObject updateDevice(String json) {
		Device _info = mGson.fromJson(json, Device.class);
		return updateDevice(_info);
	}

	public JsonObject updateDevice(Device info) {
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		resultObj.addProperty(Constants.MESSAGE, Constants.UPDATE_SUCCESS);
		SqlSession session = sqlSessionFactory.openSession();
		IDevice _IDevice = session.getMapper(IDevice.class);
		_IDevice.updateDevice(info);
		try{
			_IDevice.updateDevice(info);
			session.commit();
			
			if(info.getErr_code() != 0){
				IRegistrationID _IRegistrationID = session.getMapper(IRegistrationID.class);
				List<RegistrationID> _list = _IRegistrationID.getRegistrationIDByDevID(info.getDevice_id());
				for (RegistrationID registrationID : _list) {
					JPushUtills.JPushT(registrationID.getId(),Constants.DEVICE_ANOMALY,registrationID.getDevice_id());
				}
			}
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}

	public JsonObject getDevice(String id) {
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		SqlSession session = sqlSessionFactory.openSession();
		IDevice _IDevice = session.getMapper(IDevice.class);
		try{
			Device _info = _IDevice.getDevice(id);
			JsonObject returnData = new JsonParser().parse(mGson.toJson(_info, Device.class)).getAsJsonObject();
			resultObj.add("device", returnData);
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}
	
	/*
	public JsonObject deleteDevice(String json) {
		Device _info = mGson.fromJson(json, Device.class);
		return deleteDevice(_info);
	}
	
	
	public JsonObject deleteDevice(Device info){
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		SqlSession session = sqlSessionFactory.openSession();
		IDevice _IDevice = session.getMapper(IDevice.class);
		try{
			int result = _IDevice.deleteDevice(info);
			resultObj.addProperty(Constants.MESSAGE, result);
			session.commit();
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}
	*/
	
	
	/*
	public Device fromJSON(String json){
		JSONObject _json = null;
		try{
			_json = JSONObject.fromObject(json);
		}catch(JSONException e){
			return null;
		}
		String device_id = null;
		try{
			device_id = _json.getString("device_id");
		}catch(JSONException e){
			return null;
		}
		Device dev = new Device(device_id, 18, 18, -20, 50);
		try{
			int temperature = _json.getInt("temperature");
			System.out.println("temperature = " + temperature);
			dev.setTemperature(temperature);
		}catch(JSONException e){
		}
		try{
			int humidity = _json.getInt("humidity");
			System.out.println("humidity = " + humidity);
			dev.setHumidity(humidity);
		}catch(JSONException e){
		}
		try{
			int low_temperature = _json.getInt("low_temperature");
			System.out.println("low_temperature = " + low_temperature);
			dev.setLow_temperature(low_temperature);
		}catch(JSONException e){
		}
		try{
			int high_temperature = _json.getInt("high_temperature");
			System.out.println("high_temperature = " + high_temperature);
			dev.setHigh_temperature(high_temperature);
		}catch(JSONException e){
		}
		return dev;
	}
	*/
}
