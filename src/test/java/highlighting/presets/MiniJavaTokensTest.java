package highlighting.presets;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import highlighting.regex.Token;

import java.util.List;

    public class MiniJavaTokensTest {

    List<Token> tokens;

    @BeforeEach
    public void setup() {
        //given
        tokens = MiniJavaTokens.defaultTokens();
    }

    @Test
    public void test_Block_Comments() {
        //given
        String testCase = "/* test */ int a;";

        //when
        var result = tokens.get(1).test(testCase);

        //then
        assertEquals(1, result.size());
    }

    @Test
    public void test_start_result() {
        //given
        String testCase = "\"String start\" abc des";

        //when
        var result = tokens.get(4).test(testCase);

        //then
        assertEquals(1, result.size());
    }

    @Test
    public void test_middle_result() {
        //given
        String testCase = "zyx meth_0de() asd";

        //when
        var result = tokens.get(8).test(testCase);
        assumeTrue(result.size() == 1);
        var region = result.getFirst();

        //then
        assertEquals(4, region.start());
        assertEquals(12, region.end());
    }

    @Test
    public void test_end_result() {
        //given
        String testCase = "int a } else";

        //when
        var result = tokens.get(9).test(testCase);
        var region = result.getFirst();

        //then
        assertEquals(1, result.size());
        assertEquals(testCase.length(), region.end());
    }

    @Test
    public void test_multiple_results() {
        //given
        String testCase = "public final class testClass {\\n \\t return new testfinal \\n }";

        //when
        var result = tokens.get(7).test(testCase);

        //then
        assertEquals(5, result.size());
    }

    @Test
    public void test_no_result() {
        //given
        String testCase = "/** Javadoc Comment with wrong end *\\";

        //when
        var result = tokens.get(0).test(testCase);

        //then
        assertEquals(0, result.size());
    }

    @Test
    public void test_keyword_in_word() {
        //given
        String testCase = "Public importing newline";
        var result = tokens.get(7).test(testCase);
        assertEquals(0, result.size());
    }
}
