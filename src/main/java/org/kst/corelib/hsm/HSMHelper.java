package org.kst.corelib.hsm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import org.pmw.tinylog.Logger;

public class HSMHelper {

	private static String HSMIP = "10.100.5.21";
	private static int HSMPORT  = 6046;
	
	
	public static String execute(String hsmIP, int hsmPort, String hsmCommand) {
		Logger.trace("HSMCommand to "+hsmIP+":"+hsmPort+" : "+ hsmCommand);
		String response = null;
		try(Socket sc = new Socket(hsmIP, hsmPort);
			DataInputStream din = new DataInputStream(sc.getInputStream());
			DataOutputStream dos = new DataOutputStream(sc.getOutputStream());) {
			dos.writeUTF(hsmCommand);
			dos.flush();
			response = din.readUTF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger.trace("HSMResponse : "+response);
		return response;
	}

	public static String execute(String hsmCommand) {
		Logger.trace("HSMCommand  : "+hsmCommand);
		String response = null;
		try(Socket sc = new Socket(HSMIP, HSMPORT	);
			DataInputStream din = new DataInputStream(sc.getInputStream());
			DataOutputStream dos = new DataOutputStream(sc.getOutputStream());) {
			sc.setSoTimeout(5000);
			dos.writeUTF(hsmCommand);
			dos.flush();
			response = din.readUTF();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger.trace("HSMResponse : "+response);
		return (response == null) ? "99" : response;
	}
	
	public static byte[] execute(byte[]... byteArrays ) {
		byte[] response = null;
		try(Socket sc = new Socket(HSMIP, HSMPORT);
			BufferedInputStream bis = new BufferedInputStream(sc.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(sc.getOutputStream());) {
			for(byte[] bytes : byteArrays)	bos.write(bytes);
			bos.flush();
			int len = bis.read();
			len = bis.read();
			len = bis.available();
			response = new byte[len];
			bis.read(response);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	
	public static String getHSMIP() {
		return HSMIP;
	}

	
	public static void setHSMIP(String hSMIP) {
		HSMIP = hSMIP;
	}

	
	public static int getHSMPORT() {
		return HSMPORT;
	}

	
	public static void setHSMPORT(int hSMPORT) {
		HSMPORT = hSMPORT;
	}
	
	
}
