package demo;

public class JnigenDemo {
	// @off
	/*JNI

	#include <stdio.h>
	extern "C" {
		#include "testc.h"
	}
	
	#include "testcpp.h"
	
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
	
	public static native int getCRand(int n); /*
	{
		return getCRand(n);
	}
	*/
	
	public static native long getCPPTest(); /*
	{
		return (jlong)new TestCPP();
	}
	*/
	
	public static native long getCPPTestValue(long addr); /*
	{
		return ((TestCPP*)addr)->getValue();
	}
	*/
	
	public static native void setCPPTestValue(long addr, int value); /*
	{
		((TestCPP*)addr)->value = value;
	}
	*/
}
