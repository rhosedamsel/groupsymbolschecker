import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class GroupingSymbolsChecker {
    public static void main(String[] args) {
        // Check if the file name is provided as a command-line argument
        if (args.length != 1) {
            System.out.println("Usage: java GroupingSymbolsChecker <file_name>");
            return;
        }

        String fileName = args[0];
        try {
            // Check grouping symbols in the file
            if (checkGroupingSymbols(fileName)) {
                System.out.println("Grouping symbols are correctly paired in the file.");
            } else {
                System.out.println("Grouping symbols are not correctly paired in the file.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Method to check grouping symbols in the file
    private static boolean checkGroupingSymbols(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Stack<Character> stack = new Stack<>();

        int lineNumber = 1;
        String line;
        while ((line = reader.readLine()) != null) {
            for (char ch : line.toCharArray()) {
                // Check for opening grouping symbols
                if (ch == '(' || ch == '[' || ch == '{') {
                    stack.push(ch);
                }
                // Check for closing grouping symbols
                else if (ch == ')' || ch == ']' || ch == '}') {
                    // If stack is empty or the closing symbol doesn't match with the top of the stack, return false
                    if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                        System.out.println("Error at line " + lineNumber + ": Unmatched closing symbol '" + ch + "'");
                        return false;
                    }
                }
            }
            lineNumber++;
        }

        // If stack is not empty after reading the file, return false
        if (!stack.isEmpty()) {
            System.out.println("Error: Unmatched opening symbols remain in the file.");
            return false;
        }

        reader.close();
        return true;
    }

    // Method to check if the given opening and closing symbols form a matching pair
    private static boolean isMatchingPair(char opening, char closing) {
        return (opening == '(' && closing == ')') ||
               (opening == '[' && closing == ']') ||
               (opening == '{' && closing == '}');
    }
}
