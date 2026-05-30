package highlighting.presets;

import highlighting.regex.Token;
import java.util.List;
import java.util.regex.Pattern;

public final class MiniJavaTokens {

  // TODO (Phase I+II: RegexHighlighter/ScanningHighlighter)
  // TODO: Define the MiniJava tokens used by the highlighters. Each token is a mapping from a
  // regular expression to a colour (and, if applicable, a specific matching group). The order of
  // tokens in this list determines their relative priority during highlighting. One example token
  // definition is provided below; define the remaining tokens in an analogous way.

  // Basic token set for MiniJava. Extend this list with further tokens as needed (e.g. identifiers,
  // numeric literals, operators, brackets, whitespace), following the same pattern. Each token is
  // defined by a regular expression and a colour. Optionally, a specific capturing group within the
  // pattern can be selected as the "highlighted" region.
  public static List<Token> defaultTokens() {
    return List.of(
        // Javadoc Comments: 0
        Token.of(Pattern.compile("/\\*\\*[\\s\\S]*?\\*/"), MiniJavaColours.JAVADOC_COMMENT_COLOUR),
        // Block Comments: 1
        Token.of(Pattern.compile("/\\*[\\s\\S]*?\\*/"), MiniJavaColours.BLOCK_COMMENT_COLOUR),
        // Oneline Comments 2
        Token.of(Pattern.compile("/{2}[^\\n\\r]*"), MiniJavaColours.LINE_COMMENT_COLOUR),
        // Escaping operations 3
        Token.of(Pattern.compile("\\\\[\\\\\"\'ntbrf]"), MiniJavaColours.ESCAPE_SEQUENCE_COLOUR),
        // Strings 4
        Token.of(Pattern.compile("\"([^\"\\\\]|\\\\.)*\""), MiniJavaColours.STRING_LITERAL_COLOUR),
        // Chars 5
        Token.of(
            Pattern.compile("'(.|(\\\\[\\\\\"\'ntbrf]))'"), MiniJavaColours.CHAR_LITERAL_COLOUR),
        // Annotattionen 6
        Token.of(Pattern.compile("@[\\S\\D][\\w]*"), MiniJavaColours.ANNOTATION_COLOUR),
        // Keywords (package, import, class, public, private, final, return, null, new) 7
        Token.of(
            Pattern.compile("\\b(package|import|class|public|private|final|return|null|new)\\b"),
            MiniJavaColours.KEYWORD_COLOUR),
        // Methoden 8
        Token.of(
            Pattern.compile("[a-zA-Z_$][^()\\s]*\\s*?(?=\\()"), MiniJavaColours.METHODE_COLOUR),
        // Strukturen wie if, else, while... 9
        Token.of(
            Pattern.compile("\\b((if|else|while|for|switch)(\\b|(?=\\s?\\()))|(case\\b)"),
            MiniJavaColours.STRUCTURES_COLOUR));
  }
}
