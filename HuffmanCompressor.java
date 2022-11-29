import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;

import shared.FileManager;
import shared.HuffmanEncoder;
import shared.Menu;

public class HuffmanCompressor {
  public static void main(String[] args) {
    Menu.printMainMenu();

    String userOption = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    HuffmanEncoder huffman = new HuffmanEncoder();

    do {
      Menu.printOptionLabel();

      try {
        userOption = reader.readLine(); 

        int option = Integer.parseInt(userOption);

        if (option != 1 && option != 2) {
          throw new InvalidParameterException();
        }

        break;
      } catch (Exception e) {
        System.out.println("\nDigite uma opcao valida!\n");
      }
    } while(true);

    String filename = null;
    FileManager file = null;
    if (Integer.parseInt(userOption) == 1) {
      do {
        Menu.printFileCompress();
        try {
          filename = reader.readLine();
          file = new FileManager(filename);
          break;
        } catch(Exception e) {
          System.out.println("\nERRO: Digite um nome de arquivo valido!");
        }
      } while (true);

      System.out.println(huffman.compress(file));
    }

    if (Integer.parseInt(userOption) == 2) {
      Menu.printFileDecompress();

      try {
        filename = reader.readLine();
      } catch(Exception e) {}
    }

    // String text = "Esse e um texto de testes para compactacao";

    // HuffmanEncoder compressor = new HuffmanEncoder();

    // System.out.println("Compressed: " + compressor.compress(text));
    // System.out.println("Decompressed: "+ compressor.decompress(compressor.compress(text)));
  } 
} 