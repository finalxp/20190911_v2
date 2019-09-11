package com.example.utils;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		/*
		 * String ss = "{ " + " \"errorData\":{" + " \"code\":\"103\"," +
		 * " \"description\":\"errorProcessingBiometric\"," + "\"info\":\"\"" +
		 * "}" + "}"; System.out.println(ss); Map<String, Object> map =
		 * JSONObject.fromObject(ss);
		 * 
		 * System.out.println(map.get("result"));
		 */

		Encryption enDeCryption = new Encryption();
		String deSencode = enDeCryption.DESencode("kkkkkkdgjdfoijaldsfj;asjdf;lasjdlgjsadjfldsjafldjskgjds", "validsoft");
		System.out.println(deSencode);
		String deSdecode = enDeCryption.DESdecode("59A40CF73004F6588289C456B6FEE70C0FE7C3109FF07B7229B1EDBEC011F718E5D821388DD7C089CF38CFECAA63F78E2D3C066A2622E7BF", "validsoft");
		System.out.println(deSdecode);

	}
}
