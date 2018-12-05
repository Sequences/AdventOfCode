package y2018.day5;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

public class Day5 {

    public static void main(String[] args) throws IOException {

        final Path p = Paths.get(args[0]);
        int min;

        try (FileChannel channel = FileChannel.open(p)) {
            min = part1(channel, '1', '1');

            System.out.format("Base is %d\n", min);
        }

        for (int test = 'a'; test <= 'z'; ++test) {
            final char uppercase = (char) (test - 32);
            // bleh
            try (FileChannel channel = FileChannel.open(p)) {
                int newMin = part1(channel, (char) test, uppercase);

                if (newMin < min) {
                    min = newMin;
                    System.out.format("New min is %d\n", min);
                }

            }

        }
    }

    private static int part1(FileChannel channel, char ignore1, char ignore2) throws IOException {

        final Stack<Character> stack = new Stack<>();

        final ByteBuffer bb = ByteBuffer.allocate(1024);

        while (channel.read(bb) > 0) {
            bb.flip();

            while (bb.hasRemaining()) {
                final char c = (char) bb.get();

                if ('\n' == c || ignore1 == c || ignore2 == c) {
                    continue;
                }

                Character top = stack.isEmpty() ? null : stack.peek();
                if (comparable(top, c)) {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }

            bb.clear();
        }

        return stack.size();
    }

    private static boolean comparable(Character top, char buf) {
        if (top == null) {
            return false;
        }
        return Math.abs(top.charValue() - buf) == 32;
    }
}
