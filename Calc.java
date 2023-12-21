public class NewMain {
    final static String OPERATIONS = "+-*/";
    java.util.Scanner scanner;

    NewMain(){
        scanner = new java.util.Scanner(System.in);
        String s = scanner.nextLine();
        java.util.StringTokenizer stok = new java.util.StringTokenizer(s,OPERATIONS,true);
        int i = 0;
        double val1 = 0;
        double val2 = 0;
        char operation = '+';
        while(stok.hasMoreTokens()){
        String token = stok.nextToken();
        i++;
        boolean out = false;
        switch(i){
           case 1 -> val1 = Double.parseDouble(token);
           case 2 -> operation = (char)token.charAt(0);
           case 3 -> val2 = Double.parseDouble(token);
           default -> out = true;
        }
        if (out) break;
        }
    double result = 0;
    switch(operation){
        case '+' -> result = val1 + val2;
        case '-' -> result = val1 - val2;
        case '*' -> result = val1 * val2;
        case '/' -> result = val1 / val2;
    }
    System.out.println(result);
    }
    public static void main(String[] args){
        new NewMain();
    }
}
