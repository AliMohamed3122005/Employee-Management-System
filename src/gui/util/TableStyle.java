package gui.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public final class TableStyle {
    private static final Color GRID = new Color(0xD9D9D3);
    private static final Color HEADER = new Color(0xE3EBD5);
    private static final Color SELECTED = new Color(0xD9E8D9);
    private static final Color SELECTED_TEXT = new Color(0x173B28);

    private TableStyle() { }

    public static void configure(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(34);
        table.setFillsViewportHeight(true);
        table.setGridColor(GRID);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(SELECTED);
        table.setSelectionForeground(SELECTED_TEXT);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(0, 38));

        DefaultTableCellRenderer header = new DefaultTableCellRenderer();
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(HEADER);
        header.setOpaque(true);

        DefaultTableCellRenderer cells = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean selected, boolean focus,
                    int row, int column) {
                Component cell = super.getTableCellRendererComponent(
                        table, value, selected, focus, row, column);
                if (!selected) {
                    cell.setBackground(row % 2 == 0
                            ? Color.WHITE : new Color(0xF5F5F2));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 8));
                return cell;
            }
        };

        for (int column = 0; column < table.getColumnCount(); column++) {
            table.getColumnModel().getColumn(column).setHeaderRenderer(header);
            table.getColumnModel().getColumn(column).setCellRenderer(cells);
        }
    }
}
