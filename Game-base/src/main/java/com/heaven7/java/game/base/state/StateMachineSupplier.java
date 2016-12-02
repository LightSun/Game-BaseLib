package com.heaven7.java.game.base.state;
/**
 * one object map one StateMachine
 * @author heaven7
 * @param <E>
 */
public interface StateMachineSupplier<E extends StateMachineSupplier<E>>{
	
	StateMachine<E> getStateMachine();
	
}