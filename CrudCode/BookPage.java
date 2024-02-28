public class BookPage extends javax.swing.JFrame {

    Connection con=null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    
    DefaultTableModel defaultTableModel = new DefaultTableModel(); 
    
    public BookPage() {
        initComponents();
        con = Connector.ConnectDb();
        Object columns[] = {"BookID", "Bookname", "BookDescription", "BookPrice", "publisher_name"};
        defaultTableModel.setColumnIdentifiers(columns);
        jTable1.setModel(defaultTableModel);
        Display();
    }
    
    public void AddBook()
    {
       
        String BookName=BookNameTXT.getText();
        String BookDescription=BookDescriptionTXT.getText();
        float BookPrice=Integer.parseInt(BookPriceTXT.getText());
        String BookPublisher=BookPublisherTXT.getText();
        
            
     try 
     {
        String query = "INSERT INTO book (BookName, BookDescription, BookPrice, publisher_name) VALUE ('"+BookName+"','"+BookDescription+"','"+BookPrice+"','"+BookPublisher+"')";
        PreparedStatement demo = con.prepareStatement(query);  
        demo.execute();
        JOptionPane.showMessageDialog(null, "Record sucessfully save");
     } catch(HeadlessException | SQLException e)
            {
                JOptionPane.showMessageDialog(null, "Record NOT sucessfully save");
            }
    }
    
    public void Clear()
    {
        BookNameTXT.setText("");
        BookDescriptionTXT.setText("");
        BookPriceTXT.setText("");
        BookPublisherTXT.setText("");
    }
    
    public void Display()
    {
        con = Connector.ConnectDb();
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        String sql = "select BookID, BookName, BookDescription, BookPrice, publisher_name  from book";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            Object columnData[] = new Object[5];
            while (rs.next()) {
                columnData[0] = rs.getInt("BookID");
                columnData[1] = rs.getString("BookName");
                columnData[2] = rs.getString("BookDescription");
                columnData[3] = rs.getString("BookPrice");
                columnData[4] = rs.getString("publisher_name");
                
                defaultTableModel.addRow(columnData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

   

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       MainMenuPage GoMainMenuPage = new MainMenuPage();
       GoMainMenuPage.setVisible(true);
       this.setVisible(false);
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       BookEditPage GoBookEditPage = new BookEditPage();
       GoBookEditPage.setVisible(true);
       this.setVisible(false);
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        AddBook();
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Clear();
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Display();
    }                                        

  
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JTextArea BookDescriptionTXT;
    private javax.swing.JTextField BookNameTXT;
    private javax.swing.JTextField BookPriceTXT;
    private javax.swing.JTextField BookPublisherTXT;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration                   
}