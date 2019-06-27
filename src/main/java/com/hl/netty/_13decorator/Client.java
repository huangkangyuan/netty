package com.hl.netty._13decorator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        Component component = new ConcreteDecorator(new ConcreteComponent());
        component.doSomething();
    }
}
