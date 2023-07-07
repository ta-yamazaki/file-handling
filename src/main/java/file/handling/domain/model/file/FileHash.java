package file.handling.domain.model.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
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
    public static String getfileHash(Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(MD5);

        // 入力ストリームの生成
        DigestInputStream inputStream = new DigestInputStream(
                new BufferedInputStream(Files.newInputStream(path)), md);

        // ファイルの読み込み
        while (inputStream.read() != -1) {}

        // ハッシュ値の計算
        byte[] hash = md.digest();


        // ハッシュ値（byte）を16進数文字列に変換し返却
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            String hex = String.format("%02x", b);
            sb.append(hex);
        }
        return sb.toString();
    }
}
