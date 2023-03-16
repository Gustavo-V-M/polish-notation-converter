// Caio Alexandre V.B. de Andrade TIA - 32229690.
// Gustavo Vilela Mitraud TIA - 32213611.
// Nicolas Fernandes Melnik TIA - 32241720.
// Jonatas Garcia de Oliveira TIA - 42181232.

public class CheckOrder {

	public static boolean check(String expression) {

		/*
		 * Percorre a string caso o caracter atual for um operador de
		 * abertura, empilha o caracter caso o caracter atual for um
		 * operador de fechadura, verifica se o topo da pilha é um
		 * operador de abertura se sim, verifica se é o operador de
		 * abertura correspondente, se sim, continua a percorrer a string
		 * e remove o topo da pilha se nao, retorna false
		 */

		char[] expression_arr = expression.toCharArray();
		OurStack<Character> char_stack = new OurStack<>(expression.length());

		for (char letter : expression_arr) {
			if (isOpening(letter)) {
				char_stack.push(letter);
			} else if (isClosing(letter) != '\0') {
				if (char_stack.isEmpty() || char_stack.top() != isClosing(letter)) {
					return false;
				} else {
					if (!char_stack.isEmpty())
						char_stack.pop();
				}

			}
		}
		if (char_stack.isEmpty())
			return true;
		else {
			return false;
		}
	}

	private static boolean isOpening(char c) {

		// função usada para verificar se o caracter atual é um operador
		// de abertura

		switch (c) {
		case '(':
		case '[':
		case '{':
		case '<':
			return true;
		default:
			return false;
		}

	}

	private static char isClosing(char c) {

		// função usada para verificar se o caracter atual é um operador
		// de fechadura E
		// retornar o seu operador de abertura correspondente

		switch (c) {
		case ')':
			return '(';
		case ']':
			return '[';
		case '}':
			return '{';
		case '>':
			return '<';

		default:
			return '\0';
		}
	}
}
