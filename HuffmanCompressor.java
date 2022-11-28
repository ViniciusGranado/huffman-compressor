import shared.HuffmanEncoder;

public class HuffmanCompressor {
  public static void main(String[] args) {
    HuffmanEncoder compressor = new HuffmanEncoder();
    String text = "Esse e um texto de testes para compactacao";

    System.out.println(compressor.compress(text));
  } 
}