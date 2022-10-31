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
public class Memory {
  
  static int size = 1024;
  static byte data[];
  
  static {
    data = new byte[size];
    for (int i=0; i<size; i++)
      data[i] = 0;
  }
  
  public static int size() {
    return size;
  }
  
  public static byte getData(int address) throws Exception {
    if (address < 0 || address >= size)
      throw new Exception("[System Error] Address out of range");
    return data[address];
  }
  
  public static String getDataHex(int address) throws Exception {
    return toHex(getData(address));
  }
  
  public static void setData(int address, byte value) throws Exception {
    if (address < 0 || address >= size)
      throw new Exception("[System Error] Address out of range");
    data[address] = value;    
  }
  
  private static String toHex(byte b) { 
    return (""+"0123456789ABCDEF".charAt(0xf&b>>4)+"0123456789ABCDEF".charAt(b&0xf));
  } 
  
}
