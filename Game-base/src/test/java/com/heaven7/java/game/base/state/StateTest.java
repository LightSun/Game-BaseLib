package com.heaven7.java.game.base.state;

public class StateTest {
	
	public static void main(String[] args) {
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
