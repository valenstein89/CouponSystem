package com.CouponSystemCore.util;
import java.util.Random;


/**
 * Class IDMaker has only one method 
 * that returns random ID of Type long.
 */
public class IDGenerator extends Random
{
	public static long makeNewID()
	{
		IDGenerator iDGen = new IDGenerator();
		long newID = Math.abs(iDGen.nextLong());
		return newID;
	}
}
