package com.heaven7.java.game.base.state;

public class StateTest {
	
	public static void main(String[] args) {
		testInternal();
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

		System.out.println( (15 & 7) == 7);
		//System.out.println( (8 & 7) == 7);
	}


}
