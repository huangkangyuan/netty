package com.hl.netty._13decorator;

public class Decorator implements Component {

    //装饰对象需要持有抽象构建角色的引用
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
