package demo;

import java.io.File;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.jnigen.AntScriptGenerator;
import com.badlogic.gdx.jnigen.BuildConfig;
import com.badlogic.gdx.jnigen.BuildExecutor;
import com.badlogic.gdx.jnigen.BuildTarget;
import com.badlogic.gdx.jnigen.BuildTarget.TargetOs;
import com.badlogic.gdx.jnigen.NativeCodeGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoader;

public class JnigenDemoBuild 
{
	public static final String SHARED_LIBRARY_NAME = "demo";
	private static boolean CROSSCOMPILE_MACOS = false;
	
	static public void main (String[] args) throws Exception
	{
		String[] headers = {"src"};
		String[] cppIncludes = {"*.cpp", "src/*.cpp"};
		String[] cIncludes = {};
		
		NativeCodeGenerator jnigen = new NativeCodeGenerator();
		jnigen.generate("src/main/java", "build/classes/java/main:build/classes/java/test", "jni");

		BuildTarget win32 = BuildTarget.newDefaultTarget(TargetOs.Windows, false);
		win32.cppIncludes = cppIncludes;
		win32.headerDirs = headers;
		win32.cFlags += " -fvisibility=hidden ";
		win32.cppFlags += " -fvisibility=hidden ";
		win32.linkerFlags += " -fvisibility=hidden ";
		win32.cIncludes = cIncludes;
		BuildTarget win64 = BuildTarget.newDefaultTarget(TargetOs.Windows, true);
		win64.cppIncludes = cppIncludes;
		win64.headerDirs = headers;
		win64.cFlags += " -fvisibility=hidden ";
		win64.cppFlags += " -fvisibility=hidden ";
		win64.linkerFlags += " -fvisibility=hidden ";
		win64.cIncludes = cIncludes;
		
		BuildTarget linux32 = BuildTarget.newDefaultTarget(TargetOs.Linux, false);
		linux32.cppIncludes = cppIncludes;
		linux32.headerDirs = headers;
		linux32.cFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		linux32.cppFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		linux32.linkerFlags += " -Wl,--gc-sections -fvisibility=hidden ";
		linux32.cIncludes = cIncludes;
		BuildTarget linux64 = BuildTarget.newDefaultTarget(TargetOs.Linux, true);
		linux64.cppIncludes = cppIncludes;
		linux64.headerDirs = headers;
		linux64.cFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		linux64.cppFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		linux64.linkerFlags += " -Wl,--gc-sections -fvisibility=hidden ";
		linux64.cIncludes = cIncludes;
		
		//Arm PR not yet merged in libgdx
		/*BuildTarget linuxarm32 = BuildTarget.newDefaultTarget(TargetOs.Linux, false, true);
		linuxarm32.cppIncludes = cppIncludes;
		linuxarm32.headerDirs = headers;
		linuxarm32.cppFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		linuxarm32.linkerFlags += " -Wl,--gc-sections -fvisibility=hidden ";
		linuxarm32.cIncludes = cIncludes;
		BuildTarget linuxarm64 = BuildTarget.newDefaultTarget(TargetOs.Linux, true, true);
		linuxarm64.cppIncludes = cppIncludes;
		linuxarm64.headerDirs = headers;
		linuxarm64.cppFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		linuxarm64.linkerFlags += " -Wl,--gc-sections -fvisibility=hidden ";
		linuxarm64.cIncludes = cIncludes;*/
		
		BuildTarget mac = BuildTarget.newDefaultTarget(TargetOs.MacOsX, true);
		if(CROSSCOMPILE_MACOS)
			mac.compilerPrefix = "x86_64-apple-darwin15-";
		mac.cppIncludes = cppIncludes;
		mac.headerDirs = headers;
		mac.cppFlags += " -fvisibility=hidden ";
		mac.linkerFlags += " -fvisibility=hidden ";
		mac.cIncludes = cIncludes;
		
		BuildTarget android = BuildTarget.newDefaultTarget(TargetOs.Android, false);
		android.cppIncludes = cppIncludes;
		android.headerDirs = headers;
		android.cFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		android.cppFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		android.linkerFlags += " -fvisibility=hidden ";
		
		BuildTarget ios = BuildTarget.newDefaultTarget(TargetOs.IOS, false);
		ios.cppIncludes = cppIncludes;
		ios.headerDirs = headers;
		ios.cFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";
		ios.cppFlags += " -fvisibility=hidden -ffunction-sections -fdata-sections ";

		BuildConfig config = new BuildConfig(SHARED_LIBRARY_NAME);

		new AntScriptGenerator().generate(config, linux32, linux64, /*linuxarm32, linuxarm64,*/ win32, win64, mac, android, ios);
		
		//Optional tweaks to Android build configurations:
		//Optional, see https://developer.android.com/ndk/guides/cpp-support
		//new FileHandle(new File("jni/Application.mk")).writeString("\nAPP_STL := c++_static\n", true);
		//Optional, useful for removing unnecessary code from final output binaries
		new FileHandle(new File("jni/Android.mk")).writeString("\nLOCAL_LDFLAGS += -Wl,--gc-sections\n", true);

		boolean success = true;
		if(SharedLibraryLoader.isMac)
		{
			success &= BuildExecutor.executeAnt("jni/build-macosx64.xml", "-v", "-Drelease=true", "clean", "postcompile");
			success &= BuildExecutor.executeAnt("jni/build-ios32.xml", "-v", "-Drelease=true", "clean", "postcompile");
		}
		else
		{
			success &= BuildExecutor.executeAnt("jni/build-linux64.xml", "-v", "-Drelease=true", "clean", "postcompile");
			if(args.length < 1 || !args[0].equals("dev"))
			{
				success &= BuildExecutor.executeAnt("jni/build-windows32.xml", "-v", "-Drelease=true", "clean", "postcompile");
				success &= BuildExecutor.executeAnt("jni/build-windows64.xml", "-v", "-Drelease=true", "clean", "postcompile");
				success &= BuildExecutor.executeAnt("jni/build-linux32.xml", "-v", "-Drelease=true", "clean", "postcompile");
				//success &= BuildExecutor.executeAnt("jni/build-linuxarm32.xml", "-v", "-Drelease=true", "clean", "postcompile");
				//success &= BuildExecutor.executeAnt("jni/build-linuxarm64.xml", "-v", "-Drelease=true", "clean", "postcompile");
				if(CROSSCOMPILE_MACOS)
					success &= BuildExecutor.executeAnt("jni/build-macosx64.xml", "-v", "-Drelease=true", "clean", "postcompile");
				success &= BuildExecutor.executeAnt("jni/build-android32.xml", "-v", "-Drelease=true", "clean", "postcompile");
			}
		}
		
		//If any executeAnt failed, this will exit with 1
		System.exit(success ? 0 : 1);
	}
}
