package com.heaven7.java.game.base.state;
/**
 * one object map one StateMachine
 * @author heaven7
 * @param <E> the entity which implement {@link StateMachineSupplier}}
 */
public interface StateMachineSupplier<E extends StateMachineSupplier<E>>{
	
	/**
	 * get the state machine
	 * @return the state machine
	 */
	StateMachine<E> getStateMachine();
	
}