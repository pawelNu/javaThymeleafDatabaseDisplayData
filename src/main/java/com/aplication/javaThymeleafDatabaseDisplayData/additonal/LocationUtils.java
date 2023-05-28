package com.aplication.javaThymeleafDatabaseDisplayData.additonal;

public class LocationUtils {

    public static String getLocation() {
        Class<?> clazz = new Exception().getStackTrace()[1].getClass();
        Package pkg = clazz.getPackage();
        String packageName = pkg.getName();
        String className = clazz.getSimpleName();
        String methodName = new Exception().getStackTrace()[1].getMethodName();

        return packageName + "." + className + "." + methodName;
    }
}
