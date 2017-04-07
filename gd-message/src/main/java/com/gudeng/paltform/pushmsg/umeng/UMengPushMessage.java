package com.gudeng.paltform.pushmsg.umeng;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.gudeng.paltform.pushmsg.GdMessageDTO;
import com.gudeng.paltform.pushmsg.umeng.android.AndroidBroadcast;
import com.gudeng.paltform.pushmsg.umeng.android.AndroidUnicast;
import com.gudeng.paltform.pushmsg.umeng.ios.IOSBroadcast;
import com.gudeng.paltform.pushmsg.umeng.ios.IOSUnicast;

public class UMengPushMessage {
	//private String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));

	public void pushMessage(GdMessageDTO message) throws Exception{
		if(null != message){
			if(null != message.getSendType() && !"".equals(message.getSendType())){
				message.setAfter_open("go_activity");
				if("1".equals(message.getSendType())){
					//unicast-单播
					sendAndroidUnicast(message);
					sendIOSUnicast(message);
				}else if("2".equals(message.getSendType())){
					//broadcast-广播
					sendAndroidBroadcast(message);
					sendIOSBroadcast(message);
				}
			}
		}
	}
	
	/**
	 * Android-unicast-单播
	 * @param message
	 * @throws Exception
	 */
	public void sendAndroidUnicast(GdMessageDTO message) throws Exception {
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		AndroidUnicast unicast = new AndroidUnicast();
		if("1".equals(message.getSendApp())){
			unicast.setAppMasterSecret(UMengConfig.NPB_APP_MASTER_SECRET_ANDROID);
			unicast.setPredefinedKeyValue("appkey", UMengConfig.NPB_APPKEY_ANDROID);
		}else if("2".equals(message.getSendApp())){
			unicast.setAppMasterSecret(UMengConfig.NST_APP_MASTER_SECRET_ANDROID);
			unicast.setPredefinedKeyValue("appkey", UMengConfig.NST_APPKEY_ANDROID);
		}else if("3".equals(message.getSendApp())){
          unicast.setAppMasterSecret(UMengConfig.NPS_APP_MASTER_SECRET_ANDROID);
          unicast.setPredefinedKeyValue("appkey", UMengConfig.NPS_APPKEY_ANDROID);
      }
		unicast.setPredefinedKeyValue("timestamp", timestamp);
		// TODO Set your device token
		unicast.setPredefinedKeyValue("device_tokens", message.getDevice_tokens());
		unicast.setPredefinedKeyValue("ticker", message.getTicket());
		unicast.setPredefinedKeyValue("title",  message.getTitle());
		unicast.setPredefinedKeyValue("text",   message.getContent());
		unicast.setPredefinedKeyValue("after_open", message.getAfter_open());
		unicast.setPredefinedKeyValue("display_type", "notification");
		unicast.setPredefinedKeyValue("description", message.getTicket());
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setPredefinedKeyValue("production_mode", message.getProduction_mode());
		// Set customized fields
		Map<String,String> extraMap = message.getExtraMap();
		if (extraMap!=null){
			Iterator<Entry<String, String>> i = extraMap.entrySet().iterator();
			while (i.hasNext()) {
			@SuppressWarnings("rawtypes")
			Entry entry = (Entry)i.next();
			unicast.setExtraField(entry.getKey().toString(), entry.getValue()==null?"":entry.getValue().toString());
			}
		}
//		unicast.setExtraField("test", "11");
		unicast.send();
	}
	
	/**
	 * Ios-unicast-单播
	 * @param message
	 * @throws Exception
	 */
	public void sendIOSUnicast(GdMessageDTO message) throws Exception {
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		IOSUnicast unicast = new IOSUnicast();
		if("1".equals(message.getSendApp())){
			unicast.setAppMasterSecret(UMengConfig.NPB_APP_MASTER_SECRET_IOS);
			unicast.setPredefinedKeyValue("appkey", UMengConfig.NPB_APPKEY_IOS);
		}else if("2".equals(message.getSendApp())){
			unicast.setAppMasterSecret(UMengConfig.NST_APP_MASTER_SECRET_IOS);
			unicast.setPredefinedKeyValue("appkey", UMengConfig.NST_APPKEY_IOS);
		}else if("3".equals(message.getSendApp())){
          unicast.setAppMasterSecret(UMengConfig.NPS_APP_MASTER_SECRET_IOS);
          unicast.setPredefinedKeyValue("appkey", UMengConfig.NPS_APPKEY_IOS);
       }
		unicast.setPredefinedKeyValue("timestamp", timestamp);
		// TODO Set your device token
		unicast.setPredefinedKeyValue("device_tokens", message.getDevice_tokens());
		unicast.setPredefinedKeyValue("alert", message.getContent());
		unicast.setPredefinedKeyValue("badge", 1);
		unicast.setPredefinedKeyValue("sound", "chime");
		unicast.setCustomizedField("key1", "testapp");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setPredefinedKeyValue("production_mode", message.getProduction_mode());
		// Set customized fields
//		unicast.setCustomizedField("test", message.getTicket());
		System.out.println(unicast.rootJson.toString());
		Map<String,String> extraMap = message.getExtraMap();
		if (extraMap!=null){
			Iterator<Entry<String, String>> i = extraMap.entrySet().iterator();
			while (i.hasNext()) {
			@SuppressWarnings("rawtypes")
			Entry entry = (Entry)i.next();
			unicast.setCustomizedField(entry.getKey().toString(), entry.getValue()==null?"":entry.getValue().toString());
			}
		}		
		unicast.send();
	}
	
	/**
	 * Android-broadcast-广播
	 * @param message
	 * @throws Exception
	 */
	public void sendAndroidBroadcast(GdMessageDTO message) throws Exception {
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		AndroidBroadcast broadcast = new AndroidBroadcast();
		if("1".equals(message.getSendApp())){
			broadcast.setAppMasterSecret(UMengConfig.NPB_APP_MASTER_SECRET_ANDROID);
			broadcast.setPredefinedKeyValue("appkey", UMengConfig.NPB_APPKEY_ANDROID);
		}else if("2".equals(message.getSendApp())){
			broadcast.setAppMasterSecret(UMengConfig.NST_APP_MASTER_SECRET_ANDROID);
			broadcast.setPredefinedKeyValue("appkey", UMengConfig.NST_APPKEY_ANDROID);
		}else if("3".equals(message.getSendApp())){
          broadcast.setAppMasterSecret(UMengConfig.NPS_APP_MASTER_SECRET_ANDROID);
          broadcast.setPredefinedKeyValue("appkey", UMengConfig.NPS_APPKEY_ANDROID);
        }
		
		broadcast.setPredefinedKeyValue("timestamp", timestamp);
		
		broadcast.setPredefinedKeyValue("ticker", message.getTicket());
		broadcast.setPredefinedKeyValue("title",  message.getTitle());
		broadcast.setPredefinedKeyValue("text",   message.getContent());
		broadcast.setPredefinedKeyValue("after_open", message.getAfter_open());
		broadcast.setPredefinedKeyValue("display_type", "notification");
		broadcast.setPredefinedKeyValue("description", message.getTicket());
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		broadcast.setPredefinedKeyValue("production_mode", message.getProduction_mode());
		// Set customized fields
//		broadcast.setExtraField("test", message.getTitle());
		
		Map<String,String> extraMap = message.getExtraMap();
		if (extraMap!=null){
			Iterator<Entry<String, String>> i = extraMap.entrySet().iterator();
			while (i.hasNext()) {
			@SuppressWarnings("rawtypes")
			Entry entry = (Entry)i.next();
			broadcast.setExtraField(entry.getKey().toString(), entry.getValue()==null?"":entry.getValue().toString());
			}
		}
		broadcast.send();
	}
	
	/**
	 * Ios-broadcast-广播
	 * @param message
	 * @throws Exception
	 */
	public void sendIOSBroadcast(GdMessageDTO message) throws Exception {
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		IOSBroadcast broadcast = new IOSBroadcast();
		if("1".equals(message.getSendApp())){
			broadcast.setAppMasterSecret(UMengConfig.NPB_APP_MASTER_SECRET_IOS);
			broadcast.setPredefinedKeyValue("appkey", UMengConfig.NPB_APPKEY_IOS);
		}else if("2".equals(message.getSendApp())){
			broadcast.setAppMasterSecret(UMengConfig.NST_APP_MASTER_SECRET_IOS);
			broadcast.setPredefinedKeyValue("appkey", UMengConfig.NST_APPKEY_IOS);
		}else if("2".equals(message.getSendApp())){
          broadcast.setAppMasterSecret(UMengConfig.NPS_APP_MASTER_SECRET_IOS);
          broadcast.setPredefinedKeyValue("appkey", UMengConfig.NPS_APPKEY_IOS);
      }
	
		broadcast.setPredefinedKeyValue("timestamp", timestamp);
		broadcast.setPredefinedKeyValue("alert", message.getTitle());
		broadcast.setPredefinedKeyValue("badge", 0);
		broadcast.setPredefinedKeyValue("sound", "chime");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		broadcast.setPredefinedKeyValue("production_mode", message.getProduction_mode());
		
		Map<String,String> extraMap = message.getExtraMap();
		if (extraMap!=null){
			Iterator<Entry<String, String>> i = extraMap.entrySet().iterator();
			while (i.hasNext()) {
			@SuppressWarnings("rawtypes")
			Entry entry = (Entry)i.next();
			broadcast.setCustomizedField(entry.getKey().toString(), entry.getValue()==null?"":entry.getValue().toString());
			}
		}
		// Set customized fields
//		broadcast.setCustomizedField("test", message.getTitle());
		broadcast.send();
	}
		
	
	public static void main(String[] args) throws Exception {
		UMengPushMessage pushMessage = new UMengPushMessage();
		GdMessageDTO gdMessage = new GdMessageDTO();
		gdMessage.setSendApp("1");
		gdMessage.setSendType("1");
		gdMessage.setTicket("农商友");
		gdMessage.setTitle("农商友");
		gdMessage.setContent("已为您的订单指派司机，请尽快支付预付款。【谷登科技】");
//		gdMessage.setAfter_open("go_activity");
		//农速通：com.gudeng.nongst.ui.activity.HomeActivity
		//农商友：com.gudeng.smallbusiness.activity.MainActivity
//		gdMessage.setActivity("com.gudeng.nongst.ui.activity.HomeActivity");
		//ios:9c6031b0c53f823498214ed8e9ba4ed65b2127633f6611836c2e93abdf0d2e8b
		//c831f20c6ae17af847956813e15dc18993e398c8b413fb32d559d4ce8f06b3da
		//android:AsfEx_AIdKOZuTfL55scurKF6PRdP3Klx0khdM3MmKM2
		//An8QnrhRr2HMLWXpPTpZ1IDIJAOXUdtuCKtedBjEtWCZ
		gdMessage.setDevice_tokens("c0e0453e83a7acc4f897b7ff2162f5439acc22de8cbcc50fe510a6d3d1f56406");
		gdMessage.setProduction_mode(false);
		Map<String,String> extraMap = new HashMap<String,String>();
//		private static final String  NSY_TJ="NSY_TJ";//推荐界面
//		private static final String  NSY_GZ="NSY_GZ";//关注界面
		extraMap.put("openmenu", "NSY_DD");
		gdMessage.setExtraMap(extraMap);
		pushMessage.pushMessage(gdMessage);
//		pushMessage.sendAndroidUnicast(gdMessage);
//		pushMessage.sendAndroidBroadcast(gdMessage);
//		pushMessage.sendIOSBroadcast(gdMessage);//ios广播
		//sendIOSUnicast(gdMessage);
	}
}
