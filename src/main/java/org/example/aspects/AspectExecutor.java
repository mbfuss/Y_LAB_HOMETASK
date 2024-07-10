package org.example.aspects;

public class AspectExecutor {

    public static void logMethodStart(String methodName) {
        System.out.println("Starting execution of method: " + methodName);
    }

    public static void logMethodEnd(String methodName, long duration) {
        System.out.println("Execution of method: " + methodName + " took " + duration + " ms");
    }

    public static void auditAction(String methodName, Object... args) {
        System.out.println("Audit: Method " + methodName + " was called with arguments " + java.util.Arrays.toString(args));
    }
}
