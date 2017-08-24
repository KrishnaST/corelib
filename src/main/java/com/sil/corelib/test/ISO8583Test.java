package com.sil.corelib.test;

import org.kst.corelib.iso8583.ISOMessage;

public class ISO8583Test {
	
	public static void main(String[] args) {
		ISOMessage isoMessage = ISOMessage.unPack("iso87", "0200F23AC40128E1901000000000040000001660779901101032463100000000000000000615184328240762001328061606160616601102106800027376077990110103246=21116203970000000000716700318884CNM8022         CNM8022        +BOI MUMBRA EAST       THANEI       MHIN044CASHNET                 4000003560000000000035626ADAB518586CFC9012EUROPRO1+000180011SB    00007824");
		System.out.println(isoMessage);
		
		
		/*isoMessage = ISOMessage.getMessage("iso87");
		isoMessage.put(0, "0200");
		isoMessage.put(2, "4135080750032932");
		isoMessage.put(3, "000000");
		isoMessage.put(4, 10);
		isoMessage.put(11, "123456");
		isoMessage.put(37, "123456789012");
		isoMessage.put(39, "00");
		isoMessage.put(102, "33317489861");
		isoMessage.put(103, "62282017341");
		isoMessage.put(104, "8983290664");
		System.out.println(isoMessage.pack());
		System.out.println(isoMessage);*/
	}
}
