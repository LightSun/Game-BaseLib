package com.heaven7.java.game.base.state;

import java.util.ArrayList;
import java.util.List;

/**
 * this is a default implements of {@link StateMachine}}.
 * 
 * @author heaven7
 *
 * @param <E>
 *            the entity which implements {@link StateMachineSupplier}.
 */
public class DefaultStateMachine<E extends StateMachineSupplier<E>> implements StateMachine<E> {

	/** The entity that owns this state machine. */
	protected final E mOwner;

	/** the state provider */
	protected final StateProvider<E> mStateProvider;

	/** The current state the owner is in. */
	protected int mCurrentState;

	/** The last state the owner was in. */
	protected int mPreviousState;

	/**
	 * The global state of the owner. Its logic is called every time the FSM is
	 * updated.
	 */
	protected int mGlobalState;

	/**
	 * the state transformer.
	 */
	protected StateMachine.StateTransformer<E> mTransformer;

	/**
	 * the temp list state.
	 */
	protected List<State<E>> mTempStates;

	/**
	 * Creates a DefaultStateMachine for the specified owner.
	 * 
	 * @param owner
	 *            the owner of the state machine
	 * @param provider
	 *            the state provider
	 */
	public DefaultStateMachine(E owner, StateProvider<E> provider) {
		this(owner, provider, 0, 0);
	}

	/**
	 * Creates a DefaultStateMachine for the specified owner and initial state.
	 * 
	 * @param owner
	 *            the owner of the state machine
	 * @param provider
	 *            the state provider
	 * 
	 * @param initialState
	 *            the initial state
	 */
	public DefaultStateMachine(E owner, StateProvider<E> provider, int initialState) {
		this(owner, provider, initialState, 0);
	}

	/**
	 * Creates a DefaultStateMachine for the specified owner, initial state and
	 * global state.
	 * 
	 * @param owner
	 *            the owner of the state machine
	 * @param provider
	 *            the state provider
	 * @param initialState
	 *            the initial state
	 * @param globalState
	 *            the global state
	 */
	public DefaultStateMachine(E owner, StateProvider<E> provider, int initialState, int globalState) {
		if (owner == null || provider == null) {
			throw new NullPointerException();
		}
		this.mOwner = owner;
		this.mStateProvider = provider;
		this.mCurrentState = initialState;
		this.mGlobalState = globalState;
		this.onFinalInit();
	}

	/**
	 * we give a chance to change instance object . eg: arrayList to other list.
	 */
	protected void onFinalInit() {
		this.mTempStates = new ArrayList<State<E>>();
	}

	@Override
	public void setInitialState(int states) {
		checkState(states);
		this.mPreviousState = 0;
		this.mCurrentState = states;
	}

	@Override
	public void setGlobalState(int states) {
		checkState(states);
		this.mGlobalState = states;
	}

	@Override
	public final void update() {
		
		onUpdate();
		// transform state if need
		if(mTransformer != null){
			mTransformer.transformState(this, mOwner);
		}
	}

	/**
	 * this is called in {@link #update()} method. 
	 */
	protected void onUpdate() {
		// Execute the global state (if any)
		if (mGlobalState != 0) {
			updateState(mGlobalState);
		}

		// Execute the current state (if any)
		if (mCurrentState != 0) {
			updateState(mCurrentState);
		}
	}

	@Override
	public void changeState(int newStates) {
		changeState(newStates, null);
	}

	@Override
	public int changeState(int newStates, List<State<E>> outOldStates) {
		checkState(newStates);
		// Keep a record of the previous state
		mPreviousState = mCurrentState;
		// Call the exit method of the existing state
		if (mCurrentState != 0) {
			exitState(mCurrentState);
		}
		// Change state to the new state
		mCurrentState = newStates;

		// Call the entry method of the new state
		enterState(newStates);

		if (outOldStates != null) {
			mStateProvider.getStates(mPreviousState, outOldStates);
		}
		return mPreviousState;
	}

	@Override
	public boolean revertToPreviousState() {
		if (mPreviousState == 0) {
			return false;
		}

		changeState(mPreviousState, null);

		return true;
	}

	@Override
	public boolean isInState(int states) {
		return mCurrentState == states;
	}

	@Override
	public int getCurrentState(List<State<E>> outStates) {
		return getStateInternal(mCurrentState, outStates);
	}

	@Override
	public int getGlobalState(List<State<E>> outStates) {
		return getStateInternal(mGlobalState, outStates);
	}

	@Override
	public int getPreviousState(List<State<E>> outStates) {
		return getStateInternal(mPreviousState, outStates);
	}

	private int getStateInternal(int expectState, List<State<E>> outStates) {
		if (expectState != 0) {
			if (outStates == null) {
				outStates = new ArrayList<State<E>>();
			}
			mStateProvider.getStates(expectState, outStates);
			return expectState;
		} else {
			return 0;
		}
	}

	protected void updateState(int expectStates) {
		final List<State<E>> mTempStates = this.mTempStates;
		final E mOwner = this.mOwner;
		mStateProvider.getStates(expectStates, mTempStates);
		for (int size = mTempStates.size(), i = size - 1; i >= 0; i--) {
			mTempStates.get(i).update(mOwner);
		}
		mTempStates.clear();
	}

	protected void exitState(int states) {
		final List<State<E>> mTempStates = this.mTempStates;
		final E mOwner = this.mOwner;
		mStateProvider.getStates(states, mTempStates);
		for (int size = mTempStates.size(), i = size - 1; i >= 0; i--) {
			mTempStates.get(i).exit(mOwner);
		}
		mTempStates.clear();
	}

	protected void enterState(int states) {
		final List<State<E>> mTempStates = this.mTempStates;
		final E mOwner = this.mOwner;
		mStateProvider.getStates(states, mTempStates);
		for (int size = mTempStates.size(), i = size - 1; i >= 0; i--) {
			mTempStates.get(i).enter(mOwner);
		}
		mTempStates.clear();
	}

	private void checkState(int expectState) {
		if (expectState <= 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void setStateTransformer(StateMachine.StateTransformer<E> transformer) {
		this.mTransformer = transformer;
	}

}
