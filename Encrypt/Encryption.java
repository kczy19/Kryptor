package Encrypt;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Encryption {
    public static void encryptFile(File file, String password) throws Exception{
        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(file.getPath() + ".encrypted");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            byte[] data = new byte[(int) file.length()];
            fis.read(data);

            byte[] encryptedData = encrypt(data, password);
            oos.writeObject(encryptedData);
        }
    }

    public static void decryptFile(File file, String password) throws Exception {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis);
             FileOutputStream fos = new FileOutputStream(file.getPath().replace(".encrypted", ""))) {

            byte[] encryptedData = (byte[]) ois.readObject();
            byte[] decryptedData = decrypt(encryptedData, password);
            fos.write(decryptedData);
        } catch (ClassNotFoundException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException |
                IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }
    }

    private static byte[] encrypt(byte[] data, String password) throws Exception {
        SecretKeySpec secretKey = generateSecretKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, String password) throws Exception{
        SecretKeySpec secretKey = generateSecretKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private static SecretKeySpec generateSecretKey(String password) throws Exception {
        byte[] keyBytes = Arrays.copyOf(password.getBytes(), 16);
        return new SecretKeySpec(keyBytes, "AES");
    }
}
