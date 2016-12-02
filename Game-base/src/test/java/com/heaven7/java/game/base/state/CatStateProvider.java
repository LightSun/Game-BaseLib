package com.heaven7.java.game.base.state;

import java.util.ArrayList;
import java.util.List;


public class CatStateProvider implements StateProvider<Cat> {
	
	public static final int STATE_RUN    = 1 ;
	public static final int STATE_EAT    = 1 << 1 ;
	public static final int STATE_SLEEP  = 1 << 2 ;

	@Override
	public List<State<Cat>> getStates(int stateFlags, List<State<Cat>> out) {
		if(out == null ){
			out = new ArrayList<State<Cat>>();
		}else{
			out.clear();
		}
		if((stateFlags & STATE_EAT ) != 0){
			out.add(CatState.Eat);
		}
		if((stateFlags & STATE_RUN ) != 0){
			out.add(CatState.Run);
		}
		if((stateFlags & STATE_SLEEP ) != 0){
			out.add(CatState.Sleep);
		}
		return out;
	}

}
