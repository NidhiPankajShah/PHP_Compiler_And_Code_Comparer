/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecomparer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.InlineCssTextArea;

/**
 * FXML Controller class
 *
 * @author sukhada
 */
public class FXMLDocumentController2 implements Initializable {

    private String keywords;
    private static String filename;
    private static File file;
    
    @FXML
    private TextArea textArea;
    
    @FXML
    private TextArea area;
    
    @FXML
    private TitledPane pane;
    
   @FXML
    private CodeArea textAreaLeft;
    
    @FXML
    private CodeArea textAreaRight;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
   public void validate(ActionEvent arg0){
       
       keywords = textArea.getText();
       //System.out.println(keywords);
       if(!keywords.equals("")){
           //System.out.println(keywords);
           //word(keywords);
       }
       else{
           //System.out.println(keywords);
           AlertBox.display("Submit", "The keywords are not valid");
       }
       
}
   @FXML
   public void check(ActionEvent arg0){
       //System.out.println(keywords);
       String parts[] = keywords.split(",");
       int n = parts.length;
       for(String s:parts)
        {
        System.out.println(s);    
        }
       boolean[] a = new boolean[n];
       for(int i=0;i<n;i++){
       try
            { 
                 
               File f = new File(filename);
                System.out.println(filename);
               a[i] = findword(parts[i]);
                //.out.println(a[i]);
            
            }
            catch(Exception e){
            
            }
       }
        int counter=0;
        boolean b = true;
        String s1 = Boolean.toString(b);
        Pattern pattern = Pattern.compile(s1,Pattern.CASE_INSENSITIVE);
        for(int j=0;j<n;j++){
            String s2 = Boolean.toString(a[j]);
            Matcher matcher = pattern.matcher(s2);
            while (matcher.find()) {
                // Get the matching string
                String match = matcher.group();
                counter++;
                System.out.println(match);
                System.out.println(counter);
                System.out.println("hii");
            }
   }
    
    AlertBox.display("Result","Number of keywords found "+counter+"/"+n);
    double result= ((double)counter/(double)n)*100.0;
    AlertBox.display("Result","Percentage of acceptability of assignment is "+result +"%");
}
   @FXML
    public void handle(ActionEvent arg0) throws IOException {
                FileChooser fileChooser = new FileChooser();
                Button button = null; 
                button = (Button) arg0.getSource();
                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                 
                //Show save file dialog
                file = fileChooser.showOpenDialog(null);
                if(file != null){
                    //t1.setText(readFile(file));
                    //System.out.println(file.exists());
                    filename = file.getName();
                    pane.setText(file.getName());
                    area.setText(readFile(file));
                    area.setWrapText(true);
                }
                else{
                    
                }
}

    
    private String readFile(File file) throws IOException{
           //t1.setText("yo");
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }

    public boolean findword(String word) throws FileNotFoundException, IOException {
    //System.out.println("findword");
    //System.out.println(word);
    
    String s = file.getAbsolutePath();
    
    boolean flag = false;
        System.out.println(file.exists());
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
        String nextToken = new String(scanner.next());
        System.out.println(nextToken);
        if(nextToken.trim().equals(word.trim())){
            flag = true;
            System.out.println("hiiiiii");
            break;
        }
    
    }
    System.out.println(flag);

    return flag;
}
}