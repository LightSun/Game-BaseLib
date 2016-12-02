package com.heaven7.java.game.base.state;

import com.heaven7.java.game.base.state.DefaultStateMachine;
import com.heaven7.java.game.base.state.StackStateMachine;
import com.heaven7.java.game.base.state.StateMachine;
import com.heaven7.java.game.base.state.StateMachineSupplier;

public class Cat implements StateMachineSupplier<Cat>{
	
	private static final CatStateProvider sProvider = new CatStateProvider();
	DefaultStateMachine<Cat> machine;
	
	public int eat;
	public int sleep;
	public int run;
	
	
	public Cat() {
		machine = new StackStateMachine<Cat>(this, sProvider);
	}
	@Override
	public StateMachine<Cat> getStateMachine() {
		return machine;
	}


}
