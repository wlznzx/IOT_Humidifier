package cn.alauncher.iot.humidifier.utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class JSONUtils {
	public static JsonArray getJSONArrayByList(List<?> list,Gson gson){
		JsonArray jsonArray = new JsonArray();
        if (list==null || list.isEmpty()) {
            return jsonArray;//nerver return null
        }
        for (Object object : list) {
            jsonArray.add(gson.toJson(object));
        }
        return jsonArray;
    }

}
