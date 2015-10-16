package com.coveracademy.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sandro on 7/4/15.
 */
public class IOUtils {

  public static void closeQuietly(InputStream inputStream) {
    if(inputStream != null) {
      try {
        inputStream.close();
      } catch(IOException e) {
        // Ignore
      }
    }
  }

  public static void closeQuietly(OutputStream outputStream) {
    if(outputStream != null) {
      try {
        outputStream.close();
      } catch(IOException e) {
        // Ignore
      }
    }
  }
}
