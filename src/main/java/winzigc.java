import FileReader.FileReader;
import FileReader.TextFileReader;
import Lexer.Lexer;
import Lexer.Tokens.Token;
import Logger.Logger;
import Node.Node;
import Parser.TopDownParser;

import java.util.List;

public class winzigc {
    public static void main(String[] args) throws Exception {
        if (args.length == 0){
            printUsage();
            return;
        }
         String filename = args[1];
        // generate token stream
        FileReader fileReader = new TextFileReader(filename);
        List<String> fileContentList = fileReader.getData();
        String fileContent = String.join("\n", fileContentList);
        Lexer lexer = new Lexer(fileContent);
        List<Token> tokens = lexer.getScreenedTokens();
        // parse
        TopDownParser parser = new TopDownParser(tokens);
        Node ast = parser.getParsedTree();
        ast.printNode();
    }

    private static void printUsage() {
        Logger.log("Usage: java winzigc -ast <input-file-path>");
        Logger.log("input-file-name         absolute or relative path to the input file");
    }
}
