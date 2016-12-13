package com.heaven7.java.game.base.state;

import java.util.ArrayList;
import java.util.List;

/**
 * this is a default implements of {@link StateMachine}}.
 *
 * @param <E> the owner
 * @author heaven7
 */
public class DefaultStateMachine<E> implements StateMachine<E> {

    /*protected static final int OP_ENTER    = 1;
    protected static final int OP_UPDATE   = 2;
    protected static final int OP_EXIT     = 3;
    protected static final int OP_REENTER  = 4;*/
    /**
     * The entity that owns this state machine.
     */
    protected final E mOwner;

    /**
     * the state provider
     */
    protected final StateProvider<E> mStateProvider;

    /**
     * The current state the owner is in.
     */
    protected int mCurrentState;

    /**
     * The last state the owner was in.
     */
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
     * @param owner    the owner of the state machine
     * @param provider the state provider
     */
    public DefaultStateMachine(E owner, StateProvider<E> provider) {
        this(owner, provider, 0, 0);
    }

    /**
     * Creates a DefaultStateMachine for the specified owner and initial state.
     *
     * @param owner        the owner of the state machine
     * @param provider     the state provider
     * @param initialState the initial state
     */
    public DefaultStateMachine(E owner, StateProvider<E> provider, int initialState) {
        this(owner, provider, initialState, 0);
    }

    /**
     * Creates a DefaultStateMachine for the specified owner, initial state and
     * global state.
     *
     * @param owner        the owner of the state machine
     * @param provider     the state provider
     * @param initialState the initial state
     * @param globalState  the global state
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
        if (mTransformer != null) {
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
    public boolean addState(int states) {
        if(states <= 0 ) return false;
        final int curState = mCurrentState;
        //check if already have the state.
        final int share = (curState & states);
        if(share != 0){
            reenterState(share);
        }
        // not change
        if (share == states) {
            return false;
        }
        mPreviousState = curState;
        mCurrentState |= states;
        enterState(states & ~share);
        return true;
    }

    @Override
    public boolean removeState(int states) {
        if(states <= 0 ) return false;
        checkState(states);
        final int curState = mCurrentState;
        int share = curState & states;
        //not have this state.
        if (share == 0) {
            return false;
        }
        mPreviousState = curState;
        mCurrentState &= ~states;
        exitState(share);
        return true;
    }

    @Override
    public void setState(int newStates) {
        changeState(newStates, null);
    }

    @Override
    public int changeState(int newStates, List<State<E>> outOldStates) {
        checkState(newStates);

        final int preState = mPreviousState;
        // Keep a record of the previous state
        mPreviousState = mCurrentState;
        // Change state to the new state
        mCurrentState = newStates;
        //dispatch the state change. as state.enter/exit
        dispatchStateChange(mPreviousState, newStates);

        if (outOldStates != null && preState != 0) {
            mStateProvider.getStates(preState, outOldStates);
        }
        return preState;
    }

    @Override
    public boolean revertToPreviousState() {
        if (mPreviousState == 0) {
            return false;
        }
        changeState(mPreviousState, null);
        mPreviousState = 0;
        return true;
    }

    @Override
    public boolean isInState(int states) {
        return mCurrentState == states;
    }

    @Override
    public boolean hasState(int state) {
        return (mCurrentState & state) != 0;
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

    @Override
    public void setStateTransformer(StateMachine.StateTransformer<E> transformer) {
        this.mTransformer = transformer;
    }

    /**
     * dispatch the state change if need.
     *
     * @param currentState the current state before this state change.
     * @param newState     the target or new state
     */
    protected void dispatchStateChange(int currentState, int newState) {
        final int shareFlag = currentState & newState;
        final int enterFlag = newState & ~shareFlag;
        final int exitFlag = currentState & ~shareFlag;
        // Call the exit method of the existing state
        if (exitFlag != 0) {
            exitState(exitFlag);
        }
        // Call the entry method of the new state
        if (enterFlag != 0) {
            enterState(enterFlag);
        }
        //call reenter state
        if(shareFlag != 0){
            reenterState(shareFlag);
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

    protected void reenterState(int states) {
        final List<State<E>> mTempStates = this.mTempStates;
        final E mOwner = this.mOwner;
        mStateProvider.getStates(states, mTempStates);
        for (int size = mTempStates.size(), i = size - 1; i >= 0; i--) {
            mTempStates.get(i).reenter(mOwner);
        }
        mTempStates.clear();
    }

    private int getStateInternal(int expectState, List<State<E>> outStates) {
        if (expectState != 0) {
            if (outStates != null) {
                mStateProvider.getStates(expectState, outStates);
            }
            return expectState;
        } else {
            return 0;
        }
    }

    private void checkState(int expectState) {
        if (expectState < 0) {
            throw new IllegalArgumentException("state flag must be >= 0.");
        }
    }

}
