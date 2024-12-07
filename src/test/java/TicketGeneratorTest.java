import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketGeneratorTest {

    @Test
    public void testGenerateTickets() {
        List<List<Integer>> tickets = TicketGenerator.generateTickets();

        assertEquals(6, tickets.size());

        for (List<Integer> ticket : tickets) {
            assertEquals(27, ticket.size());

            List<Integer> sortedTicket = new ArrayList<>(ticket);

            assertEquals(sortedTicket, ticket);

            int[] groupCounts = new int[9];
            for (int number : ticket) {
                if(number == 0) {
                    continue;
                }
                int group = number / 10;
                if (number == 90) {
                    group = 8;
                }
                groupCounts[group]++;
            }

            for (int count : groupCounts) {
                assertTrue(count >= 1 && count <= 3);
            }
        }
    }

    @Test
    public void testTicketBlankSpace() {
        List<List<Integer>> tickets = TicketGenerator.generateTickets();

        for (List<Integer> ticket : tickets) {
            List<List<Integer>> rows = TicketGenerator.createRows(ticket);

            for (List<Integer> row : rows) {
                long zeroCount = row.stream().filter(n -> n == 0).count();
                assertEquals(4, zeroCount, "Each row should have exactly 4 blank spaces");
            }
        }
    }

    @Test
    public void testNoDuplicateNumbersInBingoStrip() {
        List<List<Integer>> tickets = TicketGenerator.generateTickets();

        List<Integer> allNumbers = tickets.stream()
                .flatMap(List::stream)
                .filter(n -> n != 0)
                .toList();

        Set<Integer> uniqueNumbers = new HashSet<>(allNumbers);

        assertEquals(uniqueNumbers.size(), allNumbers.size(), "There should be no duplicate numbers in the entire bingo strip");
    }


    @Test
    public void testColumnsSortedInAscendingOrder() {
        List<List<Integer>> tickets = TicketGenerator.generateTickets();

        for (List<Integer> ticket : tickets) {
            List<List<Integer>> columns = TicketGenerator.createColumns(ticket);

            for (List<Integer> column : columns) {
                List<Integer> nonZeroNumbers = column.stream()
                        .filter(n -> n != 0)
                        .toList();

                List<Integer> sortedNonZeroNumbers = new ArrayList<>(nonZeroNumbers);
                Collections.sort(sortedNonZeroNumbers);

                assertEquals(nonZeroNumbers, sortedNonZeroNumbers, "Column numbers must be sorted in ascending order");
            }
        }
    }

    @Test
    public void testEachRowHasAtLeastOneValue() {
        List<List<Integer>> tickets = TicketGenerator.generateTickets();

        for (List<Integer> ticket : tickets) {
            List<List<Integer>> rows = TicketGenerator.createRows(ticket);

            for (List<Integer> row : rows) {
                boolean hasValue = row.stream().anyMatch(n -> n != 0);
                assertTrue(hasValue, "Each row should have at least one value");
            }
        }
    }

    @Test
    @Timeout(value = 1)
    public void testImprovementFor10KTickets() {

        for(int i=0; i<10000; i++) {
            TicketGenerator.generateTickets();
        }
    }
}