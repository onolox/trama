import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
 
public class VerticalHeaderTable extends JTable {
    
    String [] [] data = {{"Row0Col0", "Row0Col1", "Row0Col2"},
    {"Row1Col0", "Row1Col1", "Row1Col2"},
    {"Row2Col0", "Row2Col1", "Row2Col2"}};
    String [] header = {"Col0", "Col1", "Col2"};
    
    TableModel model = new DefaultTableModel (data, header);
    
    
    public VerticalHeaderTable () {
        
        setModel (model);
        Enumeration <TableColumn> columns = getColumnModel ().getColumns ();
        while (columns.hasMoreElements ()) {
            columns.nextElement ().setHeaderRenderer (new VerticalRenderer ());
        }
    }
    
    public static void main (String[] args) {
        SwingUtilities.invokeLater (new Runnable () {
            public void run () {
                
                VerticalHeaderTable vht = new VerticalHeaderTable ();
                JScrollPane scroll = new JScrollPane (vht);
                JFrame frame = new JFrame ("Vertical Header Table");
                frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
                frame.add (scroll);
                frame.pack ();
                frame.setVisible (true);
            }
        });
    }
    
}
 
class VerticalRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent (JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        JLabel label = new JLabel ();
        Icon icon = VerticalCaption.getVerticalCaption (label, value.toString (), false);
        label.setIcon (icon);
        label.setHorizontalAlignment (JLabel.CENTER);
        label.setBorder (new BevelBorder (BevelBorder.RAISED));
        return label;
    }
    
}
 
class VerticalCaption {
    
    static Icon getVerticalCaption (JComponent component, String caption, boolean clockwise) {
        Font f = component.getFont ();
        FontMetrics fm = component.getFontMetrics (f);
        int captionHeight = fm.getHeight ();
        int captionWidth = fm.stringWidth (caption);
        BufferedImage bi = new BufferedImage (captionHeight + 4,
                captionWidth + 4, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bi.getGraphics ();
        
        g.setColor (new Color (0, 0, 0, 0)); // transparent
        g.fillRect (0, 0, bi.getWidth (), bi.getHeight ());
        
        g.setColor (component.getForeground ());
        g.setFont (f);
        g.setRenderingHint (RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        if (clockwise) {
            g.rotate (Math.PI / 2);
        } else {
            g.rotate (- Math.PI / 2);
            g.translate (-bi.getHeight (), bi.getWidth ());
        }
        g.drawString (caption, 2, -6);
        
        Icon icon = new ImageIcon (bi);
        return icon;
    }
}
