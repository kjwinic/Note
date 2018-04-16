
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
  PrintJob  p=null;//声明一个要打印的对象
  Graphics  g=null;//要打印的对象
  //撤销管理器
  public UndoManager undoMgr = new UndoManager(); 
  
  //剪贴板
  public Clipboard clipboard = new Clipboard("系统剪切板"); 
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
	  file_menu=new JMenu("文件(F)");
	  menubar.add(file_menu);
	  setJMenuBar(menubar);
	  itemNew =new JMenuItem("新建(N)");
	  itemOpen=new JMenuItem("打开(O)");
	  itemSave= new JMenuItem("保存(S)");
	  itemSaveAs= new JMenuItem("另保存(A)...");
	  itemPage=new JMenuItem("页面设置(U)...");
	  itemPrint=new JMenuItem("打印(P)...");
	  itemExit=new JMenuItem("退出(X)");
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
	  edite_menu=new JMenu("编辑(E)");
	  menubar.add(edite_menu);
	  itemUndo=new JMenuItem("撤销(U)");
	  itemUndo.setEnabled(false);
	  itemCut=new JMenuItem("剪切(T)");
	  itemCut.setEnabled(false);
	  itemCopy=new JMenuItem("复制(C)");
	  itemCopy.setEnabled(false);
	  itemPaste=new JMenuItem("粘贴(P)");
	  itemDelete=new JMenuItem("删除(L)");
	  itemDelete.setEnabled(false);
	  itemfind=new JMenuItem("\u67E5\u627E(F)...");
	  itemfind.setEnabled(false);
	  itemfind_next=new JMenuItem("查找下一个(N)");
	  itemfind_next.setEnabled(false);
	  itemReplace=new JMenuItem("替换(R)...");
	  itemReplace.setEnabled(false);
	  itemGo_to=new JMenuItem("转到(G)...");
	  item_A=new JMenuItem("全选(A)");
	  itemDate=new JMenuItem("日期(D)");
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
	  format_menu=new JMenu("格式(O)");
	  v_menu=new JMenu("查看(V)");
	  help_menu=new JMenu("帮助(H)");
	  menubar.add(format_menu);
	  menubar.add(v_menu);
	  menubar.add(help_menu);
	  itemLine =new JMenuItem("自动换行(W)");
	  itemFont=new JMenuItem("字体(F)");
	  itemLine.addActionListener(this);
	  itemFont.addActionListener(this);
	  format_menu.add(itemLine);
	  format_menu.add(itemFont);
	  itemstate=new   JCheckBoxMenuItem("状态栏(S)");
	  itemstate.addActionListener(this);
	  v_menu.add(itemstate);
	  itemHelp=new JMenuItem("查看帮助(H)");
	  itemAbout=new JMenuItem("关于记事本(A)");
	  help_menu.add(itemHelp);
	  help_menu.addSeparator();
	  help_menu.add(itemAbout);
	   textArea = new JTextArea();
	  scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      panel = new JPanel();
      panel.setBorder(new EmptyBorder(1, 1, 1, 1));
      //设置边框布局
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
              //在这里我进行了对使用快捷键，但是没有输入字符却没有改变textArea中内容的判断
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
                  //这是弹出小窗口
                  //1、（刚启动记事本为0，刚新建文档为1）条件下修改后
                  int result=JOptionPane.showConfirmDialog(Note.this, "是否将更改保存到无标题?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                  if(result==JOptionPane.OK_OPTION){
                     Note.this.saveAs();
                  }else if(result==JOptionPane.NO_OPTION){
                      Note.this.dispose();
                      Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                  }
              }else if(flag==2 && currentPath!=null){
                  //这是弹出小窗口
                  //1、（刚启动记事本为0，刚新建文档为1）条件下修改后
                  int result=JOptionPane.showConfirmDialog(Note.this, "是否将更改保存到"+currentPath+"?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                  if(result==JOptionPane.OK_OPTION){
                      Note.this.save();
                  }else if(result==JOptionPane.NO_OPTION){
                     Note.this.dispose();
                      Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                  }
              }else{
                  //这是弹出小窗口
                  int result=JOptionPane.showConfirmDialog(Note.this, "确定关闭？", "系统提示", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
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
	if(e.getSource()==itemOpen){            //打开
        openFile();
    }else if(e.getSource()==itemSave){        //保存
        //如果该文件是打开的，就可以直接保存
        save();
    }else if(e.getSource()==itemSaveAs)
    {    //另存为
        saveAs();
    }else if(e.getSource()==itemNew){        //新建
        newFile();
    }else if(e.getSource()==itemExit){        //退出
        exit();
    }else if(e.getSource()==itemPage){        //页面设置
        ///页面设置，百度到的，不知道具体的用法
        PageFormat pf = new PageFormat();
        PrinterJob.getPrinterJob().pageDialog(pf); 
    }else if(e.getSource()==itemPrint){        //打印
        //打印机
        Print();
    }else if(e.getSource()==itemUndo ){        //撤销
        if(undoMgr.canUndo()){
            undoMgr.undo();
        }
    }else if(e.getSource()==itemUndo ){        //恢复
        if(undoMgr.canRedo()){
            undoMgr.redo();
        }
    }else if(e.getSource()==itemCut ){        //剪切
        cut();
    }else if(e.getSource()==itemCopy ){        //复制
        copy();
    }else if(e.getSource()==itemPaste ){    //粘贴
        paste();
    }else if(e.getSource()==itemDelete ){    //删除
        String tem=textArea.getText().toString();
        textArea.setText(tem.substring(0,textArea.getSelectionStart())); 
    }else if(e.getSource()==itemfind){        //查找
        myfind();
    }else if(e.getSource()==itemfind_next){    //查找下一个
        myfind();
    }else if(e.getSource()==itemReplace){    //替换
        mySearch();
    }else if(e.getSource()==itemGo_to){    //转到
        turnTo();
    }else if(e.getSource()==item_A){    //选择全部
        textArea.selectAll();
    }else if(e.getSource()==itemDate){       
    	//时间/日期
    	  GregorianCalendar time = new GregorianCalendar();  
          int hour = time.get(Calendar.HOUR_OF_DAY);  
          int min = time.get(Calendar.MINUTE);        
		int second = time.get(Calendar.SECOND);  
		String str=hour+":"+min+":"+second+" "+time.get(Calendar.YEAR)+"/"+(time.get(Calendar.MONTH)+1)+"/"+time.get(Calendar.DAY_OF_MONTH);
			 if(this.textArea.getSelectedText()!=null)
	            {
	                //定位被选中字符的开始位置
	                int start = this.textArea.getSelectionStart();
	                //定位被选中字符的末尾位置
	                int end = this.textArea.getSelectionEnd();
	                //把粘贴的内容替换成被选中的内容
	                this.textArea.replaceRange(str, start, end);
	            }
	            else
	            {
	                //获取鼠标所在TextArea的位置
	                int mouse = this.textArea.getCaretPosition();
	                //在鼠标所在的位置粘贴内容
	                this.textArea.insert(str, mouse);
	            }
		flag=2;
    }else if(e.getSource()==itemLine){    //设置自动换行
        //设置文本区的换行策略。如果设置为 true，则当行的长度大于所分配的宽度时，将换行。此属性默认为 false。 
        if(itemLine.isSelected()){
            textArea.setLineWrap(true);
        }else{
            textArea.setLineWrap(false);
        }
    }else if(e.getSource()==itemFont){        //设置字体大小
        // 构造字体选择器，参数字体为预设值
        MQFontChooser fontChooser = new MQFontChooser(textArea.getFont());
        fontChooser.showFontDialog(this);
        Font font = fontChooser.getSelectFont();
        textArea.setFont(font);
    
    }else if(e.getSource()==itemstate){    //设置状态
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
        JOptionPane.showMessageDialog(this, "记事本","软件说明 ",1);
    }
}
private void turnTo() {
    final JDialog gotoDialog = new JDialog(this, "转到下列行");
    JLabel gotoLabel = new JLabel("行数(L):");
    final JTextField linenum = new JTextField(5);
    linenum.setText("1");
    linenum.selectAll();

    JButton okButton = new JButton("确定");
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
                JOptionPane.showMessageDialog(null, "请输入行数!", "提示", JOptionPane.WARNING_MESSAGE);
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

    JButton cancelButton = new JButton("取消");
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
 * 推出按钮，和窗口的红叉实现一样的功能
 */
private void exit() {
    if(flag==2 && currentPath==null){
        //这是弹出小窗口
        //1、（刚启动记事本为0，刚新建文档为1）条件下修改后
        int result=JOptionPane.showConfirmDialog(Note.this, "是否将更改保存到无标题?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            Note.this.saveAs();
        }else if(result==JOptionPane.NO_OPTION){
            Note.this.dispose();
            Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }else if(flag==2 && currentPath!=null){
        //这是弹出小窗口
        //1、（刚启动记事本为0，刚新建文档为1）条件下修改后
        int result=JOptionPane.showConfirmDialog(Note.this, "是否将更改保存到"+currentPath+"?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            Note.this.save();
        }else if(result==JOptionPane.NO_OPTION){
            Note.this.dispose();
            Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }else{
        //这是弹出小窗口
        int result=JOptionPane.showConfirmDialog(Note.this, "确定关闭？", "系统提示", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            Note.this.dispose();
            Note.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
}
/*===================================================================*/


/*===============================4====================================*/
/**
 * 新建文件，只有改过的和保存过的需要处理
 */
private void newFile() {
    if(flag==0 || flag==1){        //刚启动记事本为0，刚新建文档为1
        return;
    }else if(flag==2 && this.currentPath==null)
    {        //修改后
        //1、（刚启动记事本为0，刚新建文档为1）条件下修改后
        int result=JOptionPane.showConfirmDialog(Note.this, "是否将更改保存到无标题?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            this.saveAs();        //另存为                
        }else if(result==JOptionPane.NO_OPTION){
            this.textArea.setText("");
            this.setTitle("无标题");
            flag=1;
        }
        return;
    }else if(flag==2 && this.currentPath!=null )
    {
        //2、（保存的文件为3）条件下修改后
        int result=JOptionPane.showConfirmDialog(Note.this, "是否将更改保存到"+this.currentPath+"?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            this.save();        //直接保存，有路径
        }else if(result==JOptionPane.NO_OPTION){
            this.textArea.setText("");
            this.setTitle("无标题");
            flag=1;
        }
    }
    else if(flag==3)
    {        //保存的文件
        this.textArea.setText("");
        flag=1;
        this.setTitle("无标题");
    }
}
/*===================================================================*/


/*===============================5====================================*/
/**
 * 另存为
 */
private void saveAs() {
    //打开保存框
    JFileChooser choose=new JFileChooser();
    //选择文件
    int result=choose.showSaveDialog(this);
    if(result==JFileChooser.APPROVE_OPTION){
        //取得选择的文件[文件名是自己输入的]
        File file=choose.getSelectedFile();
        FileWriter fw=null;
        //保存
        try {
            fw=new FileWriter(file);
            fw.write(textArea.getText());
            currentFileName=file.getName();
            currentPath=file.getAbsolutePath();
            //如果比较少，需要写
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
 * 保存
 */
private void save() {
    if(this.currentPath==null){
        this.saveAs();
        if(this.currentPath==null){
            return;
        }
    }
    FileWriter fw=null;
    //保存
    try {
        fw=new FileWriter(new  File(currentPath));
        fw.write(textArea.getText());
        //如果比较少，需要写
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
 * 打开文件
 */
private void openFile() {
    if(flag==2 && this.currentPath==null){
        //1、（刚启动记事本为0，刚新建文档为1）条件下修改后
        int result=JOptionPane.showConfirmDialog(Note.this, "是否将更改保存到无标题?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            this.saveAs();
        }
    }else if(flag==2 && this.currentPath!=null){
        //2、（打开的文件2，保存的文件3）条件下修改
        int result=JOptionPane.showConfirmDialog(Note.this, "是否将更改保存到"+this.currentPath+"?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(result==JOptionPane.OK_OPTION){
            this.save();
        }
    }
    //打开文件选择框
    JFileChooser choose=new JFileChooser();
    //选择文件
    int result=choose.showOpenDialog(this);
    if(result==JFileChooser.APPROVE_OPTION){
        //取得选择的文件
        File file=choose.getSelectedFile();
        //打开已存在的文件，提前将文件名存起来
        currentFileName=file.getName();
        //存在文件全路径
        currentPath=file.getAbsolutePath();
        flag=3;
        this.setTitle(this.currentPath);
        BufferedReader br=null;
        try {
            //建立文件流[字符流]
            InputStreamReader isr=new InputStreamReader(new FileInputStream(file),"GBK");
            br=new BufferedReader(isr);//动态绑定
            //读取内容
            StringBuffer sb=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                sb.append(line+System.getProperty("line.separator"));
            }
            //显示在文本框[多框]
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
        p = getToolkit().getPrintJob(this,"ok",null);//创建一个Printfjob 对象 p
        g = p.getGraphics();//p 获取一个用于打印的 Graphics 的对象
        //g.translate(120,200);//改变组建的位置 
        this.textArea.printAll(g);
        p.end();//释放对象 g  
    }
    catch(Exception a){

    } 
}
/*================================================================*/


public void cut(){
    copy();
    //标记开始位置
    int start = this.textArea.getSelectionStart();
    //标记结束位置
    int end = this.textArea.getSelectionEnd();
    //删除所选段
    this.textArea.replaceRange("", start, end);
    
}

public void copy(){
    //拖动选取文本
    String temp = this.textArea.getSelectedText();
    //把获取的内容复制到连续字符器，这个类继承了剪贴板接口
    StringSelection text = new StringSelection(temp);
    //把内容放在剪贴板
    this.clipboard.setContents(text, null);
}

 public void paste(){
     //Transferable接口，把剪贴板的内容转换成数据
     Transferable contents = this.clipboard.getContents(this);
     //DataFalvor类判断是否能把剪贴板的内容转换成所需数据类型
     DataFlavor flavor = DataFlavor.stringFlavor;
     //如果可以转换
     if(contents.isDataFlavorSupported(flavor)){
         String str;
         try {//开始转换
            str=(String)contents.getTransferData(flavor);
            //如果要粘贴时，鼠标已经选中了一些字符
            if(this.textArea.getSelectedText()!=null)
            {
                //定位被选中字符的开始位置
                int start = this.textArea.getSelectionStart();
                //定位被选中字符的末尾位置
                int end = this.textArea.getSelectionEnd();
                //把粘贴的内容替换成被选中的内容
                this.textArea.replaceRange(str, start, end);
            }
            else
            {
                //获取鼠标所在TextArea的位置
                int mouse = this.textArea.getCaretPosition();
                //在鼠标所在的位置粘贴内容
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
    final JDialog findDialog = new JDialog(this, "替换", true);
    Container con = findDialog.getContentPane();
    con.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel searchContentLabel = new JLabel("查找内容(N) :");
    JLabel replaceContentLabel = new JLabel("替换为(P)　 :");
    final JTextField findText = new JTextField(20);
    final JTextField replaceText = new JTextField(20);
    final JCheckBox matchcase = new JCheckBox("区分大小写");
    ButtonGroup bGroup = new ButtonGroup();
    final JRadioButton up = new JRadioButton("向上(U)");
    final JRadioButton down = new JRadioButton("向下(D)");
    down.setSelected(true);
    bGroup.add(up);
    bGroup.add(down);
    JButton searchNext = new JButton("查找下一个(F)");
    JButton replace = new JButton("替换(R)");
    final JButton replaceAll = new JButton("全部替换(A)");
    searchNext.setPreferredSize(new Dimension(110, 22));
    replace.setPreferredSize(new Dimension(110, 22));
    replaceAll.setPreferredSize(new Dimension(110, 22));
    // "替换"按钮的事件处理
    replace.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
     
            if (replaceText.getText().length() == 0 && textArea.getSelectedText() != null)
                textArea.replaceSelection("");
            if (replaceText.getText().length() > 0 && textArea.getSelectedText() != null)
                textArea.replaceSelection(replaceText.getText());
        }
    });

    // "替换全部"按钮的事件处理
    replaceAll.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            textArea.setCaretPosition(0); // 将光标放到编辑区开头
            int a = 0, b = 0, replaceCount = 0;

            if (findText.getText().length() == 0) {
                JOptionPane.showMessageDialog(findDialog, "请填写查找内容!", "提示", JOptionPane.WARNING_MESSAGE);
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
                        JOptionPane.showMessageDialog(findDialog, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(findDialog, "成功替换" + replaceCount + "个匹配内容!", "替换成功", JOptionPane.INFORMATION_MESSAGE);
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
    }); /* "替换全部"按钮的事件处理结束 */

    // "查找下一个"按钮事件处理
    searchNext.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            int a = 0, b = 0;
            int FindStartPos = textArea.getCaretPosition();
            String str1, str2, str3, str4, strA, strB;
            str1 = textArea.getText();
            str2 = str1.toLowerCase();
            str3 = findText.getText();
            str4 = str3.toLowerCase();
            // "区分大小写"的CheckBox被选中
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
                JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    });/* "查找下一个"按钮事件处理结束 */
    // "取消"按钮及事件处理
    JButton cancel = new JButton("取消");
    cancel.setPreferredSize(new Dimension(110, 22));
    cancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            findDialog.dispose();
        }
    });

    // 创建"查找与替换"对话框的界面
    JPanel bottomPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel topPanel = new JPanel();

    JPanel direction = new JPanel();
    direction.setBorder(BorderFactory.createTitledBorder("方向 "));
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
    // 设置"查找与替换"对话框的大小、可更改大小(否)、位置和可见性
    findDialog.setSize(440, 220);
    findDialog.setResizable(false);
    findDialog.setLocation(230, 280);
    findDialog.setVisible(true);
}

public void myfind() {
    final JDialog findDialog = new JDialog(this, "查找", true);
    Container con = findDialog.getContentPane();
    con.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel searchContentLabel = new JLabel("查找内容(N) :"); 
    final JTextField findText = new JTextField(20);
    final JCheckBox matchcase = new JCheckBox("区分大小写");
    ButtonGroup bGroup = new ButtonGroup();
    final JRadioButton up = new JRadioButton("向上(U)");
    final JRadioButton down = new JRadioButton("向下(D)");
    down.setSelected(true);
    bGroup.add(up);
    bGroup.add(down);
    JButton searchNext = new JButton("查找下一个(F)");
    searchNext.setPreferredSize(new Dimension(110, 22));
    // "查找下一个"按钮事件处理
    searchNext.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            int a = 0, b = 0;
            int FindStartPos = textArea.getCaretPosition();
            String str1, str2, str3, str4, strA, strB;
            str1 = textArea.getText();
            str2 = str1.toLowerCase();
            str3 = findText.getText();
            str4 = str3.toLowerCase();
            // "区分大小写"的CheckBox被选中
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
                JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    });/* "查找下一个"按钮事件处理结束 */
    // "取消"按钮及事件处理
    JButton cancel = new JButton("取消");
    cancel.setPreferredSize(new Dimension(110, 22));
    cancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            findDialog.dispose();
        }
    });
    // 创建"查找与替换"对话框的界面
    JPanel bottomPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel topPanel = new JPanel();

    JPanel direction = new JPanel();
    direction.setBorder(BorderFactory.createTitledBorder("方向 "));
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
    // 设置"查找与替换"对话框的大小、可更改大小(否)、位置和可见性
    findDialog.setSize(450, 180);
    findDialog.setResizable(false);
    findDialog.setLocation(230, 280);
    findDialog.setVisible(true);
}
}
