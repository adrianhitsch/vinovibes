package com.vinovibes.vinoapi.handlers;

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
 * Custom async exception handler.
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    /**
     * Handles the uncaught exception.
     * @param throwable throwable
     * @param method method
     * @param obj objects
     */
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            System.out.println("Parameter value - " + param);
        }
    }
}
