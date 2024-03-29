import java.math.BigInteger;
import java.util.Scanner;


public class RSA{
	
	public static BigInteger B1 = BigInteger.ONE;

	public static void main(String[] args){
	
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter p and q: ");
		BigInteger p = new BigInteger(scanner.next()), q = new BigInteger(scanner.next());
		
		
		BigInteger n = p.multiply(q), phi = p.subtract(B1).multiply(q.subtract(B1));

		System.out.print("\nRSA Parameters: \nN = " + n + " Phi(N) = " + phi);
		
		
		System.out.print("\nEnter the message to be transmitted: ");
		String message = "Hi but this is taking too long.";

		//We need to calculate block size. 
		//Usually block size should be less than N.
		
		int blockSize = n.bitCount();
		System.out.print("\nBlock Size = " + blockSize);
		System.out.print("\nN = " + n.toString(2) + "\n");

		blockSize = blockSize % 2 == 1? blockSize - 1 : blockSize;

		//No. of characters that can be transmitted is blockSize/2.
		byte[] asciiMessage = message.getBytes();

		for(int i = 0; i < asciiMessage.length; ++i){
			System.out.print(asciiMessage[i]);
		}	

		BigInteger e = BigInteger.valueOf((1 << 16) + 1);

		//Now we have to calculate d, where e and d are modular inverses of each other modulo phi(n).
		
		BigInteger d = e.modInverse(phi);


		System.out.print(String.format("\nPublic Key = {%s, %s}", e.toString(), n.toString()));
		System.out.print(String.format("\nPrivate Key = {%s, %s}", d.toString(), n.toString()));
		
		/*

		//Let's check how this performs.
		
		//Encryption
		int m = 100;
		BigInteger C = BigInteger.valueOf(m).modPow(e, n);

		//Decryption

		BigInteger M = C.modPow(d, n);

		System.out.print(String.format("\nPlaintext = %d, Deciphered Plaintext = %d", m, M.intValue()));
		*/

		// Encryption
		// Lets try with block size = 4.
		blockSize = 4;
		String cipherString = "";
		byte[][] cipherBlocks = new byte[message.length()/blockSize][blockSize];


		System.out.print("\nJust checking..," + message.length() + "=== " + message.getBytes().length);	
		for(int i = message.length() % blockSize; i < message.length(); i += blockSize){
			byte[] octetString = message.substring(i, i + blockSize).getBytes();
			BigInteger m = new BigInteger(octetString);
			System.out.print(String.format("\nOS2IP(%s) = %s", new String(octetString), m.toString()));
			
			BigInteger c = m.modPow(d, n);
			System.out.print(String.format("\nRSAE(%s) = %s\n\n", m.toString(), c.toString()));

			System.out.print("\n------------------------------------------\n");
			char[] C = ip2os(c, 10);
			System.out.print("\nC=" + new String(C));
			
			BigInteger c_hat = os2ip(C);
			System.out.print("\nc=" + c.toString() + " c_hat=" + c_hat.toString());

			byte[] cipherOctetString = c.toByteArray();
			cipherBlocks[i/blockSize] = cipherOctetString;
			System.out.print("\nl=" + cipherOctetString.length + " s=" + new String(cipherOctetString));
			cipherString += new String(cipherOctetString);
			
		}

		// Decryption

		for(int i = 0; i < cipherBlocks.length; ++i){
		
			BigInteger c = new BigInteger(cipherBlocks[i]);
			BigInteger m = c.modPow(e, n);

			byte[] plainOctetString = m.toByteArray();
			System.out.print("\n" + new String(plainOctetString));
		
		
		}






	}


	public static BigInteger os2ip(char[] octetString){
	
		BigInteger ip = BigInteger.ZERO, B256 = BigInteger.valueOf(256);

		for(int i = 0; i < octetString.length; ++i){
			System.out.print(String.format("\nValue=%d Base=256 Position=%d", octetString[i], i));
			ip = ip.add(BigInteger.valueOf((int)octetString[i]).multiply(B256.pow(i)));
		}

		return ip;
	}


	public static char[] ip2os(BigInteger ip, int k){
	
		BigInteger B256 = BigInteger.valueOf(256);
		if(ip.compareTo(B256.pow(k)) == 1){
			System.out.print("\nToo large.");
			return null;
		}

		String m = "";
			
	
		char[] result = new char[k];
		int i = 0;

		while(k > 0){
			BigInteger remainder = ip.mod(B256);
			m += remainder.toString();
			result[i++] = (char)remainder.intValue();
			ip = ip.divide(B256);
			--k;
			System.out.print(String.format("\nip = %s, remainder = %d", ip.toString(), remainder.intValue()));
		}

		System.out.print("\n" + new String(result));
		return result;
	}

}
