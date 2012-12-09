
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import rl.*;

public class ParamFrame extends GraphFrame
{
	
	private JTextField textX, textY;

	
	public ParamFrame(double minT, double maxT, double intT, MathFun funX, MathFun funY, String title) throws ArithmeticException
	{
		super(minT, maxT, intT, funX, funY, title);	
		
		equations.setText("<html>" + "X(t)=" + funX.str("t") + "<p>" + 
									 "Y(t)=" + funY.str("t") + "</html>");
									 
		textX = new JTextField(funX.lispStr("t"), 40);
		textY = new JTextField(funY.lispStr("t"), 40);
		
		setLayout(new RiverLayout());
		add("center", equations); //equations is part of GraphFrame
		add("p center hfill vfill", pan);
		
		add("br hfill", textAxis); //textAxis also part of GraphFrame
		add("br left", new JLabel("X(t)="));
		add("tab hfill", textX);
		add("br", new JLabel("Y(t)="));
		add("tab hfill", textY);
		add("p center", goGraph);
		
		
		goGraph.addActionListener(new ButtonListener());
		
		repaint();
	}
	
	public ParamFrame(double minT, double maxT, double intT, MathFun funX, MathFun funY) throws ArithmeticException
	{
		this(minT, maxT, intT, funX, funY, "Parametric Grapher");
	}

	
	public ParamFrame(MathFun funX, MathFun funY) throws ArithmeticException
	{
		this(-10, 10, .1, funX, funY);
	}
	
	public ParamFrame(String x, String y) throws ArithmeticException
	{
		this(FunParser.parseLispExp(x), FunParser.parseLispExp(y));
	}
	
	public ParamFrame() throws ArithmeticException
	{
		this(MathFun.basic("x"), MathFun.basic("x"));
	}	
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				if(e.getSource() == goGraph)
				{
					graph(FunParser.parseLispExp(textX.getText(), "t"), 
						  FunParser.parseLispExp(textY.getText(), "t"),
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
	
	
	public void graph(MathFun f, MathFun g, double minT, double maxT, double intT) throws ArithmeticException
	{
		super.graph(f, g, minT, maxT, intT);
		equations.setText("<html>" + "X(t)=" + f.str("t") + "<p>" + 
									 "Y(t)=" + g.str("t") + "</html>");
		
	}
	
	public void graph(MathFun f, MathFun g)
	{
		super.graph(f, g);
		equations.setText("<html>" + "X(t)=" + f.str("t") + "<p>" + 
									 "Y(t)=" + g.str("t") + "</html>");
	}
	
	public void graph(String f, String g)
	{
		graph(FunParser.parseLispExp(f), FunParser.parseLispExp(g));
	}
	
	public static void main (String args[]) 
	{
		//you can use this to graph parametric functions
		MathFun paramX = FunParser.parseLispExp("(square t)", "t");
		MathFun paramY = FunParser.parseLispExp("(log t)", "t");
		ParamFrame frameParam = new ParamFrame(paramX, paramY);
		
		/*
		//polar functions can be rewritten as param. functions so
		//you can graph these too...
		MathFun polar = FunParser.parseLispExp("1");
		ParamFrame framePolar = new ParamFrame(0, Math.PI*2, .01,
						polar.mul(MathFun.basic("cos")),
						polar.mul(MathFun.basic("sin")));
						
		//normal functions are param. functions with
		//fx(x) = x and fy(x) = f(x)
		MathFun fun = FunParser.parseLispExp("(+ (abs x) 2)");
		ParamFrame frameFun = new ParamFrame(MathFun.basic("x"), fun); */
						
	}
}
