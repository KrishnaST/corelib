package org.kst.corelib.iso8583;


public class Test {

	public static void main(String[] args) {
		ISOMessage isoMessage = ISOMessage.unPack("iso87", "02007238648129E190081660714300013096730110000000001000000423012201005053065201042360113560210006810005376071430001309673=20095200740000000000711306005053520ATM0101 CNSB           SHIVSAGARCMPLX SHOP12 BHANDUP W MUM MHIN011051005ATM0135618AE8D4FF375C71F04112021222003300040007800000000000000000000");
		System.out.println(isoMessage);
	}
}
