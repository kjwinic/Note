
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.PrintJob;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.undo.UndoManager;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
@SuppressWarnings("serial")
public class Note extends JFrame implements ActionListener{
  JMenuBar menubar;
  JMenu file_menu,edite_menu,format_menu,v_menu,help_menu;
  JSplitPane splitPane;
  JMenuItem itemNew,itemOpen,itemSave,itemSaveAs,itemPage,itemPrint,itemExit;
  JMenuItem itemUndo,itemCopy,itemCut,itemPaste,itemDelete,itemfind,itemfind_next,itemReplace,itemGo_to,item_A,itemDate;
  JMenuItem itemLine,itemFont;
  JMenuItem itemHelp,itemAbout;
  JTextArea textArea;
  public static JLabel lblState;
  JTextField field;
  JPopupMenu popupMenu;
  JToolBar statusBar;
  JCheckBoxMenuItem itemstate;
  String currentFileName;
  PrintJob  p=null;//����һ��Ҫ��ӡ�Ķ���
  Graphics  g=null;//Ҫ��ӡ�Ķ���
  //����������
  public UndoManager undoMgr = new UndoManager(); 
  
  //������
  public Clipboard clipboard = new Clipboard("ϵͳ���а�"); 
  JScrollPane scrollPane;
  private JPanel panel;
 int  flag;
 
 String currentPath;
  public Note()
  {
	  init();
	  setBounds(500,150,500,500);
	  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  	 
  }
  public void init()
  {
	  this.setTitle("\u65E0\u6807\u9898-\u7B14\u8BB0\u672C");
	  menubar =new JMenuBar();
	  file_menu=new JMenu("�ļ�(F)");
	  menubar.add(file_menu);
	  setJMenuBar(menubar);
	  itemNew =new JMenuItem("�½�(N)");
	  itemOpen=new JMenuItem("��(O)");
	  itemSave= new JMenuItem("����(S)");
	  itemSaveAs= new JMenuItem("����(A)...");
	  itemPage=new JMenuItem("ҳ������(U)...");
	  itemPrint=new JMenuItem("��ӡ(P)...");
	  itemExit=new JMenuItem("�˳�(X)");
	  file_menu.add(itemNew);
	  file_menu.add(itemOpen);
	  file_menu.add(itemSave);
	  file_menu.add(itemSaveAs);
	  file_menu.addSeparator();
	  file_menu.add(itemPage);
	  file_menu.add(itemPrint);
	  file_menu.addSeparator();
	  file_menu.add(itemExit);
	  itemNew.addActionListener(this);
	  itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
	  itemOpen.addActionListener(this);
	  itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
	  itemSave.addActionListener(this);
	  itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
	  itemSaveAs.addActionListener(this);
	  itemPrint.addActionListener(this);
	  itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
	  edite_menu=new JMenu("�༭(E)");
	  menubar.add(edite_menu);
	  itemUndo=new JMenuItem("����(U)");
	  itemUndo.setEnabled(false);
	  itemCut=new JMenuItem("����(T)");
	  itemCut.setEnabled(false);
	  itemCopy=new JMenuItem("����(C)");
	  itemCopy.setEnabled(false);
	  itemPaste=new JMenuItem("ճ��(P)");
	  itemDelete=new JMenuItem("ɾ��(L)");
	  itemDelete.setEnabled(false);
	  itemfind=new JMenuItem("\u67E5\u627E(F)...");
	  itemfind.setEnabled(false);
	  itemfind_next=new JMenuItem("������һ��(N)");
	  itemfind_next.setEnabled(false);
	  itemReplace=new JMenuItem("�滻(R)...");
	  itemReplace.setEnabled(false);
	  itemGo_to=new JMenuItem("ת��(G)...");
	  item_A=new JMenuItem("ȫѡ(A)");
	  itemDate=new JMenuItem("����(D)");
	  edite_menu.add(itemUndo);
	  edite_menu.addSeparator();
	  edite_menu.add(itemCut);
	  edite_menu.add(itemCopy);
	  edite_menu.add(itemPaste);
	  edite_menu.add(itemDelete);
	  edite_menu.addSeparator();
	  edite_menu.add(itemfind);
	  edite_menu.add(itemfind_next);
	  edite_menu.add(itemReplace);
	  edite_menu.addSeparator();
	  edite_menu.add(itemGo_to);
	  edite_menu.add(item_A);
	  edite_menu.add(itemDate);
	  itemUndo.addActionListener(this);
	  itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
	  itemCut.addActionListener(this);
	  itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
	  itemCopy.addActionListener(this);
	  itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
	  itemPaste.addActionListener(this);
	  itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
	  itemDelete.addActionListener(this);
	  itemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
	  itemfind.addActionListener(this);
	  itemfind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
	  itemfind_next.addActionListener(this);
	  itemfind_next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
	  itemReplace.addActionListener(this);
	  itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
	  itemGo_to.addActionListener(this);
	  itemGo_to.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));
	  item_A.addActionListener(this);
	  item_A.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
	  itemDate.addActionListener(this);
	  itemDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
	  format_menu=new JMenu("��ʽ(O)");
	  v_menu=new JMenu("�鿴(V)");
	  help_menu=new JMenu("����(H)");
	  menubar.add(format_menu);
	  menubar.add(v_menu);
	  menubar.add(help_menu);
	  itemLine =new JMenuItem("�Զ�����(W)");
	  itemFont=new JMenuItem("����(F)");
	  itemLine.addActionListener(this);
	  itemFont.addActionListener(this);
	  format_menu.add(itemLine);
	  format_menu.add(itemFont);
	  itemstate=new   JCheckBoxMenuItem("״̬��(S)");
	  itemstate.addActionListener(this);
	  v_menu.add(itemstate);
	  itemHelp=new JMenuItem("�鿴����(H)");
	  itemAbout=new JMenuItem("���ڼ��±�(A)");
	  help_menu.add(itemHelp);
	  help_menu.addSeparator();
	  help_menu.add(itemAbout);
	   textArea = new JTextArea();
	  scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      panel = new JPanel();
      panel.setBorder(new EmptyBorder(1, 1, 1, 1));
      //���ñ߿򲼾�
      panel.setLayout(new BorderLayout(0, 0));
      setContentPane(panel);
      scrollPane = new JScrollPane();
      panel.add(scrollPane, BorderLayout.CENTER);
      textArea = new JTextArea();
      textArea.getDocument().addUndoableEditListener(undoMgr);
      scrollPane.setViewportView(textArea);
      
      statusBar = new JToolBar();
      statusBar.setSize(textArea.getSize().width, 10);//toolState.setLayout(new FlowLayout(FlowLayout.LEFT));
      lblState = new JLabel(" ");
      statusBar.add(lblState);
      panel.add(statusBar, BorderLayout.SOUTH);
      statusBar.setVisible(false);
      statusBar.setFloatable(false);
      Clock clock=new Clock();
      clock.start();
      isChanged();
      this.MainFrameWidowListener();
  }
  private void isChanged() 
  {
      textArea.addKeyListener(new KeyAdapter() {
          public void keyTyped(KeyEvent e) {
              //�������ҽ����˶�ʹ�ÿ�ݼ�������û�������ַ�ȴû�иı�textArea�����ݵ��ж�
              Character c=e.getKeyChar();
              if(c != null && !textArea.getText().toString().equals("")){
            	  itemfind.setEnabled(true);
            	  itemfind_next.setEnabled(true);
            	  itemReplace.setEnabled(true);
                  flag=2;
              }
              if(textArea.getSelectedText()!=null)
    		  {
    		  itemCopy.setEnabled(true);
        	  itemCut.setEnabled(true);
        	  itemDelete.setEnabled(true);
    		  }
              else
              {
            	  itemCopy.setEnabled(false);
            	  itemCut.setEnabled(false);
            	  itemDelete.setEnabled(false);
              }
          }        
      });
      textArea.addMouseMotionListener(new MouseMotionListener()
      {
    	  public void  mouseMoved(MouseEvent e)
    	  {
    		  if(textArea.getSelectedText()!=null)
    		  {
    		  itemCopy.setEnabled(true);
        	  itemCut.setEnabled(true);
        	  itemDelete.setEnabled(true);
    		  }
    		  else
    		  {
    			  itemCopy.setEnabled(false);
            	  itemCut.setEnabled(false);
            	  itemDelete.setEnabled(false);
    		  }
    	  }

		@Override
		public void mouseDragged(MouseEvent e) {
			  if(textArea.getSelectedText()!=null)
    		  {
    		  itemCopy.setEnabled(true);
        	  itemCut.setEnabled(true);
        	  itemDelete.setEnabled(true);
    		  }
		}
      });
  }
  private void MainFrameWidowListener() {
      this.addWindowListener(new WindowAdapter(){
          @Override
          public void windowClosing(WindowEvent e) {
       
			if(flag==2 && currentPath==null){
                  //���ǵ���С����
                  //1�������������±�Ϊ0�����½��ĵ�Ϊ1���������޸ĺ�
                  int result=JOptionPane.showConfirmDialog(Note.this, "�Ƿ񽫸��ı��浽�ޱ���?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                  if(result==JOptionPane.OK_OPTION){
                     Note.this.saveAs();
                  }else if(result==JOptionPane.NO_OPTION){
                      Note.this.dispose();
                      Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                  }
              }else if(flag==2 && currentPath!=null){
                  //���ǵ���С����
                  //1�������������±�Ϊ0�����½��ĵ�Ϊ1���������޸ĺ�
                  int result=JOptionPane.showConfirmDialog(Note.this, "�Ƿ񽫸��ı��浽"+currentPath+"?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                  if(result==JOptionPane.OK_OPTION){
                      Note.this.save();
                  }else if(result==JOptionPane.NO_OPTION){
                     Note.this.dispose();
                      Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                  }
              }else{
                  //���ǵ���С����
                  int result=JOptionPane.showConfirmDialog(Note.this, "ȷ���رգ�", "ϵͳ��ʾ", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                  if(result==JOptionPane.OK_OPTION){
                      Note.this.dispose();
                      Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                  }
              }
          }
      });
  }
  public  static void main(String[] args)
  {

		Note frame = new Note();
		frame.setVisible(true);	    	  				   
	  
  }	
  
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource()==itemOpen){            //��
        openFile();
    }else if(e.getSource()==itemSave){        //����
        //������ļ��Ǵ򿪵ģ��Ϳ���ֱ�ӱ���
        save();
    }else if(e.getSource()==itemSaveAs)
    {    //���Ϊ
        saveAs();
    }else if(e.getSource()==itemNew){        //�½�
        newFile();
    }else if(e.getSource()==itemExit){        //�˳�
        exit();
    }else if(e.getSource()==itemPage){        //ҳ������
        ///ҳ�����ã��ٶȵ��ģ���֪��������÷�
        PageFormat pf = new PageFormat();
        PrinterJob.getPrinterJob().pageDialog(pf); 
    }else if(e.getSource()==itemPrint){        //��ӡ
        //��ӡ��
        Print();
    }else if(e.getSource()==itemUndo ){        //����
        if(undoMgr.canUndo()){
            undoMgr.undo();
        }
    }else if(e.getSource()==itemUndo ){        //�ָ�
        if(undoMgr.canRedo()){
            undoMgr.redo();
        }
    }else if(e.getSource()==itemCut ){        //����
        cut();
    }else if(e.getSource()==itemCopy ){        //����
        copy();
    }else if(e.getSource()==itemPaste ){    //ճ��
        paste();
    }else if(e.getSource()==itemDelete ){    //ɾ��
        String tem=textArea.getText().toString();
        textArea.setText(tem.substring(0,textArea.getSelectionStart())); 
    }else if(e.getSource()==itemfind){        //����
        myfind();
    }else if(e.getSource()==itemfind_next){    //������һ��
        myfind();
    }else if(e.getSource()==itemReplace){    //�滻
        mySearch();
    }else if(e.getSource()==itemGo_to){    //ת��
        turnTo();
    }else if(e.getSource()==item_A){    //ѡ��ȫ��
        textArea.selectAll();
    }else if(e.getSource()==itemDate){       
    	//ʱ��/����
    	  GregorianCalendar time = new GregorianCalendar();  
          int hour = time.get(Calendar.HOUR_OF_DAY);  
          int min = time.get(Calendar.MINUTE);        
		int second = time.get(Calendar.SECOND);  
		String str=hour+":"+min+":"+second+" "+time.get(Calendar.YEAR)+"/"+(time.get(Calendar.MONTH)+1)+"/"+time.get(Calendar.DAY_OF_MONTH);
			 if(this.textArea.getSelectedText()!=null)
	            {
	                //��λ��ѡ���ַ��Ŀ�ʼλ��
	                int start = this.textArea.getSelectionStart();
	                //��λ��ѡ���ַ���ĩβλ��
	                int end = this.textArea.getSelectionEnd();
	                //��ճ���������滻�ɱ�ѡ�е�����
	                this.textArea.replaceRange(str, start, end);
	            }
	            else
	            {
	                //��ȡ�������TextArea��λ��
	                int mouse = this.textArea.getCaretPosition();
	                //��������ڵ�λ��ճ������
	                this.textArea.insert(str, mouse);
	            }
		flag=2;
    }else if(e.getSource()==itemLine){    //�����Զ�����
        //�����ı����Ļ��в��ԡ��������Ϊ true�����еĳ��ȴ���������Ŀ��ʱ�������С�������Ĭ��Ϊ false�� 
        if(itemLine.isSelected()){
            textArea.setLineWrap(true);
        }else{
            textArea.setLineWrap(false);
        }
    }else if(e.getSource()==itemFont){        //���������С
        // ��������ѡ��������������ΪԤ��ֵ
        MQFontChooser fontChooser = new MQFontChooser(textArea.getFont());
        fontChooser.showFontDialog(this);
        Font font = fontChooser.getSelectFont();
        textArea.setFont(font);
    
    }else if(e.getSource()==itemstate){    //����״̬
        if(itemstate.isSelected()){
            //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        	 statusBar.setVisible(true);
        }else{
            //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        	 statusBar.setVisible(false);
        }
    }else if(e.getSource()==itemHelp){
        JOptionPane.showMessageDialog(this, "......",".....",1);
    }else if(e.getSource()==itemAbout){
        JOptionPane.showMessageDialog(this, "���±�","���˵�� ",1);
    }
}
private void turnTo() {
    final JDialog gotoDialog = new JDialog(this, "ת��������");
    JLabel gotoLabel = new JLabel("����(L):");
    final JTextField linenum = new JTextField(5);
    linenum.setText("1");
    linenum.selectAll();

    JButton okButton = new JButton("ȷ��");
    okButton.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            int totalLine = textArea.getLineCount();
            int[] lineNumber = new int[totalLine + 1];
            String s = textArea.getText();
            int pos = 0, t = 0;
	       while(true)
	       {
	    	   pos=s.indexOf('\n',pos);
	    	   if(pos==-1)
	    		   break;
	    	   lineNumber[t++]=pos++;
	       }

            int gt = 1;
            try {
                gt = Integer.parseInt(linenum.getText());
            } catch (NumberFormatException efe) {
                JOptionPane.showMessageDialog(null, "����������!", "��ʾ", JOptionPane.WARNING_MESSAGE);
                linenum.requestFocus(true);
                return;
            }

                if (gt < 2)
                {
                    textArea.setCaretPosition(0);
                }
                else if(gt>totalLine)
                {
                    textArea.setCaretPosition(s.length());
                } 
                else
                {
                textArea.setCaretPosition(lineNumber[gt - 2]+1 );
                }

            gotoDialog.dispose();
        }

    });

    JButton cancelButton = new JButton("ȡ��");
    cancelButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            gotoDialog.dispose();
        }
    });

    Container con = gotoDialog.getContentPane();
    con.setLayout(new FlowLayout());
    con.add(gotoLabel);
    con.add(linenum);
    con.add(okButton);
    con.add(cancelButton);

    gotoDialog.setSize(200, 100);
    gotoDialog.setResizable(false);
    gotoDialog.setLocation(300, 280);
    gotoDialog.setVisible(true);
}


/*===============================8====================================*/
/**
 * �Ƴ���ť���ʹ��ڵĺ��ʵ��һ���Ĺ���
 */
private void exit() {
    if(flag==2 && currentPath==null){
        //���ǵ���С����
        //1�������������±�Ϊ0�����½��ĵ�Ϊ1���������޸ĺ�
        int result=JOptionPane.showConfirmDialog(Note.this, "�Ƿ񽫸��ı��浽�ޱ���?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            Note.this.saveAs();
        }else if(result==JOptionPane.NO_OPTION){
            Note.this.dispose();
            Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }else if(flag==2 && currentPath!=null){
        //���ǵ���С����
        //1�������������±�Ϊ0�����½��ĵ�Ϊ1���������޸ĺ�
        int result=JOptionPane.showConfirmDialog(Note.this, "�Ƿ񽫸��ı��浽"+currentPath+"?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            Note.this.save();
        }else if(result==JOptionPane.NO_OPTION){
            Note.this.dispose();
            Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }else{
        //���ǵ���С����
        int result=JOptionPane.showConfirmDialog(Note.this, "ȷ���رգ�", "ϵͳ��ʾ", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            Note.this.dispose();
            Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
}
/*===================================================================*/


/*===============================4====================================*/
/**
 * �½��ļ���ֻ�иĹ��ĺͱ��������Ҫ����
 */
private void newFile() {
    if(flag==0 || flag==1){        //���������±�Ϊ0�����½��ĵ�Ϊ1
        return;
    }else if(flag==2 && this.currentPath==null)
    {        //�޸ĺ�
        //1�������������±�Ϊ0�����½��ĵ�Ϊ1���������޸ĺ�
        int result=JOptionPane.showConfirmDialog(Note.this, "�Ƿ񽫸��ı��浽�ޱ���?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            this.saveAs();        //���Ϊ                
        }else if(result==JOptionPane.NO_OPTION){
            this.textArea.setText("");
            this.setTitle("�ޱ���");
            flag=1;
        }
        return;
    }else if(flag==2 && this.currentPath!=null )
    {
        //2����������ļ�Ϊ3���������޸ĺ�
        int result=JOptionPane.showConfirmDialog(Note.this, "�Ƿ񽫸��ı��浽"+this.currentPath+"?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            this.save();        //ֱ�ӱ��棬��·��
        }else if(result==JOptionPane.NO_OPTION){
            this.textArea.setText("");
            this.setTitle("�ޱ���");
            flag=1;
        }
    }
    else if(flag==3)
    {        //������ļ�
        this.textArea.setText("");
        flag=1;
        this.setTitle("�ޱ���");
    }
}
/*===================================================================*/


/*===============================5====================================*/
/**
 * ���Ϊ
 */
private void saveAs() {
    //�򿪱����
    JFileChooser choose=new JFileChooser();
    //ѡ���ļ�
    int result=choose.showSaveDialog(this);
    if(result==JFileChooser.APPROVE_OPTION){
        //ȡ��ѡ����ļ�[�ļ������Լ������]
        File file=choose.getSelectedFile();
        FileWriter fw=null;
        //����
        try {
            fw=new FileWriter(file);
            fw.write(textArea.getText());
            currentFileName=file.getName();
            currentPath=file.getAbsolutePath();
            //����Ƚ��٣���Ҫд
            fw.flush();
            this.flag=3;
            this.setTitle(currentPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally{
            try {
                if(fw!=null) fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
/*===================================================================*/


/*===============================6====================================*/
/**
 * ����
 */
private void save() {
    if(this.currentPath==null){
        this.saveAs();
        if(this.currentPath==null){
            return;
        }
    }
    FileWriter fw=null;
    //����
    try {
        fw=new FileWriter(new  File(currentPath));
        fw.write(textArea.getText());
        //����Ƚ��٣���Ҫд
        fw.flush();
        flag=3;
        this.setTitle(this.currentPath);
    } catch (IOException e1) {
        e1.printStackTrace();
    }finally{
        try {
            if(fw!=null) fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
/*===================================================================*/


/*================================7===================================*/
/**
 * ���ļ�
 */
private void openFile() {
    if(flag==2 && this.currentPath==null){
        //1�������������±�Ϊ0�����½��ĵ�Ϊ1���������޸ĺ�
        int result=JOptionPane.showConfirmDialog(Note.this, "�Ƿ񽫸��ı��浽�ޱ���?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            this.saveAs();
        }
    }else if(flag==2 && this.currentPath!=null){
        //2�����򿪵��ļ�2��������ļ�3���������޸�
        int result=JOptionPane.showConfirmDialog(Note.this, "�Ƿ񽫸��ı��浽"+this.currentPath+"?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            this.save();
        }
    }
    //���ļ�ѡ���
    JFileChooser choose=new JFileChooser();
    //ѡ���ļ�
    int result=choose.showOpenDialog(this);
    if(result==JFileChooser.APPROVE_OPTION){
        //ȡ��ѡ����ļ�
        File file=choose.getSelectedFile();
        //���Ѵ��ڵ��ļ�����ǰ���ļ���������
        currentFileName=file.getName();
        //�����ļ�ȫ·��
        currentPath=file.getAbsolutePath();
        flag=3;
        this.setTitle(this.currentPath);
        BufferedReader br=null;
        try {
            //�����ļ���[�ַ���]
            InputStreamReader isr=new InputStreamReader(new FileInputStream(file),"GBK");
            br=new BufferedReader(isr);//��̬��
            //��ȡ����
            StringBuffer sb=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                sb.append(line+System.getProperty("line.separator"));
            }
            //��ʾ���ı���[���]
            textArea.setText(sb.toString());
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally{
            try {
                if(br!=null) br.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
/*================================================================*/


/*=============================9===================================*/
public void Print()
{
    try{
        p = getToolkit().getPrintJob(this,"ok",null);//����һ��Printfjob ���� p
        g = p.getGraphics();//p ��ȡһ�����ڴ�ӡ�� Graphics �Ķ���
        //g.translate(120,200);//�ı��齨��λ�� 
        this.textArea.printAll(g);
        p.end();//�ͷŶ��� g  
    }
    catch(Exception a){

    } 
}
/*================================================================*/


public void cut(){
    copy();
    //��ǿ�ʼλ��
    int start = this.textArea.getSelectionStart();
    //��ǽ���λ��
    int end = this.textArea.getSelectionEnd();
    //ɾ����ѡ��
    this.textArea.replaceRange("", start, end);
    
}

public void copy(){
    //�϶�ѡȡ�ı�
    String temp = this.textArea.getSelectedText();
    //�ѻ�ȡ�����ݸ��Ƶ������ַ����������̳��˼�����ӿ�
    StringSelection text = new StringSelection(temp);
    //�����ݷ��ڼ�����
    this.clipboard.setContents(text, null);
}

 public void paste(){
     //Transferable�ӿڣ��Ѽ����������ת��������
     Transferable contents = this.clipboard.getContents(this);
     //DataFalvor���ж��Ƿ��ܰѼ����������ת����������������
     DataFlavor flavor = DataFlavor.stringFlavor;
     //�������ת��
     if(contents.isDataFlavorSupported(flavor)){
         String str;
         try {//��ʼת��
            str=(String)contents.getTransferData(flavor);
            //���Ҫճ��ʱ������Ѿ�ѡ����һЩ�ַ�
            if(this.textArea.getSelectedText()!=null)
            {
                //��λ��ѡ���ַ��Ŀ�ʼλ��
                int start = this.textArea.getSelectionStart();
                //��λ��ѡ���ַ���ĩβλ��
                int end = this.textArea.getSelectionEnd();
                //��ճ���������滻�ɱ�ѡ�е�����
                this.textArea.replaceRange(str, start, end);
            }
            else
            {
                //��ȡ�������TextArea��λ��
                int mouse = this.textArea.getCaretPosition();
                //��������ڵ�λ��ճ������
                this.textArea.insert(str, mouse);
            }
         } catch(UnsupportedFlavorException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } catch(IllegalArgumentException e){
            e.printStackTrace();
         }
     }
 }
 
public void mySearch() {
    final JDialog findDialog = new JDialog(this, "�滻", true);
    Container con = findDialog.getContentPane();
    con.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel searchContentLabel = new JLabel("��������(N) :");
    JLabel replaceContentLabel = new JLabel("�滻Ϊ(P)�� :");
    final JTextField findText = new JTextField(20);
    final JTextField replaceText = new JTextField(20);
    final JCheckBox matchcase = new JCheckBox("���ִ�Сд");
    ButtonGroup bGroup = new ButtonGroup();
    final JRadioButton up = new JRadioButton("����(U)");
    final JRadioButton down = new JRadioButton("����(D)");
    down.setSelected(true);
    bGroup.add(up);
    bGroup.add(down);
    JButton searchNext = new JButton("������һ��(F)");
    JButton replace = new JButton("�滻(R)");
    final JButton replaceAll = new JButton("ȫ���滻(A)");
    searchNext.setPreferredSize(new Dimension(110, 22));
    replace.setPreferredSize(new Dimension(110, 22));
    replaceAll.setPreferredSize(new Dimension(110, 22));
    // "�滻"��ť���¼�����
    replace.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
     
            if (replaceText.getText().length() == 0 && textArea.getSelectedText() != null)
                textArea.replaceSelection("");
            if (replaceText.getText().length() > 0 && textArea.getSelectedText() != null)
                textArea.replaceSelection(replaceText.getText());
        }
    });

    // "�滻ȫ��"��ť���¼�����
    replaceAll.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            textArea.setCaretPosition(0); // �����ŵ��༭����ͷ
            int a = 0, b = 0, replaceCount = 0;

            if (findText.getText().length() == 0) {
                JOptionPane.showMessageDialog(findDialog, "����д��������!", "��ʾ", JOptionPane.WARNING_MESSAGE);
                findText.requestFocus(true);
                return;
            }
            while (a > -1) {

                int FindStartPos = textArea.getCaretPosition();
                String str1, str2, str3, str4, strA, strB;
                str1 = textArea.getText();
                str2 = str1.toLowerCase();
                str3 = findText.getText();
                str4 = str3.toLowerCase();

                if (matchcase.isSelected()) {
                    strA = str1;
                    strB = str3;
                } else {
                    strA = str2;
                    strB = str4;
                }

                if (up.isSelected()) {
                    if (textArea.getSelectedText() == null) {
                        a = strA.lastIndexOf(strB, FindStartPos - 1);
                    } else {
                        a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                    }
                } else if (down.isSelected()) {
                    if (textArea.getSelectedText() == null) {
                        a = strA.indexOf(strB, FindStartPos);
                    } else {
                        a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                    }

                }

                if (a > -1) {
                    if (up.isSelected()) {
                        textArea.setCaretPosition(a);
                        b = findText.getText().length();
                        textArea.select(a, a + b);
                    } else if (down.isSelected()) {
                        textArea.setCaretPosition(a);
                        b = findText.getText().length();
                        textArea.select(a, a + b);
                    }
                } else {
                    if (replaceCount == 0) {
                        JOptionPane.showMessageDialog(findDialog, "�Ҳ��������ҵ�����!", "���±�", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(findDialog, "�ɹ��滻" + replaceCount + "��ƥ������!", "�滻�ɹ�", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                if (replaceText.getText().length() == 0 && textArea.getSelectedText() != null) {
                    textArea.replaceSelection("");
                    replaceCount++;
                }
                if (replaceText.getText().length() > 0 && textArea.getSelectedText() != null) {
                    textArea.replaceSelection(replaceText.getText());
                    replaceCount++;
                }
            }// end while
        }
    }); /* "�滻ȫ��"��ť���¼�������� */

    // "������һ��"��ť�¼�����
    searchNext.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            int a = 0, b = 0;
            int FindStartPos = textArea.getCaretPosition();
            String str1, str2, str3, str4, strA, strB;
            str1 = textArea.getText();
            str2 = str1.toLowerCase();
            str3 = findText.getText();
            str4 = str3.toLowerCase();
            // "���ִ�Сд"��CheckBox��ѡ��
            if (matchcase.isSelected()) {
                strA = str1;
                strB = str3;
            } else {
                strA = str2;
                strB = str4;
            }

            if (up.isSelected()) {
                if (textArea.getSelectedText() == null) {
                    a = strA.lastIndexOf(strB, FindStartPos - 1);
                } else {
                    a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                }
            } else if (down.isSelected()) {
                if (textArea.getSelectedText() == null) {
                    a = strA.indexOf(strB, FindStartPos);
                } else {
                    a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                }

            }
            if (a > -1) {
                if (up.isSelected()) {
                    textArea.setCaretPosition(a);
                    b = findText.getText().length();
                    textArea.select(a, a + b);
                } else if (down.isSelected()) {
                    textArea.setCaretPosition(a);
                    b = findText.getText().length();
                    textArea.select(a, a + b);
                }
            } else {
                JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ�����!", "���±�", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    });/* "������һ��"��ť�¼�������� */
    // "ȡ��"��ť���¼�����
    JButton cancel = new JButton("ȡ��");
    cancel.setPreferredSize(new Dimension(110, 22));
    cancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            findDialog.dispose();
        }
    });

    // ����"�������滻"�Ի���Ľ���
    JPanel bottomPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel topPanel = new JPanel();

    JPanel direction = new JPanel();
    direction.setBorder(BorderFactory.createTitledBorder("���� "));
    direction.add(up);
    direction.add(down);
    direction.setPreferredSize(new Dimension(170, 60));
    JPanel replacePanel = new JPanel();
    GridLayout gridlayout=new GridLayout(2, 1); 
    gridlayout.setHgap(4);
    gridlayout.setVgap(14);
    replacePanel.setLayout( gridlayout);
    replacePanel.add(replace);
    replacePanel.add(replaceAll);

    topPanel.add(searchContentLabel);
    topPanel.add(findText);
    topPanel.add(searchNext);
    centerPanel.add(replaceContentLabel);
    centerPanel.add(replaceText);
    centerPanel.add(replacePanel);
    bottomPanel.add(matchcase);
    bottomPanel.add(direction);
    bottomPanel.add(cancel);
    con.add(topPanel);
    con.add(centerPanel);
    con.add(bottomPanel);
    // ����"�������滻"�Ի���Ĵ�С���ɸ��Ĵ�С(��)��λ�úͿɼ���
    findDialog.setSize(440, 220);
    findDialog.setResizable(false);
    findDialog.setLocation(230, 280);
    findDialog.setVisible(true);
}

public void myfind() {
    final JDialog findDialog = new JDialog(this, "����", true);
    Container con = findDialog.getContentPane();
    con.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel searchContentLabel = new JLabel("��������(N) :"); 
    final JTextField findText = new JTextField(20);
    final JCheckBox matchcase = new JCheckBox("���ִ�Сд");
    ButtonGroup bGroup = new ButtonGroup();
    final JRadioButton up = new JRadioButton("����(U)");
    final JRadioButton down = new JRadioButton("����(D)");
    down.setSelected(true);
    bGroup.add(up);
    bGroup.add(down);
    JButton searchNext = new JButton("������һ��(F)");
    searchNext.setPreferredSize(new Dimension(110, 22));
    // "������һ��"��ť�¼�����
    searchNext.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            int a = 0, b = 0;
            int FindStartPos = textArea.getCaretPosition();
            String str1, str2, str3, str4, strA, strB;
            str1 = textArea.getText();
            str2 = str1.toLowerCase();
            str3 = findText.getText();
            str4 = str3.toLowerCase();
            // "���ִ�Сд"��CheckBox��ѡ��
            if (matchcase.isSelected()) {
                strA = str1;
                strB = str3;
            } else {
                strA = str2;
                strB = str4;
            }

            if (up.isSelected()) {
                if (textArea.getSelectedText() == null) {
                    a = strA.lastIndexOf(strB, FindStartPos - 1);
                } else {
                    a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                }
            } else if (down.isSelected()) {
                if (textArea.getSelectedText() == null) {
                    a = strA.indexOf(strB, FindStartPos);
                } else {
                    a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                }

            }
            if (a > -1) {
                if (up.isSelected()) {
                    textArea.setCaretPosition(a);
                    b = findText.getText().length();
                    textArea.select(a, a + b);
                } else if (down.isSelected()) {
                    textArea.setCaretPosition(a);
                    b = findText.getText().length();
                    textArea.select(a, a + b);
                }
            } else {
                JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ�����!", "���±�", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    });/* "������һ��"��ť�¼�������� */
    // "ȡ��"��ť���¼�����
    JButton cancel = new JButton("ȡ��");
    cancel.setPreferredSize(new Dimension(110, 22));
    cancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            findDialog.dispose();
        }
    });
    // ����"�������滻"�Ի���Ľ���
    JPanel bottomPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel topPanel = new JPanel();

    JPanel direction = new JPanel();
    direction.setBorder(BorderFactory.createTitledBorder("���� "));
    direction.add(up);
    direction.add(down);
    direction.setPreferredSize(new Dimension(170, 60));
    topPanel.add(searchContentLabel);
    topPanel.add(findText);
    topPanel.add(searchNext);
    bottomPanel.add(matchcase);
    bottomPanel.add(direction);
    bottomPanel.add(cancel);
    con.add(topPanel);
    con.add(centerPanel);
    con.add(bottomPanel);
    // ����"�������滻"�Ի���Ĵ�С���ɸ��Ĵ�С(��)��λ�úͿɼ���
    findDialog.setSize(450, 180);
    findDialog.setResizable(false);
    findDialog.setLocation(230, 280);
    findDialog.setVisible(true);
}
}
