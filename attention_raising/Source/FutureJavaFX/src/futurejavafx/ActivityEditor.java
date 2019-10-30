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
public class ActivityEditor extends javafx.application.Application {

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }

    @Override
    public void start(javafx.stage.Stage stage) {

        java.util.Map<String, String> cmdParams = this.getParameters().getNamed();

        stage.setTitle("FUTURE 6 - TEV. és TEV. TUL. SZERKESZTŐ/ACT & ACT PROPS EDITOR");
//        javafx.scene.layout.GridPane box = new javafx.scene.layout.GridPane();
        javafx.scene.layout.VBox box = new javafx.scene.layout.VBox();
        final javafx.scene.Scene scene = new javafx.scene.Scene(box);
        scene.getStylesheets().add("style.css");

        String city = "City/";
        if (cmdParams.containsKey("city")) {
            city = city + cmdParams.get("city");
        }

        String props = "me.props,gaming.props,programming.props";
        if (cmdParams.containsKey("props")) {

            props = cmdParams.get("props");

        }
        java.util.List<String> propsList = java.util.Arrays.asList(props.split(","));

        java.util.List<String> properties = new java.util.ArrayList<>();

        for (String p : propsList) {
            java.io.File f = new java.io.File(p);

            try {
                java.util.Scanner scanner = new java.util.Scanner(f);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.startsWith("//")) {
                        properties.add(line);
                    }

                }
                scanner.close();

            } catch (java.io.FileNotFoundException fnfE) {

                fnfE.printStackTrace();

            }

        }
                
        javafx.scene.control.Label actPropsLabel = new javafx.scene.control.Label("A tevékenységekhez hozzárendelt tulajdonságok");
        javafx.scene.control.TextArea propsEdit = new javafx.scene.control.TextArea();
        javafx.scene.layout.VBox propsEditLayout = new javafx.scene.layout.VBox();
        propsEditLayout.getChildren().add(actPropsLabel);
        propsEditLayout.getChildren().add(propsEdit);
        propsEditLayout.prefHeightProperty().bind(box.heightProperty());
        propsEdit.prefHeightProperty().bind(propsEditLayout.heightProperty());
        propsEditLayout.setVisible(false);
        propsEditLayout.managedProperty().bind(propsEditLayout.visibleProperty());
        propsEdit.setEditable(false);

        
        javafx.scene.layout.HBox treesLayout = new javafx.scene.layout.HBox();
        treesLayout.prefHeightProperty().bind(box.heightProperty());
        treesLayout.prefWidthProperty().bind(box.widthProperty());
        
        javafx.scene.control.TreeView<String> stringTree = new StringTree(properties, true, propsEdit);
        javafx.scene.layout.VBox stringTreeLayout = new javafx.scene.layout.VBox();
        stringTree.prefHeightProperty().bind(treesLayout.heightProperty());
        stringTreeLayout.prefHeightProperty().bind(treesLayout.heightProperty());
        stringTreeLayout.prefWidthProperty().bind(treesLayout.widthProperty());
        stringTree.setEditable(false);
        stringTreeLayout.setDisable(true);
        stringTreeLayout.getChildren().add(new javafx.scene.control.Label("Tulajdonságok fája"));
        stringTreeLayout.getChildren().add(stringTree);

        javafx.scene.control.TreeView<java.io.File> fileTree = new FileTree(city, true, propsEdit, propsEditLayout, stringTreeLayout, actPropsLabel);
        javafx.scene.layout.VBox fileTreeLayout = new javafx.scene.layout.VBox();
        fileTree.prefHeightProperty().bind(treesLayout.heightProperty());
        fileTreeLayout.prefHeightProperty().bind(treesLayout.heightProperty());
        fileTreeLayout.prefWidthProperty().bind(treesLayout.widthProperty());
        fileTreeLayout.getChildren().add(new javafx.scene.control.Label("Tevékenységek fája és a tevékenységekhez hozzárendelt tulajdonságok"));
        fileTreeLayout.getChildren().add(fileTree);
        fileTree.setEditable(true);

        treesLayout.getChildren().add(fileTreeLayout);
        treesLayout.getChildren().add(stringTreeLayout);
        
        
        
        box.getChildren().add(treesLayout);
        box.getChildren().add(propsEditLayout);
        box.getChildren().add(new javafx.scene.control.Label("FUTURE6: ACT & ACT PROPS EDITOR, (F6ActEdit) v.: 0.0.1, Szerzői jog (C) 2018, GNU GPL v3, Bátfai Norbert, nbatfai@inf.unideb.hu, nbatfai@gmail.com"));
        stage.setScene(scene);

        javafx.geometry.Rectangle2D primaryScreenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() / 5);
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth()*3 / 5);
        stage.setHeight(primaryScreenBounds.getHeight()*3 / 5);

        stage.show();
    }

}
