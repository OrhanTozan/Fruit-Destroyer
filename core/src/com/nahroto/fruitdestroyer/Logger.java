package com.nahroto.fruitdestroyer;


public class Logger
{
    public static void log(String string)
    {
        System.out.println(string);
    }

    public static void log(int intNumber)
    {
        System.out.println(intNumber);
    }

    public static void log(float floatNumber)
    {
        System.out.println(floatNumber);
    }

    public static void log(String string, int intNumber)
    {
        System.out.println(string + ": " + intNumber);
    }

    public static void log(String string, float floatNumber)
    {
        System.out.println(string + ": " + floatNumber);
    }

    public static void log(boolean booleanA)
    {
        System.out.println(booleanA);
    }
}
