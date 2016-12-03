package com.heaven7.java.game.base.state;

public class Cat implements StateMachineSupplier<Cat>{
	
	private final StateMachine<Cat> machine;
	
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
