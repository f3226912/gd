package com.gudeng.paltform.pushmsg.umeng.ios;

import com.gudeng.paltform.pushmsg.umeng.IOSNotification;

public class IOSGroupcast extends IOSNotification {
	public IOSGroupcast() {
		try {
			this.setPredefinedKeyValue("type", "groupcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
