package year2019;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Problem01 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        final Path path = Paths.get(Problem01.class.getClassLoader()
                .getResource("20191201.adventofcode").toURI());

        try (final Stream<String> lines = Files.lines(path)) {
            doProblem(lines);
        }
        doProblem(Stream.of("1969"));
        doProblem(Stream.of("100756"));
    }

    private static void doProblem(Stream<String> initialStream) {
        final int sumofRequirements = initialStream
                .mapToInt(Integer::parseInt)
                .flatMap(Problem01::calculateFuel)
                .sum();

        System.out.format("Initial cost: %d\n", sumofRequirements);
    }

    private static IntStream calculateFuel(int moduleMass) {
        return IntStream.iterate(moduleMass, Problem01::requiresFuel, Problem01::simpleSum)
                .skip(1);
    }

    private static boolean requiresFuel(int moduleMass) {
        return 0 < moduleMass;
    }

    private static int simpleSum(int moduleMass) {
        return (moduleMass / 3) - 2;
    }
}
