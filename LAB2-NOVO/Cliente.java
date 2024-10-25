
/**
 * Laboratorio 1 de Sistemas Distribuidos
 * 
 * Autor: Lucio A. Rocha
 * Ultima atualizacao: 17/12/2022
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    
    private static Socket socket;
    private static DataInputStream entrada;
    private static DataOutputStream saida;
    
    private int porta=1025;
    
    public void iniciar(){
    	System.out.println("Cliente iniciado na porta: "+porta);
    	
    	try {
            
            socket = new Socket("127.0.0.1", porta);
            
            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());
            
            //Recebe do usuario algum valor
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int valor = 0;
            
            do{
				System.out.print("Digite 1 para ler uma fortuna aleatória ou 2 para escrever: ");
                valor = Integer.parseInt(br.readLine());

				if (valor == 1)
                    saida.writeUTF("{\n\"method\":\"read\",\n\"args\":[\"\"]\n}");
				else if (valor == 2){
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Escreva uma mensagem para gravar no arquivo de fortunas: ");
                    String mensagem = sc.nextLine();
                    saida.writeUTF(mensagem);
                    saida.writeUTF("{\n\"method\":\"write\",\n\"args\":[\""+ mensagem + "\"]\n}");
                }				
                else
                    System.out.println("Valor inválido!");
			}while(valor!=1 && valor !=2);
            
            //Recebe-se o resultado do servidor
            String resultado = entrada.readUTF();
            
            //Mostra o resultado na tela
            System.out.println(resultado);
            
            socket.close();
            
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Cliente().iniciar();
    }
    
}
