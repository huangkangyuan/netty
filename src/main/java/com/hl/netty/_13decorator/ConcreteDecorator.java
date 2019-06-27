package com.hl.netty._13decorator;

public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(Component component) {
        super(component);
    }

    //包装
    @Override
    public void doSomething() {
        this.doAnotherThing();
        super.doSomething();
    }

    private void doAnotherThing(){
        System.out.println("其他功能");
    }
}
