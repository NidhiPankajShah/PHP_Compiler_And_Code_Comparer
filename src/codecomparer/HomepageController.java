package codecomparer;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class HomepageController implements Initializable {
Scene Scene;

  @FXML
  public Button button1;
  
  @FXML
  public Button button2;
  
  @FXML
  public Button button3;
  
  
  public void handle1(ActionEvent event) throws IOException
  {
      Parent home_page_scene = FXMLLoader.load(getClass().getResource("FXMLDocument3.fxml"));
      Scene home = new Scene(home_page_scene);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.hide();
      stage.setScene(home);
      stage.show();
  }
  
   public void handle2(ActionEvent event) throws IOException
  {
      Parent home_page_scene = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
      Scene home = new Scene(home_page_scene);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.hide();
      stage.setScene(home);
      stage.show();
  }
   
   public void handle3(ActionEvent event) throws IOException
  {
      Parent home_page_scene = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
      Scene home = new Scene(home_page_scene);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.hide();
      stage.setScene(home);
      stage.show();
  }
  
  





    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
      
}
   
    
    }    
 

