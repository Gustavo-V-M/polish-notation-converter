import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class App {
    

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int selection = 0;
        String expression = "";
        int size = 0;

        // a stack polish_stk é usada para realizar a conta depois de a expressão já ser transformada em notação polonesa
        // a stack operators_stk é usada para empilhar os operadores na hora de converter para notação polonesa

        OurStack<Character> operators_stk = new OurStack<>();
        OurStack<Character> polish_stk = new OurStack<>();
        OurStack<Float> float_stk = new OurStack<>();

        ArrayList<Variable> variables = new ArrayList<>();


        while (selection != 5){

            System.out.println("--------------- MENU ---------------");
            System.out.println("1. Entrada da expressão aritmética na notação infixa.\n" +
                           "2. Entrada dos valores numéricos associados às variáveis.\n" +
                           "3. Conversão da expressão, da notação infixa para a notação posfixa, e exibição da expressão convertida para posfixa.\n" + 
                           "4. Avaliação da expressão (apresentação do resultado do cálculo, mostrando a expressão e valores das variáveis).\n" +
                           "5. Encerramento do programa.");

            selection = sc.nextInt();

            if (selection == 1){

                // adicionando a expressão na stack e adicionando as variaveis na variables;

                System.out.println("Digite a expressão");
                expression = sc.next();
                size = expression.length();
                
                selection_1(operators_stk, polish_stk, expression, size, variables, sc);
            }
            else if (selection == 2){
                // Colocando valores nas variaveis
                selection_2(variables, sc);
            }

            else if (selection == 3){

                selection_3(operators_stk, polish_stk, expression);
            }
            else if (selection == 4){

                selection_4(float_stk, size, variables);
            }
        }
    }

    private static int getPriority(char letter) {

        // usamos essa função para não ter que fazer muitos if-else na conversão

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
    private static float operation(char operator, float a, float b){
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

    private static void selection_1(OurStack<Character> operators_stk, OurStack<Character> polish_stk, String expression, int size, ArrayList<Variable> variables, Scanner sc){

        operators_stk.setSize(size);
        polish_stk.setSize(size);

        for (int i = 0; i < size; i++){
        char[] char_expression = expression.toCharArray();
        
        
        if (Character.isLetter(char_expression[i])){
            Variable latter = new Variable(char_expression[i], 0);
        
        variables.add(latter);
        }
      }
    }
    
    private static void selection_2(ArrayList<Variable> variables, Scanner sc){
        for (Variable variable : variables){
            System.out.print("Digite o valor da variavel ");
            System.out.println(variable.getLetter());
            variable.setValue(sc.nextInt());
        }
    }

    private static void selection_3(OurStack<Character> operators_stk, OurStack<Character> polish_stk, String expression){

        
        for (char letter: expression.toCharArray()){

            // se o char atual é uma variavel, empilha na stack da notação polonesa (o equivalente a printar para a saida no pseudocodigo)
            if (Character.isLetter(letter)){
                polish_stk.push(letter);
                System.out.print(letter);
                
            }
            
            // se o char atual é um parenteses de abertura, empilha na stack de operadores
            else if (letter == '('){
                operators_stk.push(letter);

            }

            
            else if (letter == ')'){
                while (!operators_stk.isEmpty() && operators_stk.top() != '('){
                    char aux = operators_stk.pop();
                    polish_stk.push(aux);
                    System.out.print(aux);
                }
                operators_stk.pop();

            }
            else {

                while (!operators_stk.isEmpty() && getPriority(letter) < getPriority(operators_stk.top()) && operators_stk.top() != '(' && operators_stk.top() != ')'){

                    char aux = operators_stk.pop();
                    polish_stk.push(aux);
                    System.out.print(aux);
        
                }
                if (!operators_stk.isEmpty() && getPriority(letter) >= getPriority(operators_stk.top()) && operators_stk.top() != '(' && operators_stk.top() != ')'){
                    char aux = operators_stk.pop();
                    System.out.print(aux);
                    polish_stk.push(aux);
                    operators_stk.push(letter);

                }
                else {
                operators_stk.push(letter);
                }
                
                
            }
        }
        char aux = operators_stk.pop();
        System.out.println(aux);
        polish_stk.push(aux);
    }

    private static void selection_4(OurStack<Float> int_stk,int size, ArrayList<Variable> variables){

        String expression = "";
        while (!int_stk.isEmpty()){
            expression = int_stk.pop() + expression;
        }
        char[] expression_chars = expression.toCharArray();

        float x = 0f;
        float y = 0f;

        for (char letter: expression_chars){
            if (Character.isLetter(letter)){
                for (Variable var: variables){
                    if (var.getLetter() == letter){
                        int_stk.push(var.getValue());
                    }
                }
            }
            else {
                y = int_stk.pop();
                x = int_stk.pop();

                int_stk.push(operation(letter, x, y));
            }
        }
        System.out.println(int_stk.top());

    }

}
