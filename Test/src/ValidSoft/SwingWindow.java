package ValidSoft;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ValidSoft.utils.FileUtils;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.REDebugCompiler;



import net.sf.json.JSONArray;




public class SwingWindow extends JFrame implements MouseListener {
	
	private JLabel URL_ADDRESS;
	
	private JLabel LOG_ID;
	
	private JLabel USER_ID;
	
	private JLabel BIOMETRIC_TYPE;
	
	private JLabel AUDIO_FORMAT;
	
	private JLabel AUDIO_SAMPLE;
	
	private JLabel AUDIO_GET_TYPE;
	
	//private JLabel AUDIO_REPLAY;
	
	private JLabel AUDIO_FILE_SELECT;

	//---
	private JTextField urlId;


	private JTextField logId;


	private JTextField userId;
	

	private JComboBox biometricType;
	

	private String biometricType_TD_8;
	
	
	private String biometricType_TD_16;

	
	private String biometricType_TI;
	
	
	private JComboBox audioSample;
	
	
	private JComboBox audioFormat;
	
	
	private JComboBox audioGetType;


	private JComboBox audioReplay;


	private JTextField audioFileSelect;
	
	
	private JButton BUTTON_OPEN_FILE;
	
	
	private JButton BUTTON_POST_SEND;
	
	
	private TextArea POST_RESULT;
	
	
	private static List<String> files;
	
	
	//---
	private String payload_url;

	private String payload_log;

	private String payload_user;

	private String payload_biometric = "text-independent";
	
	private String payload_biometric_mode = "ti_plp2covv2";

	private String payload_sample = "16000";
	
	private String payload_format = "pcm16";

	private String payload_channel = "default";

	private String payload_replay = "default";
	
	private String audioToBase64 = null;

	private String string2;

	private String string3;

	//private String byteArrayToBase64 = null; 
	
//	String payload = "{\r\n" 
//					+ "  \"serviceData\": {\r\n" 
//					+ "    \"loggingId\": \""+payload_log+"\"\r\n"
//					+ "  },\r\n" 
//					+ "  \"userData\": {\r\n" 
//					+ "    \"identifier\": \""+payload_user+"\"\r\n" 
//					+ "  },\r\n"
//					+ "  \"processingInformation\": {\r\n" 
//					+ "    \"biometric\": {\r\n"
//					+ "      \"type\": \""+payload_biometric+"\",\r\n" 
//					+ "      \"mode\": \""+biometricType_TD_8+"\"\r\n"
//					+ "    },\r\n"
//					+ "    \"audioCharacteristics\": {\r\n" 
//					+ "      \"samplingRate\": \""+payload_sample+"\",\r\n"
//					+ "      \"format\": \""+payload_format+"\"\r\n" 
//					+ "    },\r\n" 
//					+ "    \"metaInformation\": [\r\n" 
//					+ "      {\r\n"
//					+ "        \"key\": \"usage-context\",\r\n" 
//					+ "        \"value\": {\r\n"
//					+ "          \"value\": \""+payload_channel+"\",\r\n" 
//					+ "          \"encrypted\": \"false\"\r\n" 
//					+ "        }\r\n"
//					+ "      },{\r\n" 
//					+ "        \"key\": \"detect-replay-v2-16k\",\r\n" 
//					+ "        \"value\": {\r\n"
//					+ "          \"value\": \"default\",\r\n" 
//					+ "          \"encrypted\": \""+payload_replay+"\"\r\n"
//					+ "        }\r\n"
//					+ "      }\r\n"
//					+ "    ]\r\n" 
//					+ "  },\r\n" 
//					+ "  \"audioInput\": {\r\n"
//					+ "    \"secondsThreshold\": \"0\",\r\n" 
//					+ "    \"audio\": {"+audioToBase64+"\r\n"
//					+ "    }\r\n" 
//					+ "  }\r\n" 
//					+ "}";
	
	


	public JTextField getUrlId() {
		return urlId;
	}


	public void setUrlId(JTextField urlId) {
		this.urlId = urlId;
	}


	public JTextField getLogId() {
		return logId;
	}


	public void setLogId(JTextField logId) {
		this.logId = logId;
	}


	public JTextField getUserId() {
		return userId;
	}


	public void setUserId(JTextField userId) {
		this.userId = userId;
	}


	public JComboBox getBiometricType() {
		return biometricType;
	}


	public void setBiometricType(JComboBox biometricType) {
		this.biometricType = biometricType;
	}


	public JComboBox getAudioFormat() {
		return audioFormat;
	}


	public void setAudioFormat(JComboBox audioFormat) {
		this.audioFormat = audioFormat;
	}


	public String getBiometricType_TD_8() {
		return biometricType_TD_8;
	}


	public void setBiometricType_TD_8(String biometricType_TD_8) {
		this.biometricType_TD_8 = biometricType_TD_8;
	}


	public String getBiometricType_TD_16() {
		return biometricType_TD_16;
	}


	public void setBiometricType_TD_16(String biometricType_TD_16) {
		this.biometricType_TD_16 = biometricType_TD_16;
	}


	public String getBiometricType_TI() {
		return biometricType_TI;
	}


	public void setBiometricType_TI(String biometricType_TI) {
		this.biometricType_TI = biometricType_TI;
	}


	public JComboBox getAudioSample() {
		return audioSample;
	}


	public void setAudioSample(JComboBox audioSample) {
		this.audioSample = audioSample;
	}


	public JComboBox getAudioGetType() {
		return audioGetType;
	}


	public void setAudioGetType(JComboBox audioGetType) {
		this.audioGetType = audioGetType;
	}


	public JComboBox getAudioReplay() {
		return audioReplay;
	}


	public void setAudioReplay(JComboBox audioReplay) {
		this.audioReplay = audioReplay;
	}


	public JTextField getAudioFileSelect() {
		return audioFileSelect;
	}


	public void setAudioFileSelect(JTextField audioFileSelect) {
		this.audioFileSelect = audioFileSelect;
	}


	public JButton getBUTTON_OPEN_FILE() {
		return BUTTON_OPEN_FILE;
	}


	public void setBUTTON_OPEN_FILE(JButton bUTTON_OPEN_FILE) {
		BUTTON_OPEN_FILE = bUTTON_OPEN_FILE;
	}


	public JButton getBUTTON_POST_SEND() {
		return BUTTON_POST_SEND;
	}


	public void setBUTTON_POST_SEND(JButton bUTTON_POST_SEND) {
		BUTTON_POST_SEND = bUTTON_POST_SEND;
	}


	public TextArea getPOST_RESULT() {
		return POST_RESULT;
	}


	public void setPOST_RESULT(TextArea pOST_RESULT) {
		POST_RESULT = pOST_RESULT;
	}




	public String getAudioToBase64() {
		return audioToBase64;
	}


	public void setAudioToBase64(String audioToBase64) {
		this.audioToBase64 = audioToBase64;
	}

	
	//------
	public String getPayload_url() {
		return payload_url;
	}


	public void setPayload_url(String payload_url) {
		this.payload_url = payload_url;
	}


	public String getPayload_log() {
		return payload_log;
	}


	public void setPayload_log(String payload_log) {
		this.payload_log = payload_log;
	}


	public String getPayload_user() {
		return payload_user;
	}


	public void setPayload_user(String payload_user) {
		this.payload_user = payload_user;
	}


	public String getPayload_biometric() {
		return payload_biometric;
	}


	public void setPayload_biometric(String payload_biometric) {
		this.payload_biometric = payload_biometric;
	}


	public String getPayload_format() {
		return payload_format;
	}


	public void setPayload_format(String payload_format) {
		this.payload_format = payload_format;
	}


	public String getPayload_sample() {
		return payload_sample;
	}


	public void setPayload_sample(String payload_sample) {
		this.payload_sample = payload_sample;
	}


	public String getPayload_channel() {
		return payload_channel;
	}


	public void setPayload_channel(String payload_channel) {
		this.payload_channel = payload_channel;
	}


	public String getPayload_replay() {
		return payload_replay;
	}


	public void setPayload_replay(String payload_replay) {
		this.payload_replay = payload_replay;
	}

	


	
	
	public SwingWindow() {
		//crate
		URL_ADDRESS = new JLabel("URL地址:");
		urlId = new JTextField();
		urlId.setPreferredSize(new Dimension(280, 30));
		//urlId.setText("http://192.168.18.216:8081/stdBiometricLite/enrolSpeaker");
		urlId.addMouseListener(this);
		
		LOG_ID = new JLabel("loggingId:");
		logId = new JTextField();
		logId.setPreferredSize(new Dimension(280, 30));
		//logId.setText("20181112");
		logId.addMouseListener(this);
		
		USER_ID = new JLabel("identifier:");
		userId = new JTextField();
		userId.setPreferredSize(new Dimension(280, 30));
		//userId.setText("20181112");
		userId.addMouseListener(this);
		
		BIOMETRIC_TYPE = new JLabel("模式选择");
		biometricType = new JComboBox();
		biometricType.setPreferredSize(new Dimension(200, 30));	
		biometricType.addItem(new String("文本无关"));
		biometricType.addItem(new String("文本相关"));
		biometricType.addMouseListener(this);
		
		
		AUDIO_FORMAT = new JLabel("音频格式:");
		audioFormat = new JComboBox();
		audioFormat.setPreferredSize(new Dimension(200,30));
		audioFormat.addItem(new String("pcm16"));
		audioFormat.addItem(new String("alaw"));
		audioFormat.addItem(new String("ulaw"));
		audioFormat.addMouseListener(this);
		
		
		AUDIO_SAMPLE = new JLabel("采样频率:");
		audioSample = new JComboBox();
		audioSample.setPreferredSize(new Dimension(200,30));
		audioSample.addItem(new String("16000"));
		audioSample.addItem(new String("8000"));
		audioSample.addMouseListener(this);
		
		
		AUDIO_GET_TYPE = new JLabel("采集渠道:");
		audioGetType = new JComboBox();
		audioGetType.setPreferredSize(new Dimension(200,30));
		audioGetType.addItem(new String("default"));
		audioGetType.addItem(new String("IVR"));
		audioGetType.addItem(new String("mobile"));
		audioGetType.addMouseListener(this);
		
		
		
//		AUDIO_REPLAY = new JLabel("录音检测:");
//		audioReplay = new JComboBox();
//		audioReplay.setPreferredSize(new Dimension(200,30));
//		audioReplay.addItem(new String("default"));
//		audioReplay.addItem(new String("false"));
//		audioReplay.addItem(new String("true"));
//		audioReplay.addMouseListener(this);
		
		
		
		AUDIO_FILE_SELECT = new JLabel("音频选择:");
		audioFileSelect = new JTextField();
		audioFileSelect.setPreferredSize(new Dimension(180, 30));
		BUTTON_OPEN_FILE = new JButton("打开文件");	
		BUTTON_OPEN_FILE.addMouseListener(this);
		
		
		
		BUTTON_POST_SEND = new JButton("   发   送   ");
		BUTTON_POST_SEND.setBackground(Color.red);
		BUTTON_POST_SEND.addMouseListener(this);
		
		
		POST_RESULT = new TextArea("",20,50);
		
		
		//add
		JPanel jPanel_url = new JPanel();
		jPanel_url.add(URL_ADDRESS);
		jPanel_url.add(urlId);
		
		
		
		JPanel jPanel_log = new JPanel();	
		jPanel_log.add(LOG_ID);
		jPanel_log.add(logId);

		
		
		JPanel jPanel_user_id = new JPanel();
		jPanel_user_id.add(USER_ID);
		jPanel_user_id.add(userId);
		
		
		JPanel jPanel_biometric = new JPanel();	
		jPanel_biometric.add(BIOMETRIC_TYPE);
		jPanel_biometric.add(biometricType);

		
		JPanel jPanel_format = new JPanel();	
		jPanel_format.add(AUDIO_FORMAT);
		jPanel_format.add(audioFormat);
		
		
		JPanel jPanel_sample = new JPanel();
		jPanel_sample.add(AUDIO_SAMPLE);
		jPanel_sample.add(audioSample);
		
		
		JPanel jPanel_get_type = new JPanel();
		jPanel_get_type.add(AUDIO_GET_TYPE);
		jPanel_get_type.add(audioGetType);
		
		
//		JPanel jPanel_replay = new JPanel();
//		jPanel_replay.add(AUDIO_REPLAY);
//		jPanel_replay.add(audioReplay);
		
		
		JPanel jPanel_open_file = new JPanel();
		jPanel_open_file.add(AUDIO_FILE_SELECT);
		jPanel_open_file.add(audioFileSelect);
		jPanel_open_file.add(BUTTON_OPEN_FILE);
		
		
		
		JPanel jPanel_post_send = new JPanel();
		jPanel_post_send.add(BUTTON_POST_SEND);
	
		
		JPanel jPanel_test_area = new JPanel();
		jPanel_test_area.add(POST_RESULT);
		
		


		//final
		this.setTitle("效生POST工具");
		this.setSize(450, 810);
		this.setLocation(200, 200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.getContentPane().add(jPanel_url);
		this.getContentPane().add(jPanel_log);
		this.getContentPane().add(jPanel_user_id);
		this.getContentPane().add(jPanel_biometric);
		this.getContentPane().add(jPanel_format);
		this.getContentPane().add(jPanel_sample);
		this.getContentPane().add(jPanel_get_type);
		//this.getContentPane().add(jPanel_replay);
		this.getContentPane().add(jPanel_open_file);
		this.getContentPane().add(jPanel_post_send);
		this.getContentPane().add(jPanel_test_area);
		
	}
	
	
	public static void main(String[] args) {
		SwingWindow testBorderLayout = new SwingWindow();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(BUTTON_OPEN_FILE)) {	
			doFileChoose();	
		}else if (e.getSource().equals(BUTTON_POST_SEND)) {
			
			
			String biometricType_ti_td = null;
			
			if (biometricType.getSelectedItem().toString() == "文本相关" && audioSample.getSelectedItem().toString() =="8000") {
				payload_biometric = "text-dependent";
				biometricType_ti_td ="td_fuse_8_atn_v2";
			}else if (biometricType.getSelectedItem().toString() =="文本相关" && audioSample.getSelectedItem().toString() =="16000") {
				payload_biometric = "text-dependent";
				biometricType_ti_td ="td_fuse_16_atn_v2";
			}else if (biometricType.getSelectedItem().toString() == "文本无关") {
				payload_biometric = "text-independent";
				biometricType_ti_td ="ti_plp2covv2";
				//System.out.println(biometricType_ti_td);
			}
			
			
			doPostRequest(logId.getText(),userId.getText(),biometricType.getSelectedItem().toString(),biometricType_ti_td,audioSample.getSelectedItem().toString(),audioFormat.getSelectedItem().toString(),audioGetType.getSelectedItem().toString(),audioToBase64);
			
		
		}else if (e.getSource().equals(urlId)) {	
			setPayload_url(urlId.getText());
		}else if (e.getSource().equals(logId)) {
			setPayload_log(logId.getText());		
		}else if (e.getSource().equals(userId)) {
			setPayload_user(userId.getText());
			//payload_user = userId.getText();
		}else if (e.getSource().equals(biometricType)) {
			
			if ((String) biometricType.getSelectedItem() == "文本相关") {
				setPayload_biometric("text-dependent");
			}else if((String) biometricType.getSelectedItem() == "文本无关") {
				setPayload_biometric("text-independent");
			}
			
		}else if (e.getSource().equals(audioFormat)) {
			setPayload_format((String) audioFormat.getSelectedItem());
		}else if (e.getSource().equals(audioSample)) {
			
			if ((String) biometricType.getSelectedItem() == "文本相关" && ( audioSample.getSelectedItem() == "8000")) {
				setPayload_sample("td_fuse_8_atn_v2");
				
			}else if((String) biometricType.getSelectedItem() == "文本相关" && ( audioSample.getSelectedItem() == "16000")) {
				setPayload_sample("td_fuse_16_atn_v2");
			}else if((String) biometricType.getSelectedItem() == "文本无关") {
				setPayload_sample("ti_plp2covv2");
			}
			
		}else if (e.getSource().equals(audioGetType)) {
			setPayload_channel((String) audioGetType.getSelectedItem());
			//payload_channel = (String) audioGetType.getSelectedItem();
		}else if (e.getSource().equals(audioReplay)) {
			setPayload_replay((String) audioReplay.getSelectedItem());
			//payload_replay = (String) audioReplay.getSelectedItem();
		}
		
	}
	
	
	
private void doPostRequest(String payload_log2,String payload_user2,String payload_biometric2,String biometricType_ti_td,String payload_sample2,String payload_format2,String payload_channel2,String audioToBase642) {

	if ((String) biometricType.getSelectedItem().toString() == "文本相关" && ( audioSample.getSelectedItem().toString() == "8000")) {
		setPayload_sample("td_fuse_8_atn_v2");
		
	}else if((String) biometricType.getSelectedItem().toString() == "文本相关" && ( audioSample.getSelectedItem().toString() == "16000")) {
		setPayload_sample("td_fuse_16_atn_v2");
	}else if((String) biometricType.getSelectedItem().toString() == "文本无关") {
		setPayload_sample("ti_plp2covv2");
	}
	
	
	
	
		for (String string : files) {
			System.out.println(string);
			// byte[] readAudioData = AudioUtils.readAudioData(string);

			// String fileNameWithOutExt = FileUtils.getFileNameWithOutExt(string);
			// String finalPath =audioFileSelect.getText()+"\\"+fileNameWithOutExt+".txt";
			// AudioUtils.saveToFile(finalPath,
			// AudioUtils.byteArrayToBase64(readAudioData));

			// byteArrayToBase64 = AudioUtils.byteArrayToBase64(readAudioData);

			
			String payload = "{\r\n" 
					+ "  \"serviceData\": {\r\n" 
					+ "    \"loggingId\": \""+payload_log2+"\"\r\n"
					+ "  },\r\n" 
					+ "  \"userData\": {\r\n" 
					+ "    \"identifier\": \""+payload_user2+"\"\r\n" 
					+ "  },\r\n"
					+ "  \"processingInformation\": {\r\n" 
					+ "    \"biometric\": {\r\n"
					+ "      \"type\": \""+payload_biometric2+"\",\r\n" 
					+ "      \"mode\": \""+biometricType_ti_td+"\"\r\n"
					+ "    },\r\n"
					+ "    \"audioCharacteristics\": {\r\n" 
					+ "      \"samplingRate\": \""+payload_sample2+"\",\r\n"
					+ "      \"format\": \""+payload_format2+"\"\r\n" 
					+ "    },\r\n" 
					+ "    \"metaInformation\": [\r\n" 
					+ "      {\r\n"
					+ "        \"key\": \"usage-context\",\r\n" 
					+ "        \"value\": {\r\n"
					+ "          \"value\": \""+payload_channel2+"\",\r\n" 
					+ "          \"encrypted\": \"false\"\r\n" 
					+ "        }\r\n"
					+ "      },{\r\n" 
					+ "        \"key\": \"detect-replay-v2-16k\",\r\n" 
					+ "        \"value\": {\r\n"
					+ "          \"value\": \"default\",\r\n" 
					+ "          \"encrypted\": \"false\"\r\n"
					+ "        }\r\n"
					+ "      }\r\n"
					+ "    ]\r\n" 
					+ "  },\r\n" 
					+ "  \"audioInput\": {\r\n"
					+ "    \"secondsThreshold\": \"0\",\r\n" 
					+ "    \"audio\": \""+audioToBase642+"\"\r\n"
					+ "    }\r\n" 
					+ "  }\r\n" 
					+ "}";
			
//				JSONArray fromObject = JSONArray.fromObject(payload);
//				string3 = fromObject.toString();
				
				
			
				String sendPost = sendPost(urlId.getText().toString(), payload);

				POST_RESULT.append(sendPost);
				POST_RESULT.append("\r\n");
				System.out.println("payload--zzz-->" + payload);
				
				
				
				
	
			System.out.println("url -->" + urlId.getText().toString());
	
	
		}
		
		

	}
	



	private void doFileChoose() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int result = fileChooser.showDialog(this, "请选择输出路径");
		System.out.println(result + "");
		if (result == JFileChooser.APPROVE_OPTION) {
			audioFileSelect.setText(fileChooser.getSelectedFile().getAbsolutePath());

			files = FileUtils.getFiles(audioFileSelect.getText(), true);

			System.out.println("file -->" + files);

			for (String string : files) {
				System.out.println("file -->" + string);

				byte[] readFile = AudioUtils.readFile(string, 0);

				audioToBase64 = new String(readFile);
				System.out.println(audioToBase64);

			}

		}

	}
	
	

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;

			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	
	
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(urlId)) {	
			setPayload_url(getUrlId().getText());	
			//System.out.print(setPayload_url(getUrlId().getText()));
		}
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
