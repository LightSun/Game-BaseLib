package com.heaven7.java.game.base.state;

public class StateTest {
	
	public static void main(String[] args) {
		//test1();
		test2();
	}
	
	/**
	 * test state machine to support multi state at the same time.
	 */
	private static void test2() {
		StateMachine<Cat> machine = new Cat(){
			
			public StateProvider<Cat> createStateProvider() {
				return new CatStateProvider2();
			};
			
		}.getStateMachine();
		
		machine.setInitialState(CatStateProvider.STATE_EAT);
		machine.setStateTransformer(new CatStateTransformer());
		
		int i = 1;
		while (i < 100) {
			machine.update();
			i++;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * test single state
	 */
	private static void test1() {
		Cat mCat = new Cat();
		mCat.getStateMachine().setInitialState(CatStateProvider.STATE_EAT);
		
		int i = 1;
		while (i < 100) {
			mCat.getStateMachine().update();
			i++;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("=============== begin test revert ================");
		while(mCat.getStateMachine().revertToPreviousState()){
			mCat.getStateMachine().update();
		}
	}
	

}
