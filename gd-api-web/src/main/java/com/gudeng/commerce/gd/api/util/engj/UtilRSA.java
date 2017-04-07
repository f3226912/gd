package com.gudeng.commerce.gd.api.util.engj;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.security.x509.CertAndKeyGen;
import sun.security.x509.X500Name;



public class UtilRSA {
	private static final Logger logger = LoggerFactory.getLogger(UtilRSA.class);

	/**
	 * 证书文件签名
	 * @param aMessage
	 * @param certfile
	 * @param certpwd
	 * @return
	 * @throws Exception
	 */
	public static String fileSignMessage(String reqdata, String certfile, String certpwd) throws Exception {
		String tSignedBase64 = "";
		Signature tSignature = null;
		try {
			tSignature = Signature.getInstance("SHA1withRSA");
			tSignature.initSign(getMerchantKey(certfile, certpwd));
			tSignature.update(reqdata.toString().getBytes("UTF-8"));
			byte[] tSigned = tSignature.sign();
			tSignedBase64 = new String(Base64.encode(tSigned));
		} catch (Exception e) {
			throw e;
		}
		//System.out.println("签名串：" + tSignedBase64);
		return new String(tSignedBase64);
	}

	/**
	 * 验签
	 * @param reqdata
	 * @param signmsg
	 * @param certfile
	 * @return
	 */
	public static boolean verifySign(String reqdata, String signmsg, String certfile) {
		byte[] tSign = Base64.decode(signmsg);
		try {
			Signature tSignature = Signature.getInstance("SHA1withRSA");
			tSignature.initVerify(getTrustCertificate(certfile));
			tSignature.update(reqdata.toString().getBytes("UTF-8"));
			if (tSignature.verify(tSign)) {
				return true;
			} else {
				logger.error("响应报文签名验证失败");
			}
		} catch (Exception e) {
			logger.error("响应报文签名验证失败 - " + e.getMessage(), e);
		}
		return false;
	}

	private static Certificate getTrustCertificate(String tCertFile) throws Exception {
		Certificate tCertificate = null;
		byte[] tCertBytes = new byte[4096];
		int tCertBytesLen = 0;
		FileInputStream tIn = null;
		try {
			tIn = new FileInputStream(tCertFile);
			tCertBytesLen = tIn.read(tCertBytes);
		} catch (Exception e) {
			logger.error("无法读取证书文档[" + tCertFile + "]！", e);
		} finally {
			try {
				tIn.close();
			} catch (Exception localException1) {
			}
		}
		byte[] tFinalCertBytes = new byte[tCertBytesLen];
		for (int i = 0; i < tCertBytesLen; ++i) {
			tFinalCertBytes[i] = tCertBytes[i];
		}

		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {
			CertificateFactory tCertificateFactory = CertificateFactory.getInstance("X.509");
			ByteArrayInputStream bais = new ByteArrayInputStream(tFinalCertBytes);

			if (bais.available() > 0) {
				tCertificate = tCertificateFactory.generateCertificate(bais);
			}
		} catch (Exception e) {
			logger.error("证书格式错误 - 无法由[" + tCertFile + "]生成X.509证书对象！", e);
		}
		return tCertificate;
	}

	private static PrivateKey getMerchantKey(String certfile, String certpwd) throws Exception {
		KeyStore tKeyStore = null;
		FileInputStream tIn = null;
		String tMerchantCertFile = certfile;
		String tMerchantCertPassword = certpwd;
		try {
			tIn = new FileInputStream(tMerchantCertFile);
			tKeyStore = KeyStore.getInstance("PKCS12", new com.sun.net.ssl.internal.ssl.Provider().getName());
			tKeyStore.load(tIn, tMerchantCertPassword.toCharArray());
		} catch (Exception e) {
			logger.error("无法读取证书文档[" + tMerchantCertFile + "]！", e);
		} finally {
			if (tIn != null) {
				try {
					tIn.close();
				} catch (Exception localException1) {
				}
			}
		}
		String tAliases = "";
		try {
			Enumeration e = tKeyStore.aliases();
			if (e.hasMoreElements()) {
				tAliases = (String) e.nextElement();
			}
		} catch (Exception e) {
			logger.error("证书格式错误，无法对证书进行编码！", e);
		}

		PrivateKey iMerchantKey = null;
		try {
			iMerchantKey = (PrivateKey) tKeyStore.getKey(tAliases, tMerchantCertPassword.toCharArray());
		} catch (Exception e) {
			logger.error("无法读取商户私钥，无法生成私钥证书对象！", e);
		}
		return iMerchantKey;
	}
	
	/**
	 * 创建自签名证书，自己作为CA
	 * pfx格式文件有私钥和公钥，crt格式文件只有公钥，没有私钥
	 * 
	 * @param pfxFilePath pfx格式文件保存路径
	 * @param crtFilePath crt格式文件保存路径
	 * @param passward 证书密码 
	 * @throws Exception
	 */
	private static void createSelfCert(String pfxFilePath, String crtFilePath, String passward) throws Exception {
		CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "MD5WithRSA", null);
		certAndKeyGen.generate(1024);
		X500Name name = new X500Name("CN=gdeng.cn,O=gudeng,L=SZ,C=CN");
		X509Certificate certificate = certAndKeyGen.getSelfCertificate(name, new Date(), 3650 * 24L * 60L * 60L);
		X509Certificate[] certificates = {certificate};
		FileOutputStream pfxOutputStream = null, crtOutputStream = null;
		try {
			KeyStore outputKeyStore = KeyStore.getInstance("pkcs12");
			outputKeyStore.load(null);
			outputKeyStore.setKeyEntry("aliasName", certAndKeyGen.getPrivateKey(), passward.toCharArray(), certificates);
			pfxOutputStream = new FileOutputStream(pfxFilePath);
			outputKeyStore.store(pfxOutputStream, passward.toCharArray());
			crtOutputStream = new FileOutputStream(new File(crtFilePath));
			crtOutputStream.write(certificate.getEncoded());
		} catch (Exception e) {
			logger.error("创建自签名证书异常：", e);
		} finally {
			if(pfxOutputStream !=  null){
				try{
					pfxOutputStream.close();
				}catch(IOException ioe){
					logger.error("关闭输出流异常：", ioe);
				}
			}
			if(crtOutputStream !=  null){
				try{
					crtOutputStream.close();
				}catch(IOException ioe){
					logger.error("关闭输出流异常：", ioe);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			//UtilRSA.createSelfCert("d:\\gdeng.pfx","d:\\gdeng.crt","123456");
			String reqdata = "中国";
			String signmsg = UtilRSA.fileSignMessage(UtilMD5.crypt(reqdata).toLowerCase(), "d:\\gdeng.pfx", "123456");
			boolean signFlag = UtilRSA.verifySign(UtilMD5.crypt(reqdata).toLowerCase(), signmsg, "d:\\gdeng.crt");
			System.out.println(">>>>>验签结果：" + signFlag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
