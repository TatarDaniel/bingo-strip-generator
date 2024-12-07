import java.util.*;
import java.util.stream.IntStream;

class DistributionGenerator {
    public static Map<Integer, List<Integer>> generateValidDistribution(List<List<Integer>> groups, int totalNumbers, int minPerGroup, int maxPerGroup, int remainingTicketToCreate) {
        int[] distribution = new int[groups.size()];
        int remaining = totalNumbers;
        Random random = new Random();
        List<Integer> testGroup = new ArrayList<>();

        for (int i = 0; i < groups.size(); i++) {
            int available = groups.get(i).size();
            distribution[i] = Math.min(minPerGroup, available);
            remaining -= distribution[i];
            testGroup.add(groups.get(i).remove(random.nextInt(0, groups.get(i).size())));
        }

        List<Integer> availableGroup = new ArrayList<>(IntStream.range(0, 9).boxed().toList());

        List<Integer> importantGroup = getValuesForImportantGroups(remainingTicketToCreate, groups);

        while (remaining > 0 && !availableGroup.isEmpty()) {
            int randomIndex = random.nextInt(availableGroup.size());
            int groupIndex = availableGroup.get(randomIndex);

            if (remainingTicketToCreate == 2 && !importantGroup.isEmpty() || remainingTicketToCreate == 3 && !importantGroup.isEmpty()) {
                groupIndex = importantGroup.get(0);
                importantGroup.remove(0);
            }

            int available = groups.get(groupIndex).size();
            if (distribution[groupIndex] < maxPerGroup && (available >= remainingTicketToCreate)) {
                distribution[groupIndex]++;
                remaining--;
                testGroup.add(groups.get(groupIndex).remove(random.nextInt(0, groups.get(groupIndex).size()))); // Remove a number from the group as it is allocated

            } else {
                availableGroup.remove(randomIndex);
            }
        }

        Collections.sort(testGroup);

        return createGroups(testGroup);
    }

    private static List<Integer> getValuesForImportantGroups(int remainingTicketToCreate, List<List<Integer>> lists) {
        List<Integer> importantGroup = new ArrayList<>();
        if (remainingTicketToCreate == 2) {
            for (int i = 0; i < lists.size(); i++) {
                if (lists.get(i).size() == 5) {
                    importantGroup.add(i);
                    importantGroup.add(i);
                } else if (lists.get(i).size() / remainingTicketToCreate == 2) {
                    importantGroup.add(i);
                }
            }
        }

        if(remainingTicketToCreate == 3 && lists.get(8).size() >= 7) {
            importantGroup.add(8);
        }
        return importantGroup;
    }

    public static Map<Integer, List<Integer>> createGroups(List<Integer> testGroup) {
        Map<Integer, List<Integer>> groupedElements = new HashMap<>();

        for (int element : testGroup) {
            int groupNumber = element / 10;
            if (element == 90) {
                groupNumber = 8;
            }
            groupedElements.computeIfAbsent(groupNumber, k -> new ArrayList<>()).add(element);
        }

        return groupedElements;
    }
}