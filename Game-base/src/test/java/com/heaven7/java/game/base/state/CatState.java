package com.heaven7.java.game.base.state;

/**
 * single state
 * @author don
 *
 */
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
				entity.getStateMachine().setState(CatStateProvider.STATE_SLEEP);
				entity.eat = 0;
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
				entity.getStateMachine().setState(CatStateProvider.STATE_RUN);
				entity.sleep = 0;
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
				entity.getStateMachine().setState(CatStateProvider.STATE_EAT);
				entity.run = 0;
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

	@Override
	public void reenter(Cat entity) {
		System.out.println(getStateName(entity)+": reenter()");
	}
}
