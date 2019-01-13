import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    private ArrayList<String> literalAtoms;
    private int numericAtomCount, numericAtomSum;
    private int openParenthesesCount, closingParenthesesCount;
    private Pattern tokenPattern;

    public LexicalAnalyzer() {
        literalAtoms = new ArrayList<>();

        StringBuilder tokenRegexBuilder = new StringBuilder();
        for(TokenType tokenType: TokenType.values()) {
            tokenRegexBuilder.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.regex));
        }

        tokenPattern = Pattern.compile(tokenRegexBuilder.substring(1));
    }

    public void scan() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher tokenMatcher = tokenPattern.matcher(line);
            while (tokenMatcher.find()) {
                Token token = getNextToken(tokenMatcher);
                switch (token.getTokenType()) {
                    case LITERALATOMS:
                        literalAtoms.add(token.getTokenValue());
                        break;
                    case NUMERICATOMS:
                        numericAtomCount++;
                        numericAtomSum += Integer.parseInt(token.getTokenValue());
                        break;
                    case OPENPARENTHESES:
                        openParenthesesCount++;
                        break;
                    case CLOSINGPARENTHESES:
                        closingParenthesesCount++;
                        break;
                    case WHITESPACES:
                        break;
                    case ERROR:
                        System.out.println("ERROR: Invalid token " + token.getTokenValue());
                        System.exit(0);
                    case EOF:
                        break;
                }
            }
        }


	StringBuilder literalAtomStr = new StringBuilder();
        literalAtomStr.append("LITERAL ATOMS: " + literalAtoms.size());
        for (String literalAtom : literalAtoms) {
            literalAtomStr.append(", " + literalAtom);
        }
        System.out.println(literalAtomStr.toString());
        System.out.println("NUMERIC ATOMS: " + numericAtomCount + ", " + numericAtomSum);
        System.out.println("OPEN PARENTHESES: " + openParenthesesCount);
        System.out.println("CLOSING PARENTHESES: " + closingParenthesesCount);
    }

    private Token getNextToken(Matcher tokenMatcher) {
        if (tokenMatcher.group(TokenType.LITERALATOMS.name()) != null) {
            return new Token(TokenType.LITERALATOMS, tokenMatcher.group(TokenType.LITERALATOMS.name()));
        }
        else if (tokenMatcher.group(TokenType.NUMERICATOMS.name()) != null) {
            return new Token(TokenType.NUMERICATOMS, tokenMatcher.group(TokenType.NUMERICATOMS.name()));
        }
        else if (tokenMatcher.group(TokenType.OPENPARENTHESES.name()) != null) {
            return new Token(TokenType.OPENPARENTHESES, tokenMatcher.group(TokenType.OPENPARENTHESES.name()));
        }
        else if (tokenMatcher.group(TokenType.CLOSINGPARENTHESES.name()) != null) {
            return new Token(TokenType.CLOSINGPARENTHESES, tokenMatcher.group(TokenType.CLOSINGPARENTHESES.name()));
        }
        else if (tokenMatcher.group(TokenType.EOF.name()) != null) {
            return new Token(TokenType.EOF, tokenMatcher.group(TokenType.EOF.name()));
        }
        else if (tokenMatcher.group(TokenType.WHITESPACES.name()) != null) {
            return new Token(TokenType.WHITESPACES, tokenMatcher.group(TokenType.WHITESPACES.name()));
        }
        else {
            return new Token(TokenType.ERROR, tokenMatcher.group(TokenType.ERROR.name()));
        }
    }

}
