import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.Optional;
public class CreateScenario implements EventHandler<ActionEvent> {

    public void handle(ActionEvent action) {

        Dialog<ButtonType> window = new Dialog<>();
        window.setTitle("Create a new game scenario");
        DialogPane pane = window.getDialogPane();
        pane.setStyle("-fx-background-color: #b7fefc;");

        //τα κουτάκια είναι ήδη γεμισμένα από default
        TextField scenID = new TextField("39");
        TextField mineN = new TextField("9");
        TextField tmr = new TextField("180");
        scenID.setPrefColumnCount(5);
        mineN.setPrefColumnCount(5);
        tmr.setPrefColumnCount(5);

        ToggleGroup lvl = new ToggleGroup();
        ToggleButton easyLvl = new RadioButton("Easy");
        ToggleButton hardLvl = new RadioButton("Hard");
        easyLvl.setToggleGroup(lvl);
        hardLvl.setToggleGroup(lvl);
        lvl.selectToggle((easyLvl));

        CheckBox supermine = new CheckBox();
        supermine.setSelected(false);

        HBox[] hbox = new HBox[5];
        for (int i=0; i<5; i++){
            hbox[i] = new HBox();
            hbox[i].setSpacing(4);
        }

        hbox[0].getChildren().addAll(new Label("Enter scenario ID"), scenID);
        hbox[1].getChildren().addAll(new Label("Choose difficulty"), easyLvl, hardLvl);
        hbox[2].getChildren().addAll(new Label("Number of mines (easy:9-11, difficult:35-45)"), mineN);
        hbox[3].getChildren().addAll(new Label("Is there a supermine?"), supermine);
        hbox[4].getChildren().addAll(new Label("Set the timer (easy:120-180, difficult:240-360)"), tmr);

        VBox finalBox = new VBox();
        finalBox.getChildren().addAll(hbox[0], hbox[1], hbox[2], hbox[3], hbox[4]);
        finalBox.setSpacing(10);

        pane.setContent(finalBox);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        pane.getButtonTypes().addAll(okButton, cancelButton);

        Optional<ButtonType> result = window.showAndWait();

        //ό,τι υπάρχει στα κουτάκια μπαίνει ως παράμετρος στην SaveScenarioFile
        if (result.isPresent() && result.get() == okButton) {
            String scenID_res = scenID.getText();
            String selectedLvl = ((ToggleButton) lvl.getSelectedToggle()).getText();
            int difLvl_res = (selectedLvl == "Easy") ? 1 : 2;
            int mineN_res = Integer.parseInt(mineN.getText());
            int supermine_res = supermine.isSelected() ? 1 : 0;
            int timer_res = Integer.parseInt(tmr.getText());

            SaveScenarioFile.save(scenID_res, difLvl_res, mineN_res, timer_res, supermine_res);
        }
    }
}



