package TestSecpApplet ;

import javacard.framework.*;
import javacard.framework.*;
import com.ftsafe.javacardx.btcmgr.CoinManager;

public class TestSecpApplet extends Applet
{

	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new TestSecpApplet().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
	}

	public void process(APDU apdu)
	{
		short ret = 0;
		if (selectingApplet()) {
			ret = CoinManager.setCurve(CoinManager.CURVE_SECP_256_K1_HC); 
	    
			if (ret != CoinManager.SEC_TRUE) {
				ISOException.throwIt((short)0x6F02);
			}
			return;
		}
		
		byte[] ROOT_HD_PATH = new byte[] {0x6D, 0x2F, 0x34, 0x34, 0x27, 0x2F, 0x31, 0x37, 0x31, 0x27, 0x2F, 0x30, 0x27, 0x2F, 0x30, 0x2F, 0x30};
	    byte[] ROOT_HD_PATH_IN_LV = new byte[] {0x11, 0x6D, 0x2F, 0x34, 0x34, 0x27, 0x2F, 0x31, 0x37, 0x31, 0x27, 0x2F, 0x30, 0x27, 0x2F, 0x30, 0x2F, 0x30};	  
	    	  
	    	     
		byte[] tmpout=new byte[2048];

		byte[] buf = apdu.getBuffer();
		short lc = apdu.setIncomingAndReceive();
		short lcOff=apdu.getOffsetCdata();
	
		
	
		switch (buf[ISO7816.OFFSET_INS])
		{
		case (byte)0x00:
			byte[] dataInLV = new byte[]{0x20, 0x35, 0x35, 0x35, 0x01, 0x35, 0x35, 0x35, 0x01, 0x35, 0x35, 0x35, 0x01, 0x35, 0x35, 0x35, 0x01, 0x35, 0x35, 0x35, 0x01, 0x35, 0x35, 0x35, 0x01, 0x35, 0x35, 0x35, 0x01, 0x35, 0x35, 0x35, 0x01};
			byte pin[]={0x35,0x35,0x35,0x35};

			/*ret = CoinManager.verifyPIN(CoinManager.PIN_MODE_FROM_API,pin,(short) 0,(short) pin.length);
			if (ret != 0) {
				ISOException.throwIt((short) 0x6f03);
			}*/
			
			ret = CoinManager.getCoinPubData(ROOT_HD_PATH, (short) 0, (short) ROOT_HD_PATH.length , buf, (short) 0, buf, (short) 65);
			if (ret != 0) {
				ISOException.throwIt((short) 0x6f01);
			}
			apdu.setOutgoingAndSend((short) 0, (short) (65 + 32));/**/
			
			/*ret = CoinManager.signCoinData((short) 0x01, dataInLV, (short) 0, (short) dataInLV.length, 
				tmpout, (short) 0, ROOT_HD_PATH_IN_LV, (short) 0, (short) ROOT_HD_PATH_IN_LV.length);
			
			if (ret != 0) {
				ISOException.throwIt( (short)0x6f01);
			}
			if(ret>256) ret=256;
			short le = apdu.setOutgoing();
			apdu.setOutgoingLength(le);
			apdu.sendBytesLong(tmpout, (short) 0, le);*/
			
			
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}

}
