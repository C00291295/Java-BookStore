package bookstoremanagementsystem;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class IntOnlyTextField {
    public static void restrictToInteger(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new IntFilter());
    }

    private static class IntFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            StringBuilder sb = new StringBuilder();
            sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.insert(offset, text);
            if (!isInteger(sb.toString())) {
                showMessage("Only integer values are allowed.");
            } else {
                super.insertString(fb, offset, text, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            StringBuilder sb = new StringBuilder();
            sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.replace(offset, offset + length, text);
            if (!isInteger(sb.toString())) {
                showMessage("Only integer values are allowed.");
            } else {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isInteger(String s) {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private void showMessage(String message) {
            JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
