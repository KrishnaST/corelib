package org.kst.corelib.crypt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class IBM3624PinOffset {
	
	
	public static void main(String[] args) {
	}
	
	/*
	
	
	main
	
	
	public static void CalculatePINOffset(String PAN, String Pin, String PDKkey, String DecTab, int StartPos, int Length,
	        char PadChar) throws Exception{
	    int PANLength = PAN.length();
	    if(Length != (PANLength - StartPos)){
	        throw new Exception(
	                "Invalid 'Start Pos and Length' format.");
	    }
	    //Padding the PAN before Start POS with Pad Chars back to 16 digits.
	    String block = ISOUtil.padleft(PAN.substring(StartPos, Length + (StartPos - 1)), PAN.length(), PadChar);

	    
	     * Doing encryption stuff on block with PDKKey. 
	     * The execute function basically encrypts block and PDKKey, and any algorithm could do the work. 
	     
	    String result = execute(block , PDKkey, "2TDES");

	    Map<Character, Character> decTab = new HashMap<Character, Character>();
	    decTab.put('A', '0');
	    decTab.put('B', '1');
	    decTab.put('C', '2');
	    decTab.put('D', '3');
	    decTab.put('E', '4');
	    decTab.put('F', '5');

	    //Replacing Hex Characters with numbers from Decmalization table.
	    char[] Inpin = result.substring(0, 4).toCharArray();
	    for(int i = 0; i < Inpin.length; i++){
	        if(decTab.containsKey(Inpin[i])){
	            Inpin[i] = decTab.get(Inpin[i]);
	        }
	    }
	    result = new String(Inpin);
	    System.out.println("Intermediate PIN: "+result);

	    //Calculating offset from Intermediate Pin.
	    int[] Offset = new int[4];
	    int Cpin;
	    int Ipin;
	    for(int i = 0; i < result.length(); i++){
	        Ipin = Integer.parseInt(result.substring(i, i+1));
	        Cpin = Integer.parseInt(Pin.substring(i, i+1));

	        if((Cpin - Ipin) >= 0){
	            Offset[i] = (Cpin - Ipin);
	        }
	        else{
	            Offset[i] = (Ipin - Cpin)%10;
	        }
	    }

	    String PinOffset = Arrays.toString(Offset);
	    System.out.println("Pin Offset: " + PinOffset);
	}
	
	
	
*/}
