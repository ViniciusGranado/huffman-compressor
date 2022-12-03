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
    String newFilename = null;
    FileManager file = null;

    if (Integer.parseInt(userOption) == 1) {
      do {
        Menu.printFileCompress();
        try {
          filename = reader.readLine();
          file = new FileManager(filename, "r");
          break;
        } catch(Exception e) {
          System.out.println("\nERRO: Digite um nome de arquivo valido!");
          System.out.println(e);
        }
      } while (true);

      Menu.printNewFileName();
      try {
        newFilename = reader.readLine();
      } catch(Exception e) {
        System.out.println(e);
        System.exit(0);
      }

      Menu.printCompressing();

      huffman.compress(file, newFilename);

      Menu.printCompressed();
    }

  //   if (Integer.parseInt(userOption) == 2) {
  //     do {
  //       Menu.printFileDecompress();
  //       try {
  //         filename = reader.readLine();
  //         file = new FileManager(filename, "rb");
  //         break;
  //       } catch(Exception e) {
  //         System.out.println("\nERRO: Digite um nome de arquivo valido!");
  //       }
  //     } while (true);

  //     Menu.printNewFileName();
  //     try {
  //       newFilename = reader.readLine();
  //     } catch(Exception e) {
  //       System.out.println(e);
  //       System.exit(0);
  //     }

  //     Menu.printDecompressing();

  //     huffman.decompress(file, newFilename);

  //     Menu.printDecompressed();
  //   }
  } 
} 