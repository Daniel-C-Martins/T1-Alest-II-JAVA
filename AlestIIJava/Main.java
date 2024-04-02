import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main{
    private static String caminhoArquivo = "Maps\\map2000.txt";
    private static String[][] matriz;
    private static int local_x;
    private static int local_y;
    private static String direcao;
    private static boolean endController = false;
    private static int money = 0;
    private static String count = "0";

    public static void main(String[] args) {
        long tempoInicial = System.currentTimeMillis();
        readMap();
        //displayMatrix();
        local_x = firstLocation();
        local_y = 0;
        direcao = "Right";
        controller();
        long tempoFinal = System.currentTimeMillis();
        long resultado = tempoFinal - tempoInicial;
        System.out.println("Tempo decorrido em milissegundos: " + resultado);
        System.out.printf("O total de dinheiro recuperado foi: %d", money);
    }

    public static void readMap(){
        try {
            // Abrir o arquivo para leitura
            BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));

            // Contar o número de linhas no arquivo
            int numLinhas = 0;
            while (leitor.readLine() != null) {
                numLinhas++;
            }
            leitor.close();

            // Reabrir o arquivo para leitura
            leitor = new BufferedReader(new FileReader(caminhoArquivo));

            // // Inicializar a matriz com o número de linhas contadas
            matriz = new String[numLinhas][];

            String linha;
            int linhaAtual = 0;
            while ((linha = leitor.readLine()) != null) {
                // Dividir a linha em elementos usando um delimitador (por exemplo, espaço)
                matriz[linhaAtual] = linha.split("");
                linhaAtual++;
            }
            leitor.close();
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            }
    }

    public static void displayMatrix(){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int firstLocation(){
        for(int i = 0; i < matriz.length; i++){
            if(matriz[i][0].equals("-")){
                
                return i;
            }
        }
        return 0;
    }

    public static boolean testBackSlash(int x, int y){
        if (matriz[x][y].equals("\\")) {
            return true;  
        }
        return false;
    }

    public static boolean testSlash(int x, int y){
        if (matriz[x][y].equals("/")) {
            return true;  
        }
        return false;
    }


    public static void moveRight(int x, int y){
        for (int i = y; i < matriz.length + 1; i++) {
            if(end(x, i)){
                endController = true;
                break;
            }

            acumulateMoney(x, i);

            if (testSlash(x, i)) {
                direcao = "Up";
                local_x = x - 1;
                local_y = i;
                break;  
            }

            if (testBackSlash(x, i)) {
                direcao = "Down";
                local_x = x + 1;
                local_y = i;
                break; 
            }
        }
    }

    public static void moveLeft(int x, int y){
        for (int i = y; i > 0; i--) {
            if(end(x, i)){
                endController = true;
                break;
            }

            acumulateMoney(x, i);

            if (testSlash(x, i)) {
                direcao = "Down";
                local_x = x + 1;
                local_y = i;
                break;  
            }    

            if (testBackSlash(x, i)) {
                direcao = "Up";
                local_x = x - 1;
                local_y = i;
                break;  
            }
            
        }
    }

    public static void moveUp(int x, int y){
        for (int i = x; i > 0; i--) {
            if(end(i, y)){
                endController = true;
                break;
            }

            acumulateMoney(i, y);

            if (testSlash(i, y)) {
                direcao = "Right";
                local_x = i;
                local_y = y + 1; 
                break; 
            }

            if (testBackSlash(i, y)) {
                direcao = "Left";
                local_x = i;
                local_y = y - 1; 
                break; 
            }
        }
    }

    public static void moveDown(int x, int y){
        for (int i = x; i < matriz.length; i++) {
            if(end(i, y)){
                endController = true;
                break;
            }

            acumulateMoney(i, y);

            if (testSlash(i, y)) {
                direcao = "Left";
                local_x = i;
                local_y = y - 1;
                break;  
            }

            if (testBackSlash(i, y)) {
                direcao = "Right";
                local_x = i;
                local_y = y + 1;
                break;  
            }
        }
    }

    public static void acumulateMoney(int x, int y){
        if (Character.isDigit(matriz[x][y].charAt(0))) {
            count = count.concat(matriz[x][y]);
        }
        else{
            money += Integer.parseInt(count);
            count = "0";
        }
    }

    public static boolean end(int x, int y){
        if (matriz[x][y].equals("#")) {
            return true;  
        }
        return false;
    }

    public static void controller(){
        while (endController == false) {
            if (direcao == "Right") {
                moveRight(local_x, local_y);
            }
            else if (direcao == "Left") {
                moveLeft(local_x, local_y);
            } 
            else if (direcao == "Up") {
                moveUp(local_x, local_y);
            } 
            else if (direcao == "Down") {
                moveDown(local_x, local_y);
            } 
        }
    }
}
