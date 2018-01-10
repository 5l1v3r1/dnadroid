package com.dnadroid.hooker.hookers;

import java.util.HashMap;
import java.util.Map;

import com.dnadroid.hooker.SubstrateMain;
import com.dnadroid.hooker.exceptions.HookerInitializationException;

public class RuntimeHooker extends Hooker {

	public static final String NAME = "Runtime";
	
	public RuntimeHooker() {
		super(RuntimeHooker.NAME);
	}

	@Override
	public void attach() {
		attachOnRuntimeClass();
		attachOnProcessBuilderClass();
	}
	
	 /**
	   * Attach on String class
	   */
	  private void attachOnRuntimeClass() {
	    Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

	    methodsToHook.put("exec", 2);
	    methodsToHook.put("getRuntime", 1);
	    methodsToHook.put("load", 2);
	    methodsToHook.put("loadLibrary", 2);
	    methodsToHook.put("traceInstructions", 0);
	    methodsToHook.put("traceMethodCalls", 0);
	    
	    try {
	      hookMethods(null, "java.lang.Runtime", methodsToHook);
	      SubstrateMain.log("hooking java.lang.Runtime methods sucessful");

	    } catch (HookerInitializationException e) {
	      SubstrateMain.log("hooking java.lang.Runtime methods has failed", e);
	    }
	    
	  } 
  
	/**
	 * Attach on ProcessBuilder class
	 */
	private void attachOnProcessBuilderClass() {

		final String className = "java.lang.ProcessBuilder";

		Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

		methodsToHook.put("command", 2);
		methodsToHook.put("directory", 1);
		methodsToHook.put("environment", 1);
		methodsToHook.put("redirectErrorStream", 1);
		methodsToHook.put("start", 2);

		try {
			hookMethods(null, className, methodsToHook);
			SubstrateMain.log(new StringBuilder("hooking ").append(className)
					.append(" methods sucessful").toString());

		} catch (HookerInitializationException e) {
			SubstrateMain.log(new StringBuilder("hooking ").append(className)
					.append(" methods has failed").toString(), e);
		}

	}
	  

}
