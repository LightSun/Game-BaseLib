package com.heaven7.java.game.base.state;

/**
 * the state which can be notify by 'enter'/'update'/'exit'. 
 * @author heaven7
 *
 * @param <E> the state machine owner
 */
public interface State<E> {

	/**
	 * this is called on enter this state.
	 * @param entity the entity
	 */
	 void enter (E entity);

	/**
	 * this is called on update this state. which is called by {@link StateMachine#update()}.
	 * @param entity the entity
	 */
	 void update (E entity);

	/**
	 * this is called on exit this state.
	 * @param entity the entity
	 */
	 void exit (E entity);

	/**
	 * called when the owner is already in this state, and reenter this state.
	 * @param entity the entity
     */
	 void reenter(E entity);
}
