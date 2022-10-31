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

public class MemoryDecimal {
  
  static int size = 100;
  static int data[];
  
  static {
    data = new int[size];
    for (int i=0; i<size; i++)
      data[i] = 0;
  }
  
  public static int size() {
    return size;
  }
  
  public static int getData(int address) throws Exception {
    if (address < 0 || address >= size)
      throw new Exception("[System Error] Address out of range");
    return data[address];
  }
  
  public static void setData(int address, int value) throws Exception {
    if (address < 0 || address >= size)
      throw new Exception("[System Error] Address out of range");
    data[address] = value;    
  }
  
  public static void setData(int memory[]) {
    int len = memory.length;
    for (int i=0; i<len; i++)
      data[i] = memory[i];    
  }    
}
