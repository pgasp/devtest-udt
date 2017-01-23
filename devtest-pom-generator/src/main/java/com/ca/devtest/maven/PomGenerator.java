/**
 * 
 */
package com.ca.devtest.maven;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * @author gaspa03
 *
 */
public class PomGenerator {
	private final static String CORE_JAR_FILE = "lisa-core-";
	private final static String CORE_JAR = "lisa-core-";
	private final static String LIB_EXTENSION = "jar";
	private final static String LIB_FOLDER = "lib";
	private final static String KEY_ARGS_DEVTESTHOME="--devtestHome=";
	private final static String KEY_ARGS_TAGET="--target=";
	private final static String DEVTEST_GROUPEID="com.ca.devtest";
	private final static String DEVTEST_ARTIFACTID="devtest-libs";
	
	private final static Pattern VERSION = Pattern.compile(CORE_JAR_FILE
			+ "(.+?)\\.jar");
	private final static IOFileFilter CORE_JAR_FILE_FILTER = new WildcardFileFilter(
			CORE_JAR_FILE + "*." + LIB_EXTENSION);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		PomGenerator pomGen = new PomGenerator();
		
		String devTestHome = getDevTestHome(args);
		String target = getTargetFolder(args);
		if(null==devTestHome||null==target)
			usage();
		if(null==devTestHome)
			throw new Exception("Error, devTestHome folder porperty is mandatory ");
		if(null==target)
				throw new Exception("Error, target folder porperty is mandatory ");
		pomGen.generate(devTestHome,target);
	}

	private static void usage() {
		System.out.println("--devtestHome\t(Mandatory)\tDefine DevTestHome");
		System.out.println("--target\t(Mandatory)\tDefine target folder");
	}

	private static String getTargetFolder(String[] args) {
		return getArgStartWith(KEY_ARGS_TAGET, args);
	}

	

	private static String getDevTestHome(String[] args) {
		return getArgStartWith(KEY_ARGS_DEVTESTHOME, args);
		
	}

	private static String getArgStartWith(String keyArg, String[] args) {
	
		String result=null;
		for (String arg : args) {
			if(arg.startsWith(keyArg)){
				result=arg.replace(keyArg, "");
			}
		}
		return result;
	}
	private void generate(String devTestHome, String target) {
		File devTestHomeFile = new File(devTestHome, LIB_FOLDER);
		String version = getVersion(devTestHomeFile);

		Collection<File> jars = FileUtils.listFiles(devTestHomeFile,
				new String[] { LIB_EXTENSION }, true);
		Set<Dependency> dependencies = new HashSet<Dependency>();
		Dependency dependency = null;
		// create dependencies
		for (File jar : jars) {
			dependency = new Dependency(devTestHome,jar);
			dependencies.add(dependency);
		}
		String pom=generatePom(version,dependencies );
		if(null!=target){
			try {
				File pomFile=new File(target,"devtest-pom.xml");
				FileUtils.write(pomFile, pom);
				System.out.println("Vous pouvez déployer le pom via la commande suivante:");
				System.out.println("mvn clean deploy -f "+pomFile+" -DaltDeploymentRepository=<urlOfRepository>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
 
	

	private String generatePom(String version, Set<Dependency> dependencies) {

		/*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        
        /*  next, get the Template  */
        Template t = ve.getTemplate( "pom.vm" );
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("devtestVersion", version);
        context.put("devtestArtifactId", DEVTEST_ARTIFACTID);
        context.put("devtestGroupId", DEVTEST_GROUPEID);
        context.put("dependencies", dependencies);
        
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
      return writer.toString() ; 
		
	}

	private String getVersion(File devTestHomeLib) {

		Collection<File> jars = FileUtils.listFiles(devTestHomeLib,
				CORE_JAR_FILE_FILTER, TrueFileFilter.INSTANCE);
		String version = null;
		for (File file : jars) {
			version = applyRegex(VERSION, file.getName());
			if (null != version)
				break;
		}
		return version;
	}

	/**
	 * @param pattern
	 * @param message
	 * @return group 1 result on pattern appied on message
	 */
	private String applyRegex(Pattern pattern, String message) {
		Matcher matcher = pattern.matcher(message);
		String result = null;
		if (matcher.find()) {
			result = matcher.group(1);
		}
		return result;

	}
}
