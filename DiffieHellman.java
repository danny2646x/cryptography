import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman{

	static boolean isProbablePrime(String p){

		BigInteger P = new BigInteger(p);
		Random random = new Random();
		BigInteger A = new BigInteger(P.bitLength() >> 1, random);

		BigInteger BI0 = BigInteger.ZERO, BI1 = BigInteger.valueOf(1), BI2 = BigInteger.valueOf(2);
	        BigInteger P1 = P.subtract(BI1);
	        BigInteger K = BI0, Q = P1;
		
		while(Q.testBit(0) == false){
			Q = Q.shiftRight(1);
			K = K.add(BI1);
		}

		System.out.print("\n" + A.toString() + " " + Q.toString() + " " + K.toString());
		
		if(A.modPow(Q, P).equals(BI1))
			return true;

		for(BigInteger I = BI1, EXP = BI1;  I.compareTo(K) <= 0; I = I.add(BI1), EXP = EXP.shiftLeft(1)){
			System.out.print("\niiii" + EXP.multiply(Q));
			if(A.modPow(EXP.multiply(Q), P).equals(P1))
				return true;
		}
		return false;
	}

	public static void main(String[] args){
		String p = "524287";
		System.out.print("\n" + p + " " + isProbablePrime(p));
		String q = "23";

		BigInteger P = new BigInteger(p), Q = new BigInteger(q);
		BigInteger Xa = BigInteger.valueOf(5), Xb = BigInteger.valueOf(11);
		BigInteger Ya = Q.modPow(Xa, P), Yb = Q.modPow(Xb, P);

		BigInteger Ka = Yb.modPow(Xa, P), Kb = Ya.modPow(Xb, P);
		System.out.print("\n" + Ka.toString() + " " + Kb.toString());
	}
}
