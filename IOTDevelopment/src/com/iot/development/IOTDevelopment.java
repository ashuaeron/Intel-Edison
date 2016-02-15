package com.iot.development;

import mraa.Aio;
import mraa.Gpio;

public class IOTDevelopment {

  static {
    try {
      System.loadLibrary("mraajava");
    } catch (UnsatisfiedLinkError e) {
      System.err.println(
        "Native code library failed to load. See the chapter on Dynamic Linking Problems in the SWIG Java documentation for help.\n" +
        e);
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    Aio a = new Aio(3);
    Gpio digital = new Gpio(13);
  }

}