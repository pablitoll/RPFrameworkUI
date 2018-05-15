package ar.com.rp.ui.pantalla;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

public class GUIUtils {

  public static void moveToFront(final JInternalFrame fr) {
    if (fr != null) {
      processOnSwingEventThread(new Runnable() {
        public void run() {
          fr.moveToFront();
          fr.setVisible(true);
          try {
            fr.setSelected(true);
            if (fr.isIcon()) {
              fr.setIcon(false);
            }
            fr.setSelected(true);
          } catch (PropertyVetoException ex) {

          }
          fr.requestFocus();          
        }
      });
    }
  }
  
  public static void processOnSwingEventThread(Runnable todo) {
    processOnSwingEventThread(todo, false);
  }

  public static void processOnSwingEventThread(Runnable todo, boolean wait) {
    if (todo == null) {
      throw new IllegalArgumentException("Runnable == null");
    }

    if (wait) {
      if (SwingUtilities.isEventDispatchThread()) {
        todo.run();
      } else {
        try {
          SwingUtilities.invokeAndWait(todo);
        } catch (Exception ex) {
          throw new RuntimeException(ex);
        }
      }
    } else {
      if (SwingUtilities.isEventDispatchThread()) {
        todo.run();
      } else {
        SwingUtilities.invokeLater(todo);
      }
    }
  }
}