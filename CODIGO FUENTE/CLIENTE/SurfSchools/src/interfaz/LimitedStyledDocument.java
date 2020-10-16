/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author pablo
 * Clase que controla que los TextArea no superen el tamaño definidio de 255 caracteres
 */
public class LimitedStyledDocument extends DefaultStyledDocument {

int maxCharacters;

public LimitedStyledDocument(int maxChars) {
maxCharacters = maxChars;
}

public void insertString(int offs, String str, AttributeSet a)
throws BadLocationException {

if ((getLength() + str.length()) <= maxCharacters)
super.insertString(offs, str, a);
else
Toolkit.getDefaultToolkit().beep();
}
}
