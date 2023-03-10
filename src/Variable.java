public class Variable {

    private char letter;
    private float value;

    public Variable(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    public float getValue() {
        return this.value;

    }

    public void setValue(int value) {
        this.value = value;
    }

    public char getLetter() {
        return this.letter;
    }
}
