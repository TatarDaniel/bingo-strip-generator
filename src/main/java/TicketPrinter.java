import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class TicketPrinter {
    public static void printTicket(List<Integer> ticket) {

        List<List<Integer>> rows = createRows(ticket);

        printTicketGrid(rows);
    }


    private static List<List<Integer>> createRows(List<Integer> numbers) {
        return IntStream.range(0, 3)
                .mapToObj(rowIndex -> IntStream.range(0, 9)
                        .mapToObj(colIndex -> numbers.get(rowIndex + colIndex * 3))
                        .collect(toList()))
                .collect(toList());
    }

    private static void printTicketGrid(List<List<Integer>> rows) {
        String header = "┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐";
        String separator = "├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤";
        String footer = "└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘";

        System.out.println(header);
        for (int i = 0; i < rows.size(); i++) {
            String line = rows.get(i).stream()
                    .map(n -> n == 0 ? "     " : String.format("%2d   ", n))
                    .collect(Collectors.joining("│"));
            System.out.printf("│%s│%n", line);
            if (i < rows.size() - 1) {
                System.out.println(separator);
            }
        }
        System.out.println(footer);
        System.out.println();
    }


}
