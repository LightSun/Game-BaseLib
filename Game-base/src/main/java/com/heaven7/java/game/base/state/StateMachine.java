package com.heaven7.java.game.base.state;

import java.util.List;

/**
 * the state machine , support multi state change at the same time.
 * <p>
 *     Note : the all state must be 2^n all their summation.
 * </p>
 *
 * @param <E> the owner
 * @author heaven7
 */
public interface StateMachine<E> {

    /**
     * add the target state to current state. after call this the target state will be merged with current state.
     * @param states the target state.
     * @return true if add state success.or else this state is already entered,
     * @see {@link #removeState(int)}
     */
    boolean addState(int states);

    /**
     * remove the target state from current state.
     * @param states the target state
     * @return true if remove state success. or else this state is not entered,
     * @see {@link #addState(int)}
     */
    boolean removeState(int states);

    /**
     * change to the state
     *
     * @param newStates the new state to change to.
     */
    void setState(int newStates);
    /**
     * set the state transformer.
     *
     * @param transformer the state transformer
     */
    void setStateTransformer(StateTransformer<E> transformer);


    /**
     * Updates the state machine by invoking first the {@code execute} method of
     * the global state (if any) then the {@code execute} method of the current
     * state.
     */
    void update();

    /**
     * change the current states.
     *
     * @param newStates    the new state flags
     * @param outOldStates the old states, can be null.
     * @return the old states flags.
     */
    int changeState(int newStates, List<State<E>> outOldStates);



    /**
     * Change state back to the previous state.
     *
     * @return {@code True} in case there was a previous state that we were able to revert to. In case there is no previous state,
     * no state change occurs and {@code false} will be returned.
     */
    boolean revertToPreviousState();

    /**
     * Sets the initial state of this state machine.
     *
     * @param states the initial state.
     */
    void setInitialState(int states);


    /**
     * Sets the global state of this state machine.
     *
     * @param states the global state.
     */
    void setGlobalState(int states);


    /**
     * Returns the current state of this state machine. outStates can be null.
     *
     * @param outStates optional , can be null
     * @return the state as int
     **/
    int getCurrentState(List<State<E>> outStates);


    /**
     * return the global state,  outStates can be null.
     *
     * @param outStates optional , can be null
     * @return the state as int
     */
    int getGlobalState(List<State<E>> outStates);


    /**
     * Returns the last state of this state machine. outStates can be null.
     *
     * @param outStates optional , can be null
     * @return the state as int
     */
    int getPreviousState(List<State<E>> outStates);

    /**
     * Indicates whether the state machine is in the given state.
     * <p/>
     * This implementation assumes states are singletons (typically an enum) so
     * they are compared with the {@code ==} operator instead of the
     * {@code equals} method.
     *
     * @param state the state to be compared with the current state
     * @return true if the current state and the given state are the same
     * object.
     */
    boolean isInState(int state);

    /**
     * indicate is the target state is acting or not. this is often used in mix state.
     * @param state the target state to check
     * @return true is has the target state.
     */
    boolean hasState(int state);

    /**
     * the state transformer. use to transform state.
     *
     * @param <E> the entity
     * @author don
     */
    interface StateTransformer<E> {

        /**
         * transform the state of StateMachine. you should call {@link StateMachine#setState(int)} or
         * {@link StateMachine#changeState(int, List)} to change state.
         *
         * @param e       the State Machine Supplier.
         * @param machine the state machine
         */
        void transformState(StateMachine<E> machine, E e);
    }

}

