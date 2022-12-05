public class HuffmanEncoder {
  public void compress(FileManager data, String newFilename) {
    Compressor.compress(data, newFilename);
  }

  public void decompress(FileManager data, String newFilename) {
    Decompressor.decompress(data, newFilename);
  }
}