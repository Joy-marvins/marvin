package com.example.mysdk;

public class MyJni {
    static {
        System.loadLibrary("MyJni");
    }

    public native static String getString();
}
