import Lexer.Lexer;
import Lexer.Tokens.Token;
import MyExeptions.InvalidTokenException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Checks for Lexer Rules")
class LexerTest {
    void testTokens(String source, List<String> expected) {
        Lexer lexer = new Lexer(source);
        try {
            List<Token> tokens = lexer.getTokens();
            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.get(i), tokens.get(i).toString());
            }
        } catch (InvalidTokenException e) {
            throw new RuntimeException(e);
        }
    }
    void testSingleToken(String source, String expected) {
        testTokens(source, Collections.singletonList(expected));
    }
    @Test
    @DisplayName("Test Comment Type One")
    void testCommentTypeOne() {
        String source = "# Enter numbers from 1 to 3";
        testSingleToken(source, "<CommentTypeOne>");
    }
    @Test
    @DisplayName("Test Comment Type Two")
    void testCommentTypeTwo() {
        String source = "{\n" +
                "\tThis is a program to compute the factors of entered numbers.\n" +
                "\tIt tests:\n" +
                "\t\tprocedures\n" +
                "\t\trepeat loop\n" +
                "\t\tif statement\n" +
                "\t\tarithmetic\n" +
                "}";
        testSingleToken(source, "<CommentTypeTwo>");
    }

    @Test
    @DisplayName("Test Identifier")
    void testIdentifier() {
        String source = "i";
        testSingleToken(source, "<identifier: " + source + ">");
    }

    @Test
    @DisplayName("Test Integer")
    void testInteger() {
        String source = "98";
        testSingleToken(source, "<integer: " + source + ">");
    }

    @Test
    @DisplayName("Test Predefined Tokens")
    void testPredefined() {
        String source = "program";
        testSingleToken(source, "<" + source + ">");
    }

    @Test
    @DisplayName("Test Char")
    void testChar() {
        String source = "'p'";
        testSingleToken(source, "<char: " + source + ">");
    }

    @Test
    @DisplayName("Test String")
    void testString() {
        String source = "\"hello\"";
        testSingleToken(source, "<string: " + source + ">");
    }
}