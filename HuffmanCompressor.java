import shared.HuffmanEncoder;
import shared.Node;

public class HuffmanCompressor {
  public static void main(String[] args) {
    HuffmanEncoder compressor = new HuffmanEncoder();
    String text = "Esse e um texto de testes para compactacao";

    int[] frequency = compressor.getFrequencyArray(text);

    Node root = compressor.geHuffmanTree(frequency);
  } 
}