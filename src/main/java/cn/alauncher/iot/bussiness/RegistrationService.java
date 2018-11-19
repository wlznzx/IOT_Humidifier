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

import cn.alauncher.iot.humidifier.constant.Constants;
import cn.alauncher.iot.humidifier.mappser.IRegistrationID;
import cn.alauncher.iot.humidifier.modle.RegistrationID;
import cn.alauncher.iot.humidifier.utils.JSONUtils;

public class RegistrationService {
	
	String resource = "mybatis-config.xml";
	SqlSessionFactory sqlSessionFactory;
	Gson mGson;
	
	static Logger logger = LogManager.getLogger("RegistrationService");
	
	public RegistrationService(){
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mGson = new GsonBuilder().disableHtmlEscaping().create();
	}
	
	public JsonObject insertRegistrationID(String json) {
		RegistrationID _info = mGson.fromJson(json, RegistrationID.class);
		return insertRegistrationID(_info);
	}
	
	public JsonObject insertRegistrationID(RegistrationID info) {
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		SqlSession session = sqlSessionFactory.openSession();
		IRegistrationID _IRegistrationID = session.getMapper(IRegistrationID.class);
		if(_IRegistrationID.getRegistrationIDBy(info) != null){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, "ID Repeats");
			return resultObj;
		}
		try{
			_IRegistrationID.insertRegistrationID(info);
			session.commit();
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}
	
	public JsonObject updateRegistrationID(String json) {
		RegistrationID _info = mGson.fromJson(json, RegistrationID.class);
		return updateRegistrationID(_info);
	}

	public JsonObject updateRegistrationID(RegistrationID info) {
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		SqlSession session = sqlSessionFactory.openSession();
		IRegistrationID _IRegistrationID = session.getMapper(IRegistrationID.class);
		_IRegistrationID.updateRegistrationID(info);
		try{
			_IRegistrationID.updateRegistrationID(info);
			session.commit();
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}

	public JsonObject getRegistrationID(String id) {
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		SqlSession session = sqlSessionFactory.openSession();
		IRegistrationID _IRegistrationID = session.getMapper(IRegistrationID.class);
		try{
			List<RegistrationID> _info = _IRegistrationID.getRegistrationID(id);
			resultObj.addProperty("list", JSONUtils.getJSONArrayByList(_info,mGson).toString());
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}
	
	public JsonObject deleteRegistrationID(String json) {
		RegistrationID _info = mGson.fromJson(json, RegistrationID.class);
		return deleteRegistrationID(_info);
	}
	
	public JsonObject deleteRegistrationID(RegistrationID info){
		JsonObject resultObj = new JsonObject();
		resultObj.addProperty(Constants.STATUS, Constants.STATUS_SUCCESS_CODE);
		SqlSession session = sqlSessionFactory.openSession();
		IRegistrationID _IRegistrationID = session.getMapper(IRegistrationID.class);
		try{
			int result = _IRegistrationID.deleteRegistrationID(info);
			resultObj.addProperty(Constants.MESSAGE, result);
			session.commit();
		}catch(Exception e){
			resultObj.addProperty(Constants.STATUS, Constants.STATUS_ERROR_CODE);
			resultObj.addProperty(Constants.MESSAGE, e.getLocalizedMessage());
		}
		session.close();
		return resultObj;
	}
	
}
