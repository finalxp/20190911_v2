package cn.xs.erp.util;

import java.util.Random;

public class RandomUtil {

	public static char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7',
			'8', '9' };

	public static String getRandomNumber(int count) {
		StringBuilder sb = new StringBuilder();

		Random random = new Random(System.currentTimeMillis());
		while (sb.length() < count) {
			int index = random.nextInt(chars.length);
			sb.append(chars[index]);

		}
		return sb.toString();
	}
}
