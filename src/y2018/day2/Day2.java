package y2018.day2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    private static class LineTracker {
        private static final int MAGIC = 97;

        final int[] base = new int[26];
        final int[] letterTracker = new int[26];

        private void reset() {
            System.arraycopy(base, 0, letterTracker, 0, 26);
        }

        int numberOf(int match) {
            int count = 0;
            for (int i = 0; i < letterTracker.length; ++i) {
                if (letterTracker[i] == match) {
                    ++count;
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {

        final Path p = Paths.get(args[0]);

        try (FileChannel channel = FileChannel.open(p)) {
            final List<String> lines = part1(channel);

            //part 2
            for (int i = 0; i < lines.size(); ++i) {
                for (int j = 0; j < lines.size(); ++j) {
                    if (i != j) {
                        // remove a character and compare.
                        for (int k = 0; k < lines.get(i).length(); ++k) {
                            final String iPrime = removeChar(lines.get(i), k);
                            final String jPrime = removeChar(lines.get(j), k);

                            if (iPrime.equals(jPrime)) {
                                System.out.println(iPrime);
                                return;
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String removeChar(String str, int indexToRemove) {
        return str.substring(0, indexToRemove) + str.substring(indexToRemove + 1);
    }

    private static List<String> part1(FileChannel channel) throws IOException {

        final List<String> fileContents = new ArrayList<>();

        final ByteBuffer bb = ByteBuffer.allocate(1024);
        final LineTracker tracker = new LineTracker();

        final int[] twosThrees = new int[]{0, 0};
        final StringBuilder sb = new StringBuilder();

        while (channel.read(bb) > 0) {
            bb.flip();

            while (bb.hasRemaining()) {
                final char c = (char) bb.get();
                switch (c) {
                    case '\n':
                        if (tracker.numberOf(2) > 0) {
                            twosThrees[0]++;
                        }
                        if (tracker.numberOf(3) > 0) {
                            twosThrees[1]++;
                        }
                        tracker.reset();

                        fileContents.add(sb.toString());
                        sb.setLength(0);
                        break;
                    default:
                        sb.append(c);
                        tracker.letterTracker[c - LineTracker.MAGIC] += 1;
                }
            }

            bb.clear();
        }
        System.out.println(Arrays.toString(twosThrees) + " = " + (twosThrees[0] * twosThrees[1]));

        return fileContents;
    }
}
