package com.example.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;

public class DialogHelper extends Dialog {
    
    public DialogHelper(String title, String caption, String bodyText) {
        super.setTitle(title);
        super.setHeaderText(caption);
        super.setContentText(bodyText);
        
        super.showAndWait();
    }
    
    // public void showOkButton() {
    //     this.set
    // }
}
