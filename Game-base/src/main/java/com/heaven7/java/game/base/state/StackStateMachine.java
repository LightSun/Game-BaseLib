package com.heaven7.java.game.base.state;

import java.util.LinkedList;
import java.util.List;


/**
 * a stack structure of state machine
 * <p>
 *     Note: {@link #addState(int)} or {@link #removeState(int)} will have no effect with state stack.
 * </p>
 *
 * @author heaven7
 * @param <E>
 *            the entity which implement {@link StateMachineSupplier}.
 */
public class StackStateMachine<E> extends DefaultStateMachine<E> {

	/**
	 * the history state stack.
	 */
	private LinkedList<Integer> mStateStack;

	public StackStateMachine(E owner, StateProvider<E> provider,
			int initialState, int globalState) {
		super(owner, provider, initialState, globalState);
	}

	public StackStateMachine(E owner, StateProvider<E> provider,
			int initialState) {
		super(owner, provider, initialState);
	}

	public StackStateMachine(E owner, StateProvider<E> provider) {
		super(owner, provider);
	}

	@Override
	protected void onFinalInit() {
		super.onFinalInit();
		mStateStack = new LinkedList<Integer>();
	}

	@Override
	public boolean addState(int states) {
		int curState = mCurrentState;
		final boolean result = super.addState(states);
        if( result ){
            mStateStack.add(curState);
		}
		return result;
	}

	@Override
	public boolean removeState(int states) {
		int curState = mCurrentState;
		final boolean result = super.removeState(states);
		if( result ){
			mStateStack.add(curState);
		}
		return result;
	}

	@Override
	public int getPreviousState(List<State<E>> outStates) {
		if(mStateStack.size() == 0){
			return 0;
		}
		//get last.
		int preState = mStateStack.peek();
		if(outStates != null){
			mStateProvider.getStates(preState, outStates);
		}
		return preState;
	}
	
	@Override
	public boolean revertToPreviousState() {
		if(mStateStack.size() == 0){
			return false;
		}
		//get and remove last
		int preState = mStateStack.pollLast();
		changeState(preState, false, null);
		return true;
	}
	
	@Override
	public int changeState(int newStates, List<State<E>> outOldStates) {
		return changeState(newStates, true, outOldStates);
	}


	//return the previous state
	private int changeState(int newStates, boolean pushCurrentStateToStack, List<State<E>> out) {
		if (pushCurrentStateToStack && mCurrentState != 0) {
			mStateStack.add(mCurrentState);
		}
		//System.err.println("stack: " + mStateStack); //test ok
		return super.changeState(newStates, out);
	}
}
