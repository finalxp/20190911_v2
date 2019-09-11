package ValidSoft;

public class AudioToBase64 {
	public static void main(String[] args) {
		AudioToBase64("C:\\Users\\leoli\\Desktop\\testbestpay.pcm","C:\\Users\\leoli\\Desktop\\testbestpay2.txt");
	}

	private static void AudioToBase64(String inPath,String outPath) {
		byte[] readAudioData = AudioUtils.readAudioData(inPath);
		
		String byteArrayToBase64 = AudioUtils.byteArrayToBase64(readAudioData).replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		
		System.out.println(byteArrayToBase64);
		
		AudioUtils.saveToFile(outPath, byteArrayToBase64);
		
		System.out.println("done");
	}
} 
