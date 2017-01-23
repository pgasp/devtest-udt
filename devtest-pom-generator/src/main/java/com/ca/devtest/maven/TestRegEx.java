package com.ca.devtest.maven;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegEx {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Pattern pattern =Pattern.compile("-(.+?)jar");
		Pattern pattern =Pattern.compile(".*?([^-]+)*\\.jar$");
		String message="reactor-bus-2.0.3.RELEASE.jar";
		System.out.println(applyRegex(pattern, message));
	}
	
	/**
	 * @param pattern
	 * @param message
	 * @return group 1 result on pattern appied on message
	 */
	private static String applyRegex(Pattern pattern, String message) {
		Matcher matcher = pattern.matcher(message);
		String result = "";
		if (matcher.find()) {
			result = matcher.group(1);
		}
		return result;

	}


}
