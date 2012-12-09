import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import rl.*; //RiverLayout

public class PolarFrame extends GraphFrame
{
	private JTextField textX;
	
	public PolarFrame(double minX, double maxX, double intX, MathFun f, String title)
	{
		super(minX, maxX, intX, f.mul(MathFun.basic("cos")), f.mul(MathFun.basic("sin")), title);
		
		equations.setText("<html>" + "r(x)=" + f.str("x") +  "</html>");
		minLabel.setText("Xmin:");
		maxLabel.setText("     " + "Xmax:");
		intLabel.setText("     " + "Xint:");
									 
		textX = new JTextField(f.lispStr("x"), 40);
		
		setLayout(new RiverLayout());
		add("center", equations); //equations on top
		add("p center hfill vfill", pan); //then graph
		
		add("br hfill", textAxis); //t params
		
		//function
		add("br left", new JLabel("r(x)="));
		add("tab hfill", textX);

		add("p center", goGraph); //button
		
		
		goGraph.addActionListener(new ButtonListener());
		
		repaint();

	}
	
	public PolarFrame(double minX, double maxX, double intX, MathFun f)
	{
		this(minX, maxX, intX, f, "Polar Grapher");
	}
	
	public PolarFrame(MathFun funX)
	{
		this(0, 6.3, .1, funX); 
	}
	
	public PolarFrame()
	{
		this(MathFun.constant(1));
	}
	
	public PolarFrame(String lispExp)
	{
		this(FunParser.parseLispExp(lispExp));
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				if(e.getSource() == goGraph)
				{
					graph(FunParser.parseLispExp(textX.getText(), "x"), 
						  Double.parseDouble(minText.getText()),
						  Double.parseDouble(maxText.getText()),
						  Double.parseDouble(intText.getText()));
				}
			}
			catch(Exception ep)
			{
				JOptionPane.showMessageDialog(null, "Incorrect Input!\n" +
													"Make sure to write the functions in lisp-style code,\n" + 
													"and that the intervals are set correctly!",
													"You did something wrong!",
													JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void graph(MathFun f, double minT, double maxT, double intT) throws ArithmeticException
	{
		super.graph(f.mul(MathFun.basic("cos")), 
					f.mul(MathFun.basic("sin")), 
					minT, maxT, intT);
		equations.setText("<html>" + "r(x)=" + f.str("x") +  "</html>");
		
	}
	
	public void graph(MathFun f)
	{
		super.graph(f.mul(MathFun.basic("cos")), 
					f.mul(MathFun.basic("sin")));
		equations.setText("<html>" + "r(x)=" + f.str("x") +  "</html>");
	}
	
	public void graph(String f)
	{
		graph(FunParser.parseLispExp(f));
	}
	
	public static void main(String[] args)
	{
		PolarFrame p = new PolarFrame("1");
	}
}
