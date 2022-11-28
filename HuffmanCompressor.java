public class HuffmanCompressor {
  public static void main(String[] args) {
    HuffmanEncoder compressor = new HuffmanEncoder();
    String text = "Esse e um texto de testes para compactacao";

    int[] frequency = compressor.getFrequencyArray(text);
    Node root = compressor.geHuffmanTree(frequency);
    compressor.geHuffmanTree(frequency);

    System.out.println(root);

    Priority priority = new Priority<Integer>();
    System.out.println(priority);

    priority.add(2);
    priority.add(24);
    priority.add(4);
    priority.add(2);
    priority.add(9);
    priority.add(2);
    
    System.out.println(priority);
  } 
}