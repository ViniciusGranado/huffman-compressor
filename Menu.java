

public class Menu {
  public static void printMainMenu() {
    System.out.println("-------------------------------------------------");
    System.out.println("        COMPACTADOR DE ARQUIVOS HUFFMAN          ");
    System.out.println("-------------------------------------------------\n\n");

    System.out.println("SELECIONE A SUA OPCAO:\n");

    System.out.println("[1] COMPACTAR UM ARQUIVO");
    System.out.println("[2] DESCOMPACTAR UM ARQUIVO\n");
  }
  
  public static void printOptionLabel() {
    System.out.print("Digite a sua opcao: ");
  }

  public static void printFileCompress() {
    System.out.print("\nDigite o nome do arquivo que deseja compactar: ");
  }

  public static void printFileDecompress() {
    System.out.print("\nDigite o nome do arquivo que deseja descompactar: ");
  }

  public static void printNewFileName() {
    System.out.print("\nDigite o nome do arquivo que será gerado: ");
  }

  public static void printCompressing() {
    System.out.println("\nComprimindo...");
  }

  public static void printDecompressing() {
    System.out.println("\nDescompactando...");
  }

  public static void printCompressed() {
    System.out.println("\nO arquivo foi compactado com sucesso!");
  }

  public static void printDecompressed() {
    System.out.println("\nO arquivo foi descompactado com sucesso!");
  }
}
