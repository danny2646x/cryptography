public class RailFence{

	static String encrypt(String plaintext, int rails){
		String[] tracks = new String[rails];
		for(int i = 0; i < plaintext.length(); ++i){
			if(i%((rails - 1) << 1) == 0)
				tracks[0] += plaintext.charAt(i);
			else if(i%(rails-1) == 0)
				tracks[rails-1] += plaintext.charAt(i);
			else
				tracks[(i%((rails-1) << 1) > rails-1) ? rails-1-(i%(rails-1)) : i%(rails-1)] += plaintext.charAt(i);
		}
		String ciphertext = "";
		for(int i = 0; i < rails; ++i){
			System.out.print("\ni=" + i + " " + tracks[i].replaceAll("null", ""));
			ciphertext += tracks[i].replaceAll("null", "");
		}
		return ciphertext;
	}

	static String decrypt(String ciphertext, int rails){
		String plaintext = "";
		int cyclelength = (rails - 1) << 1, cycles = ciphertext.length()/cyclelength, remainder = ciphertext.length() % cyclelength;
		int[] lengths = new int[rails];
		String[] tracks = new String[rails];
		for(int i = 0, k = 0; i < rails; k = k + lengths[i], ++i){
			if(i == 0 || i == rails-1)
				lengths[i] = cycles + (remainder >= i ? 1 : 0);
			else
				lengths[i] = (cycles << 1) + (remainder > i ? 1 : 0) + (remainder - rails >= rails - i - 1 ? 1 : 0);
			tracks[i] = ciphertext.substring(k, k + lengths[i]);
		}
		int[] i = new int[rails];
		for(int j = 0; j < rails; ++j)
			i[j] = 0;

		int k = 0, j = 0, d = 1; 
		while(k < ciphertext.length()){
			plaintext += tracks[j].charAt(i[j]++);
			if(j == rails-1)
				d = -1;
			else if(j == 0)
				d = 1;
			j += d;
			++k;
		}
		return plaintext;
	}

	public static void main(String[] args){
		int rails = 5;
		String plaintext = "meetmeafterthetogaparty";
		String ciphertext = encrypt(plaintext, rails);
		String decipheredtext = decrypt(ciphertext, rails);
		System.out.print(String.format("n = %d\np = %s\nc = %s\nd = %s", rails, plaintext, ciphertext, decipheredtext));
	}
}
