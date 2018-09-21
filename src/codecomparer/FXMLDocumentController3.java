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
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.Codec;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;
import org.fxmisc.richtext.demo.JavaKeywordsAsync;

/**
 *
 * @author sukhada
 */
public class FXMLDocumentController3 implements Initializable {
    private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private static final String sampleCode = String.join("\n", new String[] {
        "package com.example;",
        "",
        "import java.util.*;",
        "",
        "public class Foo extends Bar implements Baz {",
        "",
        "    /*",
        "     * multi-line comment",
        "     */",
        "    public static void main(String[] args) {",
        "        // single-line comment",
        "        for(String arg: args) {",
        "            if(arg.length() != 0)",
        "                System.out.println(arg);",
        "            else",
        "                System.err.println(\"Warning: empty string as argument\");",
        "        }",
        "    }",
        "",
        "}"
    });
        
    @FXML
    private InlineCssTextArea textAreaLeft;
    
    @FXML
    private InlineCssTextArea textAreaRight;
    
    @FXML
    private TitledPane Left;
    
    @FXML
    private TitledPane Right;
    
    @FXML
    private TextArea messageLeft;
    
    @FXML
    private TextArea messageRight;
    
    private static File file1,file2;
    
    private static String filename1, filename2, temporary1, temporary2;
    
    private List<String> words = new ArrayList<String>();
    private List<String> words1 = new ArrayList<String>();
    
    @FXML
    public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                Button button = null; 
                button = (Button) arg0.getSource();
                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.TXT)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                 
                //Show save file dialog
                File file = fileChooser.showOpenDialog(null);
                //System.out.println(button.getId());
                //System.out.println(button.getId());
                
                
                if("uploadLeft".equals(button.getId())){
                if(file != null){
                    //t1.setText(readFile(file));
                    //System.out.println(file.exists());
                    filename1 = file.getName();
                    file1 = file;
                    Left.setText(file.getName());
                    //textAreaLeft.setWrapText(true);
                    //textAreaLeft.replaceText(0, 0, readFile(file));
                    //textAreaLeft.setText(readFile(file));
                    textAreaLeft.replaceText(0, 0, readFile(file));
                    textAreaLeft.setWrapText(true);
                }
                else{
                    //t1.setText("yo");
                }
                }
                else if("uploadRight".equals(button.getId())){
                    if(file != null){
                        filename2 = file.getName();
                        file2 = file;
                        Right.setText(file.getName());
                        //textAreaRight.replaceText(0, 0, readFile(file));
                        //codeAreaRight.setWrapText(true);
                        //textAreaRight.setText(readFile(file));
                        textAreaRight.replaceText(0, 0, readFile(file));
                        textAreaRight.setWrapText(true);
                    }
                    else{
                        
                    }
                }
            }
        private String readFile(File file){
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

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /*textAreaLeft.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    textAreaLeft.setStyleSpans(0, computeHighlighting(textAreaLeft.getText()));
                });*/
        textAreaLeft.replaceText(0, 0, sampleCode);
        /*textAreaRight.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    textAreaRight.setStyleSpans(0, computeHighlighting(textAreaRight.getText()));
                });*/
        textAreaRight.replaceText(0, 0, sampleCode);
        
    }    
    @FXML
    public void compare(ActionEvent arg0){
        try
        {
            //file1 = new File(filename1);
            System.out.println(filename1);
            System.out.println(file1.exists());
            //file2 = new File(filename2);
            System.out.println(filename2);
            System.out.println(file2.exists());
            textAreaLeft.setStyle("-fx-text-fill: red;");
            FileReader fr1 = new FileReader(file1);
            FileReader fr2 = new FileReader(file2);
            BufferedReader br1 = new BufferedReader(fr1);
            BufferedReader br2 = new BufferedReader(fr2);
            //System.out.println(file1.exists());
            String str;
            String str1;
            while((str=br1.readLine())!= null & (str1=br2.readLine())!= null){
                Locale currentLocale1 = new Locale ("en","US");
                BreakIterator sentenceIterator = 
                BreakIterator.getSentenceInstance(currentLocale1);
		Locale currentLocale2 = new Locale ("en","US");
                BreakIterator sentenceIterator1 = 
                BreakIterator.getSentenceInstance(currentLocale2);
	 
                String st1 = str;
                //temporary1 = temporary1.concat(st1);
                System.out.println(st1);
                String st2 = str1;
                System.out.println(st2);
                //temporary2 = temporary2.concat(st2);
                extractsentences(st1, sentenceIterator, st2, sentenceIterator1);
	 
            }
            //textAreaLeft.setStyle(0, 8, "-fx-fill: cadetblue;");
            /*for(String w:words){
                String pattern = w;
                System.out.println(pattern);
      // Create a Pattern object
                Pattern r = Pattern.compile(pattern);

      // Now create matcher object.
                Matcher m = r.matcher(textAreaLeft.getText());
                if (m.find( )) {
                    System.out.println(textAreaLeft.getText().indexOf(w));
                    System.out.println(textAreaLeft.getText().indexOf(w)+w.length());
                    textAreaLeft.setStyle(textAreaLeft.getText().indexOf(w), textAreaLeft.getText().indexOf(w)+w.length(), "-fx-fill: red;");
      } else {
         System.out.println("NO MATCH");
      }
            }*/
            
            for(String w1:words1){
                String pattern1 = w1;
                System.out.println(pattern1);
      // Create a Pattern object
                Pattern r = Pattern.compile(pattern1);

      // Now create matcher object.
                Matcher m = r.matcher(textAreaRight.getText());
                if (m.find( )) {
                    System.out.println(textAreaRight.getText().indexOf(w1));
                    System.out.println(textAreaRight.getText().indexOf(w1)+w1.length());
                    textAreaRight.setStyle(textAreaRight.getText().indexOf(w1), textAreaRight.getText().indexOf(w1)+w1.length(), "-fx-fill: red;");
      } else {
         System.out.println("NO MATCH");
      }
            }
            /*textAreaLeft.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    textAreaLeft.setStyleSpans(0, Highlight(textAreaLeft.getText(),"This"));
                });*/
      
              //String store1 = temporary1;
        
        /*int cc=0;
        
        for(String g:temp1){
            store1 = temporary1.substring(p1);
            if(store1.contains(g)){
                p0=store1.indexOf(g)+cc;
                
                p1 = p0 + g.length();
                cc=temporary1.codePointCount(0,p1);
                //JOptionPane.showMessageDialog(null,p1);
                highlighter1.addHighlight(p0, p1, painter );
            }
        }
        String store2 = temporary2;
        int cc2=0;
        
        for(String g:temp2){
            store2 = temporary2.substring(p3);
            if(store2.contains(g)){
                p2=store2.indexOf(g)+cc2;
                
                p3 = p2 + g.length();
                cc2=temporary2.codePointCount(0,p3);
                //JOptionPane.showMessageDialog(null,p3);
                highlighter2.addHighlight(p2, p3, painter );
            }
        }*/
     
        
        }
        catch(Exception e){
            
        }
        
    }
    
    private void extractWords(String target, BreakIterator wordIterator, String target1, BreakIterator wordIterator1) {

        wordIterator.setText(target);
        System.out.println(target);
        int start = wordIterator.first();
        //System.out.println("sexy");
        System.out.println(start);
        int end = wordIterator.next();
	System.out.println(end);
        wordIterator1.setText(target1);
        int start1 = wordIterator1.first();
        int end1 = wordIterator1.next();

        while (end != BreakIterator.DONE && end1 != BreakIterator.DONE) {
            String word = target.substring(start,end);
            //System.out.println("text");
            System.out.println(start);
            System.out.println(end);
            String word1 = target1.substring(start1,end1);
            //System.out.println("Jon snow");
            System.out.println(start1);
            System.out.println(end1);
            if (Character.isLetterOrDigit(word.charAt(0)) && Character.isLetterOrDigit(word1.charAt(0))) {
           // System.out.println(word);
			//System.out.println(word1);
         
		if(!word.equalsIgnoreCase(word1)){
                    System.out.println("hiiii");
                   //window1 window = new window1();
                   //jTextField1.setText(jTextField1.getText()+" "+"Difference");
                   textAreaLeft.setStyle("-fx-highlight-fill: red;");
                   //jTextField1.setText(jTextField1.getText()+" "+word);
                   messageLeft.appendText(word+" ");
                   words.add(word);
                   
                   //textAreaRight.setStyle(end1, end, word1);
                   messageRight.appendText(word1+" ");
                   words1.add(word1);
                   
                   String pattern = word;
                System.out.println(pattern);
      // Create a Pattern object
                Pattern r = Pattern.compile(textAreaLeft.getText(start, end));

      // Now create matcher object.
                Matcher m = r.matcher(target);
                if (m.find( )) {
                    //System.out.println(start);
                    //System.out.println(end);
                    textAreaLeft.setStyle(start, end, "-fx-fill: red;");
      } else {
         System.out.println("NO MATCH");
      }
                   //jTextField1.setText(jTextField1.getText()+" "+word1);
		// window.jTextField1.setText(jTextField1.getText()+" " + "Difference");
		 //jTextField1.setText(jTextField1.getText()+" " + word);
                //temp1.add(word);
                //JOptionPane.showMessageDialog(null,temp1);
		//jTextField1.setText(jTextField1.getText()+" " + word1);	
                  //      temp2.add(word1);
                     System.out.println("difference");
                     System.out.println(word);
                     System.out.println(word1);
		 }
		 }
         start = end;
         end = wordIterator.next();
		  start1 = end1;
         end1 = wordIterator1.next();
      }
        
        
   } 
    private void extractsentences(String target, BreakIterator sentenceIterator, String target1, BreakIterator sentenceIterator1) {

        //JOptionPane.showMessageDialog(null,target);
        //JOptionPane.showMessageDialog(null,target1);
        sentenceIterator.setText(target);
        int start = sentenceIterator.first();
        int end = sentenceIterator.next();
	//JOptionPane.showMessageDialog(null,start);
        //JOptionPane.showMessageDialog(null,end);
	sentenceIterator1.setText(target1);
        int start1 = sentenceIterator1.first();
        int end1 = sentenceIterator1.next();
      //JOptionPane.showMessageDialog(null,start1);
      //JOptionPane.showMessageDialog(null,end1);

        while (end != BreakIterator.DONE && end1 != BreakIterator.DONE) {
            String word = target.substring(start,end);
         
            String word1 = target1.substring(start1,end1);
               //JOptionPane.showMessageDialog(null,word);
               //JOptionPane.showMessageDialog(null,word1);
		 //System.out.println(word);
		//System.out.println(word1);
            Locale currentLocale = new Locale ("en","US");
            BreakIterator wordIterator = 
            BreakIterator.getWordInstance(currentLocale);
            BreakIterator wordIterator1 = 
            BreakIterator.getWordInstance(currentLocale);
            extractWords(word,wordIterator,word1,wordIterator1);
            if (Character.isLetterOrDigit(word.charAt(0)) && Character.isLetterOrDigit(word1.charAt(0))) {
            //System.out.println(word);
             
			//System.out.println(word1);
         
		 
            }
            start = end;
            end = sentenceIterator.next();
            start1 = end1;
            end1 = sentenceIterator1.next();
            System.out.println("end");
      }
	  
   }

    
    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("PAREN") != null ? "paren" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("BRACKET") != null ? "bracket" :
                    matcher.group("SEMICOLON") != null ? "semicolon" :
                    matcher.group("STRING") != null ? "string" :
                    matcher.group("COMMENT") != null ? "comment" :
                    null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
    
    private static StyleSpans<Collection<String>> Highlight(String text,String word) {
        Pattern r = Pattern.compile(word);
        Matcher matcher = r.matcher(text);
        System.out.println("highlight");
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass = "differnce";
                    /*matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("PAREN") != null ? "paren" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("BRACKET") != null ? "bracket" :
                    matcher.group("SEMICOLON") != null ? "semicolon" :
                    matcher.group("STRING") != null ? "string" :
                    matcher.group("COMMENT") != null ? "comment" :*/
                    //null; /* never happens */ assert styleClass != null;
                            
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
    
}
