package com.ruipengkj.common.util;

/**
 * 实现驼峰式命名与DB下划线命名转换
 * @author Squall
 *
 */
public class NameUtils {
	private static final char SEPARATOR = '_';
	
	/**
	 * 将驼峰命名转成下划线命名
	 * @param s
	 * @return
	 */
	public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            boolean nextUpperCase = true;
 
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
 
            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
 
            sb.append(Character.toLowerCase(c));
        }
 
        return sb.toString();
    }
 
	/**
	 * 下划线命名将转成驼峰命名
	 * @param s
	 * @return
	 */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
 
        s = s.toLowerCase();
 
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
 
        return sb.toString();
    }
 
    /**
	 * 下划线命名将转成驼峰命名，并将首字母大写
	 * @param s
	 * @return
	 */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
 
    public static void main(String[] args) {
        System.out.println(NameUtils.toUnderlineName("ISOCertifiedStaff"));
        System.out.println(NameUtils.toUnderlineName("CertifiedStaff"));
        System.out.println(NameUtils.toUnderlineName("UserID"));
        System.out.println(NameUtils.toCamelCase("iso_certified_staff"));
        System.out.println(NameUtils.toCamelCase("certified_staff"));
        System.out.println(NameUtils.toCamelCase("user_id"));
    }
}
