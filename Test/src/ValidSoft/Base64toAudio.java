package ValidSoft;

import java.io.File;

public class Base64toAudio {
	public static void main(String[] args) {
		
		String input ="C:\\Users\\leoli\\Desktop\\testbestpay.txt";
		String output ="C:\\Users\\leoli\\Desktop\\testbestpay3.pcm";

		Base64toAudio(input,output);
	}

	private static void Base64toAudio(String inPath,String outPath) {
		
		File file = new File(inPath);	
		
		String readFile = AudioUtils.readFile(file);
		
		byte[] readAudioData = AudioUtils.readAudioData("path");
		
		byte[] base64ToByteArray = AudioUtils.base64ToByteArray(readFile);
		
		AudioUtils.saveBinaryFile(outPath, base64ToByteArray);
		
		System.out.println("done");
	}
}
