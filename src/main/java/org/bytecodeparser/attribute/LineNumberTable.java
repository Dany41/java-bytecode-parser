package org.bytecodeparser.attribute;

import org.bytecodeparser.structures.AttributeInfo;
import org.bytecodeparser.structures.PrettyPrintable;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LineNumberTable extends AttributeInfo {
  private final short lineNumberTableLength;
  private final LineNumber[] lineNumberTable;

  public LineNumberTable(short attributeNameIndex, int attributeLength, DataInputStream dataInputStream) throws IOException {
    super(attributeNameIndex, attributeLength);
    this.lineNumberTableLength = dataInputStream.readShort();
    this.lineNumberTable = LineNumber.readNClassInfo(dataInputStream, lineNumberTableLength);
  }

  @Override
  public String toPrettyString(int tabs) {
    return "LineNumberTable {" +
            "\n" + "\t".repeat(tabs) + "attribute_name_index = " + attributeNameIndex + ";" +
            "\n" + "\t".repeat(tabs) + "attribute_length = " + attributeLength + ";" +
            "\n" + "\t".repeat(tabs) + "line_number_table_length = " + lineNumberTableLength + ";" +
            "\n" + "\t".repeat(tabs) + "line_number_table = " +
            Arrays.stream(lineNumberTable)
                    .map(ln -> ln.toPrettyString(tabs + 3))
                    .collect(Collectors
                            .joining(
                                    "\n" + "\t".repeat(tabs + 2),
                                    "{\n" + "\t".repeat(tabs + 2),
                                    "\n" + "\t".repeat(tabs + 1) + "}"))
            + ";" +
            "\n" + "\t".repeat(tabs - 1) + '}';
  }

  static class LineNumber implements PrettyPrintable {
    private final short startPc;
    private final short lineNumber;

    public static LineNumber[] readNClassInfo(DataInputStream dataInputStream, int size)
        throws IOException {
      LineNumber[] lineNumbers = new LineNumber[size];
      for (int i = 0; i < size; i++) {
        lineNumbers[i] = new LineNumber(dataInputStream);
      }
      return lineNumbers;
    }

    public LineNumber(DataInputStream dataInputStream) throws IOException {
      this.startPc = dataInputStream.readShort();
      this.lineNumber = dataInputStream.readShort();
    }

    @Override
    public String toPrettyString(int tabs) {
      return "LineNumber {" +
              "\n" + "\t".repeat(tabs) + "start_pc = " + startPc + ";" +
              "\n" + "\t".repeat(tabs) + "line_number = " + lineNumber + ";" +
              "\n" + "\t".repeat(tabs - 1) + '}';
    }
  }
}
