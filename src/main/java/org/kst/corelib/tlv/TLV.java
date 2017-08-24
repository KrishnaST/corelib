package org.kst.corelib.tlv;

import java.util.Map;
import java.util.TreeMap;

public class TLV
{
	private TreeMap<String, String> tlvMap = new TreeMap<String, String>();
	
	public TLV(Map<String, String> tlvMap) {
		this.tlvMap = (TreeMap<String, String>) tlvMap;
	}
	
	public TLV()
	{
		
	}
	
	public TreeMap<String, String> parseTLV(String string)
	{
		StringBuilder sb = new StringBuilder(string);
		while(!sb.toString().isEmpty())
		{
			try
			{
				String key = sb.substring(0, 3);
				sb.delete(0, 3);
				int length = Integer.parseInt(sb.substring(0, 3));
				sb.delete(0, 3);
				String value = sb.substring(0, length);
				sb.delete(0, length);
				tlvMap.put(key, value);
				System.out.println("Key : "+key+"\tValue : "+value);
			} catch (Exception e) {
				e.printStackTrace();
				break;							
			}
		}
		return tlvMap;
	}
	
	public String buildTLV()
	{
		StringBuilder sb = new StringBuilder("");
		for (String key : tlvMap.keySet()) {
		   sb.append(key);
		   sb.append(String.format("%03d", tlvMap.get(key).length()));
		   sb.append(tlvMap.get(key));
		}
		return sb.toString();
	}
	
	public void put(String key, String value)
	{
		tlvMap.put(key, value);
	}
	
	public String get(String key)
	{
		return tlvMap.get(key);
	}
	
	public String remove(String key)
	{
		return tlvMap.remove(key);
	}
	
	public boolean containsKey(String key)
	{
		if(tlvMap.get(key) != null) return true;
		return false;
	}
	
	
	public TreeMap<String, String> getTlvMap() {
		return tlvMap;
	}

	public void setTlvMap(TreeMap<String, String> tlvMap) {
		this.tlvMap = tlvMap;
	}

	public static void main(String[] args) {
		TLV tlv = new TLV();

		tlv.parseTLV("9F090200999F1A0203569F1E009F3501229F3704A110AD3C9F2201088F01089F0802009A9F360200729F34030203009F3303E0F8C89F100706010A03A000025F2A020356950500800480009F2701809A031705129F26086C0BF8093BD8C0AB9F4104000009309F020600000000002582023C008407A00000000310109C01009B02E8009F0607A0000000031010A0 to kis056003MOB");
		System.out.println("TLV : "+tlv.buildTLV());
		System.out.println("Tag 000 : "+tlv.get("000"));
	}
}