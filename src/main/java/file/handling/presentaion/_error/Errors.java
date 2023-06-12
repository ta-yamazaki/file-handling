package file.handling.presentaion._error;

import java.util.ArrayList;
import java.util.List;

/**
 * エラーメッセージ
 */
public class Errors {
    List<String> lines = new ArrayList<>();

    public void addLine(String line) {
        lines.add(line);
    }

    public List<String> message() {
        return lines;
    }
}
