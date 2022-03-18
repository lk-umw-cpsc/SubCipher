import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * A Cipher object allows for encryption and decryption using a 
 * substitution cipher. The Cipher.generate() method can be used to
 * generate a randomized substitution cipher string which can be
 * passed to the Cipher constructor.
 */
public class Cipher {
    private static final Random rng = new Random();

    private Map<Character, Character> encryptMap;
    private Map<Character, Character> decryptMap;

    /**
     * Creates a new Cipher object with the given character mapping.
     * Each letter of the alphabet must be present in the string, otherwise
     * an IllegalArgumentException is thrown.
     * @param cipher A String containing all 26 letters of the alphabet.
     * The first character substitutes a, the second substitutes b, and so on.
     */
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

    /**
     * Encrypts a given plaintext string into cypher text using this Cipher
     * @param plaintext The string to encrypt
     * @return The resulting ciphertext string
     */
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

    /**
     * Decrypts ciphertext into plaintext using the given cipher
     * @param ciphertext The ciphertext to decrypt
     * @return The resulting plaintext string
     */
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
        // Add each lower case letter of the alphabet to a set
        Set<Character> characterSet = new HashSet<>();
        for (char letter = 'a'; letter <= 'z'; letter++) {
            characterSet.add(letter);
        }
        // Start with an empty string
        String cipher = "";
        // Pick a random letter to substitute letters a-x with,
        // without allowing a letter to be assigned to itself
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
        
        // If y or z are remaining in the character set, 
        // ensure that y and z are not assigned themselves
        if (characterSet.contains('y')) {
            // z gets y, y gets the other
            characterSet.remove('y');
            cipher += characterSet.toArray()[0];
            cipher += 'y';
        } else if (characterSet.contains('z')) {
            // y gets z, z gets the other
            characterSet.remove('z');
            cipher += 'z';
            cipher += characterSet.toArray()[0];
        } else {
            Object[] remaining = characterSet.toArray();
            int randomIndex = rng.nextInt(remaining.length);
            Object pick = remaining[randomIndex];
            // y gets the pick, z gets the other
            cipher += pick;
            cipher += remaining[(randomIndex + 1) % 2];
        }
        // return cipher string
        return cipher;
    }

}
