public class OffByN implements CharacterComparator {
    // an integer defines the difference
    private int diff;

    public OffByN(int N) {
        diff = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int sub = x - y;
        return sub == diff || sub == -diff;
    }
}
