import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class autocomplete implements DocumentListener{
	private static final String COMMIT_ACTION = "commit";
    private static enum Mode { INSERT, COMPLETION };
    private  List<String> words;
    private Mode mode = Mode.INSERT;
	JTextArea jTextArea1;
    public autocomplete(JTextArea jT)
    {	jTextArea1=jT;
    	jTextArea1.getDocument().addDocumentListener(this);
        
        InputMap im = jTextArea1.getInputMap();
        ActionMap am = jTextArea1.getActionMap();
        //im.put(KeyStroke.getKeyStroke(""), COMMIT_ACTION);
        im.put(KeyStroke.getKeyStroke("RIGHT"), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());
        
        words = new ArrayList<String>();
        words.add("aci ");
        words.add("add ");
        words.add("adi ");
        words.add("ana ");
        words.add("call ");
        words.add("cnc ");
        words.add("cpe ");
        words.add("cma ");
        words.add("cmp ");
        words.add("cpi ");
        words.add("daa ");
        words.add("dcr ");
        words.add("hlt ");
        words.add("inr ");
        words.add("jc ");
        words.add("jmp ");
        words.add("jnc ");
        words.add("jpe ");
        words.add("lda ");
        words.add("ldax ");
        words.add("lhld ");
        words.add("lxi ");
        words.add("mov ");
        words.add("mvi ");
        words.add("nop ");
        words.add("ora ");
        words.add("out ");
        words.add("pchl ");
        words.add("pop ");
        words.add("push ");
        words.add("ral ");
        words.add("ret ");
        words.add("rlc ");
        words.add("rnc ");
        words.add("rpe ");
        words.add("rrc ");
        words.add("sbb ");
        words.add("shld ");
        words.add("sphl ");
        words.add("sta ");
        words.add("sub ");
        words.add("xchg ");
        words.add("xra ");
        words.add("xthl ");
        words.add("xxx \n===Team=== \nAkhilRaj\nArjun B\nLikhil kt\nRamees YPV\nRoshith MP\n==Rabb!tfoot== ");
        
        
        
        
        
        jTextArea1.setWrapStyleWord(true);
    }
	
	
	 public void changedUpdate(DocumentEvent ev) {
	    }
	    
	    public void removeUpdate(DocumentEvent ev) {
	    }
	    
	    public void insertUpdate(DocumentEvent ev) {
	        if (ev.getLength() != 1) {
	            return;
	        }
	        
	        int pos = ev.getOffset();
	        String content = null;
	        try {
	            content = jTextArea1.getText(0, pos + 1);
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
	        
	        // Find where the word starts
	        int w;
	        for (w = pos; w >= 0; w--) {
	            if (! Character.isLetter(content.charAt(w))) {
	                break;
	            }
	        }
	        if (pos - w < 1) {
	            // Too few chars
	            return;
	        }
	        
	        String prefix = content.substring(w + 1).toLowerCase();
	        int n = Collections.binarySearch(words, prefix);
	        if (n < 0 && -n <= words.size()) {
	            String match = words.get(-n - 1);
	            if (match.startsWith(prefix)) {
	                // A completion is found
	                String completion = match.substring(pos - w);
	                // We cannot modify Document from within notification,
	                // so we submit a task that does the change later
	                SwingUtilities.invokeLater(
	                        new CompletionTask(completion, pos + 1));
	            }
	        } else {
	            // Nothing found
	            mode = Mode.INSERT;
	        }
	    }
	    private class CompletionTask implements Runnable {
	        String completion;
	        int position;
	        
	        CompletionTask(String completion, int position) {
	            this.completion = completion;
	            this.position = position;
	        }
	        
	        public void run() {
	            jTextArea1.insert(completion, position);
	            jTextArea1.setCaretPosition(position + completion.length());
	            jTextArea1.moveCaretPosition(position);
	            mode = Mode.COMPLETION;
	        }
	    }
	    
	    private class CommitAction extends AbstractAction {
	        public void actionPerformed(ActionEvent ev) {
	            if (mode == Mode.COMPLETION) {
	                int pos = jTextArea1.getSelectionEnd();
	                jTextArea1.insert(" ", pos);//editbyr " "
	                jTextArea1.setCaretPosition(pos);//edit pos+1
	                mode = Mode.INSERT;
	            } else {
	                //jTextArea1.replaceSelection("\n");
	            	try{
	                jTextArea1.setCaretPosition(jTextArea1.getCaretPosition()+1);//edit pos+1
	            	}catch(Exception e){}
	            }
	        }
	    }
	    

}
