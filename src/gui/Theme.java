package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

final class Theme
{
    private Theme() { }

    // ---- colour tokens from the style spec ----
    static final Color FOREST_GREEN      = new Color(0x075A38);
    static final Color DARK_GREEN        = new Color(0x064B30);
    static final Color TITLE_GREEN       = new Color(0x123D2A);
    static final Color IVORY             = new Color(0xFBF9F1);
    static final Color SELECTED_CREAM    = new Color(0xFFF8DF);
    static final Color SIDEBAR_SEPARATOR = new Color(0x79A38C);
    static final Color MAIN_SEPARATOR    = new Color(0x83A88F);
    static final Color GRID_GRAY         = new Color(0xD9D9D3);
    static final Color INPUT_BORDER      = new Color(0x9CB3C4);
    static final Color SECONDARY_BG      = new Color(0xF1EFE8);
    static final Color SECONDARY_TEXT    = new Color(0x222222);
    static final Color SECONDARY_BORDER  = new Color(0xC9C9C2);
    static final Color TEXT              = new Color(0x202020);

    // ---- fonts ----
    static Font bold(int size)  { return new Font("Segoe UI", Font.BOLD, size); }
    static Font plain(int size) { return new Font("Segoe UI", Font.PLAIN, size); }

    // ---- icons: returns null (no icon) if the PNG is not in src/gui/assets yet ----
    static ImageIcon icon(String fileName, int size)
    {
        File file = new File("src/gui/assets/" + fileName);
        if (!file.exists())
        {
            return null;
        }
        Image scaled = new ImageIcon(file.getPath()).getImage()
                .getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // ---- a flat button that paints its own background, so no Look and Feel can turn it gray ----
    static JButton button(String text, Color background, Color foreground, Color border, Icon icon)
    {
        JButton b = new JButton(text, icon)
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
                if (border != null)
                {
                    g.setColor(border);
                    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                }
                super.paintComponent(g);   // text + icon on top
            }
        };
        b.setBackground(background);
        b.setForeground(foreground);
        b.setFont(bold(14));
        b.setContentAreaFilled(false);     // stops the L&F from painting its own fill
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setIconTextGap(8);
        b.setBorder(BorderFactory.createEmptyBorder(9, 13, 9, 13));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ---- text field: 240 x 36, 1 px blue-gray border, 4/8 padding ----
    static void styleField(JTextField f)
    {
        f.setFont(plain(15));
        f.setBackground(Color.WHITE);
        f.setPreferredSize(new Dimension(240, 36));
        f.setMinimumSize(new Dimension(120, 34));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(INPUT_BORDER, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
    }

    // ---- thin green line under the screen title ----
    static JPanel separator()
    {
        JPanel line = new JPanel();
        line.setBackground(MAIN_SEPARATOR);
        line.setPreferredSize(new Dimension(0, 2));
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        return line;
    }

    // ---- title row + separator, reused by every screen ----
    static JPanel header(String title, String iconFile)
    {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        row.setOpaque(false);
        ImageIcon icon = icon(iconFile, 39);
        if (icon != null)
        {
            row.add(new JLabel(icon));
        }
        JLabel label = new JLabel(title);
        label.setFont(bold(30));
        label.setForeground(TITLE_GREEN);
        row.add(label);

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(row);
        header.add(Box.createVerticalStrut(13));
        JPanel line = separator();
        line.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(line);
        header.add(Box.createVerticalStrut(22));
        return header;
    }
}
