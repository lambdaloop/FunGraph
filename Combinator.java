import java.util.*;


public class Combinator
{
	char type;
	/* type determines what the combinator does:
	* multiply
	+ add
	/ divide
	- subtract
	^ raise to a power
	% modulus
	o compose MathFuns (based on sign for composition)  

	As of 9/13/10 some relative operators were added
	< less than
	> greater than
	= equal to
	
	they return 0 if something is false, 1 if it is true
	e.g. (< 2 3) -> 1
	     (> 2 3) -> 0
	     (= 2 3) -> 0
	     (= 3 3) -> 1
	
	these operators can help make composite functions...
	*/
	
	static String allComs = "*+/-^%<>=";
	
	public Combinator(char type)
	{
		this.type = type;
	}

	public Combinator()
	{
		this('+');
	}

	public double apply(double x, double y)
	{
		double result = 0;
		switch(this.type)
		{
			case '+': result=x+y; break;
			case '-': result=x-y; break;
			case '/': result=x/y; break;
			case '*': result=x*y; break;
			case '^': result=Math.pow(x, y); break;
			case '%': result=x%y; break;
			case '<': result=(x<y ? 1 : 0); break;
			case '>': result=(x>y ? 1 : 0); break;
			case '=': result=(x==y ? 1 : 0); break;
		}
		
		return result;
	}

	public double apply(MathFun f, MathFun g, double num)
	{
		double x = f.apply(num);
		double y = g.apply(num);

		if(type == 'o')
			return f.apply(g.apply(num));
		else
			return this.apply(x,y);
	}
	
	public char getType()
	{
		return type;
	}
	
	public static String getAllCom()
	{
		return allComs;
	}
}
