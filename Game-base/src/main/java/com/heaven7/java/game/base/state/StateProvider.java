package com.heaven7.java.game.base.state;

import java.util.List;

/**
 * the state provider. provide the state.
 * @author heaven7
 *
 * @param <E> the entity which implement {@link StateMachineSupplier}}.
 */
public interface StateProvider<E extends StateMachineSupplier<E>>{
	
	/**
	 * get states by target state flags.
	 * @param stateFlags the state flags.
	 * @param out the out state list, optional.
	 * @return the state list.
	 */
	List<State<E>> getStates(int stateFlags, List<State<E>> out);
}
