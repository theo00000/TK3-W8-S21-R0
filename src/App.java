import java.util.Scanner;
import java.util.Stack;

// Kelas kalkulator
class KalkulatorUntukInfix {

    static int precedence(char c) {
        if (c == '+' || c == '-') return 1;
        else if (c == '*' || c == '/') return 2;
        return -1;
    }

    static boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    static boolean isValid(String expr) {
        boolean expectOperand = true;
        int parenCount = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == ' ') continue;
            if (Character.isDigit(c)) {
                if (!expectOperand) return false;
                expectOperand = false;
            } else if (c == '(') {
                parenCount++;
                // setelah '(' selalu expect operand
                expectOperand = true;
            } else if (c == ')') {
                if (expectOperand || parenCount == 0) return false;
                parenCount--;
                // setelah ')' expect operator
                expectOperand = false;
            } else if (isOperator(c)) {
                if (expectOperand) return false;
                expectOperand = true;
            } else {
                return false;
            }
        }
        return (!expectOperand && parenCount == 0);
    }
    static String infixToPostfix(String expr) {
        
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : expr.toCharArray()) {
            if (c == ' ') continue;
            if (Character.isDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
                
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                    
                }
                stack.pop(); 
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop());
                    
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();

        
    }

    // Konversi infix ke prefix via reverse
    static String infixToPrefix(String expr) {
        
        StringBuilder sb = new StringBuilder(expr).reverse();
    
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '(') sb.setCharAt(i, ')');
            else if (c == ')') sb.setCharAt(i, '(');
        }

        
        String reversed = sb.toString();
        
        String postfix = infixToPostfix(reversed);
        return new StringBuilder(postfix).reverse().toString();

        
    }
    

    static int evaluatePostfix(String expr) {
        Stack<Integer> stack = new Stack<>();
        for (char c : expr.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else if (isOperator(c)) {
                int b = stack.pop();
                int a = stack.pop();
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }

    static int evaluatePrefix(String expr) {
        Stack<Integer> stack = new Stack<>();
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else if (isOperator(c)) {
                int a = stack.pop();
                int b = stack.pop();
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }
}

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan notasi infix yang valid (contoh: 5 + 4 / 5): ");
        String infix = sc.nextLine().replaceAll("\\s+", "");

        if (!KalkulatorUntukInfix.isValid(infix)) {
            System.out.println("Notasi infix tidak valid!");
            return;
        }

        String postfix = KalkulatorUntukInfix.infixToPostfix(infix);
        String prefix = KalkulatorUntukInfix.infixToPrefix(infix);

        System.out.println("Postfix: " + postfix);
        System.out.println("Prefix : " + prefix);

        int postfixResult = KalkulatorUntukInfix.evaluatePostfix(postfix);
        int prefixResult = KalkulatorUntukInfix.evaluatePrefix(prefix);

        System.out.println("Hasil evaluasi postfix: " + postfixResult);
        System.out.println("Hasil evaluasi prefix : " + prefixResult);
    }
}
