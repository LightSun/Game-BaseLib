package com.heaven7.java.game.base.state;

import java.util.List;

/**
 * the state machine , support multi state change at the same time.
 * @author heaven7
 *
 * @param <E> the entity which implement StateMachineSupplier.
 */
public interface StateMachine<E extends StateMachineSupplier<E>>{
	
	
	/**
	 * set the state transformer.
	 * @param transformer the state transformer
	 */
    public void setStateTransformer(StateTransformer<E> transformer);  
	
	
    /**
	 * Updates the state machine by invoking first the {@code execute} method of
	 * the global state (if any) then the {@code execute} method of the current
	 * state.
	 */
	public void update ();
	
	/**
	 * change the current states.
	 * @param newStates the new state flags
	 * @param outOldStates the old states, can be null.
	 * @return the old states flags.
	 */
	public int changeState (int newStates, List<State<E>> outOldStates);
	
	
	/**
	 * change to the state
	 * @param newStates the new state to change to.
	 */
	public void changeState(int newStates);
	

	/** Change state back to the previous state.
	 * @return {@code True} in case there was a previous state that we were able to revert to. In case there is no previous state,
	 *         no state change occurs and {@code false} will be returned. */
	public boolean revertToPreviousState ();

	/** Sets the initial state of this state machine.
	 * @param states the initial state. */
	public void setInitialState (int states);
	

	/** Sets the global state of this state machine.
	 * @param states the global state. */
	public void setGlobalState (int states);
	

	/** Returns the current state of this state machine. outStates can be null. 
	 * @param outStates  optional , can be null 
	 * @return the state as int
	 **/
	public int getCurrentState(List<State<E>> outStates);
	

	/** return the global state,  outStates can be null. 
	 * @param outStates  optional , can be null 
	 * @return the state as int
	 * */
	public int getGlobalState (List<State<E>> outStates);
	

	/** Returns the last state of this state machine. outStates can be null.
	 * @param outStates  optional , can be null 
	 * @return the state as int
	 *  */
	public int getPreviousState (List<State<E>> outStates);
	
	/**
	 * Indicates whether the state machine is in the given state.
	 * <p>
	 * This implementation assumes states are singletons (typically an enum) so
	 * they are compared with the {@code ==} operator instead of the
	 * {@code equals} method.
	 * 
	 * @param states
	 *            the state to be compared with the current state
	 * @return true if the current state and the given state are the same
	 *         object.
	 */
	public boolean isInState (int states);
	
	/**
	 * the state transformer. use to transform state.
	 * @author don
	 *
	 * @param <E> the entity
	 */
	public interface StateTransformer<E extends StateMachineSupplier<E>>{
		
		/**
		 * transform the state of StateMachine. you should call {@link StateMachine#changeState(int)} or 
		 * {@link StateMachine#changeState(int, List)} to change state.
		 * @param e the State Machine Supplier.
		 * @param machine the state machine
		 */
		void transformState(StateMachine<E> machine, E e);
	}
	
}

