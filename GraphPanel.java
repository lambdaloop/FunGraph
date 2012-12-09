import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;	
import java.text.*;

public class GraphPanel extends JPanel
{
	Axis tAxis;
	Axis xAxis = new Axis();
	Axis yAxis = new Axis();
	int pointSize = 2;
	MathFun funX;
	MathFun funY;
	int markerDist = 35;
	int heightMarker = 2;
	int charWidth = 5;
	int charHeight = 8;
	
	public GraphPanel(MathFun funX, MathFun funY, Axis t)
	{
		super();
		this.funX = funX;
		this.funY = funY;
		this.tAxis = t;
		setBackground(Color.white);
	}
	
	public void setFun(MathFun fx, MathFun fy)
	{
		funX = fx;
		funY = fy;
	}
	
	public void setTAxis(Axis t)
	{
		tAxis = t;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		graph(g);
		drawAxes(g);
	}
	
	public void drawAxes(Graphics g)
	{
		
		//draw the actual axes
		Point2D.Double xleft, xright, yup, ydown;
		xleft  = new Point2D.Double(xAxis.min, 0);
		xright = new Point2D.Double(xAxis.max, 0);		
		yup    = new Point2D.Double(0, yAxis.max);
		ydown  = new Point2D.Double(0, yAxis.min);
				
		g.setColor(Color.black); //BLACK!
		drawPointLineC(xleft, xright, g); //x-axis
		drawPointLineC(yup, ydown, g); //y-axis
		
		//draw the markers
		int numMarkersX = getWidth() / markerDist;
		int numMarkersY = getHeight() / markerDist;
		double addX = xAxis.range() / numMarkersX;
		double addY = yAxis.range() / numMarkersY;
		
		Point2D.Double current;
		Point converted;
		DecimalFormat formatter = new DecimalFormat("#.##");
		String text;
		g.setFont(new Font("Monospace", Font.PLAIN, 10));
		
		//x markers
		current = xleft;
		for(int i=1; i<numMarkersX; i++)
		{
			current.x += addX;
			
			converted = convertPoint(current);
			g.drawLine(converted.x, converted.y - heightMarker,
					   converted.x, converted.y + heightMarker);
			
			text = formatter.format(current.x);
			int x = (int) Math.round(converted.x-(text.length()/2.0)*charWidth);
			g.drawString(text, x, converted.y-heightMarker-5);
		}
		
		//y markers
		current = ydown;
		for(int i=1; i<numMarkersY; i++)
		{
			current.y += addY;
			
			converted = convertPoint(current);
			g.drawLine(converted.x-heightMarker, converted.y,
					   converted.x+heightMarker, converted.y);
					   
			text = formatter.format(current.y);
			int x = converted.x+ heightMarker+1;
			int y = converted.y + charHeight/2;
			g.drawString(text, x, y);
		}
		
	}
	
	
	public void graph(Graphics g)
	{
		yAxis.min = xAxis.min = Double.POSITIVE_INFINITY;
		yAxis.max = xAxis.max = Double.NEGATIVE_INFINITY;
		LinkedList<Point2D.Double> ll = new LinkedList<Point2D.Double>();
		
		double x,y, goodT;
		for(double t=tAxis.min; t<=tAxis.max; t+=tAxis.interval)
		{
			//get point
			goodT = t - (t % tAxis.interval);
			x = funX.apply(goodT);
			y = funY.apply(goodT);
			
			if(isValidDouble(x) && isValidDouble(y))
			{
				ll.add(new Point2D.Double(x, y));
				
				//set window parameters
				xAxis.min = Math.min(x, xAxis.min);
				xAxis.max = Math.max(x, xAxis.max);
				yAxis.min = Math.min(y, yAxis.min);
				yAxis.max = Math.max(y, yAxis.max);
			}
			else
			{
				//make sure we dont connect the points later if this point is something weird
				ll.add(null);
			}
		}
		
		g.setColor(Color.black);
		
		int len = ll.size();
		Point previous = convertPoint(ll.getFirst());
		Point current;
		for(int i=0; i<len; i++)
		{
			current = convertPoint(ll.pop());
			drawPointLine(previous, current, g);
			previous = current;
		}
	}
	
	public boolean isValidDouble(double num)
	{
		if(Double.isNaN(num) || Double.isInfinite(num))
			return false;
		else
			return true;
	}
	
	public void plotPoint(Point2D.Double p, Graphics g)
	{
		Point pl = convertPoint(p);
		g.fillRect(pl.x-(pointSize/2), pl.y-(pointSize/2), pointSize, pointSize);
		System.out.println(p);
	}
	
	public Point convertPoint(Point2D.Double p)
	{
		if(p == null)
			return null;
		
		int x = (int) ((p.x - xAxis.min) * getWidth()/xAxis.range());
		int y = (int) ((p.y - yAxis.max) * getHeight()/yAxis.range());
		y *= -1;
		
		return new Point(x, y);
	}
	
	public void drawPointLine(Point a, Point b, Graphics g)
	{
		if(a != null && b != null)
			g.drawLine(a.x, a.y, b.x, b.y);
		
	}
	
	public void drawPointLineC(Point2D.Double a, Point2D.Double b, Graphics g)
	{
		drawPointLine(convertPoint(a), convertPoint(b), g);
	}

}
