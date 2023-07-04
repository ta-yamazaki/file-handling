package file.handling.domain.model.file;

import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHash {
    public static final String MD2 = "MD2";
    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_512 = "SHA-512";

    /**
     * ファイルのハッシュ値（文字列）を返す
     * @param path ファイルパス
     * @return ハッシュ値（文字列）
     */
    public static String getfileHash(Path path) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(MD5);
        byte[] hash = md.digest();
        return new String(hash);
    }
}
