import java.util.List;

public class Main {
    public static void main(String[] args) {

        int count = 1;
        boolean showStrips = false;

        for (String arg : args) {
            if (arg.startsWith("count=")) {
                try {
                    count = Integer.parseInt(arg.split("=")[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid count value. Using default: 1");
                }
            } else if (arg.equalsIgnoreCase("showStrips=true")) {
                showStrips = true;
            }
        }

        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        System.out.println("Running with count: " + count);
        System.out.println("Show strips: " + showStrips);

        long startTime = System.nanoTime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();

        for (int i = 0; i < count; i++) {
            System.out.println("GROUP " + (i + 1));
            List<List<Integer>> tickets = TicketGenerator.generateTickets();

            if (showStrips) {
                tickets.forEach(TicketPrinter::printTicket);
            }
            System.out.println();
        }

        long endTime = System.nanoTime();
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        double elapsedTimeInSeconds = (endTime - startTime) / 1.0e9;
        long memoryUsed = endMemory - startMemory;
        double memoryUsedInMB = memoryUsed / (1024.0 * 1024.0);

        System.out.println("Elapsed time in seconds: " + elapsedTimeInSeconds);
        System.out.println("Memory used in MB: " + memoryUsedInMB);
    }
}
