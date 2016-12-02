package com.heaven7.java.game.base.state;

import org.junit.Test;

import junit.framework.TestCase;

public class StateJnitTest extends TestCase {

	private Cat mCat;

	@Override
	protected void setUp() throws Exception {
		mCat = new Cat();
		mCat.getStateMachine().setInitialState(CatStateProvider.STATE_EAT);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testSingleStateChange() {
		
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
	}
}
