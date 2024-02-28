import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;





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

   

    
  
    
}