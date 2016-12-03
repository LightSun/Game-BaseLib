package com.heaven7.java.game.base.state;

/**
 * Created by heaven7 on 2016/12/3.
 */
public class Dog extends Cat implements StateMachineSupplier {

    @Override
    public StateMachine<Dog> getStateMachine() {
        return super.getStateMachine();
    }

    @Override
    protected StateProvider<Dog> createStateProvider() {
        return super.createStateProvider();
    }
}
