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
class FileTree extends javafx.scene.control.TreeView<java.io.File> {

    class FileTreeItem extends javafx.scene.control.TreeItem<java.io.File> {

        protected boolean leaf = true;

        public FileTreeItem(java.io.File file, javafx.scene.Node icon) {

            super(file, icon);

            leaf = file.isFile();

        }

        @Override
        public boolean isLeaf() {
            return leaf;
        }

        protected boolean treeChanged = true;

        @Override
        public javafx.collections.ObservableList<javafx.scene.control.TreeItem<java.io.File>> getChildren() {

            if (treeChanged) {
                treeChanged = false;
                super.getChildren().setAll(buildChildren(this));
            }
            return super.getChildren();
        }

        private javafx.collections.ObservableList<javafx.scene.control.TreeItem<java.io.File>>
                buildChildren(javafx.scene.control.TreeItem<java.io.File> treeItem) {

            java.io.File file = treeItem.getValue();
            if (file != null) {
                if (file.isDirectory()) {
                    java.io.File[] list = file.listFiles();
                    if (list != null) {
                        javafx.collections.ObservableList<javafx.scene.control.TreeItem<java.io.File>> children = javafx.collections.FXCollections.observableArrayList();

                        for (java.io.File f : list) {
                            if (f.isDirectory()) {
                                children.add(new FileTreeItem(f, new javafx.scene.image.ImageView(actIcon)));
                            } else {
                                children.add(new FileTreeItem(f, new javafx.scene.image.ImageView(actpropsIcon)));
                            }

                        }

                        return children;
                    }
                }
            }

            return javafx.collections.FXCollections.emptyObservableList();
        }

    }

    protected final javafx.scene.image.Image actIcon = new javafx.scene.image.Image(getClass().getResourceAsStream("activity.png"));

    protected final javafx.scene.image.Image actpropsIcon = new javafx.scene.image.Image(getClass().getResourceAsStream("activityprops.png"));

    public void save(javafx.scene.control.TextArea propsEdit, javafx.scene.control.Label actPropsLabel) {

        String old = actPropsLabel.getText();

        try {
            java.io.File oldf = new java.io.File(old);
            if (oldf.exists()) {

                java.io.FileWriter fileWriter = new java.io.FileWriter(oldf);
                fileWriter.write(propsEdit.getText());
                fileWriter.close();

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public FileTree(String cityName, boolean expanded, javafx.scene.control.TextArea propsEdit, javafx.scene.layout.VBox propsEditLayout, javafx.scene.layout.VBox stringTreeLayout, javafx.scene.control.Label actPropsLabel) {
        super();
        javafx.scene.control.TreeItem<java.io.File> root = new FileTreeItem(new java.io.File(cityName), new javafx.scene.image.ImageView(actIcon));
        root.setExpanded(expanded);
        setRoot(root);

        setCellFactory((javafx.scene.control.TreeView<java.io.File> p) -> new TextFieldTreeCell(propsEdit));

        setOnMouseClicked((javafx.scene.input.MouseEvent evt) -> {

            if (evt.getClickCount() == 1) {

                javafx.scene.control.TreeItem<java.io.File> item = getSelectionModel().getSelectedItem();

                if (item != null) {

                    java.io.File f = item.getValue();

                    try {
                        java.util.Scanner scanner = new java.util.Scanner(f);
                        StringBuilder fileContents = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            fileContents.append(scanner.nextLine());
                            fileContents.append(System.getProperty("line.separator"));

                        }
                        scanner.close();
                        
                        System.out.println("File");
                        
                        propsEditLayout.setVisible(true);
                        stringTreeLayout.setDisable(false);
                        
                        save(propsEdit, actPropsLabel);
                        
                        actPropsLabel.setText(f.getPath());
                        propsEdit.setVisible(true);
                        propsEdit.setText(fileContents.toString());

                    } catch (java.io.FileNotFoundException fnfE) {
                        
                        stringTreeLayout.setDisable(true);
                        propsEditLayout.setVisible(false);
                        save(propsEdit, actPropsLabel);

                        propsEdit.setText("");
                        actPropsLabel.setText("");

                    }

                } else {

                    save(propsEdit, actPropsLabel);

                    propsEdit.setText("");
                    actPropsLabel.setText("");

                }
            }
        });

    }
        private final class TextFieldTreeCell extends javafx.scene.control.TreeCell<java.io.File> {

        private final javafx.scene.control.TextField textField = new javafx.scene.control.TextField();
        private final javafx.scene.control.ContextMenu addMenu = new javafx.scene.control.ContextMenu();
        javafx.scene.control.TextArea propsEdit;

        public TextFieldTreeCell(javafx.scene.control.TextArea propsEdit) {
            this.propsEdit = propsEdit;
            javafx.scene.control.MenuItem subaMenuItem = new javafx.scene.control.MenuItem("Új altevékenység");//"New subactivity");
            addMenu.getItems().add(subaMenuItem);
            subaMenuItem.setOnAction((javafx.event.ActionEvent evt) -> {
                java.io.File file = getTreeItem().getValue();

                java.io.File f = new java.io.File(file.getPath() + System.getProperty("file.separator") + "Új altevékenység");

                if (f.mkdir()) {
                    javafx.scene.control.TreeItem<java.io.File> newAct
 //                           = new javafx.scene.control.TreeItem<java.io.File>(f, new javafx.scene.image.ImageView(actIcon));
                           = new FileTreeItem(f, new javafx.scene.image.ImageView(actIcon));                            
                    getTreeItem().getChildren().add(newAct);
                } else {

                    System.err.println("Cannot create " + f.getPath());

                }
            });

            javafx.scene.control.MenuItem propsMenuItem = new javafx.scene.control.MenuItem("Új altevékenység tulajdonságok");
            addMenu.getItems().add(propsMenuItem);
            propsMenuItem.setOnAction((javafx.event.ActionEvent evt) -> {
                java.io.File file = getTreeItem().getValue();

                java.io.File f = new java.io.File(file.getPath() + System.getProperty("file.separator") + "Új altevékenység tulajdonságok");

                try {
                    f.createNewFile();
                } catch (java.io.IOException e) {

                    System.err.println(e.getMessage());

                }

                javafx.scene.control.TreeItem<java.io.File> newProps
//                        = new javafx.scene.control.TreeItem<java.io.File>(f, new javafx.scene.image.ImageView(actpropsIcon));
                        = new FileTreeItem(f, new javafx.scene.image.ImageView(actpropsIcon));
                getTreeItem().getChildren().add(newProps);
            });

        }

        @Override
        public void startEdit() {

            super.startEdit();

            editCell();

            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {

            super.cancelEdit();

            setText((String) getItem().getPath());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(java.io.File item, boolean empty) {

            super.updateItem(item, empty);

            if (empty || item == null) {

                setText(null);
                setGraphic(null);

            } else {

                setText(getItem().toString());
                setGraphic(getTreeItem().getGraphic());

                if (!getTreeItem().isLeaf()) {
                    setContextMenu(addMenu);
                }

            }
        }

        private void editCell() {

            if (getItem() == null) {
                return;
            }
            textField.setVisible(true);
            String oldText = getItem().toString();
            textField.setText(oldText);
            
            textField.setOnKeyReleased((javafx.scene.input.KeyEvent t) -> {
                if (t.getCode() == javafx.scene.input.KeyCode.ENTER) {

                    String newText = textField.getText();

                    java.io.File newf = new java.io.File(newText);
                    java.io.File oldf = new java.io.File(oldText);
                    try {
                        if (oldf.isDirectory()) {
                            newf.mkdir();
                        } else {
                            newf.createNewFile();
                        }
                    } catch (java.io.IOException e) {

                        System.err.println(e.getMessage());

                    }

                    commitEdit(newf);
                } 
                
            });

        }

    }

}
