package ValidSoft;

import java.util.List;

import ValidSoft.utils.FileUtils;

public class Test0624 {
	private void mian() {
		
		List<String> filesAudios = FileUtils.getFiles("W:\\audio\\record_20190525");	
		List<String> filesResult = FileUtils.getFiles("W:\\audio\\record_20190525_测试结果\\ti_plp2covv2");
		for (String audio : filesAudios) {
			//String audio;
			String fileNameWithOutExtAudio = FileUtils.getFileNameWithOutExt(audio);
			if (!filesResult.contains(fileNameWithOutExtAudio)) {
				FileUtils.copy(audio, "W:\\audio\\test001"+fileNameWithOutExtAudio);
			}
			
			
			for (String result : filesResult) {
				//String fileNameWithOutExtResult= FileUtils.getFileNameWithOutExt(string);
				
			}
		}
		
	}
}
