package com.heaven7.java.game.base.state;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * a stack structure of state machine
 * 
 * @author heaven7
 * 
 * @param <E>
 *            the entity which implement {@link StateMachineSupplier}.
 */
public class StackStateMachine<E extends StateMachineSupplier<E>> extends
		DefaultStateMachine<E> {

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
	public List<State<E>> getPreviousState(List<State<E>> outStates) {
		if(mStateStack.size() == 0){
			return null;
		}
		//get last.
		int preState = mStateStack.peek();
		if(outStates == null){
			outStates = new ArrayList<State<E>>();
		}
		mStateProvider.getStates(preState, outStates);
		return outStates;
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
		if(outOldStates == null){
			outOldStates = new ArrayList<State<E>>();
		}
		return changeState(newStates, true, outOldStates);
	}


	//return the previous state
	private int changeState(int newState, boolean pushCurrentStateToStack, List<State<E>> oldState) {

		if (pushCurrentStateToStack && mCurrentState != 0) {
			mStateStack.add(mCurrentState);
		}
		int preState = mCurrentState;
		// Call the exit method of the existing state
		if (preState != 0) {
			exitState(preState);
			if(oldState != null){
				mStateProvider.getStates(preState, oldState);
			}
		}
		// Change state to the new state
		mCurrentState = newState;

		// Call the entry method of the new state
		enterState(newState);
		return preState;
	}
}
