package org.kst.corelib.crypt;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RSA {
	
	public static boolean generateRSAKeyPair(int keyLength)
	{
		try {
			KeyPairGenerator keyKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyKeyPairGenerator.initialize(1024);
			KeyPair keyPair = keyKeyPairGenerator.genKeyPair();
			Key publicKey  = keyPair.getPublic();
			Key privateKey = keyPair.getPrivate();
			FileOutputStream fosPublic = new FileOutputStream(new File("C:/public.key"));
			FileOutputStream fosPrivate = new FileOutputStream(new File("C:/private.key"));
			fosPrivate.write(privateKey.getEncoded());
			fosPrivate.flush();
			fosPrivate.close();
			
			fosPublic.write(publicKey.getEncoded());
			fosPublic.flush();
			fosPublic.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		generateRSAKeyPair(1024);
	}
}
