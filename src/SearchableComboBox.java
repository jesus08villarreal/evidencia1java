import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SearchableComboBox extends JComboBox<ComboBoxItem> {
    private final List<ComboBoxItem> items;

    public SearchableComboBox(List<ComboBoxItem> items) {
        super(items.toArray(new ComboBoxItem[0]));
        this.items = new ArrayList<>(items);
        setEditable(true);
        JTextField textField = (JTextField) getEditor().getEditorComponent();
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = textField.getText();
                updateComboBox(text);
            }
        });
        setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setText(value.toString());
                return this;
            }
        });
        setEditor(new BasicComboBoxEditor() {
            @Override
            public Component getEditorComponent() {
                JTextField editor = (JTextField) super.getEditorComponent();
                editor.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        String text = editor.getText();
                        updateComboBox(text);
                    }
                });
                return editor;
            }
        });
    }

    private void updateComboBox(String text) {
        List<ComboBoxItem> filteredItems = items.stream()
                .filter(item -> item.toString().toLowerCase().contains(text.toLowerCase()))
                .toList();
        removeAllItems();
        for (ComboBoxItem item : filteredItems) {
            addItem(item);
        }
        setSelectedItem(new ComboBoxItem("", text));
        showPopup();
    }
}
