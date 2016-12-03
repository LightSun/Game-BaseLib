package com.heaven7.java.game.base.state;

public class StateTest {
	
	public static void main(String[] args) {
		//test1();
		test2();
		//testInternal();
	}

	/**
	 * test state machine to support multi state at the same time.
	 */
	private static void test2() {
		StateMachine<Cat> machine = new Cat(){

			protected StateProvider<Cat> createStateProvider() {
				return new CatStateProvider2();
			}

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

	private static void testInternal() {
		int a = 1 | 2 | 4;
		//System.out.println( a & ~15); // 0
		//System.out.println( a & ~6); // 1
		//System.out.println( 15 & ~7);
		// 1001,  0011
	/*	System.out.println( 9 & ~3); //8
		// 1001,  0101
		System.out.println( 9 & ~5); //8
		// 1001,  0111
		System.out.println( 9 & ~7); //8
        //  0011, 1001
		System.out.println( 3 & ~9); //2
		//  0101, 1001
		System.out.println( 5 & ~9); //4
		//  0111, 1001
		System.out.println( 7 & ~9); //6*/
		int newS = 7; //1 2 4
		int oldS = 9; // 1  8
		int share = newS & oldS;
		int enter = newS & ~share;
		int exit = oldS & ~share;
		System.out.println( "share = " + share + " , enter = " + enter + " ,exit = " + exit);
	}


}
