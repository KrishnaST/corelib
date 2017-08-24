import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.kst.corelib.crypt.TrippleDES;
import org.kst.corelib.crypt.TrippleDES.MODE;
import org.kst.corelib.crypt.TrippleDES.PADDING;

public class NewTest {
	public static void main(String[] args) {
		
		//System.out.println(HSMUtils.generateCVV("6071020002000049", "2407", "150D8C0DF3348295", "B75E6BCE8B0A1D07", "000"));
		//System.out.println(HSMUtils.generateCVV("6071020002000049", "2407", "150D8C0DF3348295", "B75E6BCE8B0A1D07", "620"));
		//System.exit(0);
		//System.out.println(PinBlock.getISO0PinBlock("1111", "4893772200442255"));
		try {
			System.out.println(TrippleDES.decrypt("c79eed5f0f2128d3", "A0A071E7DB39781ECCB1188153EE8790", MODE.ECB, PADDING.NoPadding));
		}
		catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		//HSMUtils.getPinOffset("0000008983290665", "1111", "4385B5DB5AEAF809");
		//HSMUtils.validatePinBlock_DUKPT("4893772200442255", "UB387DC23B416D398F17E431C3CB72B93", "4385B5DB5AEAF809", "110000F15CAD8800006F", "606C5A5E0CED68A2", "2269");
		
		//HSMUtils.getPinOffset("4135080750032932", "1234", "4385B5DB5AEAF809");
		//System.out.println(HSMHelper.execute("0000DAU2CB13C08469BB8500A65A0E65474AE4B4385B5DB5AEAF809120E368AC2CE27DB25010478708900000101234567890123455087870890N29132FFFFFFFF"));
	}
}	
