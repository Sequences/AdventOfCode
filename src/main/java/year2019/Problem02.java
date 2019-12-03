package year2019;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

public class Problem02 {

    public static void main(String[] args) throws URISyntaxException {
        final Path path = Paths.get(Problem01.class.getClassLoader()
                .getResource("20191202.adventofcode").toURI());

        try (final Stream<String> lines = Files.lines(path)) {
            final String line = lines.findFirst().get();
            doProblem(line, 12, 2);

            // find 19690720
            for (int noun = 0; noun < 100; ++noun) {
                for (int verb = 0; verb < 100; ++verb) {
                    if (19690720 == doProblem(line, noun, verb)) {
                        System.out.format("Found noun %d and verb %d: %d", noun, verb, 100 * noun + verb);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int doProblem(String line, int one, int two) {
        final int[] array = Stream.of(line.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        array[1] = one;
        array[2] = two;

        for (int i = 0; i < array.length; i += 4) {
            final Optional<IntBinaryOperator> operation = getOperation(array[i]);
            if (operation.isPresent()) {
                final int param1Loc = array[i + 1];
                final int param2Loc = array[i + 2];
                final int resultLoc = array[i + 3];

                array[resultLoc] = operation.get().applyAsInt(array[param1Loc], array[param2Loc]);
            } else {
                System.out.println(array[0]);
                break;
            }
        }
        return array[0];
    }

    private static Optional<IntBinaryOperator> getOperation(int op) {
        switch (op) {
            case 1:
                return Optional.of((a, b) -> a + b);
            case 2:
                return Optional.of((a, b) -> a * b);
            default:
                return Optional.empty();
        }
    }
}
