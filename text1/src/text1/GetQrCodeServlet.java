package text1;

import java.util.Date;


/**
 * 生成二维码图片以及uuid
 * @author zijuntang
 *
 */
public class GetQrCodeServlet  {
		
		//生成唯一ID
		int uuid = (int) (Math.random() * 100000);
		//二维码内容
		String content = "";
		//生成二维码
		String imgName =  uuid + "_" + (int) (new Date().getTime() / 1000) + ".png";
		String imgPath = "/home/web/apache/htdocs/QrCodeLogin/" + imgName;
		TwoDimensionCode handler = new TwoDimensionCode();
		//handler.encoderQRCode(content, imgPath, "png");
		
		
		
		
		String qrCodeImg = "http://127.0.0.1:8080/QrCodeLoginPro/" + imgName;
		String jsonStr = "{\"uuid\":" + uuid + ",\"qrCodeImg\":\"" + qrCodeImg + "\"}";
		
	
	
}
