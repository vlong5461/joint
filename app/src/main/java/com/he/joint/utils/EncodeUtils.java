package com.he.joint.utils;


public class EncodeUtils {

    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            try {
                int data = Integer.parseInt(hex[i], 16);
                string.append((char) data);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return string.toString();
    }

    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {
            try {
                char c = string.charAt(i);
                unicode.append("\\u" + Integer.toHexString(c));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return unicode.toString();
    }


}
