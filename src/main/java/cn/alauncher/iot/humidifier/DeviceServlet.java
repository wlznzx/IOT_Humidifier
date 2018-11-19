package cn.alauncher.iot.humidifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.alauncher.iot.bussiness.DeviceService;

public class DeviceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DeviceService mDeviceService;
	
	

	@Override
	public void init() throws ServletException {
		mDeviceService = new DeviceService();
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		if(id != null){
			
		}
		writer.write(mDeviceService.getDevice(id).toString().replace("\\", ""));
//		writer.write(mDeviceService.getDevice(id).toString());
		writer.flush();
		writer.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("DeviceServlet doPost");
		String jsonBody = charReader(req);
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.write(mDeviceService.insertDevice(jsonBody).toString().replace("\\", ""));
		writer.flush();
		writer.close();
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("DeviceServlet doPost");
		String jsonBody = charReader(req);
		String resultJson = mDeviceService.updateDevice(jsonBody).toString();
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		writer.write(resultJson);
		writer.flush();
		writer.close();
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("DeviceServlet doDelete");
//		String jsonBody = charReader(req);
//		String resultJson = mDeviceService.deleteDevice(jsonBody).toString();
//		resp.setContentType("application/json");
//		PrintWriter writer = resp.getWriter();
//		writer.write(resultJson);
//		writer.flush();
//		writer.close();
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
}
