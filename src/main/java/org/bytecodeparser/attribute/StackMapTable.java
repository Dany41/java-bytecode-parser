package org.bytecodeparser.attribute;

import lombok.Getter;

import java.io.DataInputStream;
import java.io.IOException;

import static org.bytecodeparser.attribute.StackMapTable.StackMapFrame.readNStackMapFrame;
import static org.bytecodeparser.attribute.StackMapTable.VerificationTypeInfo.readNVerificationInfo;

public class StackMapTable {
  private final short attributeNameIndex;
  private final int attributeLength;
  private final short numberOfEntries;
  private final StackMapFrame[] entries;


  public StackMapTable(DataInputStream dataInputStream) throws IOException {
    this.attributeNameIndex = dataInputStream.readShort();
    this.attributeLength = dataInputStream.readInt();
    this.numberOfEntries = dataInputStream.readShort();
    this.entries = readNStackMapFrame(dataInputStream, numberOfEntries);
  }

  static final class StackMapFrame {
    @Getter
    private final short type;
    private final short offsetDelta;
    private final short numberOfLocals;
    private final VerificationTypeInfo[] locals;
    private final short numberOfStackItems;
    private final VerificationTypeInfo[] stack;

    public StackMapFrame(DataInputStream dataInputStream) throws IOException {
      this.type = dataInputStream.readByte();

      short offsetDelta = 0;
      short numberOfLocals = 0;
      VerificationTypeInfo[] locals = new VerificationTypeInfo[numberOfLocals];
      short numberOfStackItems = 0;
      VerificationTypeInfo[] stack = new VerificationTypeInfo[numberOfStackItems];

      if (isSameLocals1StackItemFrame()) {
        numberOfStackItems = 1;
        stack = readNVerificationInfo(dataInputStream, numberOfStackItems);
      } else if(isSameLocals1StackItemFrameExtended()) {
        offsetDelta = dataInputStream.readShort();
        numberOfStackItems = 1;
        stack = readNVerificationInfo(dataInputStream, numberOfStackItems);
      } else if(isChopFrame() || isSameFrameExtended()) {
        offsetDelta = dataInputStream.readShort();
      } else if(isAppendFrame()) {
        offsetDelta = dataInputStream.readShort();
        numberOfStackItems = (short) (type - 251);
        stack = readNVerificationInfo(dataInputStream, numberOfStackItems);
      } else if(isFullFrame()) {
        offsetDelta = dataInputStream.readShort();
        numberOfLocals = dataInputStream.readShort();
        locals = readNVerificationInfo(dataInputStream, numberOfLocals);
        numberOfStackItems = dataInputStream.readShort();
        stack = readNVerificationInfo(dataInputStream, numberOfStackItems);
      }

      this.offsetDelta = offsetDelta;
      this.numberOfLocals = numberOfLocals;
      this.locals = locals;
      this.numberOfStackItems = numberOfStackItems;
      this.stack = stack;
    }

    public static StackMapFrame[] readNStackMapFrame(DataInputStream dataInputStream, int size)
        throws IOException {
      StackMapFrame[] frames = new StackMapFrame[size];
      for (int i = 0; i < size; i++) {
        frames[i] = new StackMapFrame(dataInputStream);
      }
      return frames;
    }

    public boolean isSameFrame() {
      return type >= 0 && type < 64;
    }

    public boolean isSameLocals1StackItemFrame() {
      return type >= 64 && type < 127;
    }

    public boolean isSameLocals1StackItemFrameExtended() {
      return type == 247;
    }

    public boolean isChopFrame() {
      return type >= 248 && type < 251;
    }

    public boolean isSameFrameExtended() {
      return type == 251;
    }

    public boolean isAppendFrame() {
      return type >= 252 && type < 254;
    }

    public boolean isFullFrame() {
      return type == 255;
    }


  }
  static final class VerificationTypeInfo {
    private final Item item;
    private final short cpoolIndex;
    private final short offset;

    public VerificationTypeInfo(DataInputStream dataInputStream) throws IOException {
      item = Item.values()[dataInputStream.readByte()];
      switch (item) {
        case ITEM_OBJECT -> {
          cpoolIndex = dataInputStream.readShort();
          offset = 0;
        }
        case ITEM_UNINITIALIZED -> {
          cpoolIndex = 0;
          offset = dataInputStream.readShort();
        }
        default -> {
          cpoolIndex = 0;
          offset = 0;
        }
      }
    }

    public static VerificationTypeInfo[] readNVerificationInfo(DataInputStream dataInputStream, int size)
        throws IOException {
      VerificationTypeInfo[] infos = new VerificationTypeInfo[size];
      for (int i = 0; i < size; i++) {
        infos[i] = new VerificationTypeInfo(dataInputStream);
      }
      return infos;
    }

    enum Item {
      ITEM_TOP,
      ITEM_INTEGER,
      ITEM_DOUBLE,
      ITEM_FLOAT,
      ITEM_LONG,
      ITEM_NULL,
      ITEM_UNINITIALIZED_THIS,
      ITEM_OBJECT,
      ITEM_UNINITIALIZED;
    }
  }





}
