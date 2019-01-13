public enum TokenType {
    LITERALATOMS("[A-Z][A-Z0-9]*"),
    NUMERICATOMS("\\b\\d+\\b"),
    OPENPARENTHESES("[(]"),
    CLOSINGPARENTHESES("[)]"),
    WHITESPACES("[\\s]+"),
    EOF("\\Z"),
    ERROR("\\b\\d+[A-Z][A-Z0-9]*\\b");

    public String regex;

    TokenType(String regex) {
        this.regex = regex;
    }
}
