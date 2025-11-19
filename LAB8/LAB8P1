import java.awt.*;
import java.awt.event.*;

class Lab8P1 extends Frame implements ActionListener{
	Label l;
	Lab8P1(){
	super("MENUBAR");
	FlowLayout fl = new FlowLayout();
	setLayout(fl);
	l=new Label();
	
	l.setPreferredSize(new Dimension(300,400));
	l.setFont(new Font("Arial",Font.BOLD,30));
	add(l);
	
	MenuBar mb = new MenuBar();
	setMenuBar(mb);
	String[] menu = {"File","Edit","Help"};
	String[][] mi={
		{"open","save","close"},
		{"cut","copy","paste"},
		{"about","update","licensse"},
	
	};
	int x =0,y=0;
	for(String s :menu){
		y=0;
		Menu m = new Menu(s);
	for(int i=0;i<3;i++){
		MenuItem mmi = new MenuItem(mi[x][y]);
		mmi.addActionListener(this);
		
		m.add(mmi);
		y++;
	}
	x++;
	mb.add(m);
	}
	
	setSize(400,400);
	setVisible(true);
	
	/*Menu m = new Menu("File");
	Menu m1 = new Menu("EDIT");
	Menu m2 = new Menu("HELP");
	mb.add(m);
	mb.add(m1);
	mb.add(m2);*/
	
	
	addWindowListener(
		new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				dispose();
				}
			}
	);
	
}
public void actionPerformed(ActionEvent ae){
	String mstr = ae.getActionCommand();
	if(mstr.equals("Close")){
	 System.exit(0);
	 }else{
	 l.setText(mstr);
	 }
	 }
	
	public static void main(String...args){	
		new Lab8P1();
	}
}
