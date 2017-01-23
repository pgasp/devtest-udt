/**
 * 
 */
package com.ca.devtest.maven;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author gaspa03
 *
 */
public class Dependency {

	private String name;
	private String location;
	private String version;
	private File jar;
	private String devTestHome;
	private final static Pattern VERSIONPATTERN = Pattern.compile(".*?([^-]+)*\\.jar$");

	/**
	 * 
	 */
	public Dependency(String devTestHome, File jar) {
		this.jar = jar;
		this.devTestHome = devTestHome;
		version = applyRegex(VERSIONPATTERN, jar.getName());

	}

	public String getVersion() {

		return version;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return StringUtils.replace(jar.getName(), "-" + version + ".jar", "");
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return StringUtils.replace(jar.getAbsolutePath(), devTestHome, "");
	}

	/**
	 * @return the location
	 */
	public String getGroupID() {
		return jar.getParentFile().getName();
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
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

	@Override
	public int hashCode() {
		return getName().hashCode()+getGroupID().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Dependency)
			return ((Dependency) obj).getName().equals(getName())&((Dependency) obj).getGroupID().equals(getGroupID());
		else
			return false;
	}
}
