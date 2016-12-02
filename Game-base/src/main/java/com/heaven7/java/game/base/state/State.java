package com.heaven7.java.game.base.state;

/**
 * the state which can be notify by 'enter'/'update'/'exit'. 
 * @author heaven7
 *
 * @param <E> the state machine suppplier
 */
public interface State<E extends StateMachineSupplier<E>> {

	/**
	 * this is called on enter this state.
	 * @param entity the entity
	 */
	public void enter (E entity);

	/**
	 * this is called on update this state. which is called by {@link StateMachine#update()}.
	 * @param entity the entity
	 */
	public void update (E entity);

	/**
	 * this is called on exit this state.
	 * @param entity the entity
	 */
	public void exit (E entity);
	
}
