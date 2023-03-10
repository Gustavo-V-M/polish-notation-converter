import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in).useDelimiter("\n");

		int selection = 0;
		String expression = "";
		int size = 0;

		/*
		 * a stack polish_stk é usada para realizar a conta depois de a
		 * expressão já ser transformada em notação polonesa a stack
		 * operators_stk é usada para empilhar os operadores na hora de
		 * converter para notação polonesa
		 */

		OurStack<Character> operators_stk = new OurStack<>();
		OurStack<Character> polish_stk = new OurStack<>();
		OurStack<Float> float_stk = new OurStack<>();

		ArrayList<Variable> variables = new ArrayList<>();

		while (selection != 5) {

			System.out.println("--------------- MENU ---------------");
			System.out.println("1. Entrada da expressão aritmética na notação infixa.\n"
					+ "2. Entrada dos valores numéricos associados às variáveis.\n"
					+ "3. Conversão da expressão, da notação infixa para a notação posfixa, e exibição da expressão convertida para posfixa.\n"
					+ "4. Avaliação da expressão (apresentação do resultado do cálculo, mostrando a expressão e valores das variáveis).\n"
					+ "5. Encerramento do programa.");

			try {
				selection = sc.nextInt();
			} catch (Exception e) {
				System.out.println("Seleção invalida");
				continue;
			}

			if (selection == 1) {

				/*
				 * limpando as stackes quando for colocar uma nova expressão
				 */

				polish_stk.clear();
				operators_stk.clear();
				float_stk.clear();

				/*
				 * adicionando a expressão na stack e adicionando as variaveis na
				 * variables;
				 */

				System.out.println("Digite a expressão");
				expression = sc.next();

				// Só será chamada a primeira seleção caso a expressão for valida
				if (CheckOrder.check(expression)) {

					size = expression.length();

					selection_1(operators_stk, polish_stk, expression, size, variables);

				}
				else {
					System.out.println("A operação não é valida!");
				}

			}
			else if (selection == 2) {
				/*
				 * Colocando valores nas variaveis só será chamada a seleção dois
				 * caso existir variaveis;
				 */

				// só será chamada a segunda seleção se existirem variaveis
				if (variables.size() > 0) {
					selection_2(variables, sc);
				}
				else {
					System.out.println("Você não adicinou uma expressão valida ou sua expressão não possuí variaveis!");
				}
			}

			else if (selection == 3) {

				// só será chamada a terceira seleção se a expressão não for nula
				if (expression != "") {
					selection_3(operators_stk, polish_stk, expression);
				}
				else {
					System.out.println("Você não digitou uma operação!");
				}

			}
			else if (selection == 4) {

				// realizando o calculo

				int num_of_operators = polish_stk.getSize() - variables.size();

				/*
				 * Só será chamada a seleção 4 se o numero de operadores for
				 * menor que o numero de variaveis
				 */

				if (variables.size() != 0 && !polish_stk.isEmpty() && num_of_operators < variables.size()) {
					selection_4(float_stk, polish_stk, size, variables);
				}
				else {
					System.out.println("Você não adicinou uma expressão valida ou sua expressão não possuí variaveis!");
				}

			}
		}
	}

	private static int getPriority(char letter) {

		/*
		 * usamos essa função para não ter que fazer muitos if-else na
		 * conversão
		 */

		switch (letter) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		case '^':
			return 3;
		default:
			return -1;
		}
	}

	private static float operation(char operator, float a, float b) {
		switch (operator) {
		case '+':
			return a + b;

		case '-':
			return a - b;

		case '*':
			return a * b;

		case '/':
			return a / b;

		case '^':
			return (float) Math.pow(a, b);

		default:
			return -1;
		}
	}

	private static void selection_1(OurStack<Character> operators_stk, OurStack<Character> polish_stk,
			String expression, int size, ArrayList<Variable> variables) {

		operators_stk.setSize(size);
		polish_stk.setSize(size);

		for (int i = 0; i < size; i++) {
			char[] char_expression = expression.toCharArray();

			if (Character.isLetter(char_expression[i])) {
				Variable latter = new Variable(char_expression[i], 0);

				variables.add(latter);
			}
		}
	}

	private static void selection_2(ArrayList<Variable> variables, Scanner sc) {
		for (Variable variable : variables) {
			System.out.print("Digite o valor da variavel ");
			System.out.println(variable.getLetter());
			variable.setValue(sc.nextInt());
		}
	}

	private static void selection_3(OurStack<Character> operators_stk, OurStack<Character> polish_stk,
			String expression) {

		expression = expression.replaceAll(" ", "");
		for (char letter : expression.toCharArray()) {

			/*
			 * se o char atual é uma variavel, empilha na stack da notação
			 * polonesa (o equivalente a printar para a saida no
			 * pseudocodigo)
			 */
			if (Character.isLetter(letter)) {
				polish_stk.push(letter);
				System.out.print(letter);

			}

			/*
			 * se o char atual é um parenteses de abertura, empilha na stack
			 * de operadores
			 */
			else if (letter == '(') {
				operators_stk.push(letter);

			}

			else if (letter == ')') {
				while (!operators_stk.isEmpty() && operators_stk.top() != '(') {
					char aux = operators_stk.pop();
					polish_stk.push(aux);
					System.out.print(aux);
				}
				operators_stk.pop();

			}
			else {

				while (!operators_stk.isEmpty() && getPriority(letter) <= getPriority(operators_stk.top())
						&& operators_stk.top() != '(' && operators_stk.top() != ')') {

					char aux = operators_stk.pop();
					polish_stk.push(aux);
					System.out.print(aux);

				}
				if (!operators_stk.isEmpty() && getPriority(letter) > getPriority(operators_stk.top())
						&& operators_stk.top() != '(' && operators_stk.top() != ')') {

					operators_stk.push(letter);

				}
				else {
					operators_stk.push(letter);
				}

			}
		}
		while (!operators_stk.isEmpty()) {
			char aux = operators_stk.pop();
			polish_stk.push(aux);
			System.out.print(aux);
		}
		System.out.print("\n");
	}

	private static void selection_4(OurStack<Float> float_stk, OurStack<Character> char_stk, int size,
			ArrayList<Variable> variables) {

		// invertendo a pilha char_stk
		String expression = "";
		while (!char_stk.isEmpty()) {
			expression = char_stk.pop() + expression;
		}
		char[] expression_chars = expression.toCharArray();
		int expression_size = expression.length();

		for (int i = expression_size - 1; i >= 0; i--) {
			char_stk.push(expression_chars[i]);
		}

		/*
		 * se o topo da pilha string_stk for uma variavel, empilha o
		 * valor dela na pilha float_stk se for um operador, remove os
		 * dois do topo da pilha float_stk, faz a operacao, e empilha na
		 * int_stk
		 */

		float x = 0f;
		float y = 0f;

		while (!char_stk.isEmpty()) {
			char string_top = char_stk.pop();
			if (Character.isLetter(string_top)) {
				for (Variable var : variables) {
					if (var.getLetter() == string_top) {
						float_stk.push(var.getValue());

					}
				}
			}
			else {
				y = float_stk.pop();
				x = float_stk.pop();

				float result = operation(string_top, x, y);
				float_stk.push(result);
			}

		}
		System.out.println(float_stk.top());
	}

}
