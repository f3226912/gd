package com.gudeng.paltform.pushmsg.umeng.ios;

import com.gudeng.paltform.pushmsg.umeng.IOSNotification;

public class IOSUnicast extends IOSNotification {
	public IOSUnicast() {
		try {
			this.setPredefinedKeyValue("type", "unicast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
