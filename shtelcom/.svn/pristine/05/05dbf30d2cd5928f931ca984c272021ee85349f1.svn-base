package cn.productivetech.shtelcom.enrol.uarest.payload;

public class AudioInput {

	public String	secondsThreshold;
	public Audio	audio;
	public String	stream;

	public AudioInput() {
		this(null);
	}

	public AudioInput(String base64) {
		this.secondsThreshold = "0";
		this.audio = new Audio(base64);
		this.stream = null;
	}

	public String getSecondsThreshold() {
		return secondsThreshold;
	}

	public void setSecondsThreshold(String secondsThreshold) {
		this.secondsThreshold = secondsThreshold;
	}

	public Audio getAudio() {
		return audio;
	}

	public void setAudio(Audio audio) {
		this.audio = audio;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

}
