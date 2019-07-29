package demo;

public class JnigenDemo {
	// @off
	/*JNI

	#include <stdio.h>
	
	*/
	
	public static int API_LEVEL = 1;
	
	/**
	 * Gets the api level baked into the native binary.<br/>
	 * Useful to make sure the java and natives are in sync. 
	 * @return int
	 */
	public static native int getApiLevel(); /*
	{
		return 1;
	}
	*/
	
	public static native int getSimpleMath(int a); /*
	{
		return 1 + a;
	}
	*/
}
