package cn.alauncher.iot.bussiness;

import java.io.IOException;
import java.io.InputStream;






import net.sf.json.JSONObject;

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
import cn.alauncher.iot.humidifier.mappser.IAction;
import cn.alauncher.iot.humidifier.modle.Action;
import cn.alauncher.iot.humidifier.modle.Device;

public class ActionService {
	
	String resource = "mybatis-config.xml";
	SqlSessionFactory sqlSessionFactory;
	Gson mGson;
	
	static Logger logger = LogManager.getLogger("ActionService");
	
	public ActionService(){
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mGson = new GsonBuilder().disableHtmlEscaping().create();
	}
	
	public JsonObject insertAction(String json) {
		Action _info = mGson.fromJson(json, Action.class);                                                                                                                                  
		return insertAction(_info);
	}
	
	public JsonObject insertAction(Action info) {
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		if(info == null){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, Constants.PARAMETER_ERROR);
			return resultObj;
		}
		SqlSession session = sqlSessionFactory.openSession();
		IAction _IAction = session.getMapper(IAction.class);
			try{
				_IAction.insertAction(info);
				session.commit();
			}catch(Exception e){
				resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
				resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
			}
			session.close();
			return resultObj;
	}
	
	public JsonObject getAction(String id) {
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		SqlSession session = sqlSessionFactory.openSession();
		IAction _IAction = session.getMapper(IAction.class);
		try{
			Action _info = _IAction.getAction(id);
//			resultObj.addProperty("Action", mGson.toJson(_info, Action.class));
			JsonObject returnData = new JsonParser().parse(mGson.toJson(_info, Action.class)).getAsJsonObject();
			resultObj.add("Action", returnData);
			deleteAction(_info);
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}
	
	public JsonObject deleteAction(String json) {
		Action _info = mGson.fromJson(json, Action.class);
		return deleteAction(_info);
	}
	
	
	public JsonObject deleteAction(Action info){
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		SqlSession session = sqlSessionFactory.openSession();
		IAction _IAction = session.getMapper(IAction.class);
		try{
			int result = _IAction.deleteAction(info);
			resultObj.addProperty(Constants.MESSAGE, result);
			session.commit();
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}
	
	
	/*
	public Action fromJSON(String json){
		JSONObject _json = null;
		try{
			_json = JSONObject.fromObject(json);
		}catch(JSONException e){
			return null;
		}
		String id = null;
		try{
			id = _json.getString("id");
		}catch(JSONException e){
			return null;
		}
		Action dev = new Action(id, 18, 18, -20, 50);
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
