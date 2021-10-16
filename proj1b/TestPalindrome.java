import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the AutoGrader might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);

        Deque e = palindrome.wordToDeque("palindrome");
        String actual2 = "";
        for (int i = 0; i < "palindrome".length(); i += 1) {
            actual2 += e.removeFirst();
        }
        assertEquals("palindrome", actual2);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("abc"));
        assertFalse(palindrome.isPalindrome("abd"));
    }

    // Test OffByOne palindrome
    @Test
    public void testPalindromeOffByOne() {
        CharacterComparator c = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", c));
        assertTrue(palindrome.isPalindrome("lak", c));
        assertTrue(palindrome.isPalindrome(" u!",  c));
        assertFalse(palindrome.isPalindrome("noon", c));
        assertFalse(palindrome.isPalindrome("AbA", c));
    }
}




