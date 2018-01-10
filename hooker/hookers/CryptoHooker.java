package com.dnadroid.hooker.hookers;

import java.util.HashMap;
import java.util.Map;

import com.dnadroid.hooker.SubstrateMain;
import com.dnadroid.hooker.exceptions.HookerInitializationException;

/**
 * @author Georges Bossert
 * 
 */
public class CryptoHooker extends Hooker {

  public static final String NAME = "Crypto";

  public CryptoHooker() {
    super(CryptoHooker.NAME);
  }


  @Override
  public void attach() {    
    
    attachOnCipherClass();
    
    attachOnMACClass();
    
    attachOnMessageDigestClass();
    
    attachOnPBEKeySpecClass();
    
    attachOnKeyStoreClass();
    
    attachOnKeyManagerFactoryClass();
    
    attachOnBase64Class();
  }
  
  /**
   * Attach on Base64 class
   */
  private void attachOnBase64Class() {
    Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

    methodsToHook.put("decode", 0);
    methodsToHook.put("encode", 0);
    methodsToHook.put("encodeToString", 0);
    
    try {
      hookMethods(null, "android.util.Base64",
        methodsToHook);
      SubstrateMain.log("hooking android.util.Base64 methods sucessful");

    } catch (HookerInitializationException e) {
      SubstrateMain.log("hooking android.util.Base64 methods has failed", e);
    }
    
  } 
  
  
  /**
   * Attach on KeyManagerFactory class
   */
  private void attachOnKeyManagerFactoryClass() {
    Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

    methodsToHook.put("getAlgorithm", 0);
    methodsToHook.put("getInstance", 0);
    methodsToHook.put("init", 0);
    
    try {
      hookMethods(null, "javax.net.ssl.KeyManagerFactory",
        methodsToHook);
      SubstrateMain.log("hooking javax.net.ssl.KeyManagerFactory methods sucessful");

    } catch (HookerInitializationException e) {
      SubstrateMain.log("hooking javax.net.ssl.KeyManagerFactory methods has failed", e);
    }
    
  } 
  
  
  
  
  /**
   * Attach on KeyStore class
   */
  private void attachOnKeyStoreClass() {
    Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

    methodsToHook.put("aliases", 0);
    methodsToHook.put("getCertificate", 0);
    methodsToHook.put("getCertificateChain", 0);
    methodsToHook.put("getDefaulType", 0);
    methodsToHook.put("getInstance", 0);
    methodsToHook.put("getKey", 0);
    methodsToHook.put("getProvider", 0);
    methodsToHook.put("load", 0);
    methodsToHook.put("store", 0);
    
    try {
      hookMethods(null, "java.security.KeyStore",
        methodsToHook);
      SubstrateMain.log("hooking java.security.KeyStore methods sucessful");

    } catch (HookerInitializationException e) {
      SubstrateMain.log("hooking java.security.KeyStore methods has failed", e);
    }
    
  } 
  

  /**
   * Attach on Cipher class
   */
  private void attachOnCipherClass() {
    Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

    methodsToHook.put("init", 0);
    methodsToHook.put("update", 0);
    methodsToHook.put("getInstance", 0);
    methodsToHook.put("doFinal", 0);
    methodsToHook.put("Random", 0);
    methodsToHook.put("getIV", 0);
    
    try {
      hookMethods(null, "javax.crypto.Cipher",
        methodsToHook);
      SubstrateMain.log("hooking javax.crypto.Cipher methods sucessful");

    } catch (HookerInitializationException e) {
      SubstrateMain.log("hooking javax.crypto.Cipher methods has failed", e);
    }
    
  }
  
  /**
   * Attach on MAC class
   */
  private void attachOnMACClass() {
    Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

    methodsToHook.put("init", 0);
    methodsToHook.put("update", 0);
    methodsToHook.put("getInstance", 0);
    
    try {
      hookMethods(null, "javax.crypto.Mac",
        methodsToHook);
      SubstrateMain.log("hooking javax.crypto.Mac methods sucessful");

    } catch (HookerInitializationException e) {
      SubstrateMain.log("hooking javax.crypto.Mac methods has failed", e);
    }
    
  }
  
  /**
   * Attach on MessageDigest class
   */
  private void attachOnMessageDigestClass() {
    Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

    methodsToHook.put("update", 0);
    methodsToHook.put("getInstance", 0);
    methodsToHook.put("digest", 0);
    
    try {
      hookMethods(null, "java.security.MessageDigest",
        methodsToHook);
      SubstrateMain.log("hooking java.security.MessageDigest methods sucessful");

    } catch (HookerInitializationException e) {
      SubstrateMain.log("hooking java.security.MessageDigest methods has failed", e);
    }
    
  }
  
  /**
   * Attach on PBEKeySpec class
   */
  private void attachOnPBEKeySpecClass() {
    Map<String, Integer> methodsToHook = new HashMap<String, Integer>();

    methodsToHook.put("PBEKeySpec", 0);
    
    try {
      hookMethods(null, "javax.crypto.spec.PBEKeySpec",
        methodsToHook);
      SubstrateMain.log("hooking javax.crypto.spec.PBEKeySpec methods sucessful");

    } catch (HookerInitializationException e) {
      SubstrateMain.log("hooking javax.crypto.spec.PBEKeySpec methods has failed", e);
    }
    
  } 

  
}
