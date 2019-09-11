package com.pccc.vprs.servicedisplay.vprs.audio.service;

import com.pccc.vprs.servicedisplay.vprs.audio.service.audioService;

public class AudioFactory {
	public static audioService getInstance(String ClassName) {
		 audioService f = null;
	        try {
	            f = (audioService) Class.forName(ClassName).newInstance();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return f;
	    }
}
