package cn.alauncher.iot.humidifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

import cn.alauncher.iot.humidifier.mappser.IHumidifierInfo;
import cn.alauncher.iot.humidifier.modle.HumidifierInfo;
import cn.jpush.api.push.model.PushPayload;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TemperatureServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String resource = "mybatis-config.xml";
	SqlSessionFactory sqlSessionFactory;

	static Logger logger = LogManager.getLogger("TemperatureServlet");

	@Override
	public void init() throws ServletException {
		super.init();
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doGet(req, resp);
		HumidifierInfo info = getHumidiierInfo(1);
		Gson gson = new Gson();
		PrintWriter writer = resp.getWriter();
		writer.write(gson.toJson(info, HumidifierInfo.class));
		writer.flush();
		writer.close();
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doPut(req, resp);
		System.out.println("doPut");
		String jsonBody = charReader(req);
		Gson gson = new Gson();
		HumidifierInfo _info = gson.fromJson(jsonBody, HumidifierInfo.class);
		System.out.println(_info.toString());
		_info.setId(1);
		updateHumidiierInfo(_info);
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.write(jsonBody);
		writer.flush();
		writer.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doPost(req, resp);
		System.out.println("doPost");

		String jsonBody = charReader(req);
		Gson gson = new Gson();
		HumidifierInfo _info = gson.fromJson(jsonBody, HumidifierInfo.class);
		System.out.println(_info.toString());

		insertHumidiierInfo(_info);

		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.write(jsonBody);
		writer.flush();
		writer.close();
	}
	
	

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doDelete(req, resp);
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.write("\"method\":\"delete\"");
		writer.flush();
		writer.close();
	}

	public String charReader(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			br = request.getReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str, wholeStr = "";
		try {
			while ((str = br.readLine()) != null) {
				wholeStr += str;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return wholeStr;
	}

	public void insertHumidiierInfo(HumidifierInfo info) {
		SqlSession session = sqlSessionFactory.openSession();
		IHumidifierInfo _IHumidifierInfo = session.getMapper(IHumidifierInfo.class);
		_IHumidifierInfo.insertHumidifierInfo(info);
		session.commit();
		session.close();
	}

	public void updateHumidiierInfo(HumidifierInfo info) {
		SqlSession session = sqlSessionFactory.openSession();
		IHumidifierInfo _IHumidifierInfo = session.getMapper(IHumidifierInfo.class);
		_IHumidifierInfo.updateHumidifierInfo(info);
		session.commit();
		session.close();
	}

	public HumidifierInfo getHumidiierInfo(int id) {
		SqlSession session = sqlSessionFactory.openSession();
		IHumidifierInfo _IHumidifierInfo = session.getMapper(IHumidifierInfo.class);
		HumidifierInfo _info = _IHumidifierInfo.getHumidifierInfo(id);
		session.commit();
		session.close();
		return _info;
	}
	
	
	public static PushPayload buildPushObject_all_all_alert() {
		PushPayload payload = PushPayload.alertAll("alert");
//		payload.
        return payload;
    }
	
	public String MASTER_SECRET= "5fbd55552a9bac7743203e47";
	public String APP_KEY="34d3b9637f3210a602a99431";
	
	public String registrationID = "13065ffa4e5a18f8218";
	
	

}
