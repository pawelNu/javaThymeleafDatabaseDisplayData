package com.aplication.javaThymeleafDatabaseDisplayData.additonal;

public class LocationUtils {

    public static String getLocation() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            // Index 2 means calling the method directly outside getLocation()
            StackTraceElement element = stackTrace[2];
            String packageName = element.getClassName();
            String methodName = element.getMethodName();
            return packageName + "." + methodName;
        }

        return "";
    }
}
