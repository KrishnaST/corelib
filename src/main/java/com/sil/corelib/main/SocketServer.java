package com.sil.corelib.main;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.kst.corelib.datautils.ByteUtils;
import org.pmw.tinylog.Logger;

public class SocketServer {

	private static String port;


	public static void main(String[] args) {
		if (args.length > 0) port = args[0];
		else {
			Logger.info("Please provide port.");
			System.exit(0);
		}

		try (ServerSocket ssc = new ServerSocket(Integer.parseInt(port))) {
			Logger.info("Server Started.");
			while (true) {
				try {
					Socket sc = ssc.accept();
					new SocketProcessor(sc).start();
				}
				catch (IOException e) {Logger.error(e);}
			}
		}
		catch (Exception e) {
			Logger.error(e);
		}
	}

	public static class SocketProcessor extends Thread {

		private Socket sc;

		public SocketProcessor(Socket sc) {
			this.sc = sc;
		}

		public void run() {
			try {

				BufferedInputStream bin = null;
				try {
					bin = new BufferedInputStream(sc.getInputStream());
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
				StringBuilder sb = new StringBuilder();
				int i = 0;
				while ((i = bin.read()) != -1) {
					sb.append(ByteUtils.byteToHexPair((byte) i));
				}
				System.out.println(sb);
				System.err.println("Sleeping for 30 Sec");
				Thread.sleep(300000);
				System.err.println("Out of Sleep");
			}
			catch (Exception e) {
				Logger.error(e);
			}
		}
	}
}
