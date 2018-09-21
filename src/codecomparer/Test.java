package codecomparer;
import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Test {
static String h;
static File filename ;
    // An instance of the subclass of the default highlight painter
    static MyHighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);

    public static void high(String s , File f) {
	h = s;
	filename = f;
	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JTextArea jep = new JTextArea(100,100);
				frame.add(jep);
                frame.pack();
                frame.setVisible(true);
				//String filename1 = "C:\\Users\\Acer\\Desktop\\my.php";
				try{
            FileReader fr1 =new FileReader(filename);
            BufferedReader br1 = new BufferedReader(fr1);
            jep.read(br1,null);
            br1.close();
            jep.requestFocus();
            
            
        }
        catch(Exception ex){
            
        }
		
		
				try{
				//File f = new File(f);
    Scanner scanner = new Scanner(filename);
while (scanner.hasNextLine()) {
    String nextToken = scanner.next();
	//System.out.println(h);
	//jep.setText(nextToken);
	//System.out.println(h);
	highlight(jep,h);
    if (nextToken.trim().equalsIgnoreCase(h.trim()))
    {
	
	System.out.println(scanner.next());
	
	//highlight(jep,h);
                
                

//'public is the word to highligh'
}
            }
			}
			catch(Exception e){}
        }});
    }

    // Creates highlights around all occurrences of pattern in textComp
    public static void highlight(JTextArea textComp, String pattern) {
        // First remove all old highlights
        removeHighlights(textComp);

        try {
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;

            // Search for pattern
            while ((pos = text.indexOf(pattern, pos)) >= 0) {
                // Create highlighter using private painter and apply around pattern
                hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                pos += pattern.length();
            }
        } catch (BadLocationException e) {
        }
    }

    // Removes only our private highlights
    public static void removeHighlights(JTextArea textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();

        for (int i = 0; i < hilites.length; i++) {
            if (hilites[i].getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }
}

class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

    public MyHighlightPainter(Color color) {
        super(color);
    }
}