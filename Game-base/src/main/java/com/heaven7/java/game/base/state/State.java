package com.heaven7.java.game.base.state;


public interface State<E extends StateMachineSupplier<E>> {

	public void enter (E entity);

	public void update (E entity);

	public void exit (E entity);
	
}
