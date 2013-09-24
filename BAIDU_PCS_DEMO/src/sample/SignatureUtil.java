package sample;

import java.net.URLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class SignatureUtil {
	static String secretKey = "";
	private final static String Flag = "MBO";
	private final static String content = Flag + "\n"
	          + "Method=GET" + "\n"
	          + "Bucket=%1$s" + "\n"
	          + "Object=/%2$s" + "\n";
	public static String createSignature(String bucket,String savePath) {
		Mac mac;
		String out = null;
		String txt = String.format(content, bucket, savePath);
		try {
			mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(),"HmacSHA1");
		    mac.init(secret);
		    out = new String(Base64.encodeBase64(mac.doFinal(txt.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(out == null){return null;}
		return URLEncoder.encode(out);
	}
}
