package com.heaven7.java.game.base.state;

public class Cat implements StateMachineSupplier{
	
	private final StateMachine<Cat> machine;
	
	public int eat;
	public int sleep;
	public int run;
	
	public Cat() {
		//machine = new StackStateMachine<Cat>(this, createStateProvider());
		machine = new DefaultStateMachine<Cat>(this, createStateProvider());
	}
	public StateMachine getStateMachine() {
		return machine;
	}
	
	protected StateProvider createStateProvider(){
		return new CatStateProvider();
	}


}
