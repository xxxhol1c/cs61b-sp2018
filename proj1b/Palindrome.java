public class Palindrome {
    /* convert the given string to Deque */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> words = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            char item = word.charAt(i);
            words.addLast(item);
        }
        return words;
    }

    /* Check whether the given string is a Palindrome */
    public boolean isPalindrome(String word) {
        Deque<Character> p = wordToDeque(word);
        return isPalindromeHelper(p);
    }

    /* Helper method uses recursion */
    private boolean isPalindromeHelper(Deque<Character> p) {
        if (p.isEmpty() || p.size() == 1) {
            return true;
        } else if (p.removeFirst() == p.removeLast()) {
            return isPalindromeHelper(p);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> p = wordToDeque(word);
        return isPalindromeHelper(p, cc);
    }

    private boolean isPalindromeHelper(Deque<Character> p, CharacterComparator cc) {
        if (p.isEmpty() || p.size() == 1) {
            return true;
        } else if (cc.equalChars(p.removeFirst(), p.removeLast())) {
            return isPalindromeHelper(p, cc);
        } else {
            return false;
        }
    }
}



