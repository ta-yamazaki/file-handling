package file.handling.presentaion._error;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * エラーメッセージ
 */
public class Errors {
    List<String> lines = new ArrayList<>();

    public Errors() {
    }

    public Errors(List<String> lines) {
        this.lines = lines;
    }

    public Errors(String line) {
        this.lines = Collections.singletonList(line);
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public List<String> message() {
        return lines;
    }
}
