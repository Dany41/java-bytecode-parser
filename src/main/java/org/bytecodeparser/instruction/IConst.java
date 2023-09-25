package org.bytecodeparser.instruction;

import org.bytecodeparser.print.CustomPrettyPrint;

import java.io.DataInputStream;

@Opcode({2, 3, 4, 5, 6, 7, 8})
@CustomPrettyPrint
public class IConst implements Instruction {

    private final String opCodeAdd;

    public IConst(DataInputStream dataInputStream, int opCode) {
        switch (opCode) {
            case 2:
                this.opCodeAdd = "_m1";
                break;
            case 3:
                this.opCodeAdd = "_0";
                break;
            case 4:
                this.opCodeAdd = "_1";
                break;
            case 5:
                this.opCodeAdd = "_2";
                break;
            case 6:
                this.opCodeAdd = "_3";
                break;
            case 7:
                this.opCodeAdd = "_4";
                break;
            case 8:
                this.opCodeAdd = "_5";
                break;
            default:
                this.opCodeAdd = "";
        }
    }

    @SuppressWarnings("unused")
    private String prettyPrint() {
        return getOpcode() + opCodeAdd;
    }
}
