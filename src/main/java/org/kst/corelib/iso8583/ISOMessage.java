package org.kst.corelib.iso8583;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.pmw.tinylog.Logger;


public class ISOMessage {
	
	private static final DateTimeFormatter YDDDHH = DateTimeFormatter.ofPattern("YYDHH");
	private static final DateTimeFormatter MMddHHmmss = DateTimeFormatter.ofPattern("MMddHHmmss");
	private static final DateTimeFormatter HHmmss = DateTimeFormatter.ofPattern("HHmmss");
	private static final DateTimeFormatter MMdd = DateTimeFormatter.ofPattern("MMdd");
	private LocalDateTime dateTime = LocalDateTime.now();
	
	private final int[] length;
	private final String[] type;
	private final String[] name;
	
	private String[] data = new String[129];	
	
	private ISOMessage(int[] length, String[] type, String[] name) {
		this.length = length;
		this.type 	= type;
		this.name	= name;
		
		Arrays.fill(data, "");
		
	}

	public static ISOMessage getMessage(String isoformat)
	{
		ISOFormat format = ISOFormatReader.readFormat(isoformat);
		if(format == null){
			Logger.error("ISOFormat "+isoformat+".xml Not Found");
			return null;		
		}
		
		ISOMessage isoMessage = new ISOMessage(format.length, format.type, format.name);
		isoMessage.put(7, isoMessage.dateTime.format(MMddHHmmss));
		isoMessage.put(12, isoMessage.dateTime.format(HHmmss));
		isoMessage.put(13, isoMessage.dateTime.format(MMdd));
		isoMessage.put(4, 0);
		return isoMessage;
	}
	
	
	public void put(int position, int intValue)
	{
		try
		{
			if(type[position].equals("NUM"))
			{
				if((intValue+"").length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+intValue);
				data[position] = String.format("%0"+length[position]+"d", intValue);
			}
			else if(type[position].equals("AMOUNT"))
			{
				if((intValue+"").length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+intValue);
				data[position] = String.format("%0"+length[position]+"d", intValue*100);
			}
			else if(type[position].equals("IFA_AMOUNT"))
			{
				if((intValue+"").length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+intValue);
				data[position] = String.format("%0"+length[position]+"d", intValue*100);
			}
			else if(type[position].equals("CHAR"))
			{
				if((intValue+"").length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+intValue);
				data[position] = String.format("%-"+length[position]+"s", ""+intValue);
			}
			else if(type[position].equals("LLNUM"))
			{
				data[position] =""+intValue;
			}
			else if(type[position].equals("LLCHAR"))
			{
				data[position] = ""+intValue;
			}
			else if(type[position].equals("LLLCHAR"))
			{
				data[position] = ""+intValue;
			}
			else
			{
				Logger.error("Invalid Type at "+position+"Type :"+type[position]);
				throw new ISO8583Exception("Invalid Type at "+position+"Type :"+type[position]);	
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void put(int position, long longValue)
	{
		try
		{
			if(type[position].equals("NUM"))
			{
				if((longValue+"").length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+longValue);
				data[position] = String.format("%0"+length[position]+"d", longValue);
			}
			else if(type[position].equals("AMOUNT"))
			{
				if((longValue+"").length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+longValue);
				data[position] = String.format("%0"+length[position]+"d", longValue*100);
			}
			else if(type[position].equals("IFA_AMOUNT"))
			{
				if((longValue+"").length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+longValue);
				data[position] = String.format("%0"+length[position]+"d", longValue*100);
			}
			else if(type[position].equals("CHAR"))
			{
				if((longValue+"").length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+longValue);
				data[position] = String.format("%-"+length[position]+"s", ""+longValue);
			}
			else if(type[position].equals("LLNUM"))
			{
				data[position] = ""+longValue;
			}
			else if(type[position].equals("LLCHAR"))
			{
				data[position] = ""+longValue;
			}
			else if(type[position].equals("LLLCHAR"))
			{
				data[position] = ""+longValue;
			}
			else
			{
				Logger.error("Invalid Type at "+position+"Type :"+type[position]);
				throw new ISO8583Exception("Invalid Type at "+position+"Type :"+type[position]);	
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void put(int position, double doubleValue)
	{
		try
		{
			if(type[position].equals("AMOUNT"))
			{
				data[position] = String.format("%0"+length[position]+"d", (long)(doubleValue*100));
			}
			else if(type[position].equals("IFA_AMOUNT"))
			{
				data[position] = String.format("%0"+length[position]+"d", (long)(doubleValue*100));
			}
			else
			{
				Logger.error("Invalid Type at "+position+"Type :"+type[position]);
				throw new ISO8583Exception("Invalid Type at "+position+"Type :"+type[position]);	
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void put(int position, String string)
	{
		try
		{
			if(type[position].equals("NUM"))
			{
				if(string.length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+string);
				data[position] = String.format("%0"+length[position]+"d", Integer.parseInt(string));
			}
			else if(type[position].equals("AMOUNT"))
			{
				if(string.length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+string);
				data[position] = String.format("%0"+length[position]+"d", Double.parseDouble(string)*100);
			}
			else if(type[position].equals("IFA_AMOUNT"))
			{
				if(string.length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+string);
				data[position] = String.format("%0"+length[position]+"d", Double.parseDouble(string)*100);
			}
			else if(type[position].equals("CHAR"))
			{
				if(string.length() > length[position]) throw new ISO8583Exception("Length Exceeds Capacity of Field at "+position+" and Value : "+string);
				data[position] = String.format("%-"+length[position]+"s", ""+string);
			}
			else if(type[position].equals("LLNUM"))
			{
				data[position] = string;
			}
			else if(type[position].equals("LLCHAR"))
			{
				data[position] = string;
			}
			else if(type[position].equals("LLLCHAR"))
			{
				data[position] = string;
			}
			else
			{
				Logger.error("Invalid Type at "+position+"Type :"+type[position]);
				throw new ISO8583Exception("Invalid Type at "+position+"Type :"+type[position]);	
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public String get(int position)
	{
		try
		{
			if(position > 128) throw new ISO8583Exception("Invalid Data Element Requested at Position "+position);
			else
			{
				return data[position];
			}
		} catch (Exception e) {e.printStackTrace();}
		return data[position];
	}
	
	public double getAmount(int position)
	{
		try
		{
			if(position > 128 || !type[position].equals("AMOUNT") || !type[position].equals("IFA_AMOUNT")) throw new ISO8583Exception("Invalid Data Element Requested at Position "+position);
			else
			{
				return Double.parseDouble(data[position])/100;
			}
		} catch (Exception e) {e.printStackTrace();}
		return 0;
	}
	
	public String pack()
	{
		StringBuilder bitmap = new StringBuilder("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		StringBuilder iso = new StringBuilder("");
		String secmap = "";
		String primap = "";
		try
		{
			for(int i=2; i<data.length; i++)
			{
				if(!isNullOrEmpty(data[i]))
				{
					bitmap.setCharAt(i-1, '1');
					if(type[i].equals("LLNUM") || type[i].equals("LLCHAR")) iso.append(String.format("%02d", data[i].length()));
					else if(type[i].equals("LLLCHAR")) iso.append(String.format("%03d", data[i].length()));
					iso.append(data[i]);
				}
			}
			
			secmap = binaryToHex(bitmap.substring(64));
			if(secmap.equals("0000000000000000")) secmap = "";
			else bitmap.setCharAt(0, '1');
			primap = binaryToHex(bitmap.substring(0, 64));
		} catch (Exception e) {	e.printStackTrace(); }
		return data[0]+primap.toUpperCase()+secmap.toUpperCase()+iso.toString();
	}
	
	public static ISOMessage unPack(String isoformat, String isoString)
	{
		ISOMessage isoMessage = null;
		try
		{
			ISOFormat format = ISOFormatReader.readFormat(isoformat);
			if(format == null){
				Logger.error("ISOFormat "+isoformat+".xml Not Found");
				return null;		
			}
			
			isoMessage = new ISOMessage(format.length, format.type, format.name);
			for(int i=0; i<format.type.length; i++){
				System.out.print("type["+i+"] = ");
				if(format.type[i].equals("NUM")) System.out.print("DataType.NUM;");
				if(format.type[i].equals("LLNUM")) System.out.print("DataType.LLNUM;");
				if(format.type[i].equals("LLLNUM")) System.out.print("DataType.LLLNUM;");
				if(format.type[i].equals("CHAR")) System.out.print("DataType.CHAR;");
				if(format.type[i].equals("AMOUNT")) System.out.print("DataType.NUM;");
				if(format.type[i].equals("IFA_AMOUNT")) System.out.print("DataType.NUM;");
				if(format.type[i].equals("LLCHAR")) System.out.print("DataType.LLCHAR;");
				if(format.type[i].equals("LLLCHAR")) System.out.print("DataType.LLLCHAR;");
				if(format.type[i].equals("AMOUNT")) System.out.print("DataType.NUM;");
				
				System.out.println();
				
				System.out.print("length["+i+"] = ");
				System.out.print(format.length[i]+";");
				System.out.println();
				System.out.println();
			}
			
			isoMessage.unPack(isoString);
			
		} catch (Exception e) {e.printStackTrace();	}
		return isoMessage;
	}
	
	private void unPack(String isoString)
	{
		StringBuilder sb = new StringBuilder(isoString);
		try
		{
			data[0] = sb.substring(0, 4);
			sb.delete(0, 4);
			String primap = hexToBinary(sb.substring(0, 16));
			sb.delete(0, 16);
			String secmap = "";
			if(primap.charAt(0) == '1')
			{
				secmap = hexToBinary(sb.substring(0, 16));
				sb.delete(0, 16);
				if(secmap.equals("0000000000000000")) secmap = "";
			}
			String bitmap = primap+secmap;
			for(int i=2; i<bitmap.length()+1; i++)
			{
				if(bitmap.charAt(i-1) == '1')
				{
					if(type[i].equals("NUM"))
					{
						data[i] = sb.substring(0, length[i]);
						sb.delete(0, length[i]);
					}
					else if(type[i].equals("AMOUNT"))
					{
						data[i] = sb.substring(0, length[i]);
						sb.delete(0, length[i]);
					}
					else if(type[i].equals("IFA_AMOUNT"))
					{
						data[i] = sb.substring(0, length[i]);
						sb.delete(0, length[i]);
					}
					else if(type[i].equals("CHAR"))
					{
						data[i] = sb.substring(0, length[i]);
						sb.delete(0, length[i]);
					}
					else if(type[i].equals("LLNUM"))
					{
						int len = Integer.parseInt(sb.substring(0, 2));
						sb.delete(0, 2);
						data[i] = sb.substring(0, len);
						sb.delete(0, len);
					}
					else if(type[i].equals("LLCHAR"))
					{
						int len = Integer.parseInt(sb.substring(0, 2));
						sb.delete(0, 2);
						data[i] = sb.substring(0, len);
						sb.delete(0, len);
					}
					else if(type[i].equals("LLLCHAR"))
					{
						int len = Integer.parseInt(sb.substring(0, 3));
						sb.delete(0, 3);
						data[i] = sb.substring(0, len);
						sb.delete(0, len);
					}
					else
					{
						throw new ISO8583Exception("Invalid Type at "+i+"Type :"+type[i]);	
					}
					//System.out.println(i+" : "+length[i]+" : "+type[i]+" : "+data[i]);
				}
			}
			if(!isNullOrEmpty(sb.toString())) throw new ISO8583Exception("Invalid ISO8583 Message Remaining Part : "+sb.toString());
		} catch (Exception e) 
		{
			e.printStackTrace();
			return;
		}
	}
		
	private static String binaryToHex(String binaryString)
	{
		return String.format("%16s", new BigInteger(binaryString, 2).toString(16)).replace(' ', '0');
	}
	
	private static String hexToBinary(String hexString)
	{
		return String.format("%64s", new BigInteger(hexString, 16).toString(2)).replace(' ', '0');
	}
	
	private static class ISO8583Exception extends Exception
	{
		private static final long serialVersionUID = -7821019359535906016L;

		public ISO8583Exception(String message)
		{
			super(message);
			Logger.error(message);
		}
	}
	
	private static boolean isNullOrEmpty(String string)
	{
		if(string == null || string.trim().length() == 0) return true;
		else return false;
	}
	
	public String print()
	{
		StringBuilder sb = new StringBuilder("");
		sb.append(" ---------------------------------------------------------------------------------------------------------------------------------------------------\r\n");
		for(int i=2; i<128; i++)
		{
			if(!isNullOrEmpty(data[i]))sb.append(" | "+String.format("%-10s", "Data["+i+"]") + " : "+ String.format("%-50s", data[i]) + " : "+name[i]+"\r\n");
		}
		sb.append(" ---------------------------------------------------------------------------------------------------------------------------------------------------\r\n");
		
		return sb.toString();		
	}
	
	@Override
	public String toString() {
		int k=0;
		StringBuilder sb = new StringBuilder("");
		sb.append(this.pack()+"\n");
		sb.append(String.format("%172s", "").replace(' ', '-')+"\r\n");
		for(int i=0; i< data.length; i++)
		{
			if(!isNullOrEmpty(data[i]))
			{
				sb.append("| ");
				sb.append("DE "+String.format("%03d", i)+" : ");
				sb.append(String.format("%-45s",data[i])+format(name[i]));
				if(k%2 == 1) sb.append("|\n");
				k++;
			}
		}
		if(k%2 == 1) {
			//sb.append("\n");
			sb.append("| ");
			sb.append(String.format("%9s", "")+"");
			sb.append(String.format("%-45s","")+format(""));
			sb.append("|\n");
			k++;
		
		}
		sb.append(String.format("%172s", "").replace(' ', '-')+"\r\n");
		return sb.toString();
	}
	
	private static String format(String string)
	{
		if(string.length() > 30) string = string.substring(0, 30);
		else string = String.format("%-30s", string);
		return string;
	}
	
	public String getRRN(String stan)
	{
		return dateTime.format(YDDDHH).substring(1)+String.format("%6s", stan).replace(' ', '0');
	}
	
	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

}
