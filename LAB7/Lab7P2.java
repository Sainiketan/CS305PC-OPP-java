import java.awt.event.*;
import java.awt.*;
class Lab7P2{
	public static void main(String...args){
		final int []count={1};
		Frame f = new Frame();
		TextField tf = new TextField(20);
		Button b = new Button();
		b.setLabel("click");
		b.setSize(10,30);
		Label l = new Label();
		l.setPreferredSize(new Dimension(200,200));
		
		f.add(b);
		f.add(tf);
		f.add(l);
		f.setSize(400,400);
		f.setVisible(true);
		FlowLayout fl = new FlowLayout();
		f.setLayout(fl);
		b.addActionListener(
		     new ActionListener(){
		     		public void actionPerformed(ActionEvent ae){
		     		System.out.println("HELLO");
		     		//tf.setText("HELLO");
		     		l.setText(tf.getText()+"-"+count[0]);
		     		count[0]++;
		     		}
		     
		     }
		
		);
		f.addWindowListener(
			new WindowAdapter(){
				public void windowClosing(WindowEvent we){
				f.dispose();
				}
	
			}
		);    



       }
}
