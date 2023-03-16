// Caio Alexandre V.B. de Andrade TIA - 32229690.
// Gustavo Vilela Mitraud TIA - 32213611.
// Nicolas Fernandes Melnik TIA - 32241720.
// Jonatas Garcia de Oliveira TIA - 42181232.

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
