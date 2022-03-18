import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cipher {

    private Map<Character, Character> encryptMap;
    private Map<Character, Character> decryptMap;

    public Cipher(String cipher) {
        encryptMap = new HashMap<>();
        decryptMap = new HashMap<>();

        if (cipher.length() != 26) {
            throw new IllegalArgumentException("Expected full alphabet");
        }

        for (int i = 0, length = cipher.length(); i < length; i++) {
            char c = cipher.charAt(i);
            if (!(Character.isAlphabetic(c) && Character.isLowerCase(c))) {
                throw new IllegalArgumentException("Expected only lower case letters");
            }
            if (decryptMap.containsKey(c)) {
                throw new IllegalArgumentException("Cipher contained duplicate letter");
            }
            char letter = (char)('a' + i);
            encryptMap.put(letter, c);
            decryptMap.put(c, letter);
        }
    }

    public String encrypt(String plaintext) {
        String ciphertext = "";
        for (int i = 0, length = plaintext.length(); i < length; i++) {
            char currentChar = plaintext.charAt(i);
            Character e = encryptMap.get(Character.toLowerCase(currentChar));
            if (e == null) {
                ciphertext += currentChar;
            } else {
                if (Character.isUpperCase(currentChar)) {
                    ciphertext += Character.toUpperCase(e);
                } else {
                    ciphertext += e;
                }
            }
        }
        return ciphertext;
    }

    public String decrypt(String ciphertext) {
        String plaintext = "";
        for (int i = 0, length = ciphertext.length(); i < length; i++) {
            char currentChar = ciphertext.charAt(i);
            Character d = decryptMap.get(Character.toLowerCase(currentChar));
            if (d == null) {
                plaintext += currentChar;
            } else {
                if (Character.isUpperCase(currentChar)) {
                    plaintext += Character.toUpperCase(d);
                } else {
                    plaintext += d;
                }
            }
        }
        return plaintext;
    }
    
    public static String generate() {
        String set = "abcdefghijklmnopqrstuvwxyz";
        String shuffled = "";
        List<Character> l = new ArrayList<Character>();
        for (char c : set.toCharArray())
            l.add(c);
        Collections.shuffle(l);
        for (char c : l)
            shuffled += c;
        return shuffled;
    }

}
