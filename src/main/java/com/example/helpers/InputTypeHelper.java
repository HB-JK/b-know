package com.example.helpers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class InputTypeHelper {
    
    public void setToInt(TextField element) {
        if(element != null) {
            element.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        element.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }
    }
}
