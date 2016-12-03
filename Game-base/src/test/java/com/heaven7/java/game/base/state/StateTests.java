package com.heaven7.java.game.base.state;

import junit.framework.TestCase;

public class StateTests extends TestCase {

	private StateMachine<Cat> machine ;

	@Override
	protected void setUp() throws Exception {
		//machine = new DefaultStateMachine<Cat>(new Cat(), new CatStateProvider2());
		machine = new StackStateMachine<Cat>(new Cat(), new CatStateProvider2());
		machine.setInitialState(CatStateProvider.STATE_EAT);
	}
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRemoveState() {
		machine.setInitialState(CatStateProvider.STATE_EAT | CatStateProvider.STATE_RUN);
		machine.update();

		System.out.println("=========== begin remove sleep ==========");
		assertFalse( machine.removeState(CatStateProvider.STATE_SLEEP ));
		machine.update();

		System.out.println("=========== begin remove run with sleep ==========");
		assertTrue(machine.removeState(CatStateProvider.STATE_RUN | CatStateProvider.STATE_SLEEP));
		machine.update();
	}

	public void testAddState() {
		/*StateMachine machine = new Cat(){
			protected StateProvider<Cat> createStateProvider() {
				return new CatStateProvider2();
			}

		}.getStateMachine();*/
		machine.setInitialState(CatStateProvider.STATE_EAT | CatStateProvider.STATE_RUN);
		machine.update();

		System.out.println("=========== begin add run ==========");
		assertFalse( machine.addState(CatStateProvider.STATE_RUN ));
		machine.update();

		System.out.println("=========== begin add run with sleep ==========");
		assertTrue(machine.addState(CatStateProvider.STATE_RUN | CatStateProvider.STATE_SLEEP));
		machine.update();
	}

	public void testStateTransformer() {
		StateMachine machine = new Cat(){

			protected StateProvider<Cat> createStateProvider() {
				return new CatStateProvider2();
			}

		}.getStateMachine();

		machine.setInitialState(CatStateProvider.STATE_EAT);
		machine.setStateTransformer(new CatStateTransformer());

		int i = 1;
		while (i < 30) {
			machine.update();
			i++;
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	/** test toggle single state   */
	public void testSingleStateChange() {
		//Cat cat = new Cat();
		Dog cat = new Dog();
		cat.getStateMachine().setInitialState(CatStateProvider.STATE_EAT);
		int i = 1;
		while (i < 30) {
			cat.getStateMachine().update();
			i++;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("=============== begin test revert ================");
		while(cat.getStateMachine().revertToPreviousState()){
			cat.getStateMachine().update();
		}
	}
}
