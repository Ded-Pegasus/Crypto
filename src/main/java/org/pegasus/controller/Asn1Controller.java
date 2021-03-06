package org.pegasus.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.pegasus.model.ErrorMessage;
import org.pegasus.model.asn1.ExecuteEvent;
import org.pegasus.model.exception.ASN1ExceptionParse;

import java.net.URL;
import java.util.ResourceBundle;

public class Asn1Controller {

    public TextArea asn1;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane Asn1;

    @FXML
    private Button executeButton;

    @FXML
    private Text message;

    @FXML
    private TextArea text;

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    @FXML
    void initialize() {

        //Style background TextArea
        asn1.getStylesheets().add("/view/TextAreaBlue.css");
        text.getStylesheets().add("/view/TextAreaBlack.css");

        asn1.setWrapText(true);
        text.setWrapText(true);

        executeButton.setOnAction(event ->{
            String decodeAsn1 = null;
            try {
                String textData = text.getText();
                textData = textData.replaceAll("\n", "");
                text.setText(textData);
                decodeAsn1 = ExecuteEvent.textToAsn1(textData);
            } catch (NullPointerException e) {
                System.out.println(ANSI_RED + "TextArea is null");
                ErrorMessage.message("TextArea is null");
                //e.printStackTrace();
            } catch (ASN1ExceptionParse asn1ExceptionParse) {
                System.out.println(ANSI_RED + "Couldn't read ASN.1 structure");
                ErrorMessage.message("Couldn't read ASN.1 structure");
                //asn1ExceptionParse.printStackTrace();
            }
            asn1.setText(decodeAsn1);
        });
    }
}
