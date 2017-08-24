package org.kst.corelib.iso8583;

import java.util.Arrays;

public class ISOFormat {
	public final int[] length;
	public final String[] name;
	public final String[] type;	
	
	public ISOFormat(int[] length, String[] type, String[] name) {
		this.length = length;
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString() {
		return "ISOFormat [length=" + Arrays.toString(length) + "\nname=" + Arrays.toString(name) + "\ntype="
				+ Arrays.toString(type) + "]";
	}
	
	
}
