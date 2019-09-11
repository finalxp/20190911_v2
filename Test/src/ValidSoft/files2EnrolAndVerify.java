package ValidSoft;

import java.io.File;

import ValidSoft.utils.Base64Utils;








public class files2EnrolAndVerify {
	
	public static void main(String[] args) {
		System.out.println("ddddd");
		Base64Utils utils = Base64Utils.getInstance();
		System.out.println("ddddd");
        String str = utils.file2Base64(new File("Z:\\test2\\lxp16k.wav"));
        System.out.println("ddddd");
        System.out.println(str);
	}
	/*private void mian() {
		Base64Utils utils = Base64Utils.getInstance();
        String str = utils.file2Base64(new File("Z:\\test2\\lxp16k.wav"));
        System.out.println(str);
		List<String> files = FileUtils.getFiles(inputPath);
		public void testJpg2Base64(){
	        Base64Utils utils = Base64Utils.getInstance();
	        String str = utils.file2Base64(new File("D:/demo_identificate.png"));
	        System.out.println(str);

	    }
	    @Test
	    public void testBase642File(){

	        Base64Utils utils = Base64Utils.getInstance();
	        String str = utils.file2Base64(new File("D:/demo_identificate.png"));

	        utils.base64ToFile(str, new File("D://xx.jpg"));

	    }*/

	
	
}
