package org.example.proxy;

import javafx.scene.control.Alert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ControllerHandler implements InvocationHandler {

    private final Object target;

    public ControllerHandler(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // i will use the Middleware here!
        String action = target.getClass().getName();

        return method.invoke(target,args);
    }

}
