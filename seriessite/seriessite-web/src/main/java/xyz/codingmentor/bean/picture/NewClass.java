/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.codingmentor.bean.picture;

/**
 *
 * @author basstik
 */
public class NewClass {
    private static String OS = System.getProperty("os.name").toLowerCase();
	
	public String fg() {
		
		System.out.println(OS);
		
		if (isWindows()) {
			return "This is Windows";
		} else if (isMac()) {
			return  "This is Mac";
		} else if (isUnix()) {
			return  "This is Unix or Linux";
		} else if (isSolaris()) {
			return "This is Solaris";
		} else {
			return  "Your OS is not support!!";
		}
	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

}
