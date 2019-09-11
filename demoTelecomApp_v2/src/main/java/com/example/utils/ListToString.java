package com.example.utils;

import java.util.List;

public class ListToString {
	public static String listToString(List<String> mList) {
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
            if (i < mListArray.length - 1) {
                convertedListStr += mListArray[i] + ",";
            } else {
                convertedListStr += mListArray[i];
            }
            }
            return convertedListStr;
        } else return "List is null!!!";
    }

    /**
    * for jdk <= java 7
    * @param mList
    * @return
    */
    // 采用Stringbuilder.append()的方式追加
    public static String listToString2(List<String> mList) {
        final String SEPARATOR = ",";
        // mList = Arrays.asList("AAA", "BBB", "CCC");
        StringBuilder sb = new StringBuilder();
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            for (String item : mList) {
                sb.append(item);
                sb.append(SEPARATOR);
            }
            convertedListStr = sb.toString();
            convertedListStr = convertedListStr.substring(0, convertedListStr.length()
            - SEPARATOR.length());
            return convertedListStr;
        } else return "List is null!!!";
    }
}

