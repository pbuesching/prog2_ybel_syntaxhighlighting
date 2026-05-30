package highlighting.regex;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaColours;
import highlighting.regex.Token;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegexHighlighterTest {

    RegexHighlighter regHigh;

    @BeforeEach
    public void setUp() {
        regHigh = new RegexHighlighter();
    }

    /* Tests for the collectMatches Method */

    @Test
    public void test_collectMatches_find_overlapping() {
        //given
        String testString = "/* public testclass */ 'a' \"String methode() \"";

        //when
        var result = regHigh.collectMatches(testString);

        //then
        assertEquals(5, result.size());
    }

    @Test
    public void test_collectMatches_find_no_overlapping() {
        //given
        String testString = "public \\n class";

        //when
        var result = regHigh.collectMatches(testString);

        //then
        assertEquals(3, result.size());
    }

    @Test
    public void test_collectMatches_find_none() {
        //given
        String testString = "abcd";

        //when
        var result = regHigh.collectMatches(testString);

        //them
        assertEquals(0, result.size());
    }

    /* Tests for the resolveConflicts() Methods */

    @Test
    public void test_resolveConflicts_no_conflicts() {
        //given
        List<HighlightRegion> testRegions = new ArrayList<>();
        testRegions.add(new HighlightRegion(0, 5, MiniJavaColours.BLOCK_COMMENT_COLOUR));
        testRegions.add(new HighlightRegion(8, 13, MiniJavaColours.METHODE_COLOUR));
        testRegions.add(new HighlightRegion(15, 20, MiniJavaColours.STRING_LITERAL_COLOUR));

        //when
        var result = regHigh.resolveConflicts(testRegions);

        //then
        assertEquals(3, result.size());
    }

    @Test
    public void test_resolveConflicts_bearly_no_conflicts() {
        //given
        List<HighlightRegion> testRegions = new ArrayList<>();
        testRegions.add(new HighlightRegion(0, 5, MiniJavaColours.BLOCK_COMMENT_COLOUR));
        testRegions.add(new HighlightRegion(5, 13, MiniJavaColours.METHODE_COLOUR));

        //when
        var result = regHigh.resolveConflicts(testRegions);

        //then
        assertEquals(2, result.size());
    }

    @Test
    public void test_resolveConflicts_conflicts_inside() {
        //given
        List<HighlightRegion> testRegions = new ArrayList<>();
        testRegions.add(new HighlightRegion(0, 15, MiniJavaColours.BLOCK_COMMENT_COLOUR));
        testRegions.add(new HighlightRegion(5, 13, MiniJavaColours.METHODE_COLOUR));
        testRegions.add(new HighlightRegion(17, 20, MiniJavaColours.CHAR_LITERAL_COLOUR));

        //when
        var result = regHigh.resolveConflicts(testRegions);

        //then
        assertEquals(2, result.size());

    }

    @Test
    public void test_resolveConflicts_conflicts_sameStart() {
        //given
        List<HighlightRegion> testRegions = new ArrayList<>();
        testRegions.add(new HighlightRegion(0, 15, MiniJavaColours.BLOCK_COMMENT_COLOUR));
        testRegions.add(new HighlightRegion(0, 13, MiniJavaColours.METHODE_COLOUR));
        testRegions.add(new HighlightRegion(17, 20, MiniJavaColours.CHAR_LITERAL_COLOUR));

        //when
        var result = regHigh.resolveConflicts(testRegions);

        //then
        assertEquals(2, result.size());

    }

    @Test
    public void test_resolveConflicts_conflicts_outgoing() {
        //given
        List<HighlightRegion> testRegions = new ArrayList<>();
        testRegions.add(new HighlightRegion(0, 8, MiniJavaColours.ANNOTATION_COLOUR));
        testRegions.add(new HighlightRegion(5, 13, MiniJavaColours.STRING_LITERAL_COLOUR));

        //when
        var result = regHigh.resolveConflicts(testRegions);

        //then
        assertEquals(1, result.size());
    }

    @Test
    public void test_resolveConflicts_no_matches() {
        //given
        List<HighlightRegion> testRegions = new ArrayList<>();

        //when
        var result = regHigh.resolveConflicts(testRegions);

        //then
        assertEquals(0, result.size());
    }

    /* Tests for the whole process */

    @Test
    public void test_regexHighlighter_no_matches() {
        //given
        String testString = "abc test xyz";

        //when
        var result = regHigh.computeRegions(testString);

        //then
        assertEquals(0, result.size());
    }

    @Test
    public void test_regexHighlighter_no_conflicts() {
        //given
        String testString = "// Line Comment \n  public char 't'";

        //when
        var result = regHigh.computeRegions(testString);

        //then
        assertEquals(3, result.size());
    }

    @Test
    public void test_regexHighlighter_conflicts() {
        //given
        String testString = "/** public javaDoc comment */ with \" // new Comment in string \"";

        //when
        var result = regHigh.computeRegions(testString);

        //then
        assertEquals(2, result.size());
    }
}
