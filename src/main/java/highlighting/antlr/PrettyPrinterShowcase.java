package highlighting.antlr;

import java.util.Scanner;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class PrettyPrinterShowcase {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.print("Indent Width: ");
    int width = sc.nextInt();
    sc.nextLine();

    String input =
        """
        class Test{
        int x;
        void foo(){
        if(a){
        boolean b = true;
        }
        }
        }
        """;

    String input2 =
      """
      class Test2{
      boolean a = true;
      if(a == true){ while(x == 1) {x++;}
      } }
      """;

    MiniJavaLexer lexer = new MiniJavaLexer(CharStreams.fromString(input));

    CommonTokenStream tokens = new CommonTokenStream(lexer);

    MiniJavaParser parser = new MiniJavaParser(tokens);

    var tree = parser.compilationUnit();

    PrettyPrinterVisitor visitor = new PrettyPrinterVisitor(width);

    visitor.visit(tree);

    System.out.println(visitor.result());

    System.out.println("Test 2:");

    lexer = new MiniJavaLexer(CharStreams.fromString(input2));

    tokens = new CommonTokenStream(lexer);

    parser = new MiniJavaParser(tokens);

    tree = parser.compilationUnit();

    visitor = new PrettyPrinterVisitor(width);
    visitor.visit(tree);
    System.out.println(visitor.result());

  }
}
