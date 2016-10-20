package com.coveracademy.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
