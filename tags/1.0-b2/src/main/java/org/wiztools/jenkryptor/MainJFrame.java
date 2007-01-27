/*
 * MainJFrame.java
 *
 * Created on 28 December 2006, 21:18
 */

package org.wiztools.jenkryptor;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import org.wiztools.jenkryptor.util.FileUtil;
import org.wiztools.jenkryptor.validation.MixedFilesValidator;
import org.wiztools.jenkryptor.validation.SameFileValidator;
import org.wiztools.jenkryptor.validation.Validator;
import org.wiztools.jenkryptor.validation.ValidatorException;

/**
 *
 * @author  subhash
 */
public class MainJFrame extends javax.swing.JFrame {
    
    private JFileChooser jfc = new JFileChooser();
    
    private JDialog jd = null;
    
    private PreferencesJPanel jpPreferences = new PreferencesJPanel();
    private JPanel jpAbout = new AboutJPanel();
    
    // CoR pattern used for Validation
    private final Validator validator = new MixedFilesValidator().setNext(
                new SameFileValidator().setNext(null)
                );
    
    /** Creates new form MainJFrame */
    public MainJFrame() {
        initComponents();
        
        setIconImage(new ImageIcon(
                this.getClass().getClassLoader().getResource(
                "org/wiztools/jenkryptor/logo.png")).getImage());
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                exit(0);
            }
        });
        
        this.setResizable(false);
        
        // Configure the JFileChooser
        jfc.setMultiSelectionEnabled(true);
        jfc.setFileHidingEnabled(false);
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return f.getAbsolutePath()
                    .toLowerCase().endsWith(".wiz")?true:false;
            }
            public String getDescription() {
                return "Encrypted files (*.wiz)";
            }
        });
        jfc.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return true;
            }
            public String getDescription() {
                return "All files";
            }
        });
        
        
        jpScrollbar.setLayout(new GridLayout(Globals.THREAD_SIZE, 2));
        
        LabelProgressbarEnsc[] lpe_arr = Globals.PBPM.getLPEArr();
        for(int i=0; i<Globals.THREAD_SIZE; i++){
            LabelProgressbarEnsc lpbe = lpe_arr[i];
            
            JPanel jp_label = new JPanel();
            jp_label.setLayout(new FlowLayout(FlowLayout.LEFT));
            jp_label.add(lpbe.getLabel());
            
            JPanel jp_progressbar = new JPanel();
            jp_progressbar.add(lpbe.getProgressBar());
            
            jpScrollbar.add(jp_label);
            jpScrollbar.add(jp_progressbar);
        }
        
        Globals.msgDisplayer = new MessageDisplay(jlStatus, jtaMessage);
        
        Globals.MAIN_FRAME = this;
        
        jd = new JDialog(this);
        jd.setResizable(false);
        
    }
    
    public void exit(final int status){
        if(Globals.isRunning){
            JOptionPane.showMessageDialog(Globals.MAIN_FRAME, 
                    "Encryption/Decryption Operation in progress. . .",
                    "Cannot close!", JOptionPane.ERROR_MESSAGE);
        }
        else{
            System.exit(status);
        }
    }
    
    public void setJDVisible(final boolean bool){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame jf = Globals.MAIN_FRAME;
                Point p = jf.getLocation();
                jd.pack();
                jd.setLocation(p.x+(jf.getWidth()-jd.getWidth())/2, p.y+(jf.getHeight()-jd.getHeight())/2);
                jd.setVisible(bool);
            }
        });
    }
    
    class FreezeKeyListener implements KeyListener{
        public void keyPressed(KeyEvent e) {
        }
        public void keyReleased(KeyEvent e) {
        }
        public void keyTyped(KeyEvent e) {
        }
    }
    
    class FreezeMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e) {
        }
        public void mouseEntered(MouseEvent e) {
        }
        public void mouseExited(MouseEvent e) {
        }
        public void mousePressed(MouseEvent e) {
        }
        public void mouseReleased(MouseEvent e) {
        }
    }
    
    private final FreezeKeyListener FREEZE_KEY_LISTENER = new FreezeKeyListener();
    private final FreezeMouseListener FREEZE_MOUSE_LISTENER = new FreezeMouseListener();
    
    private final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    private final Cursor WAIT_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
    
    public void freeze(){
        final Component gpane = this.getGlassPane();

        gpane.addKeyListener(FREEZE_KEY_LISTENER);
        gpane.addMouseListener(FREEZE_MOUSE_LISTENER);
        try{
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    gpane.setCursor(WAIT_CURSOR);
                    gpane.setVisible(true);
                }
            });
        }
        catch(InvocationTargetException ite){
            ite.printStackTrace();
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    public void unfreeze(){
        final Component gpane = this.getGlassPane();
        try{
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    gpane.removeKeyListener(FREEZE_KEY_LISTENER);
                    gpane.removeMouseListener(FREEZE_MOUSE_LISTENER);
                    gpane.setVisible(false);
                    gpane.setCursor(DEFAULT_CURSOR);
                }
            });
        }
        catch(InvocationTargetException ite){
            ite.printStackTrace();
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jlStatus = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfFiles = new javax.swing.JTextField();
        jbFileOpen = new javax.swing.JButton();
        jpf1 = new javax.swing.JPasswordField();
        jpf2 = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jbProcess = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jpScrollbar = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaMessage = new javax.swing.JTextArea();
        jMenuBar2 = new javax.swing.JMenuBar();
        jmFile = new javax.swing.JMenu();
        jmtExit = new javax.swing.JMenuItem();
        jmEdit = new javax.swing.JMenu();
        jmiClearLog = new javax.swing.JMenuItem();
        jmiClearFileSelection = new javax.swing.JMenuItem();
        jmiClearPassword = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jmiClearAll = new javax.swing.JMenuItem();
        jmTools = new javax.swing.JMenu();
        jmiPreferences = new javax.swing.JMenuItem();
        jmHelp = new javax.swing.JMenu();
        jmtAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("jEnkryptor");
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jlStatus.setText("jEnkryptor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlStatus)
        );

        controlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("jEnkryptor"));
        controlPanel.setName("ControlPanel");
        jLabel1.setText("Files: ");

        jtfFiles.setEditable(false);

        jbFileOpen.setMnemonic('r');
        jbFileOpen.setText("Browse");
        jbFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFileOpenActionPerformed(evt);
            }
        });

        jpf1.setPreferredSize(new java.awt.Dimension(109, 19));

        jpf2.setPreferredSize(new java.awt.Dimension(109, 19));

        jLabel2.setText("Password:");

        jLabel3.setText("Re-enter Password (only for encrypt):");

        jbProcess.setMnemonic('y');
        jbProcess.setText("Encrypt / Decrypt");
        jbProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbProcessActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbFileOpen))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jpf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                        .addComponent(jpf1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbProcess, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jbFileOpen)
                    .addComponent(jtfFiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpf1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(14, 14, 14)
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpf2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbProcess)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpScrollbarLayout = new javax.swing.GroupLayout(jpScrollbar);
        jpScrollbar.setLayout(jpScrollbarLayout);
        jpScrollbarLayout.setHorizontalGroup(
            jpScrollbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );
        jpScrollbarLayout.setVerticalGroup(
            jpScrollbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );
        jScrollPane1.setViewportView(jpScrollbar);

        jtaMessage.setColumns(20);
        jtaMessage.setEditable(false);
        jtaMessage.setRows(5);
        jScrollPane2.setViewportView(jtaMessage);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jmFile.setMnemonic('f');
        jmFile.setText("File");
        jmFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmFileActionPerformed(evt);
            }
        });

        jmtExit.setMnemonic('x');
        jmtExit.setText("Exit");
        jmtExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmtExitActionPerformed(evt);
            }
        });

        jmFile.add(jmtExit);

        jMenuBar2.add(jmFile);

        jmEdit.setMnemonic('e');
        jmEdit.setText("Edit");
        jmiClearLog.setMnemonic('l');
        jmiClearLog.setText("Clear Log");
        jmiClearLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiClearLogActionPerformed(evt);
            }
        });

        jmEdit.add(jmiClearLog);

        jmiClearFileSelection.setMnemonic('f');
        jmiClearFileSelection.setText("Clear File Selection");
        jmiClearFileSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiClearFileSelectionActionPerformed(evt);
            }
        });

        jmEdit.add(jmiClearFileSelection);

        jmiClearPassword.setMnemonic('p');
        jmiClearPassword.setText("Clear Password Fields");
        jmiClearPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiClearPasswordActionPerformed(evt);
            }
        });

        jmEdit.add(jmiClearPassword);

        jmEdit.add(jSeparator1);

        jmiClearAll.setMnemonic('a');
        jmiClearAll.setText("Clear All");
        jmiClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiClearAllActionPerformed(evt);
            }
        });

        jmEdit.add(jmiClearAll);

        jMenuBar2.add(jmEdit);

        jmTools.setMnemonic('t');
        jmTools.setText("Tools");
        jmTools.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmToolsActionPerformed(evt);
            }
        });

        jmiPreferences.setMnemonic('r');
        jmiPreferences.setText("Preferences");
        jmiPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPreferencesActionPerformed(evt);
            }
        });

        jmTools.add(jmiPreferences);

        jMenuBar2.add(jmTools);

        jmHelp.setMnemonic('h');
        jmHelp.setText("Help");
        jmtAbout.setMnemonic('a');
        jmtAbout.setText("About");
        jmtAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmtAboutActionPerformed(evt);
            }
        });

        jmHelp.add(jmtAbout);

        jMenuBar2.add(jmHelp);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiClearPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiClearPasswordActionPerformed
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                clearPasswordFields();
            }
        });
    }//GEN-LAST:event_jmiClearPasswordActionPerformed

    private void jmiClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiClearAllActionPerformed
        clearFileSelection();
        Globals.msgDisplayer.clear();
        clearPasswordFields();
    }//GEN-LAST:event_jmiClearAllActionPerformed

    private void clearPasswordFields(){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                jpf1.setText("");
                jpf2.setText("");
            }
        });
    }
    
    private void clearFileSelection(){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                Globals.files = null;
                jtfFiles.setText("");
            }
        });
    }
    
    private void jmiClearFileSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiClearFileSelectionActionPerformed
        clearFileSelection();
    }//GEN-LAST:event_jmiClearFileSelectionActionPerformed

    private void jmiClearLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiClearLogActionPerformed
        Globals.msgDisplayer.clear();
    }//GEN-LAST:event_jmiClearLogActionPerformed

    private void jmtAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmtAboutActionPerformed
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                jd.setContentPane(jpAbout);
                setJDVisible(true);
            }
        });
    }//GEN-LAST:event_jmtAboutActionPerformed

    private void jmiPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPreferencesActionPerformed
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                jpPreferences.setPreferences(Preferences.overwriteDestination_pref,
                        Preferences.deleteSource_pref);
                jd.setContentPane(jpPreferences);
                setJDVisible(true);
            }
        });
        
    }//GEN-LAST:event_jmiPreferencesActionPerformed

    private void jmtExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmtExitActionPerformed
        exit(0);
    }//GEN-LAST:event_jmtExitActionPerformed

    private void jmToolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmToolsActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jmToolsActionPerformed

    private void jmFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmFileActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jmFileActionPerformed

    private void jbProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProcessActionPerformed
        
        if(Globals.files == null){
            final String msg = "No files selected!";
            JOptionPane.showMessageDialog(this, msg, msg, JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        char[] pwd1 = jpf1.getPassword();
        char[] pwd2 = jpf2.getPassword();
        
        if(pwd1.length == 0){
            final String msg = "Password is empty!";
            JOptionPane.showMessageDialog(this, msg, msg, JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        final String msg = "Password not equal!";
        if(Globals.mode == Globals.MODE_ENCRYPT){
            if(!Arrays.equals(pwd1, pwd2)){
                JOptionPane.showMessageDialog(this, msg, msg, JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        if(Globals.mode == Globals.MODE_DECRYPT){
            if(pwd2.length != 0 && !Arrays.equals(pwd1, pwd2)){
                JOptionPane.showMessageDialog(this, msg, msg, JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        Globals.password = new String(pwd1);
        
        Globals.msgDisplayer.clear();
        Processor.getInstance().process();
    }//GEN-LAST:event_jbProcessActionPerformed

    private void jbFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFileOpenActionPerformed
        jfc.showOpenDialog(this);
        File[] files = jfc.getSelectedFiles();

        if(files == null || files.length == 0){
            // JOptionPane.showMessageDialog(this, "No file selected!", "No file selected!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            validator.validate(files);
        }
        catch(ValidatorException ve){
            JOptionPane.showMessageDialog(this, ve.getMessage(), "Validation Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try{
            final String filesCSV = FileUtil.getFileNamesCSV(files);
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    jtfFiles.setText(filesCSV);
                }
            });
            
            
        }
        catch(ValidatorException ve){
            JOptionPane.showMessageDialog(this, ve.getMessage(), "Validation Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        catch(IOException ioe){
            Globals.msgDisplayer.appendMessage("ERROR: "+ioe.getMessage());
            return;
        }
        
    }//GEN-LAST:event_jbFileOpenActionPerformed
       
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbFileOpen;
    private javax.swing.JButton jbProcess;
    private javax.swing.JLabel jlStatus;
    private javax.swing.JMenu jmEdit;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenu jmTools;
    private javax.swing.JMenuItem jmiClearAll;
    private javax.swing.JMenuItem jmiClearFileSelection;
    private javax.swing.JMenuItem jmiClearLog;
    private javax.swing.JMenuItem jmiClearPassword;
    private javax.swing.JMenuItem jmiPreferences;
    private javax.swing.JMenuItem jmtAbout;
    private javax.swing.JMenuItem jmtExit;
    private javax.swing.JPanel jpScrollbar;
    private javax.swing.JPasswordField jpf1;
    private javax.swing.JPasswordField jpf2;
    private javax.swing.JTextArea jtaMessage;
    private javax.swing.JTextField jtfFiles;
    // End of variables declaration//GEN-END:variables
    
}
