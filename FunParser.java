import java.util.*;

public class FunParser 
{
	public static MathFun parseLispExp(String exp, String variable)
	{
		/* parses lisp expressions and gives a MathFun object
		 * 
		 * Examples of expressions:
		 * (+ (square x) 2)
		 * (- 1 (^ (cos x) 2)
		 * (square (+ 1 (log x))) 
		 * (+ t (^ (cos t) 2))
		 *
		 * (this is basically a lisp parser... but only for math expressions)
		 */
		
		if(exp.charAt(0) != '(')
		{
			//basic exp
			//should be either the variable or a double
			if(exp.equals(variable))
				return MathFun.basic("x");
			else if(exp.equals("pi"))
				return MathFun.constant(Math.PI);
			else if(exp.equals("phi"))
				return MathFun.constant((1+Math.sqrt(5))/2.0);
			else
				return MathFun.constant(Double.parseDouble(exp));
		}			
	
		
		String temp;
		String[] blocks = splitBlocks(exp.substring(1,exp.length()-1)); //split by parantheses
	
		/* debugging purposes only
		for (int i=0; i<blocks.length; i++)
			System.out.print(blocks[i] + " ");
		System.out.println("\n");
		*/
		
		if(blocks[0].length() == 1 && (Combinator.getAllCom()).contains(blocks[0]))
		{
			//operator is combinator
			Combinator com = new Combinator(blocks[0].charAt(0));
			MathFun[] funs = new MathFun[blocks.length-1];
			MathFun result = parseLispExp(blocks[1], variable);
			for(int i=2; i<blocks.length; i++)
			{
				result = result.combine(parseLispExp(blocks[i], variable), com);
			}
			
			return result;
		}
		else
		{
			//operator is a function
			MathFun f = MathFun.basic(blocks[0]);
			return f.compose(parseLispExp(blocks[1], variable));
		}
	}
	
	public static MathFun parseLispExp(String exp)
	{
		return parseLispExp(exp, "x"); //default var is x
	}
	
	public static String[] splitBlocks(String x)
	{
		//splits expression such that each block of parentheses is an element of a String array
		ArrayList<String> al = new ArrayList<String>(10);
		int parcount = 0;
		int start = 0;
		
		String exp = x.replaceAll("[ ]+", " ");
		
		for(int i=0; i<exp.length(); i++)
		{
			if(exp.charAt(i) == ' ' && parcount == 0)
			{
				al.add(exp.substring(start, i));
				start = i+1;
				continue;
			}
			else if(exp.charAt(i) == '(')
				parcount++;
			else if(exp.charAt(i) == ')')
				parcount--;
			
		}
		
		if(start < exp.length())
			al.add(exp.substring(start));
		
		String[] out = new String[al.size()];
		al.toArray(out);
		return out;
	}
	
	public static void main(String[] args)
	{
		MathFun f = FunParser.parseLispExp("(+ cake (sqrt cake))", "cake");
		MathFun g = FunParser.parseLispExp("(+ t (sqrt t))", "t");
		System.out.println(f);
		System.out.println(g);
	}

}
