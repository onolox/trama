package visao;


import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RendererLinha extends DefaultTableCellRenderer {
    public RendererLinha() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        JLabel label = ( JLabel ) super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );

        label.setHorizontalAlignment( RIGHT );
        label.setOpaque( true );
        label.setBackground( javax.swing.UIManager.getDefaults().getColor( "Button.light" ) );
        label.setBorder( new javax.swing.border.SoftBevelBorder( javax.swing.border.BevelBorder.RAISED ) );


        return label;
    }
}
