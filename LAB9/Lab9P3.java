import java.awt.*;
import java.awt.event.*;
import javax. swing.*;
class Lab9P3 extends JFrame{
		Lab9P3(){

			JFrame Jf = new JFrame();
			JTree jt = new JTree(root);
			DeafaultMutableTreeNode root = new  DeafaultMutableTreeNode("Fruits");
			root.add(new DeafultMutableTreeNode("Apple"));
			root.add(new DeafultMutableTreeNode("Mango"));
			root.add(new DeafultMutableTreeNode("pine apple"));
			root.add(new DeafultMutableTreeNode("kiwi"));
			
			JTree jt = new JTree(root);
			add(jt);
			
			setVisible(true);
			setSize(500, 500);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		public static void main(String...args){
			Lab9P3();
		}	
}
