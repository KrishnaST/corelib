package org.kst.corelib.iso8583;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

import javax.print.StreamPrintService;

import org.pmw.tinylog.Logger;

public class ISOClient {	
	
	public static ISOMessage send(String string, String IP , int PORT)
	{
		ISOMessage response = null;
		try(Socket sc = new Socket("127.0.0.1", 9876);
			BufferedInputStream bin  = new BufferedInputStream(sc.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(sc.getOutputStream()))
		{
			Logger.info("Sending ISO : "+String.format("%04d", string.length())+string);			
			bos.write((String.format("%04d", string.length())+string).getBytes());
			bos.flush();
			
			int length = 0;
			length = length + (bin.read()-48)*1000;
			length = length + (bin.read()-48)*100;
			length = length + (bin.read()-48)*10;
			length = length + (bin.read()-48)*1;
			
			byte[] isoBytes = new byte[length];
			bin.read(isoBytes);
			String responseString = new String(isoBytes, "ASCII");
			Logger.info("Received ISO : "+responseString);
			response = ISOMessage.unPack("iso87", responseString);
		} catch (Exception e) {e.printStackTrace();}
		
		return response;
	}
	
	
	public static void main(String[] args) {

		ISOMessage ISOBuilder = ISOMessage.getMessage("iso87");
		ISOBuilder.put(0, "0200");
		ISOBuilder.put(2, "8227156009177369369");
		ISOBuilder.put(3, "430000");
		ISOBuilder.put(4, 60);
		ISOBuilder.put(7, "0502162230");
		ISOBuilder.put(11, "421551");
		ISOBuilder.put(12, "162230");
		ISOBuilder.put(13, "0502");
		ISOBuilder.put(15, "0502");
		ISOBuilder.put(17, "0502");
		ISOBuilder.put(18, "4814");
		ISOBuilder.put(32, "109235");
		ISOBuilder.put(37, "604884631255");
		ISOBuilder.put(41, "DSB18092");
		ISOBuilder.put(42, "DSB9994918092");
		ISOBuilder.put(49, "356");
		ISOBuilder.put(60, "2017201720172017");
		ISOBuilder.put(103, "000130010000007093");
		ISOBuilder.put(123, "IMPS");
		ISOBuilder.put(124, "INET");
		ISOBuilder.put(125,"IMPS|366|45|9552548278|9184|9177369369|8227|000130010000007093||TEST|INET");
		System.out.println(ISOBuilder.pack());
		//System.out.println(ISOBuilder);
		//System.out.println(ISOMessage.unPack("iso87", "0200F23AC00108C08010000000000201982216560091773693694300000000000060000502162230421551162230050205020502481406109235604884631255DSB18092DSB9994918092356016201720172017201718000130010000007093004IMPS004INET073IMPS|366|45|9552548278|9184|9177369369|8227|000130010000007093||TEST|INET"));;
	}
}
