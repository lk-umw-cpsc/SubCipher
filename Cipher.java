import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Cipher {
    private static final Random rng = new Random();

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
            if (!(c >= 'a' && c <= 'z')) {
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
        Set<Character> characterSet = new HashSet<>();
        for (char letter = 'a'; letter <= 'z'; letter++) {
            characterSet.add(letter);
        }
        String cipher = "";
        for (char letter = 'a'; letter <= 'x'; letter++) {
            if (characterSet.contains(letter)) {
                characterSet.remove(letter);
                Object[] remaining = characterSet.toArray();
                Object pick = remaining[rng.nextInt(remaining.length)];
                cipher += pick;
                characterSet.add(letter);
                characterSet.remove(pick);
            } else {
                Object[] remaining = characterSet.toArray();
                Object pick = remaining[rng.nextInt(remaining.length)];
                cipher += pick;
                characterSet.remove(pick);
            }
        }
        if (characterSet.contains('y')) {
            characterSet.remove('y');
            cipher += characterSet.toArray()[0];
            cipher += 'y';
        } else if (characterSet.contains('z')) {
            characterSet.remove('z');
            cipher += 'z';
            cipher += characterSet.toArray()[0];
        } else {
            Object[] remaining = characterSet.toArray();
            int randomIndex = rng.nextInt(remaining.length);
            Object pick = remaining[randomIndex];
            cipher += pick;
            cipher += remaining[(randomIndex + 1) % 2];
        }
        return cipher;
    }

}
