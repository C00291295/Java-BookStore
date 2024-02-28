import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class BookEditPage extends javax.swing.JFrame {

    Connection con=null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    
    DefaultTableModel defaultTableModel = new DefaultTableModel();
    
    public BookEditPage() {
        initComponents();
        con = Connector.ConnectDb();
        Object columns[] = {"BookID", "Bookname", "BookDescription", "BookPrice", "publisher_name"};
        defaultTableModel.setColumnIdentifiers(columns);
        jTable1.setModel(defaultTableModel);
        Display();
        Hid();
         
    }

    public void Hid() {
        BookIDtxt.setVisible(false);
    }
    public void UpdateBook() {
    String BookName = BookNameTXT.getText();
    String BookDescription = BookDescriptionTXT.getText();
    float BookPrice = Float.parseFloat(BookPriceTXT.getText());
    String BookPublisher = BookPublisherTXT.getText();
    String BookID=BookIDtxt.getText();

    try {
        
        String query = "UPDATE book SET BookName=?, BookDescription=?, BookPrice=?, publisher_name=? WHERE BookID=?";
        PreparedStatement demo = con.prepareStatement(query);

        // Ensure the lengths of BookDescription and BookPublisher are within the specified limits
       

        demo.setString(1, BookName);
        demo.setString(2, BookDescription);
        demo.setFloat(3, BookPrice);
        demo.setString(4, BookPublisher);
        demo.setString(5, BookID);  // Assuming BookID is the primary key

        int rowsAffected = demo.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Record successfully updated");
        } else {
            JOptionPane.showMessageDialog(null, "Record not found or update unsuccessful");
        }
    } catch (HeadlessException | SQLException e) {
        JOptionPane.showMessageDialog(null, "Error updating record: " + e.getMessage());
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
    
    public void GetDataFromTable()
    {
        String  BookIDT= defaultTableModel.getValueAt (jTable1.getSelectedRow(), 0).toString();
        String  BookNameT= defaultTableModel.getValueAt (jTable1.getSelectedRow(), 1).toString();
        String  BookDescriptionT= defaultTableModel.getValueAt (jTable1.getSelectedRow(), 2).toString();
        String  BookPriceT= defaultTableModel.getValueAt (jTable1.getSelectedRow(), 3).toString();
        String  BookPublisherT= defaultTableModel.getValueAt (jTable1.getSelectedRow(), 4).toString();
        
        BookIDtxt.setText (BookIDT);
        BookNameTXT.setText (BookNameT);
        BookDescriptionTXT.setText (BookDescriptionT);
        BookPriceTXT.setText (BookPriceT);
        BookPublisherTXT.setText (BookPublisherT);
    }
    
    public void Search()
    {
        con = Connector.ConnectDb();
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        
        String SearchBook = SearchTXT.getText();
        
        String sql = "select BookID, BookName, BookDescription, BookPrice, publisher_name  from book WHERE BookName LIKE ?";
        try {
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + SearchBook + "%"); // Use LIKE with wildcards to match partial names
                rs = ps.executeQuery();
                

                Object[] columnData = new Object[5];
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
    
    public void deleteBook(int BookID) {
    try {
        // Replace "your_table" with your actual table name
        String query = "DELETE FROM book WHERE BookID=?";
        PreparedStatement demo = con.prepareStatement(query);
        
        demo.setInt(1, BookID);

        int rowsAffected = demo.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Record successfully deleted");
        } else {
            JOptionPane.showMessageDialog(null, "Record not found or delete unsuccessful");
        }
    } catch (HeadlessException | SQLException e) {
        JOptionPane.showMessageDialog(null, "Error deleting record: " + e.getMessage());
    }
}
    
    
}
