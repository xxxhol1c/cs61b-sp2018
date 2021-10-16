/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {


    public static int countPalindrome(int n) {
        int minLength = 4;
        In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator c = new OffByN(n);
        int count = 0;
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength
                    && palindrome.isPalindrome(word, c)) {
                count += 1;
            }
        }
        return count;
    }

    public static String longestPlaindrome(int n) {
        int minLength = 4;
        In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator c = new OffByN(n);
        int count = 0;
        String longest = "";
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && word.length() > longest.length()
                    && palindrome.isPalindrome(word, c)) {
                longest = word;
            }
        }
        return longest;
    }

    // just for fun -- for what N are there the most palindromes in English?
    // What is the longest offByN palindrome for any N?
    public static void main(String[] args) {
        for (int i = 0; i < 26; i++) {
            int t = countPalindrome(i);
            String l = longestPlaindrome(i);
            System.out.format("N: %d, Total: %d, Longest: %s\n", i, t, l);
        }

    }
}
