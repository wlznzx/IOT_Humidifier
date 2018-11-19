package cn.alauncher.iot.humidifier.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.alauncher.iot.humidifier.constant.Constants;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtills {
	
	public static Logger logger = LogManager.getLogger("JPushUtills");
	
	public static PushPayload buildPushObject_all_all_alert() {
		PushPayload payload = PushPayload.alertAll("alert");
        return payload;
    }
	
	public static PushPayload buildPushNotification(String registrationID,String alert,String dev_id) {
		PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.newBuilder()
                		.addPlatformNotification(AndroidNotification.newBuilder()
                		.setTitle(dev_id)
                		.setAlert(alert)
                		//此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                		.addExtra("dev_id",dev_id)
                		.build()
                		)
                		.addPlatformNotification(IosNotification.newBuilder()
                		//传一个IosAlert对象，指定apns title、title、subtitle等
                		.setAlert(alert)
                		//直接传alert
                		//此项是指定此推送的badge自动加1
                		.incrBadge(1)
                		//此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                		// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                		.setSound("sound.caf")
                		//此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                		.addExtra("dev_id",dev_id)
                		//此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                		// .setContentAvailable(true)
                		.build()
                		).build())
                .build();
        return payload;
    }
	
	public static void JPushT(String registrationID,String alert,String dev_id){
		JPushClient jpushClient = new JPushClient(Constants.MASTER_SECRET, Constants.APP_KEY, null, ClientConfig.getInstance());

	    // For push, all you need do is to build PushPayload object.
	    PushPayload payload = buildPushNotification(registrationID,alert,dev_id);

	    try {
//	    	PushResult result = jpushClient.sendMessageWithRegistrationID("title", "heyB", registrationID);
	    	PushResult result = jpushClient.sendPush(payload);
	        logger.info("Got result - " + result);

	    } catch (APIConnectionException e) {
	        // Connection error, should retry later
	    	logger.error("Connection error, should retry later", e);

	    } catch (APIRequestException e) {
	        // Should review the error, and fix the request
	    	logger.error("Should review the error, and fix the request", e);
	    	logger.info("HTTP Status: " + e.getStatus());
	    	logger.info("Error Code: " + e.getErrorCode());
	    	logger.info("Error Message: " + e.getErrorMessage());
	    }
	}
}
