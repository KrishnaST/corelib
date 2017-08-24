package com.sil.corelib.test;

import org.kst.corelib.hsm.HSMUtils;

public class MaximusTest {
	
	public static void main(String[] args) {
		//String pinOffset = HSMUtils.getPinOffset("6078210100000032", "1111", "4385B5DB5AEAF809");
		//System.out.println("pinOffset : "+pinOffset);
		
		//String valTPK = HSMUtils.validatePinBlock_TPK("6078210100000032", "B533C68E4B553BA3", "2882", "UB9322774320AB911EC740326592F688C", "4385B5DB5AEAF809");
		
		String resp = HSMUtils.translatePinBlock_TPK_ZPK("6078210100000032", "11BB2E77E55B95CA", "UB9322774320AB911EC740326592F688C", "UB27EC3FAB16D5D4D0DFCC5C3246776E3");
		System.out.println(resp);
		
		//resp = HSMUtils.validatePinBlock_ZPK("6078210100000032", "4B51036BB03C63EC", pinOffset, "4385B5DB5AEAF809", "U401770057601955CD7797A450474C4E1");
	}
}
