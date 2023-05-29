package Lexer.LexerRules;

import Lexer.Tokens.Token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AbstractRule is the base abstract class for lexer rules
 *
 * @author Hirumal Priyashan
 * @version 1.0
 * @since 1.0
 * @see "https://sourcemaking.com/design_patterns/chain_of_responsibility"
 */
public abstract class AbstractRule {
    // next handler in the chain
    protected AbstractRule successor;

    /**
    * Sets successor for the current handler
    *
    * @param  successor next handler
    * @return successor
    */
    public AbstractRule setSuccessor(AbstractRule successor) {
        this.successor = successor;
        return this.successor;
    }

    /**
    * Abstract protected method for CSERule implementation
    *
    * @param currentSource source to tokenize
    * @return   handler number if handled by one of the handlers
    *           otherwise 0
    */
    protected abstract Token applyRuleImplementation(
            String currentSource, int line, int column
    );

    /**
     * Apply the abstract rule if it can be handled otherwise pass to the
     * successor
     *
     * @param currentSource source to tokenize
     * @return handler number if handled by one of the handlers
     * otherwise 0
     */
    public final Token applyRule(
            String currentSource, int line, int column
    ) {
        Token generatedToken = applyRuleImplementation(
                currentSource, line, column);
        if (successor != null && generatedToken == null)
            return successor.applyRule(currentSource, line, column);
        return generatedToken;
    }

    /**
     * Match the source with a given regex
     *
     * @param source Source to be matched
     * @param regex Regex to be matched
     * @return Match if found otherwise null
     */
    protected final String matchWithRegex(String source, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find())
            return matcher.group();
        return null;
    }
}
