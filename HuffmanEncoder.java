import java.util.Collections;
import java.util.PriorityQueue;

public class HuffmanEncoder {
  public CompressResult compress (String data) {
    int[] frequency = getFrequencyArray(data);
    return null;
  }

  public String decompress(CompressResult compressResult) {
    return null;
  }

  public static int[] getFrequencyArray(String data) {
    int[] frequency = new int[256]; // Valor 256 pois é o número de caracteres possíveos na tabela ASCII

    for (char c : data.toCharArray()) {
      frequency[c]++;
    }

    return frequency;
  }  

  public static Node geHuffmanTree(int[] frequency) {
    PriorityQueue<Node> queue = new PriorityQueue<>();

    for (char i = 0; i < 256; i++) {
      if (frequency[i] > 0) {
        queue.add(new Node(frequency[i], i));
      }
    }

    if (queue.size() == 1) {
      queue.add(new Node(1, '\0', null, null));
    }

    while(queue.size() > 1) {
      Node left = queue.poll();
      Node right = queue.poll();

      Node newNode = new Node((left.getFrequency() + right.getFrequency()), '\0', left, right);

      queue.add(newNode);
    }


    return queue.poll();
  }

  static class CompressResult {

  }
}