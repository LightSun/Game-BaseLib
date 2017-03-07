package com.heaven7.java.game.base.state;

public abstract class CatState2 implements State<Cat>{
	
	public static final CatState2 Eat = new CatState2(){
		@Override
		protected String getStateName(Cat cat) {
			return "eat   --> " + cat.eat;
		}
		@Override
		public void update(Cat entity) {
			entity.eat++;
			super.update(entity);
		}
	};
	public static final CatState2 Sleep = new CatState2(){
		@Override
		protected String getStateName(Cat cat) {
			return "Sleep --> " + cat.sleep;
		}
		@Override
		public void update(Cat entity) {
			entity.sleep++;
			super.update(entity);
		}
	};
	public static final CatState2 Run = new CatState2(){
		@Override
		protected String getStateName(Cat cat) {
			return "Run   -- > " + cat.run;
		}
		@Override
		public void update(Cat entity) {
			entity.run++;
			super.update(entity);
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
