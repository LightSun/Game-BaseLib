package com.heaven7.java.game.base.state;

import java.util.List;

/**
 * the state provider. provide the state.
 * @author heaven7
 *
 * @param <E> the owner
 */
public interface StateProvider<E>{
	
	/**
	 * get states by target state flags. you should provide a unique State for every flag with
	 * the same Provider.
	 * @param stateFlags the state flags.
	 * @param out the out state list, optional.
	 * @return the state list.
	 */
	List<State<E>> getStates(int stateFlags, List<State<E>> out);
}
