package org.kst.corelib.tlv;

import java.util.Map;
import java.util.TreeMap;

import org.kst.corelib.datautils.ByteHexUtils;
import org.pmw.tinylog.Logger;

public class BERTLV {
	
	public static enum ENCODING{
		HEX,
		ASCII
	}
	
	private TreeMap<String, String> tlvMap = new TreeMap<>();
	private ENCODING encoding;
	
	public static BERTLV parseTLV(String tlvString, ENCODING encoding){
		BERTLV bertlv = new BERTLV();
		bertlv.encoding = encoding;
		StringBuilder tlvBuilder = new StringBuilder(tlvString);
		
		String tagName = "";
		String tagValue = "";
		
		for(int i=0; tlvBuilder.length() > 0; i=i+2){
			try {
				String namePartPair = tlvBuilder.substring(0,2);
				tlvBuilder.delete(0, 2);
				tagName = tagName + namePartPair;
				if(((ByteHexUtils.hexPairToByte(namePartPair) & 0x1F) == 0x1F) && tagName.length() < 4) continue;
				else{
					String lengthPartPair = tlvBuilder.substring(0,2);
					tlvBuilder.delete(0, 2);
					int length = ByteHexUtils.hexPairToByte(lengthPartPair);
					if((length & 0x80) != 0) {
						lengthPartPair = tlvBuilder.substring(0,2);
						System.out.println(lengthPartPair);
						tlvBuilder.delete(0, 2);
						length = ByteHexUtils.hexPairToByte(lengthPartPair);
					}
					tagValue = tlvBuilder.substring(0, length*2);
					tlvBuilder.delete(0, length*2);
					if(encoding == BERTLV.ENCODING.ASCII) bertlv.tlvMap.put(tagName, new String(ByteHexUtils.hexStringToByteArray(tagValue)));
					else bertlv.tlvMap.put(tagName, tagValue);
				}
			}
			catch (Exception e) {Logger.error(e);return bertlv;}
			tagName = "";
			tagValue = "";
		}
		
		return bertlv;		
	}
	
	
	public ENCODING getEncoding() {
		return encoding;
	}
	
	public String get(String key){
		return tlvMap.get(key);
	}
	
	public TreeMap<String, String> getTlvMap() {
		return tlvMap;
	}

	@Override
	public String toString() {
		int k = 0;
		StringBuilder sb = new StringBuilder("");
		sb.append(String.format("%116s", "").replace(' ', '-')+"\r\n");
		
		for (Map.Entry<String, String> entry : tlvMap.entrySet()) {  
			if(k%2 == 0) sb.append("| "+String.format("%4s",entry.getKey())+" |"+String.format("%-50s", entry.getValue())+"|");
			else{
				sb.append(String.format("%4s",entry.getKey())+" |"+String.format("%-50s", entry.getValue())+"|"+"\r\n");
			}
			k++;
		}
		sb.append(String.format("%116s", "").replace(' ', '-')+"\r\n");
		return sb.toString();
	}

	
	private StringBuilder toStringSimple() {
		StringBuilder sb = new StringBuilder(); 
		for (Map.Entry<String, String> entry : tlvMap.entrySet()) {  
				sb.append(String.format("%4s",entry.getKey())+" | "+String.format("%-50s", entry.getValue())+" |"+"\r\n");
		}
		return sb;
	}
	
	private static long byteToUnsignedLong(byte b){
		return ((int) b) & 0xff;
	}
	
	private static long byteToUnsignedLong(byte b1, byte b2){
		int i1 = ((int) b1) & 0xff;
		int i2 = ((int) b2) & 0xff;
		return i1*256+i2;
	}
	
	private static long byteToUnsignedLong(byte b1, byte b2, byte b3){
		int i1 = ((int) b1) & 0xff;
		int i2 = ((int) b2) & 0xff;
		int i3 = ((int) b3) & 0xff;
		return i1*65536+i2*256+i3;
	}
	
	private static long byteToUnsignedLong(byte b1, byte b2, byte b3, byte b4){
		long i1 = ((long) b1) & 0xff;
		long i2 = ((long) b2) & 0xff;
		long i3 = ((long) b3) & 0xff;
		long i4 = ((long) b4) & 0xff;
		return i1*16777216L + i2*65536 + i3*256 + i4;
	}
	
	public static void main(String[] args) {
		Logger.info("\n"+BERTLV.parseTLV("e979d21306112233445530000000000000000000000000d21300112233445530000000000000000000000000e14ddf100103d21300112233445530000000000000000000000000d21362112233445530000000000000000000000000d3027600df110102d40377800cd5106e7deb17d68338a56e7deb17d68338a5", ENCODING.HEX).toStringSimple());
	}
}
