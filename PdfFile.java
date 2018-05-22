/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfdata;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import jdk.nashorn.internal.parser.TokenStream;
import org.apache.lucene.analysis.id.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.snowball.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author sutri
 */
public class PdfFile extends javax.swing.JFrame {
    String path = " ";
    String term;
    
    DefaultTableModel model;
    String [] hapus = {"abaout","above","according","across","actually","after","afterwards","again","against","all",
    "amongst","among","am","always","although","also","already","along","alone","almost","allows","allow",
    "another","any","anybody","anyhow","anyone","anything","anyway","anyways","anywhere","apart","appear","appreciate",
    "asking","ask","aside","as","arround","arent","are","aren't","are","appropriate",
    "are","ba","between","better","can't","became","certainly","changes","clearly","down", "some", "like this",
    "inasmuch","beyond","both","brief","but","by","certain","can",
    "becoming","become","because","becomes","below","beside","besides","best","containing","berakhirlah",
    "cannot","behind","being","believe","came","causes","contain","hasn't","have","haven't",
    "cause","been","co","contains","corresponding","eg","eight","either","pertinent", "different",
    "before","cant","comes","concerning","consequently","consider","considering","together", "get together", "get ready", "get ready", "ask",
    "beforehand","come","could","ever","doesn't","far","example","true","having",
    "biasanya","did","couldn't","every","else","everybody","few","he","had","hadn't","happens",
    "bung","each","doing","edu","elsewhere","enough","except","he's","dalam","has","hardly","he","he's","hello","help","demi","demikian","demikianlah",
    "dengan","down","downwards","didn't","different","everyone","hello","hasn't","haven't","having","granted", "granted",
    "i'll","does","do","don't","entirely","especially","have","hence",
    "i'm","etc","during","fifth","former","everything","hereby","here's","dikira",
    "i've","done","even","gives","whence","formerly","here","himself","prompted", "superseded",
    "however","first","get","indeed","indicate","her","whenever","him","his",
    "i'd","exactly","everywhere","when","herein","herself","inward","hither",
    "inc","followed","for","getting","given","indicates","hereafter","into","is","isn't",
    "ignored","ex","from","five","further","forth","inner","hers","insofar","instead",
    "immediate","dong","in","gets","four","furthermore","indicated","it","entahlah","it'd","it'll","hal","hampir","hanya",
   "never","nevertheless","whom","want", "let", "should", "to", "he", "cup", "like",
    "neither","nothing","despite","new","itself","want", "want", "this", "is this", "this", "that",
   "needs","need","necessary","next","whole","jangankan","its","jauh","it's",
    "answer","not","described","whether","will","whoever","with","if", "also", "sum", "sum", "precisely",
    "kala", "if","normally","nearly","wish","won't","just",
    "kata","whereafter","namely","why","now","willing","let","let's","like","liked",
    "where's","none","myself","near","whose","keep","latterly","lest",
    "where","whereby","nowhere","ketika","keeps","least","less",
    "whereas","non","definitely","next","within","without","last","latter","likely","yours","your","yourself","lanjut","lanjutnya",
    "lebih","lewat","my","nine","meanwhile","which","looking","look","maybe","you've","yourselves",
    "nobody","no","wherein","me","little","",
    "whither","must","obviously","mean","who's","knows","known","may","get","put",
    "memintakan","wherever","merely","whatever","lately","looks","ready","exam",
    "tries","'tried","who","were","know","many","yet","you",
    "truly","of","might","would","who","mainly","you're","get ","get","geeting",
    "whereupon","much","more","moreover","yes","you'd","you'll","change","use",
    "try","trying","most","twice","later","meanwhile",
    "oh","mostly","mostly","weren't","merely","wonder",
    "okay","most","much",
    "might","moreover","towards","wouldn't","go",
    "often","more","two","via","nah","naik","what","by",
    "ok","old","on","once","pak","very","long","urgent", "per",
    "off","perlu","wants","want","we","persoalan","first","second","secondly","see",
    "pihaknya","one","pula","various","way","we'll","seem","seeing","sensible","sent",
    "or","got","ones","only","onto","what's","satu","saya","seemed",
    "se","other","going","otherwise","we'd","welcome","seeming","seems","seen","self",
    "others","greetings","currently","we're","well","since","selves","serious",
    "ourselves","goes","ought","regardless","regarding","example","seriously","seven","several",
    "out","gotten","had","our","was","we've","went","since","far","thus","throughout","shouldn't",
    "ours","reasonably","wasn't","provides","today","thru","shall","something","sometime","sometimes",
    "outside","happens","regards","up","through","someone","soon","somewhere",
    "over","go","hadn't","upon","together","six","somehow","somebody",
    "useful","quite","rather","unfortunately","probably","so","some","somewhat",
    "used","really","under","unless","sorry","to","toward","took","too",
    "hardly","value","unlikely","unto","after","before","saying","place ","true",
    "use","uses","using","therefore","setidak-tidaknya","presumably","still","specifying","time","say","who",
    "per","sini","usually","therein","until","thereby","they've","specified","saw","year","day","month",
    "particularly","respectively","place",
    "particular","relatively","there's","theres","thereupon","tepat","third","think","that's","same","such","","default","loss",
    "own","has","right","themselves","thereafter","terkira","terlalu","this","thorough","thoroughly","thats",
    "perhaps","them","then","there","true","this","will","the","their","they're","thanx","that",
    "theirs","overall","said","thence","done","tutur","though","three","those","late","speak","public","owner","thanks",
    "please","placed","possible","plus","these","they","they'd","times","will","take","taken","wong","tell","tends","than","thank"};
    /**
     * Creates new form PdfFile
     */
    ArrayList<String>  wordList = new ArrayList<String>();
    public PdfFile() {
        initComponents();
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        teksarea2 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        tokenizing = new javax.swing.JButton();
        stopword = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        teksarea2.setColumns(20);
        teksarea2.setRows(5);
        jScrollPane3.setViewportView(teksarea2);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("file upload"));

        jLabel1.setText("FILE :");

        jLabel2.setText("Empty");

        jButton1.setText("Upload");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jButton1))
                .addContainerGap(312, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Proses "));

        tokenizing.setText("Tokenizing");
        tokenizing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tokenizingActionPerformed(evt);
            }
        });

        stopword.setText("Stopword");
        stopword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopwordActionPerformed(evt);
            }
        });

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tokenizing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stopword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(0, 64, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tokenizing)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(stopword)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kata"
            }
        ));
        jScrollPane2.setViewportView(tabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 45, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        teksarea2.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tokenizingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tokenizingActionPerformed
        // TODO add your handling code here:
        String kalimat = textArea.getText();
        kalimat = Normalizer.normalize(kalimat, Normalizer.Form.NFD);
        String kata1 = kalimat.replaceAll("[^a-zA-Z0-9]", " ");
        String kata2 = kata1.replaceAll("( )+", " ");
        String kata = kata2.toLowerCase();
        String [] st = kata.split(" ");
        for(String kataStr:st){
            teksarea2.setText(teksarea2.getText()+kataStr+"\n");
        }
    }//GEN-LAST:event_tokenizingActionPerformed

    private void stopwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopwordActionPerformed

        try{

            BodyContentHandler handler = new BodyContentHandler();
            teksarea2.append(handler.toString());
            FileWriter tulis = null;
            tulis = new FileWriter ("E:\\simpan.txt");
            BufferedWriter bw = new BufferedWriter(tulis);
            teksarea2.write(bw);
            bw.close();
            teksarea2.requestFocus();
            tulis.close();
            Scanner baca = new Scanner(new File("E:\\simpan.txt"));
            FileOutputStream out = new FileOutputStream("Hasil.txt");

            String [] header = {"Kata"};

            while(baca.hasNext()){
                int ls = 1;
                String kata = baca.next();

                for(int a=0;a<hapus.length;a++){
                    if(kata.equals(hapus[a])){
                        ls = 0;
                    }
                }

                if (ls!=0){
                    teksarea2.append(kata +"\n");

                }
            }
            String huruf = teksarea2.getText();
            String[] ambil = huruf.split("\n");
            Object data[][] = new Object[590000][1];
            for (int a = 0; a < ambil.length; a++) {
                data[a][0] = ambil[a];
            }
            model = new DefaultTableModel(data, header);
            tabel.setModel(model);

            String printstemming = new Scanner(new File("baru3.txt")).useDelimiter("\\A").next();
            teksarea2.append(printstemming);

        }catch(Exception ex){

        }

    }//GEN-LAST:event_stopwordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
        }
       jLabel2.setText(path);
        try {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputstream = new FileInputStream(new File(path));
            ParseContext pcontext = new ParseContext();
            PDFParser pdfparser = new PDFParser();
            pdfparser.parse(inputstream, handler, metadata, pcontext);
            String teks = handler.toString();
            textArea.setText(teks);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException ex) {
            Logger.getLogger(PdfFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PdfFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PdfFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PdfFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PdfFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PdfFile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PdfFile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton stopword;
    private javax.swing.JTable tabel;
    private javax.swing.JTextArea teksarea2;
    private javax.swing.JTextArea textArea;
    private javax.swing.JButton tokenizing;
    // End of variables declaration//GEN-END:variables
}
