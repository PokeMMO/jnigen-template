package demo;

import java.io.IOException;

import com.badlogic.gdx.utils.SharedLibraryLoader;

public class JnigenDemoTest
{
	public static void main(String[] args) throws IOException
	{
		new SharedLibraryLoader().load(JnigenDemoBuild.SHARED_LIBRARY_NAME);
		
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
		
		if(error)
			System.exit(1);
	}
}