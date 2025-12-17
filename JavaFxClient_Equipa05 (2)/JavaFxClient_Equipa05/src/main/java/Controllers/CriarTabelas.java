package Controllers;

/**@author aires
 * @version 1
 */
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class CriarTabelas 
{

    public static <T> void show(String title, List<T> items, Class<T> type) 
    {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(null);

        TableView<T> table = createTable(items, type);

        DialogPane pane = dialog.getDialogPane();
        pane.setContent(table);
        pane.getButtonTypes().add(ButtonType.CLOSE);
        pane.setPrefWidth(800);
        pane.setPrefHeight(600);

        dialog.showAndWait();
    }

    public static <T> Optional<T> showAndSelect(String title, List<T> items, Class<T> type) 
    {
        Dialog<T> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("Selecione um item da lista");

        TableView<T> table = createTable(items, type);

        DialogPane pane = dialog.getDialogPane();
        pane.setContent(table);
        pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        pane.setPrefWidth(800);
        pane.setPrefHeight(600);

        dialog.setResultConverter(buttonType -> 
        {
            if (buttonType == ButtonType.OK) 
            {
                return table.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private static <T> TableView<T> createTable(List<T> items, Class<T> type) 
    {
        TableView<T> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(items));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (Field field : type.getDeclaredFields()) 
        {
            // Simple logic: all fields. 
            // In a real app we might want to filter, but this is better than TextArea.
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;
           
            TableColumn<T, String> col = new TableColumn<>(field.getName());
            col.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            table.getColumns().add(col);
        }
        return table;
    }
}
