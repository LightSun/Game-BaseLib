package com.heaven7.java.game.base.state;

import com.heaven7.java.game.base.state.StateMachine.StateTransformer;
import static com.heaven7.java.game.base.state.CatStateProvider.*;

public class CatStateTransformer implements StateTransformer<Cat> {

	@Override
	public void transformState(StateMachine<Cat> machine, Cat e) {
		
		//this is just a sample. you can change the state as you want.
		int sum = e.eat + e.run + e.sleep;
		
		String msg = "";
		int state = 0;
		
		if (sum % (STATE_EAT | STATE_RUN | STATE_SLEEP) == 0) {
			
			msg = "STATE_EAT | STATE_RUN | STATE_SLEEP ";
			state = STATE_EAT | STATE_RUN | STATE_SLEEP;
			
		} else if (sum % (STATE_EAT | STATE_SLEEP) == 0) {
			
			msg = "STATE_EAT | STATE_SLEEP ";
			state = STATE_EAT | STATE_SLEEP;
			
		}else if (sum % (STATE_RUN | STATE_SLEEP) == 0) {
			
			msg = "STATE_RUN | STATE_SLEEP ";
			state = STATE_RUN | STATE_SLEEP;
			
		} else if (sum % (STATE_EAT | STATE_RUN) == 0) {
			
			msg = "STATE_EAT | STATE_RUN ";
			state = STATE_EAT | STATE_RUN;
			
		} else if ((sum % STATE_SLEEP) == 0) {
			
			msg = "STATE_SLEEP ";
			state = STATE_SLEEP ;
			
		}else if ((sum % STATE_EAT) == 0) {
			
			msg = "STATE_EAT ";
			state = STATE_EAT ;
			
		}else {
			
			msg = "STATE_RUN ";
			state = STATE_RUN ;
		}
		
		System.out.println("CatStateTransformer: new state = " + msg );
		machine.changeState(state);
	}

}
