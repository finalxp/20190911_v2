package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mapper.TextMapper;
import com.example.model.Text;
import com.example.service.IGetAllTextService;

import com.example.utils.ReturnResults;

@Service
public class GetAllTextService implements IGetAllTextService {

	@Autowired
	private TextMapper textMapper;

	@Override
	public String getAllText() {

		List<Text> allText = textMapper.getAllTextMapper();

		String string = null;
		if (allText.size() == 0) {

			return ReturnResults.ReReJson(0, string, "no data");
		} else {
			return ReturnResults.ReReJson(1, allText, string);
		}
	}
}
