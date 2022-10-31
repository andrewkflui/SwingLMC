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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MemoryPanelDecimal extends javax.swing.JPanel {
    
    private ArrayList labelList = new ArrayList();
    private ArrayList textfieldList = new ArrayList();
    private int currentPage = 0;
    private int currentAddress = -1;
    private int memorySize = MemoryDecimal.size();
    
    private Font font1 = new java.awt.Font("Arial", 0, 10);
    private Font font2 = new java.awt.Font("Arial", 0, 14);
    private Font font3 = new java.awt.Font("Arial", Font.BOLD, 18);
    
    /** Creates new form MemoryPanel */
    public MemoryPanelDecimal() {
        initComponents();
        initPanel();
    }
    
    private void initPanel() {
        Font font = new Font("Arial", 0, 10);
        Dimension defaultDim = new Dimension(25, 20);
        for (int i=0; i<memorySize; i++) {
            if (i % 10 == 0) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(font1);
                label.setPreferredSize(defaultDim);
                jPanel1.add(label);
                labelList.add(label);
            }
            JTextField textfield = new JTextField();
            textfield.setFont(font1);
            textfield.setPreferredSize(defaultDim);
            textfield.setMinimumSize(defaultDim);
            
            textfield.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    textfieldFocusLost(e);
                }
            });
            
            jPanel1.add(textfield);
            textfieldList.add(textfield);
        }
        //this.setPreferredSize(new Dimension(280, 560));
        this.validate();
        refreshPanel();
    }
    
    public void setCurrentAddress(int address) {
        if (this.currentAddress >= 0) {
            JTextField textfield = (JTextField)textfieldList.get(currentAddress);
            textfield.setBackground(Color.WHITE);
        }
        this.currentAddress = address;
        if (this.currentAddress >= 0) {
            JTextField textfield = (JTextField)textfieldList.get(currentAddress);
            textfield.setBackground(Color.YELLOW);
        }
    }
    
    public void update() {
        refreshPanel();
    }
    
    public void setFontSize(int size) {
        Font thisFont = font1;
        switch (size) {
            case 1:
                thisFont = font1; break;
            case 2:
                thisFont = font2; break;
            case 3:
                thisFont = font3; break;
        }
        for (int i=0; i<memorySize; i++) {
            JTextField textfield = (JTextField)textfieldList.get(i);
            textfield.setFont(thisFont);
        }
    }
    
    public void refreshPanel() {
        try {
            if (currentPage == 0) {
                jButton1.setBackground(Color.WHITE);
                //jButton2.setBackground(Color.GRAY);
            } else {
                jButton1.setBackground(Color.GRAY);
                //jButton2.setBackground(Color.WHITE);
            }
            for (int i=0; i<100; i++) {
                int address = i + currentPage * 256;
                if (i % 10 == 0) {
                    JLabel label = (JLabel)labelList.get(i / 10);
                    label.setText("" + address);
                }
                JTextField textfield = (JTextField)textfieldList.get(i);
                textfield.setText(MemoryDecimal.getData(address) + "");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    jButton1 = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    jPanel1 = new javax.swing.JPanel();

    setLayout(new java.awt.GridBagLayout());

    jButton1.setFont(new java.awt.Font("Arial", 0, 10));
    jButton1.setText("Page 1");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        page1ActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    add(jButton1, gridBagConstraints);

    jPanel1.setLayout(new java.awt.GridLayout(10, 9));

    jScrollPane1.setViewportView(jPanel1);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    add(jScrollPane1, gridBagConstraints);

  }// </editor-fold>//GEN-END:initComponents
  
  private void page1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_page1ActionPerformed
      this.currentPage = 0;
      refreshPanel();
  }//GEN-LAST:event_page1ActionPerformed
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  // End of variables declaration//GEN-END:variables
  
  private void textfieldFocusLost(FocusEvent e) {
      JTextField textField = (JTextField)e.getSource();
      int address = -1;
      for (int i=0; i<textfieldList.size(); i++) {
          if (textField == textfieldList.get(i)) {
              address = i;
              break;
          }
      }
      String content = textField.getText();
      int value = -1;
      if (content.length() != 0) {
          try {
              value = Integer.parseInt(content);
              if (value < 0 || value > 999)
                  throw new Exception();
              MemoryDecimal.setData(address, value);
              textField.setBackground(Color.WHITE);
          } catch (Exception nfe) {
              getToolkit().beep();
              textField.setBackground(Color.PINK);
              textField.requestFocus();
          }
      }
  }
  
  public static void main(String args[]) throws Exception {
      MemoryPanelDecimal mpanel = new MemoryPanelDecimal();
      JFrame frame = new JFrame();
      frame.getContentPane().add(mpanel);
      frame.pack();
      frame.setVisible(true);
  }
  
}
