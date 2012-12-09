import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import rl.*; //RiverLayout

public class FunFrame extends GraphFrame
{
    private JTextField textX;
	
    public FunFrame(double minX, double maxX, double intX, MathFun f, String title)
    {
	super(minX, maxX, intX, MathFun.basic("x"), f, title);
		
	equations.setText("<html>" + "f(x)=" + f.str("x") +  "</html>");
	minLabel.setText("Xmin:");
	maxLabel.setText("     " + "Xmax:");
	intLabel.setText("     " + "Xint:");
									 
	textX = new JTextField(f.lispStr("x"), 40);
		
	setLayout(new RiverLayout());
	add("center", equations); //equations on top
	add("p center hfill vfill", pan); //then graph
		
	add("br hfill", textAxis); //t params
		
	//function
	add("br left", new JLabel("f(x)="));
	add("tab hfill", textX);

	add("p center", goGraph); //button
		
		
	//goGraph.addActionListener(new ButtonListener());
	//super.graph();
	equations.revalidate();
	this.validate();
	((JComponent)getContentPane()).revalidate();
	System.out.println("there ya go!");

    }
	
    public FunFrame(double minX, double maxX, double intX, MathFun f)
    {
	this(minX, maxX, intX, f, "Function Grapher");
    }
	
    public FunFrame(MathFun funX)
    {
	this(-10, 10, .1, funX); 
    }
	
    public FunFrame()
    {
	this(MathFun.basic("x"));
    }
	
    public FunFrame(String lispExp)
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
	super.graph(MathFun.basic("x"), f, minT, maxT, intT);
	equations.setText("<html>" + "f(x)=" + f.str("x") +  "</html>");
		
    }
	
    public void graph(MathFun f)
    {
	super.graph(MathFun.basic("x"), f);
	equations.setText("<html>" + "f(x)=" + f.str("x") +  "</html>");
    }
	
    public void graph(String f)
    {
	graph(FunParser.parseLispExp(f));
    }
	
    public static void main(String[] args)
    {
	FunFrame p = new FunFrame("x");
    }
}
