package com.hl.netty._13decorator;

//FileInputStream
public class ConcreteComponent implements Component{

    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
