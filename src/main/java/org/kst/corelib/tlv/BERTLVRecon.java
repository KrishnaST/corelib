package org.kst.corelib.tlv;

import java.util.Map;
import java.util.TreeMap;

import org.kst.corelib.datautils.HexUtils;
import org.pmw.tinylog.Logger;

public class BERTLVRecon {
	
	public static enum ENCODING{
		HEX,
		ASCII
	}
	
	private TreeMap<String, String> tlvMap = new TreeMap<>();
	private ENCODING encoding;
	
	public static BERTLVRecon parseTLV(String tlvString, ENCODING encoding){
		BERTLVRecon bertlv = new BERTLVRecon();
		bertlv.encoding = encoding;
		StringBuilder tlvBuilder = new StringBuilder(tlvString);
		
		String tagName = "";
		String tagValue = "";
		
		for(int i=0; i<tlvBuilder.length(); i=i+2){
			try {
				String namePartPair = tlvBuilder.substring(0,2);
				tlvBuilder.delete(0, 2);
				tagName = tagName + namePartPair;
				if(((HexUtils.hexPairToByte(namePartPair) & 0x1F) == 0x1F) && tagName.length() < 4) continue;
				else{
					String lengthPartPair = tlvBuilder.substring(0,2);
					tlvBuilder.delete(0, 2);
					if((tagName.equals("DF93") || tagName.equals("DF94") || tagName.equals("DF95")) && lengthPartPair.equals("81")){
						lengthPartPair = tlvBuilder.substring(0,2);
						tlvBuilder.delete(0, 2);
					}
					int length = HexUtils.hexPairToInt(lengthPartPair);
					tagValue = tlvBuilder.substring(0, length*2);
					tlvBuilder.delete(0, length*2);
					if(encoding == BERTLVRecon.ENCODING.ASCII) bertlv.tlvMap.put(tagName, new String(HexUtils.hexStringToByteArray(tagValue)));
					else bertlv.tlvMap.put(tagName, tagValue);
				}
			}
			catch (Exception e) {Logger.error(e);}
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
		StringBuilder sb = new StringBuilder("");
		sb.append(String.format("%160s", "").replace(' ', '-')+"\r\n");
		
		for (Map.Entry<String, String> entry : tlvMap.entrySet()) {  
			sb.append("| "+String.format("%4s",entry.getKey())+" |"+entry.getValue()+"\r\n");
		}
		sb.append(String.format("%160s", "").replace(' ', '-')+"\r\n");
		return sb.toString();
	}

	
	
	public static void main(String[] args) {
		System.out.println(BERTLVRecon.parseTLV("DF0B06303031303035DF2703353030DF891D32303137303531383039343032387C3230313730353138303934303239DF9203353730DF291036333435303030313031343536393638DF2A0F343530313039303033363830202020DF936F342C41582C414D45582C302C302C302C302C302C302C302C302C302C4D432C534142422C312C302E31302C302C302C302C302C312C312C302E31302C50312C534142422C302C302C302C302C302C302C302C302C302C56432C534142422C302C302C302C302C302C302C302C302C30DF946F342C41582C414D45582C302C302C302C302C302C302C302C302C302C4D432C534142422C312C302E31302C302C302C302C302C312C312C302E31302C50312C534142422C302C302C302C302C302C302C302C302C302C56432C534142422C302C302C302C302C302C302C302C302C30DF958184342C41582C414D45582C302C302C302C302C302C302C302C302C302C302C302C302C4D432C534142422C302C302C312C302E31302C302C302C302C302C302C302C302C302C50312C534142422C302C302C302C302C302C302C302C302C302C302C302C302C56432C534142422C302C302C302C302C302C302C302C302C302C302C302C30DF811000000000000000000000000000000000DF9146312E30317C416C205368617965652054726164696E677C9CE69F98A7C420B0A6CE987C48656164204F66666963657CAD97E6B3CEA7C420CFA6CEA8A7C47C7CD39FE6CEA7C421DF870453414242DF880435363531", ENCODING.ASCII));
	}
}
