/***************************************************************
 *
 * Interactive LMC based on Swing
 * Copyright (c) 2007 Dr. Andrew Kwok-Fai LUI
 * The Open University of Hong Kong
 *
 * Enhance the learning effectiveness of students through greater interactions
 */
/*  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package faifai.swinglmc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

/*
 * Line Type 1: 12 LDA 10
 * Line Type 2: A: LDA 10
 * Line Type 3: LDA 10
 */

public class AssemblerDecimal {
  
  private static Hashtable symbolTable = new Hashtable();
  private static ArrayList instructionList = new ArrayList();
  private static String instructionSet1[] =
  {"LDA", "STO", "ADD", "SUB", "BR", "BRP", "BRZ", "DAT"};
  private static String instructionSet2[] =
  {"IN", "OUT", "HLT", "COB"};
  
  public static void assemble(String lmcprogram, int memory[]) throws Exception {
    for (int i=0; i<memory.length; i++)
      memory[i] = 0;
    passOne(lmcprogram);
    passTwo(memory);
    System.out.println("Symbol Table");
    printSymbolTable();
    System.out.println("Token List");
    printInstructionList();
  }
  
  private static boolean isInstruction(String instruction) {
    return isZeroInstruction(instruction) || isOneInstruction(instruction);
  }
  
  private static boolean isZeroInstruction(String instruction) {
    for (int i=0; i<instructionSet2.length; i++) {
      if (instruction.equalsIgnoreCase(instructionSet2[i]))
        return true;
    }
    return false;
  }
  
  private static boolean isOneInstruction(String instruction) {
    for (int i=0; i<instructionSet1.length; i++) {
      if (instruction.equalsIgnoreCase(instructionSet1[i]))
        return true;
    }
    return false;
  }
  
  public static void printSymbolTable() {
    Iterator it = symbolTable.keySet().iterator();
    while (it.hasNext()) {
      String key = (String)it.next();
      Integer value = (Integer)symbolTable.get(key);
      System.out.println(key + ": " + value);
    }
  }
  
  public static void printInstructionList() {
    int size = instructionList.size();
    for (int i=0; i<size; i++) {
      Instruction instruction = (Instruction)instructionList.get(i);
      System.out.print(instruction.address + ": " + instruction.code);
      if (instruction.operand != null)
        System.out.print(" " + instruction.operand);
      System.out.println(" at line " + instruction.lineNo);
    }
  }
  
  private static int parseNumber(String token) {
    try {
      int address = Integer.parseInt(token);
      return address;
    } catch (Exception ex) {}
    return -1;
  }
  
  public static void passOne(String lmcprogram) throws Exception {
    int lineNo = 0;
    int memoryAddress = -1;
    int state = 0;
    int type = -1;
    String tokenstr = "";
    instructionList.clear();
    StringTokenizer st = new StringTokenizer(lmcprogram, "\r\n");
    while (st.hasMoreTokens()) {
      lineNo++;
      String line = st.nextToken();
      
      int semicolonIndex = line.indexOf(';');
      if (semicolonIndex > -1)
        line = line.substring(0, semicolonIndex);
      line = line.trim();
      //System.out.println("line: " + line);
      if (line.length() == 0)
        continue;
      if (type == 2)
        throw new Exception("[Assemble Error] Expected instruction or ':' after tag at line " + (lineNo-1));
      else if (type != -1 && !tokenstr.equalsIgnoreCase("DAT"))
        throw new Exception("[Assemble Error] Invalid format at line " + (lineNo-1) + " " + type);        
      
      StringTokenizer linest = new StringTokenizer(line, " \t:", true);
      String instruction = "";
      String tag = "";
      int address = -1;
      state = 0;
      memoryAddress++;
      while (linest.hasMoreTokens()) {
        tokenstr = linest.nextToken();
        if (tokenstr.equals(" ") || tokenstr.equals("\t"))
          continue;
        switch (state) {
          case 0:
            address = parseNumber(tokenstr);
            if (address >= 0) { // is a number
              type = 1; state = 1;
              memoryAddress = address; // token is an address
            } else if (isOneInstruction(tokenstr)) {
              type = 3; state = 1;
              instruction = tokenstr;
            } else if (isZeroInstruction(tokenstr)) {
              type = -1; state = 0;
              instructionList.add(new Instruction(memoryAddress, tokenstr, null, lineNo));
            } else {
              type = 2; state = 1;
              tag = tokenstr;
            }
            //System.out.println("type = " + type + " state = " + state + " " + tokenstr);
            break;
          case 1:
            if (type == 1) {
              if (isOneInstruction(tokenstr)) {
                state = 2;
                instruction = tokenstr;
              } else if (isZeroInstruction(tokenstr)) {
                type = -1; state = 0;
                instructionList.add(new Instruction(memoryAddress, tokenstr, null, lineNo));
              } else {
                throw new Exception("[Assembler Error] Expected instruction at line " + lineNo);
              }
            } else if (type == 2) {
              if (tokenstr.equals(":")) {
                state = 2;
                symbolTable.put(tag, new Integer(memoryAddress));
              } else {
                throw new Exception("[Assembler Error] Invalid instruction or missing colon at line " + lineNo);
              }
            } else if (type == 3) {
              type = -1; state = 0;
              instructionList.add(new Instruction(memoryAddress, instruction, tokenstr, lineNo));
            }
            break;
          case 2:
            if (type == 1) {
              type = -1; state = 0;
              instructionList.add(new Instruction(memoryAddress, instruction, tokenstr, lineNo));
            } else if (type == 2) {
              if (isOneInstruction(tokenstr)) {
                state = 3;
                instruction = tokenstr;
              } else if (isZeroInstruction(tokenstr)) {
                type = -1; state = 0;
                instructionList.add(new Instruction(memoryAddress, instruction, null, lineNo));
              }
            }
            break;
          case 3:
            if (type == 2) {
              type = -1; state = 0;
              instructionList.add(new Instruction(memoryAddress, instruction, tokenstr, lineNo));
            }
            break;
        }
      }
    }
  }
  
  public static void passTwo(int memory[]) throws Exception {
    int size = instructionList.size();
    for (int i=0; i<size; i++) {
      Instruction instruction = (Instruction)instructionList.get(i);
      String icode = instruction.code;
      int code = 0;
      if (isOneInstruction(icode)) { // one operand instruction
        if (icode.equalsIgnoreCase("LDA")) {
          code = 500;
        } else if (icode.equalsIgnoreCase("STO")) {
          code = 300;
        } else if (icode.equalsIgnoreCase("ADD")) {
          code = 100;
        } else if (icode.equalsIgnoreCase("SUB")) {
          code = 200;
        } else if (icode.equalsIgnoreCase("BR")) {
          code = 600;
        } else if (icode.equalsIgnoreCase("BRP")) {
          code = 800;
        } else if (icode.equalsIgnoreCase("BRZ")) {
          code = 700;
        } else if (icode.equalsIgnoreCase("DAT")) {
          code = 000;
        }
        String operand = instruction.operand;
        if (operand == null)
          throw new Exception ("[Assembler Error] Missing operand at line " + instruction.lineNo);
        int address = parseNumber(operand);
        if (address < 0)
          if (!symbolTable.containsKey(operand))
            throw new Exception ("[Assembler Error] Undefined operand at line " + instruction.lineNo);            
          else
            address = ((Integer)(symbolTable.get(operand))).intValue();
        if (code != 0 && (address < 0 || address >= 100))
            throw new Exception ("[Assembler Error] Invalid operand address at line " + instruction.lineNo);
        memory[instruction.address] = code + address;
      } else { // zero operand instruction
        if (icode.equalsIgnoreCase("IN")) {
          code = 901;
        } else if (icode.equalsIgnoreCase("OUT")) {
          code = 902;
        } else if (icode.equalsIgnoreCase("COB") || icode.equalsIgnoreCase("HLT")) {
          code = 000;
        }
         memory[instruction.address] = code;       
      }
    }
  }
  
  static class Instruction {
    int address;
    String code;
    String operand;
    int lineNo;
    
    Instruction(int address, String code, String operand, int lineNo) {
      this.address = address;
      this.code = code;
      this.operand = operand;
      this.lineNo = lineNo;
    }
  }
}
