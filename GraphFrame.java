
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public abstract class GraphFrame extends JFrame
{
	
	protected GraphPanel pan;
	protected JButton goGraph;
//	protected JTextField textX, textY;
	protected JLabel equations;
	protected JTextField minText, maxText, intText;
	protected JPanel textAxis;
	protected JLabel minLabel, maxLabel, intLabel;
	//textAxis holds the TextFieldS for the T-axis parameters
	
	public GraphFrame(double minT, double maxT, double intT, MathFun funX, MathFun funY, String title) throws ArithmeticException
	{
		super(title);		
		setSize(600,700);
		
		pan = new GraphPanel(funX, funY, new Axis(minT, maxT, intT));
		goGraph = new JButton("GO! GRAPH!");
		equations = new JLabel();
		equations.setHorizontalAlignment(JLabel.CENTER);
		
		textAxis = new JPanel(new FlowLayout());
		minText = new JTextField(Double.toString(minT), 8);
		maxText = new JTextField(Double.toString(maxT), 8);
		intText = new JTextField(Double.toString(intT), 8);
		
		minLabel = new JLabel("Tmin:");
		maxLabel = new JLabel("    "+"Tmax:");
		intLabel = new JLabel("    "+"Tint:");
				
		textAxis.add(minLabel);
		textAxis.add(minText);
		textAxis.add(maxLabel);
		textAxis.add(maxText);
		textAxis.add(intLabel);
		textAxis.add(intText);
		
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public GraphFrame(double minT, double maxT, double intT, MathFun funX, MathFun funY)
	{
		this(minT, maxT, intT, funX, funY, "Pierre's Java Graphing Tool");
	}

	
	public GraphFrame(MathFun funX, MathFun funY) 
	{
		this(-10, 10, .1, funX, funY);
	}
	
	public GraphFrame(String x, String y) 
	{
		this(FunParser.parseLispExp(x), FunParser.parseLispExp(y));
	}
	
	public GraphFrame() 
	{
		this(MathFun.basic("x"), MathFun.basic("x"));
	}	
	
	public void graph()
	{
		super.paint(this.getGraphics());
		pan.paintComponent(pan.getGraphics());
	}
	
	public void graph(MathFun f, MathFun g, double minT, double maxT, double intT) throws ArithmeticException
	{
		pan.setTAxis(new Axis(minT, maxT, intT));
		pan.setFun(f, g);
		pan.repaint();
	}
	
	public void graph(MathFun f, MathFun g)
	{
		pan.setFun(f, g);
//		equations.setText("<html>" + "X(t)=" + f.str("t") + "<p>" + 
//									 "Y(t)=" + g.str("t") + "</html>");
		pan.repaint();
	}
	
}
