package cn.productivetech.shtelcom.enrol.uarest.core;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.productivetech.shtelcom.enrol.uarest.payload.MetaInformation;
import cn.productivetech.shtelcom.enrol.uarest.payload.MetaInformationValue;
import cn.productivetech.shtelcom.enrol.uarest.payload.Payload;
import cn.productivetech.shtelcom.enrol.uarest.response.RspDelete;
import cn.productivetech.shtelcom.enrol.uarest.response.RspEnrol;
import cn.productivetech.shtelcom.enrol.uarest.response.RspIsEnrolled;
import cn.productivetech.shtelcom.enrol.uarest.response.RspVerify;
import cn.productivetech.shtelcom.enrol.utils.HttpUtil;
import cn.productivetech.shtelcom.enrol.utils.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 声纹验证类
 * 
 * @author brain
 *
 */
@Component
public class Speaker {

	@Value("${ua.threshold:1.2}")
	private float threshold;

	@Value("${ua.threshold.replay:4.8}")
	private float thresholdReplay;

	@Value("${ua.snr:12}")
	private String snr;

	@Value("${ua.speech:120}")
	private String speech;

	@Value("${ua.sec:0}")
	private float sec;

	@Value("${ua.addr:http://127.0.0.1:4567/stdBiometric/}")
	private String addr;

	@Autowired
	private ObjectMapper mapper;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private AudioFormat audioFormat;
	private UABiometric biometric;

	public Speaker() {
		audioFormat = new AudioFormat();
		audioFormat.setFormat(AudioFormat.AUDIO_FORMAT_PCM);
		audioFormat.setSamplingRate(AudioFormat.AUDIO_SAMPLINGRATE_16K);

		biometric = new UABiometric();
		biometric.setBiometricType(UABiometric.BIOMETRIC_TYPE_TD);
		biometric.setBiometricMode(UABiometric.BIOMETRIC_TYPE_TD_MODE_16K);
		biometric.setDetectReplayMode(UABiometric.DETECT_REPLAY_MODE_16K);
	}

	/**
	 * 获取音频格式
	 * 
	 * @return 音频格式
	 */
	public AudioFormat getAudioFormat() {
		return audioFormat;
	}

	/**
	 * 设置音频格式
	 * 
	 * @param audioFormat
	 *            音频格式
	 */
	public void setAudioFormat(AudioFormat audioFormat) {
		this.audioFormat = audioFormat;
	}

	/**
	 * 获取引擎信息
	 * 
	 * @return 引擎信息
	 */
	public UABiometric getBiometric() {
		return biometric;
	}

	/**
	 * 设置引擎信息
	 * 
	 * @param biometric
	 *            引擎信息
	 */
	public void setBiometric(UABiometric biometric) {
		this.biometric = biometric;
	}

	/**
	 * 创建报文
	 * 
	 * @param userId
	 *            用户ID
	 * @param data
	 *            音频数据
	 * @return 报文
	 */
	protected Payload CreatePayLoad(String userId, byte[] data) {

		Payload payLoad = new Payload();
		payLoad.getServiceData().setLoggingId(UUID.randomUUID().toString());
		payLoad.getProcessingInformation().getAudioCharacteristics()
				.setSamplingRate(String.valueOf(audioFormat.getSamplingRate()));
		payLoad.getProcessingInformation().getAudioCharacteristics()
				.setFormat(audioFormat.getFormat());
		payLoad.getProcessingInformation().getBiometric().setMode(biometric.getBiometricMode());
		payLoad.getProcessingInformation().getBiometric().setType(biometric.getBiometricType());
		payLoad.getUserData().setIdentifier(userId);
		if (data != null) {
			payLoad.getAudioInput().getAudio().setBase64(Utils.byteArrayToBase64(data));
			payLoad.getAudioInput().setSecondsThreshold(String.valueOf(sec));
		} else {
			payLoad.setAudioInput(null);
		}
		return payLoad;
	}

	public void enrolSpeaker(String userId, byte[] audioData) throws UAException {
		enrolSpeaker(userId, audioData, true);
	}

	public void enrolSpeaker(String userId, byte[] audioData, boolean checkAudio)
			throws UAException {

		Payload enrolPayload = CreatePayLoad(userId, audioData);
		if (checkAudio) {
			MetaInformation metaSnr = new MetaInformation();
			metaSnr.setKey(UABiometric.GET_SNR);
			MetaInformationValue metaSnrValue = new MetaInformationValue();
			metaSnrValue.setValue(snr);
			metaSnr.setValue(metaSnrValue);
			enrolPayload.getProcessingInformation().getMetaInformation().add(metaSnr);

			MetaInformation metaSpeech = new MetaInformation();
			metaSpeech.setKey(UABiometric.DETECT_SPEECH);
			MetaInformationValue metaSpeechValue = new MetaInformationValue();
			metaSpeechValue.setValue(speech);
			metaSpeech.setValue(metaSpeechValue);
			enrolPayload.getProcessingInformation().getMetaInformation().add(metaSpeech);
		}
		RspEnrol requestResult = getResult(addr + Config.enrolURL, toJson(enrolPayload),
				RspEnrol.class);

		if (!requestResult.isSuccess()) {
			throw new UAException(requestResult.getErrorData().getCode(), requestResult
					.getErrorData().getDescription());
		}
	}

	public void updateSpeaker(String userId, byte[] audioData) throws UAException {
		updateSpeaker(userId, audioData, true);
	}

	public void updateSpeaker(String userId, byte[] audioData, boolean checkAudio)
			throws UAException {

		Payload updatePayload = CreatePayLoad(userId, audioData);
		if (checkAudio) {
			MetaInformation metaSnr = new MetaInformation();
			metaSnr.setKey(UABiometric.GET_SNR);
			MetaInformationValue metaSnrValue = new MetaInformationValue();
			metaSnrValue.setValue(snr);
			metaSnr.setValue(metaSnrValue);
			updatePayload.getProcessingInformation().getMetaInformation().add(metaSnr);

			MetaInformation metaSpeech = new MetaInformation();
			metaSpeech.setKey(UABiometric.DETECT_SPEECH);
			MetaInformationValue metaSpeechValue = new MetaInformationValue();
			metaSpeechValue.setValue(speech);
			metaSpeech.setValue(metaSpeechValue);
			updatePayload.getProcessingInformation().getMetaInformation().add(metaSpeech);
		}

		RspEnrol requestResult = getResult(addr + Config.updateURL, toJson(updatePayload),
				RspEnrol.class);

		if (!requestResult.isSuccess()) {
			throw new UAException(requestResult.getErrorData().getCode(), requestResult
					.getErrorData().getDescription());
		}
	}

	/**
	 * 删除用户
	 * 
	 * @throws Exception
	 *             报文不合法则会抛出异常
	 */
	public void deleteSpeaker(String userId) throws UAException {

		Payload deletePayload = CreatePayLoad(userId, null);

		RspDelete requestResult = getResult(addr + Config.deleteURL, toJson(deletePayload),
				RspDelete.class);
		if (!requestResult.isSuccess()) {
			throw new UAException(requestResult.getErrorData().getCode(), requestResult
					.getErrorData().getDescription());
		}
	}

	/**
	 * 查询账户是否注册
	 * 
	 * @param enrolNumbers
	 *            需要最小注册的次数。 在用户未达到注册次数而关闭注册页面的情况下，后台有数据， 但是注册信息不完整,这种情况需要重新注册
	 * @return true为已注册,其他为未注册
	 * @throws Exception
	 */
	public boolean isSpeakerEnrolled(int enrolNumbers, String userId) throws UAException {

		int times = isSpeakerEnrolled(userId);

		return (times == -1 || times < enrolNumbers - 1) ? false : true;
	}

	public int isSpeakerEnrolled(String userId) throws UAException {

		Payload queryPayload = CreatePayLoad(userId, null);

		RspIsEnrolled rspResult = getResult(addr + Config.isSpeakerEnrolled, toJson(queryPayload),
				RspIsEnrolled.class);

		if (!rspResult.isSuccess()) {
			throw new UAException(rspResult.getErrorData().getCode(), rspResult.getErrorData()
					.getDescription());
		}
		if (!rspResult.isExist())
			return -1;

		String numberOfUpdate = rspResult.getNumberOfUpdate(biometric.getBiometricType());
		if (numberOfUpdate == null)
			return -1;
		return Integer.parseInt(numberOfUpdate);
	}

	public boolean verifySpeaker(String userId, byte[] audioData) throws UAException {

		Payload verifyPayload = CreatePayLoad(userId, audioData);

		if (biometric.getDetectReplayMode() != null) {
			MetaInformation info = new MetaInformation();
			MetaInformationValue metaValue = new MetaInformationValue();
			info.setKey(biometric.getDetectReplayMode());
			info.setValue(metaValue);
			verifyPayload.getProcessingInformation().getMetaInformation().add(info);
		}
		RspVerify requestResult = getResult(addr + Config.verifyURL, toJson(verifyPayload),
				RspVerify.class);

		if (!requestResult.isSuccess()) {
			throw new UAException(requestResult.getErrorData().getCode(), requestResult
					.getErrorData().getDescription());
		}
		String score = requestResult.getResult().getScore();
		String replayScore = requestResult.getResult()
				.getMetaInfoScore(biometric.getDetectReplayMode()).split(",")[0];
		float fsc = Float.parseFloat(score);
		float frsc = Float.parseFloat(replayScore);

		if (Float.isNaN(fsc) || Float.isNaN(frsc))
			return false;
		return fsc >= threshold && frsc >= thresholdReplay;
	}

	protected String toJson(Object value) throws UAException {
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			logger.error("JSON", e);
			throw new UAException("-1", "json format error");
		}
	}

	protected <T> T getResult(String url, String data, Class<T> cls) throws UAException {

		try (Response response = HttpUtil.postRequest(url, data)) {

			if (response.isSuccessful()) {
				String result = response.body().string();
				logger.info(result);
				return mapper.readValue(result, cls);
			}
			throw new UAException(response.code() + "", "声纹服务不可用, code=" + response.code());

		} catch (IOException e) {
			logger.error("HTTP", e);
			throw new UAException("500", "声纹服务不可用");
		}
	}
}
