package com.gudeng.commerce.gd.supplier.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.util.FileUploadUtil;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//		"classpath*:conf/spring/spring-hessian.xml",
//		"classpath*:conf/spring/spring-servlet.xml",
//		"classpath*:conf/spring/spring-cache.xml",
//		"classpath*:conf/spring/spring-da.xml",
//		"classpath*:conf/spring/spring-exception.xml",
//		"classpath*:conf/spring/spring-impl.xml",
//		"classpath*:conf/spring/spring-res.xml" })
public class FileUploadServiceTest extends TestCase {

	private static FileUploadService fileUploadService;

	@Test
	public void test() {
		System.out.println("111111111111");
		System.out.println(fileUploadService);
	}

	@Test
	public void testImgUploadTest() {
		System.out.println("1111111111");
		InputStream inputStream;
		String fileUrl = null;
		String fileUploadUrl = "D:\\test";
		String fileName = "Tulips.jpg";
		String imgSize = "150,150;160,160;200,200;370,370;650,650;";
		try {
			File file = new File(
					"D:\\test\\1449737844202.jpg");
			if (file.exists()) {
				inputStream = new FileInputStream(file);
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int n = 0;
				while (-1 != (n = inputStream.read(buffer))) {
					output.write(buffer, 0, n);
				}
				byte[] data = output.toByteArray();
				// fileUrl=FileUploadUtil.uploadImgfile(inputStream, fileName,
				// fileUploadUrl, imgSize);
				fileUrl = fileUploadService.uploadImgfile(data, fileName,
						fileUploadUrl, imgSize);
				System.out.println(fileUrl);
			} else {
				System.out.println("文件不存在。。。。。。");
			}
			System.out.println(fileUrl);
		} catch (Exception e) {
			System.out.println("出错。。。。。。。。。");
			e.printStackTrace();
		}
	}
	
	@Test
	public void uploadImg() {
		System.out.println("ddddddddddddd");
	}

	@Override
	protected void setUp() throws Exception {
		String url = "http://127.0.0.1:8080/gd-supplier/service/fileUploadService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		FileUploadServiceTest.fileUploadService = (FileUploadService) factory
				.create(FileUploadService.class, url);
	}

}
