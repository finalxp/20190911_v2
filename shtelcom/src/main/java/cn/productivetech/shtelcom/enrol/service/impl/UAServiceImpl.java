package cn.productivetech.shtelcom.enrol.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.productivetech.shtelcom.enrol.service.IUAService;
import cn.productivetech.shtelcom.enrol.uarest.core.Speaker;
import cn.productivetech.shtelcom.enrol.uarest.core.UAException;

@Service
public class UAServiceImpl implements IUAService {

	@Autowired
	Speaker speaker;

	@Override
	public void enrolSpeaker(String userId, byte[] data, int times) throws UAException {
		if (times == 0) {
			speaker.enrolSpeaker(userId, data);
		} else {
			speaker.updateSpeaker(userId, data);
		}
	}

}
