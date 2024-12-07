import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupGenerator {
    public static List<List<Integer>> createGroups() {
        List<List<Integer>> groups = IntStream.range(0, 9)
                .mapToObj(i -> {
                    if (i == 0) {
                        return IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList());
                    }
                    if(i == 8) {
                        return IntStream.rangeClosed(80, 90).boxed().collect(Collectors.toList());
                    }
                    else {
                        int start = i * 10;
                        int end = start + 9;
                        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
                    }
                })
                .toList();
        return groups;
    }
}
