package shared;

import java.util.Map;

public class HuffmanTree {
  Node root;

  public HuffmanTree(Priority<Node> queue) {
    if (queue.size() == 1) {
      queue.add(new Node(1, (byte) 0, null, null));
    }

    while (queue.size() > 1) {
      Node left = queue.poll();
      Node right = queue.poll();

      Node newNode = new Node((left.getFrequency() + right.getFrequency()), null, left, right);

      queue.add(newNode);
    }

    root = queue.poll();
  }

  public Node getRoot() {
    return root;
  }

  public void setRoot(Node root) {
    this.root = root;
  }
}
