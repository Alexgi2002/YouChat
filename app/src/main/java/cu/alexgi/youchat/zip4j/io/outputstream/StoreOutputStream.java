package cu.alexgi.youchat.zip4j.io.outputstream;

class StoreOutputStream extends CompressedOutputStream {

  public StoreOutputStream(CipherOutputStream cipherOutputStream) {
    super(cipherOutputStream);
  }

}
