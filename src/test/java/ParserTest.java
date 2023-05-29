import FileReader.FileReader;
import FileReader.TextFileReader;
import Lexer.Lexer;
import Lexer.Tokens.Token;
import MyExeptions.InvalidTokenException;

import MyExeptions.ParsingException;
import Node.Node;
import Parser.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Checks for Parser")
public class ParserTest {
    @Test
    @DisplayName("Test for Top Down Parser")
    void test() throws InvalidTokenException, ParsingException {
        for(int i = 1; i <= 15; i++){
            RunTest(String.format("winzig_test_programs/winzig_%02d" , i));
        }
    }
    private void RunTest(String filename) throws InvalidTokenException, ParsingException {
        // generate token stream
        FileReader fileReader = new TextFileReader(filename);
        List<String> fileContentList = fileReader.getData();
        String fileContent = String.join("\n", fileContentList);
        Lexer lexer = new Lexer(fileContent);
        List<Token> tokens = lexer.getScreenedTokens();
        // parse
        Parser parser = new Parser(tokens);
        Node ast = parser.getParsedTree();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ast.printNode();
        String expectedOutput = getExpectedOutput(filename + ".tree");
        assertEquals(expectedOutput, outContent.toString());
    }

    private String getExpectedOutput(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
