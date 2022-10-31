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

import javax.swing.JOptionPane;

public class Processor {
  
  static int programCounter = 0;
  static int instructionRegister = 0;
  static int accumulator = 0;
  
  static boolean suspended = true;
  
  public static boolean executeInstruction() throws Exception {
    if (suspended)
      return false;
    instructionRegister = MemoryDecimal.getData(programCounter);
    programCounter++;
    if (instructionRegister >= 903) {
      throw new Exception("[Processor Error] Invalid Instruction");    
    } else if (instructionRegister == 901) {
      String input = JOptionPane.showInputDialog(null, "Enter an integer (0 to 999)", "Input", JOptionPane.OK_CANCEL_OPTION);
      int value = -1;
      try {
        value = Integer.parseInt(input);
        accumulator = value;
      } catch (Exception ex) {
        throw new Exception("[Processor Error] Input Error");
      }
      if (value < 0 || value > 999)
        throw new Exception("[Processor Error] Input Out Of Range Error");        
    } else if (instructionRegister == 902) {
      OutputBuffer.write(accumulator + "");
    } else if (instructionRegister >= 800) {
      if (accumulator >= 0)
        programCounter = instructionRegister - 800;
    } else if (instructionRegister >= 700) {
      if (accumulator == 0)
        programCounter = instructionRegister - 700;      
    } else if (instructionRegister >= 600) {
      programCounter = instructionRegister - 600;
    } else if (instructionRegister >= 500) {
      accumulator = MemoryDecimal.getData(instructionRegister - 500);
    } else if (instructionRegister >= 400) {
      throw new Exception("[Processor Error] Invalid Instruction");
    } else if (instructionRegister >= 300) {
      MemoryDecimal.setData(instructionRegister - 300, accumulator);
    } else if (instructionRegister >= 200) {
      accumulator -= MemoryDecimal.getData(instructionRegister - 200);
    } else if (instructionRegister >= 100) {
      accumulator += MemoryDecimal.getData(instructionRegister - 100);
    } else if (instructionRegister == 0) {
      suspended = true;
      return false;
    } else {
      throw new Exception("[Processor Error] Invalid Instruction");      
    }
    return true;
  }
  
  public static void reset() {
    programCounter = 0;
    suspended = false;
  }
  
}
