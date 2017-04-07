/**
 * 文件：BeanUtils.java
 * 公司：深圳祥云信息科技有限公司
 * 版权：Copyrigong © 2011 Shenzhen Innovane Information Technologies Co..Ltd, Inc. All rights reserved.
 * 系统：
 * 描述：ＩＮＮＯＶＡＮＥ（ＰＡＡＳ　&　ＳＡＡＳ）ＳＯＦＴＷＡＲＥ
 * 作者：Nail.zhang
 * 时间：Jan 10, 2012
 */
package com.gudeng.commerce.gd.api.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈功能详细描述〉
 * 
 * @author Nail.zhang
 * @version [V0.1, Jan 10, 2012]
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	/**
	 * convert a map to a Bean Add by Nail
	 * 
	 * @param srcMap
	 * @param destVO
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void copyPropertiesFromMapToBean(Object destVO,Map srcMap) {
		Iterator keys = srcMap.keySet().iterator();

		try {
			String key ;
			String name ;
			Class destType;
			Object value;
		for (; keys.hasNext();) {
			key = (String) keys.next();
			name = StringUtils.firstTwoWordToLowerCase(key);
			destType = PropertyUtils.getPropertyType(destVO, name);
						
			value = srcMap.get(key);
				if(destType!=null && srcMap.get(key)!=null && !srcMap.get(key).equals("null")){
					try {
						BeanUtils.setProperty(destVO, name, srcMap.get(key));
						//BeanUtils.
					} catch (Exception e) {
						//logger.error("复制属性错误 " + e.getMessage(), e);
					}
				}
				
		}
		} catch (IllegalAccessException e) {
			logger.error("BeanUtils复制属性异常 " + e.getMessage(), e);
		} catch (InvocationTargetException e) {
		} catch(Exception e){
			logger.error("" + e.getMessage(), e);
		}
	}
	
	
	public static void copyPropertiesFromBeanToMap(Map destMap,Object srcVO) {
		
		if (destMap == null || srcVO == null) {
			return;
		}

		PropertyDescriptor[] srcDesc = PropertyUtils.getPropertyDescriptors(srcVO);
		try {
			for (int i = 0; i < srcDesc.length; i++) {
				String name=srcDesc[i].getName();
				Object value = PropertyUtils.getProperty(srcVO, name);
				destMap.put(name, value);
			
			}

		} catch (Exception e) {
			logger.error(" " + e.getMessage(), e);
		}
	}
	
	public static Map convertFirstKeyWordToLower(Map srcMap){
		Iterator keys = srcMap.keySet().iterator();
		Map destMap = new HashMap();
		String key ;
		String name ;
		Class destType;
		for (; keys.hasNext();) {
			key = (String) keys.next();
			name = StringUtils.firstWordToLowerCase(key);
			destMap.put(name, srcMap.get(key));
		}
		return destMap;
	}
	
	public static Map convertFirstKeyWordToUpper(Map srcMap){
		Iterator keys = srcMap.keySet().iterator();
		Map destMap = new HashMap();
		String key ;
		String name ;
		Class destType;
		for (; keys.hasNext();) {
			key = (String) keys.next();
			name = StringUtils.firstWordToUpperCase(key);
			destMap.put(name, srcMap.get(key));
		}
		return destMap;
	}
	
	//public static void 

	public static void copyProperties(Object dest, Object orig) {
		if (dest == null || orig == null) {
			//return dest;
		}

		PropertyDescriptor[] destDesc = PropertyUtils.getPropertyDescriptors(dest);
		try {
			for (int i = 0; i < destDesc.length; i++) {
				Class destType = destDesc[i].getPropertyType();
				Class origType = PropertyUtils.getPropertyType(orig, destDesc[i].getName());
				
				if (destType != null && destType.equals(origType) && !destType.equals(Class.class)) {
					if (!Collection.class.isAssignableFrom(origType)) {
						Object value = PropertyUtils.getProperty(orig, destDesc[i].getName());
						PropertyUtils.setProperty(dest, destDesc[i].getName(), value);
					}
				}else if(origType!=null && origType.getName().indexOf("innovane")>0){
					 
					Object obj = destType.newInstance();
					PropertyUtils.setProperty(dest, destDesc[i].getName(), obj);
					copyProperties(obj,PropertyUtils.getProperty(orig, destDesc[i].getName()));
					
				}
			}

			// return dest;
		} catch (Exception e) {
			logger.error(" " + e.getMessage(), e);
		}
	}

	public static Object copyProperties(Object dest, Object orig, String[] ignores) throws Exception {
		if (dest == null || orig == null) {
			return dest;
		}

		PropertyDescriptor[] destDesc = PropertyUtils.getPropertyDescriptors(dest);
		try {
			for (int i = 0; i < destDesc.length; i++) {
				if (contains(ignores, destDesc[i].getName())) {
					continue;
				}

				Class destType = destDesc[i].getPropertyType();
				Class origType = PropertyUtils.getPropertyType(orig, destDesc[i].getName());
				if (destType != null && destType.equals(origType) && !destType.equals(Class.class)) {
					if (!Collection.class.isAssignableFrom(origType)) {
						Object value = PropertyUtils.getProperty(orig, destDesc[i].getName());
						PropertyUtils.setProperty(dest, destDesc[i].getName(), value);
					}
				}
			}

			return dest;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	static boolean contains(String[] ignores, String name) {
		boolean ignored = false;
		for (int j = 0; ignores != null && j < ignores.length; j++) {
			if (ignores[j].equals(name)) {
				ignored = true;
				break;
			}
		}

		return ignored;
	}

}
