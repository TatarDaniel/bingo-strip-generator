import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class TicketGenerator {

    private static final int DEFAULT_TICKET_COUNT = 6;
    private static final int DEFAULT_TOTAL_NUMBERS = 15;
    private static final int DEFAULT_MIN_PER_GROUP = 1;
    private static final int DEFAULT_MAX_PER_GROUP = 3;

    public static List<List<Integer>> generateTickets() {
        List<List<Integer>> tickets = new ArrayList<>();
        List<List<Integer>> groups = GroupGenerator.createGroups();

        for (int remainingTicketToCreate = DEFAULT_TICKET_COUNT; remainingTicketToCreate > 0; remainingTicketToCreate--) {
            List<Integer> ticket = new ArrayList<>();
            Map<Integer, List<Integer>> distribution = DistributionGenerator.generateValidDistribution(groups, DEFAULT_TOTAL_NUMBERS, DEFAULT_MIN_PER_GROUP, DEFAULT_MAX_PER_GROUP, remainingTicketToCreate);

            insertZeroes(distribution);

            for (int groupIndex = 0; groupIndex < distribution.size(); groupIndex++) {
                List<Integer> group = distribution.get(groupIndex);
                for (int i = 0; i < group.size(); i++) {
                    ticket.add(group.get(i));
                }
            }

            tickets.add(ticket);
        }

        return tickets;
    }

    public static void insertZeroes(Map<Integer, List<Integer>> distribution) {
        distribution.values().stream()
                .filter(group -> group.size() < 3)
                .collect(shuffleStream())
                .limit(4)
                .forEach(group -> group.add(0, 0));

        var secondRowInsertCounter = new AtomicLong();
        distribution.values().stream()
                .filter(group -> group.size() == 1)
                .forEach(group -> {
                    secondRowInsertCounter.incrementAndGet();
                    group.add(1, 0);
                    group.add(2, 0);
                });

        distribution.values().stream()
                .filter(group -> group.size() == 2)
                .collect(shuffleStream())
                .limit(4 - secondRowInsertCounter.get())
                .forEach(group -> group.add(1, 0));

        distribution.values().stream()
                .filter(group -> group.size() == 2)
                .forEach(group -> group.add(2, 0));
    }

    private static <T> Collector<T, ?, Stream<T>> shuffleStream() {
        return Collectors.collectingAndThen(
                toList(),
                collected -> {
                    Collections.shuffle(collected);
                    return collected.stream();
                }
        );
    }

    public static List<List<Integer>> createRows(List<Integer> numbers) {
        return IntStream.range(0, 3)
                .mapToObj(rowIndex -> IntStream.range(0, 9)
                        .mapToObj(colIndex -> numbers.get(rowIndex + colIndex * 3))
                        .collect(toList()))
                .collect(toList());
    }

    public static List<List<Integer>> createColumns(List<Integer> numbers) {
        return IntStream.range(0, 9)
                .mapToObj(colIndex -> IntStream.range(0, 3)
                        .mapToObj(rowIndex -> numbers.get(rowIndex + colIndex * 3))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
