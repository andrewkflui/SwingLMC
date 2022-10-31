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
import javax.swing.JFrame;
import java.lang.Class;

public class SwingLMC {
    public static void main(String[] args) throws Exception {
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            final Class launchClass = Class.forName("faifai.swinglmc.LMCDecimalFrame");
            JFrame frame = (JFrame) launchClass.newInstance();
            frame.pack();
            frame.setVisible(true);
            return;
        } catch (Exception ex) {
            System.err.println("Launcher: Class not found\n");
        }
    }
}
