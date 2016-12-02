package com.heaven7.java.game.base.state;

import com.heaven7.java.game.base.state.DefaultStateMachine;
import com.heaven7.java.game.base.state.StackStateMachine;
import com.heaven7.java.game.base.state.StateMachine;
import com.heaven7.java.game.base.state.StateMachineSupplier;

public class Cat implements StateMachineSupplier<Cat>{
	
	private final DefaultStateMachine<Cat> machine;
	
	public int eat;
	public int sleep;
	public int run;
	
	public Cat() {
		machine = new StackStateMachine<Cat>(this, createStateProvider());
		//machine = new DefaultStateMachine<Cat>(this, createStateProvider());
	}
	@Override
	public StateMachine<Cat> getStateMachine() {
		return machine;
	}
	
	protected StateProvider<Cat> createStateProvider(){
		return new CatStateProvider();
	}


}
