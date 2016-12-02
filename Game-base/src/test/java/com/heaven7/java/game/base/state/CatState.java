package com.heaven7.java.game.base.state;

import com.heaven7.java.game.base.state.State;

public abstract class CatState implements State<Cat>{
	
	public static final CatState Eat = new CatState(){
		@Override
		protected String getStateName(Cat cat) {
			return "eat";
		}
		@Override
		public void update(Cat entity) {
			super.update(entity);
			entity.eat++;
			if(entity.eat >=3){
				entity.eat = 0;
				entity.getStateMachine().changeState(CatStateProvider.STATE_SLEEP);
			}
		}
	};
	public static final CatState Sleep = new CatState(){
		@Override
		protected String getStateName(Cat cat) {
			return "Sleep";
		}
		@Override
		public void update(Cat entity) {
			super.update(entity);
			entity.sleep++;
			if(entity.sleep >=10){
				entity.sleep = 0;
				entity.getStateMachine().changeState(CatStateProvider.STATE_RUN);
			}
		}
	};
	public static final CatState Run = new CatState(){
		@Override
		protected String getStateName(Cat cat) {
			return "Run";
		}
		@Override
		public void update(Cat entity) {
			super.update(entity);
			entity.run++;
			if(entity.run >=5){
				entity.run = 0;
				entity.getStateMachine().changeState(CatStateProvider.STATE_EAT);
			}
		}
	}
	;

	protected abstract String getStateName(Cat cat);
	
	@Override
	public void enter(Cat entity) {
		System.out.println(getStateName(entity)+": enter()");
	}

	@Override
	public void update(Cat entity) {
		System.out.println(getStateName(entity)+": update()");
	}

	@Override
	public void exit(Cat entity) {
		System.out.println(getStateName(entity)+": exit()");
	}

}
