public class Playfair{
	
	static char[][] createKey(String key){
		char[][] K = new char[5][5];
		String alphabet = "abcdefghiklmnopqrstuvwxyz";
		for(int i = 0; i < key.length(); ++i){
			K[i/5][i%5] = key.charAt(i);
		       	alphabet = alphabet.replace(key.charAt(i), '*');
		}
		for(int i = key.length(), k = 0; i < 25; ++i, ++k)
			K[i/5][i%5] = (alphabet.charAt(k) == '*' ? K[i/5][(i--)%5]: alphabet.charAt(k));
		return K;
	}

	static String encrypt(String plaintext, char[][] K, int e){
		String ciphertext = "";
		int[] positions = new int[26];
		for(int i = 0; i < 25; ++i)
			positions[K[i/5][i%5] - 'a'] = i;
		positions['j' - 'a'] = positions['i' - 'a'];
		for(int i = 0, k = 0; i < plaintext.length(); i += 2){
			int a = positions[plaintext.charAt(i) - 'a'];
		        int b = (i + 1 < plaintext.length() ? positions[plaintext.charAt(i + 1) - 'a'] : a);
			if(a == b)
				b = positions['x' - 'a'] + (i - i--);
			if(a/5 == b/5)
				ciphertext += K[a/5][(a+e)%5] + "" + K[b/5][(b+e)%5];
			else if(a%5 == b%5)
				ciphertext += K[(a/5+e)%5][a%5] + "" + K[(b/5+e)%5][b%5];
			else
				ciphertext += K[a/5][b%5] + "" + K[b/5][a%5];
		}
		return ciphertext;
	}

	public static void main(String[] args){
		String key = "uncopyrightable";
		char[][] K = createKey(key);
		String plaintext = "hillcipher";//"playfairisnotfair";
		String ciphertext = encrypt(plaintext, K, 1);
		String decipheredtext = encrypt(ciphertext, K, 4);
		System.out.print(String.format("\nPlaintext = %s\nCiphertext = %s\nDecipheredtext = %s",
				 plaintext, ciphertext, decipheredtext));
	}
}
