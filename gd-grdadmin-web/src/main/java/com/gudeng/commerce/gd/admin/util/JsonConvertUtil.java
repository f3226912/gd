package com.gudeng.commerce.gd.admin.util;
import java.io.StringWriter;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonConvertUtil {
		public static String returnJson(Object object) throws Exception{
			ObjectMapper objectMapper = new ObjectMapper();
			StringWriter stringWriter = new StringWriter();
			objectMapper.writeValue(stringWriter, object);
			String jsonStr = stringWriter.toString();
			return jsonStr;
		}
}
