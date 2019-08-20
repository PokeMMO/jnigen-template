package demo;

import java.io.IOException;

import com.badlogic.gdx.utils.SharedLibraryLoader;

public class JnigenDemoTest
{
	public static void main(String[] args) throws IOException
	{
		new SharedLibraryLoader().load(args[0]);

		boolean error = false;

		if(JnigenDemo.API_LEVEL != JnigenDemo.getApiLevel())
		{
			System.out.println("Mismatched API level.");
			error = true;
		}
		else
		{
			System.out.println("Matched API level.");
		}

		if(JnigenDemo.getSimpleMath(1) != 2)
		{
			System.out.println("Simple math test failed");
			error = true;
		}
		else
		{
			System.out.println("Simple math test passed");
		}

		int crand = JnigenDemo.getCRand(10);
		if(crand < 0 || crand > 9)
		{
			System.out.println("CRand test failed");
			error = true;
		}
		else
		{
			System.out.println("CRand test passed: "+ crand);
		}

		long addr = JnigenDemo.getCPPTest();
		JnigenDemo.setCPPTestValue(addr, crand+1);

		if(JnigenDemo.getCPPTestValue(addr) != crand+1)
		{
			System.out.println("CPPTestValue test failed");
			error = true;
		}
		else
		{
			System.out.println("CPPTestValue test passed: "+ (crand+1));
		}

		if(error)
			System.exit(1);
	}
}