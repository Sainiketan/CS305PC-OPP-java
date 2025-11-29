import java.awt.event.*;
import java.awt.*;
class Lab7P3 implements ActionListener{
		Frame f = new Frame();
		TextField tf = new TextField(20);
		Button b = new Button();
		Label l = new Label();
		int count = 1;
	Lab7P2(){
	
		final int []count={1};
		
		b.setLabel("click");
		b.setSize(10,30);
		
		l.setPreferredSize(new Dimension(200,200));
		
		f.add(b);
		f.add(tf);
		f.add(l);
		f.setSize(400,400);
		f.setVisible(true);
		FlowLayout fl = new FlowLayout();
		f.setLayout(fl);
		b.addActionListener(this);
		f.addWindowListener(
		new WindowAdapter(){
				public void windowClosing(WindowEvent we){
				f.dispose();
				}
	
			}
		);    



       }
     
		     		public void actionPerformed(ActionEvent ae){
		     		System.out.println("HELLO");
		     		//tf.setText("HELLO");
		     		l.setText(tf.getText()+"-"+count);
		     		count++;
		     		}
		     
	
	public static void main(String...args){
		Lab7P2 L1 = new Lab7P2();
		
	}
	
}	               
