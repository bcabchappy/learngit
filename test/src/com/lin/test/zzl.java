package com.lin.test;

public class zzl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			System.out.print("1");
			int i=1/0;
		}catch(Exception e){
			System.out.print("2");
		}finally{
			System.out.print("3");
		}
	}

}
