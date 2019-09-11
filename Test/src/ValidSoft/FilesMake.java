package ValidSoft;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public class FilesMake {
	public static void main(String[] args) throws IOException {
		String s1 = "validsoft_";
		String s2 = "_0123456789_";
		String s3 = "en_";
		String s4 = "_vr";
		String s8 = "01";
		String s9 = "02";
		String s10 = "03";

		DecimalFormat decimalFormat = new DecimalFormat("000");

		FileWriter fileWriterTD = new FileWriter("D:\\TD.txt");

		FileWriter fileWriterTI = new FileWriter("D:\\TI.txt");

		TDFileName(s1, s2, s3, s4, s8, s9, s10, decimalFormat, fileWriterTD);

		TIFileName(s1, s4, decimalFormat, fileWriterTI);
	}

	private static void TIFileName(String s1, String s4, DecimalFormat decimalFormat, FileWriter fileWriter)
			throws IOException {
		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0) {

					fileWriter.write(s1 + decimalFormat.format(i + 1) + s4 + "\r\n");
					fileWriter.flush();
				} else if (j == 1) {
					fileWriter.write(s1 + decimalFormat.format(i + 1) + "_en" + "\r\n");
					fileWriter.flush();
				}
			}
		}
		fileWriter.close();
	}

	@SuppressWarnings("unused")
	private static void TDFileName(String s1, String s2, String s3, String s4, String s8, String s9, String s10,
			DecimalFormat decimalFormat, FileWriter fileWriter) throws IOException {
		Random random = new Random();

		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < 4; j++) {

				if (j == 0) {

					int nextInt = random.nextInt(88888888);
					int nextInt2 = nextInt + 11111111;

					fileWriter.write(s1 + decimalFormat.format(i + 1) + "_" + nextInt2 + "_" + s4 + "\r\n");
					fileWriter.flush();
				} else if (j == 1) {
					fileWriter.write(s1 + decimalFormat.format(i + 1) + s2 + s3 + s8 + "\r\n");
					fileWriter.flush();
				} else if (j == 2) {
					fileWriter.write(s1 + decimalFormat.format(i + 1) + s2 + s3 + s9 + "\r\n");
					fileWriter.flush();
				} else if (j == 3) {
					fileWriter.write(s1 + decimalFormat.format(i + 1) + s2 + s3 + s10 + "\r\n");
					fileWriter.flush();
				}

			}

		}
		fileWriter.close();
	}
}
