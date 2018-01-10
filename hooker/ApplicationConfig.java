package com.dnadroid.hooker;

import android.content.Context;

public class ApplicationConfig {

  private static String packageName;
  private static String dataDir;
  private static Context context;  
  private static boolean filtered;
  
  private ApplicationConfig() {}

  /**
   * @return the packageName
   */
  public static String getPackageName() {
    return packageName;
  }

  /**
   * @param packageName the packageName to set
   */
  public static void setPackageName(String packageName) {
    ApplicationConfig.packageName = packageName;
  }

  /**
   * @return the dataDir
   */
  public static String getDataDir() {
    return dataDir;
  }

  /**
   * @param dataDir the dataDir to set
   */
  public static void setDataDir(String dataDir) {
    ApplicationConfig.dataDir = dataDir;
  }

  /**
   * @return the context
   */
  public static Context getContext() {
    return context;
  }

  /**
   * @param context the context to set
   */
  public static void setContext(Context context) {
    ApplicationConfig.context = context;
  }

  /**
   * @return the filtered
   */
  public static boolean isFiltered() {
    return filtered;
  }

  /**
   * @param filtered the filtered to set
   */
  public static void setFiltered(boolean filtered) {
    ApplicationConfig.filtered = filtered;
  }

  
  
}
