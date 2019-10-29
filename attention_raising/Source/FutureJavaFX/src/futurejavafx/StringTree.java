/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futurejavafx;

/**
 *
 * @author fupn26
 */
class StringTree extends javafx.scene.control.TreeView<String> {

    protected final javafx.scene.image.Image propIcon
            = new javafx.scene.image.Image(getClass().getResourceAsStream("activity.png"));

    protected final javafx.scene.image.Image actIcon = new javafx.scene.image.Image(getClass().getResourceAsStream("activity.png"));

    protected final javafx.scene.image.Image actpropsIcon = new javafx.scene.image.Image(getClass().getResourceAsStream("activityprops.png"));

    javafx.scene.control.TextArea propsEdit;

    private boolean isInvalid(String text) {
        try {
            int d = Integer.parseInt(text);
        } catch (NumberFormatException | NullPointerException nfe) {
            return true;
        }
        if(Integer.parseInt(text) < 0 || Integer.parseInt(text) > 100) return true;
        return false;
    }

    public StringTree(java.util.List<String> properties, boolean expanded, javafx.scene.control.TextArea propsEdit) {

        super();

        this.propsEdit = propsEdit;

        javafx.scene.control.TreeItem<String> root = new javafx.scene.control.TreeItem<String>("Én, magam", new javafx.scene.image.ImageView(propIcon));
        root.setExpanded(expanded);
        setRoot(root);

        for (String prop : properties) {

            javafx.scene.control.TreeItem<String> where = root;

            java.util.StringTokenizer st = new java.util.StringTokenizer(prop, "/");
            StringBuilder prevTokens = new StringBuilder();

            while (st.hasMoreTokens()) {
                String t = st.nextToken();

                if (prevTokens.toString().length() > 0) {
                    prevTokens.append(System.getProperty("file.separator"));
                }

                prevTokens.append(t);

                t = prevTokens.toString();

                boolean found = false;
                for (javafx.scene.control.TreeItem<String> actNode : where.getChildren()) {
                    if (actNode.getValue().contentEquals(t)) {
                        found = true;
                        where = actNode;
                        break;
                    }
                }
                if (!found) {
                    javafx.scene.control.TreeItem<String> actNode = new javafx.scene.control.TreeItem<String>(t, new javafx.scene.image.ImageView(propIcon));
                    where.getChildren().add(actNode);
                    where = actNode;

                }

            }

        }

        setOnMouseClicked((javafx.scene.input.MouseEvent mouseEvent) -> {
            {
                if (mouseEvent.getClickCount() == 2) {
                    javafx.scene.control.TreeItem<String> item = getSelectionModel().getSelectedItem();
                    if (item != null) {
                        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog("Add meg az értéket!");
                        dialog.setTitle("Érték megadása");
                        dialog.setHeaderText(item.getValue());
                        dialog.setContentText("Érték (0-100)");
                        
                        javafx.scene.Node okButton = dialog.getDialogPane().lookupButton(javafx.scene.control.ButtonType.OK);
                        okButton.setDisable(true);
                        javafx.scene.control.TextField inputField = dialog.getEditor();
                        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
                            okButton.setDisable(isInvalid(newValue));
                        });


                        java.util.Optional<String> result = dialog.showAndWait();

                        result.ifPresent(name -> {
                            propsEdit.appendText(item.getValue() + " " + name);
                            propsEdit.appendText(System.getProperty("line.separator"));

                        });
                    }
                }
            }
        });

    }

}
