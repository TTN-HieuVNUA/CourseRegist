package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount;

public class controller_login implements Initializable{
	private Scene scene;
	private Stage stage;
    @FXML
    private TextField txtusername;

    @FXML
    private Button btnlogin;

    @FXML
    private PasswordField txtpassword;
    private ObservableList<Acount> acountList;
    @FXML
    void switchtoscenehome(ActionEvent event) {
    	if(!(txtpassword.getText().equals("")) && !(txtusername.getText().equals(""))) {
    		for(int i=0; i< acountList.size(); i++) {
        		if(txtusername.getText().equals(acountList.get(i).getUsername())
        			&& txtpassword.getText().equals(acountList.get(i).getPassword())) {
        			control_courseRegis.user = txtusername.getText();
        			control_courseRegis.classcode = acountList.get(i).getClasscode();
        			switchtosceneCourseRegis(event);
        			break;
        		}
        	}
    	}
    	else {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setContentText("user, password không được để trống");
    		alert.show();
    	}
    }
    public void switchtosceneCourseRegis(ActionEvent event) {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../views/course_regis.fxml"));
			stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../views/style.css").toExternalForm());
			stage.setScene(scene);
			stage.show();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    void loadDate() {
    	acountList = FXCollections.observableArrayList(
    			new Acount("637630", "123", "K63ATTT"),
    			new Acount("637624", "1234", "K63HTTT"),
    			new Acount("646180", "12345", "K64CNTTA")
    			);
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadDate();
		txtusername.setFocusTraversable(false);
		txtusername.setStyle("-fx-text-inner-color: #ffffff;");
		txtpassword.setStyle("-fx-text-inner-color: #ffffff;");
	}
}
