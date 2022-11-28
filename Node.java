public class Node implements Comparable<Node> {
  private int frequency;
  private char character;
  private Node left;
  private Node right;

  public Node (int frequency, char character) {
    this.frequency = frequency;
    this.character = character;
    this.left = null;
    this.right = null;
  }

  public Node (int frequency, char character, Node left, Node right) {
    this.frequency = frequency;
    this.character = character;
    this.left = left;
    this.right = right;
  }

  public int getFrequency() {
    return frequency;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  public int getCharacter() {
    return character;
  }

  public void setCharacter(char character) {
    this.character = character;
  }

  public Node getLeft() {
    return left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(Node right) {
    this.right = right;
  }

  public boolean isLeaf() {
    return (left == null && right == null);
  }

  @Override
  public int compareTo(Node comparedNode) {
    return this.frequency - comparedNode.frequency;
  }
}
