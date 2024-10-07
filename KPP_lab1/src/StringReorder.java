
import java.util.List;
import java.util.stream.Collectors;

public class StringReorder {
    public String reorderStrings(List<String> inputStrings) {
        return inputStrings.stream()
                .flatMap(str -> str.chars().mapToObj(c -> String.valueOf((char) c)))
                .collect(Collectors.joining(",", "(", ")"));
    }
}
