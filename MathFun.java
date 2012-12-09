import java.util.*;


public class MathFun
{
    Combinator com;
    MathFun f1, f2;
    public String fun;

    double constant;
    boolean basic;

    public MathFun(String fun)
    {
        this.fun = fun;
        basic = true;
    }

    public MathFun(double num)
    {
        //interface to constants
        this.fun = "const";
        constant = num;
        basic = true;
    }

    public static MathFun constant(double num)
    {
        MathFun f = new MathFun(num);
        return f;
    }

    public static MathFun basic(String fun)
    {
        MathFun f = new MathFun(fun);
        return f;
    }

    public MathFun(MathFun f1, MathFun f2, Combinator com)
    {
        //create a combined MathFun
        this.f1 = f1;
        this.f2 = f2;
        this.com = com;
        basic = false;
    }

    public boolean isBasic()
    {
        return basic;
    }

    public boolean isIdentity()
    {
        if(this.isBasic() && this.fun == "x")
            return true;
        else
            return false;
    }


    public double apply(double x)
    {
        if(basic) {
            //basic MathFuns
            //there are quite a bunch...
            //I'll admit that I don't like doing it this way
            //there must be a BETTER way....
            if (fun.equals("x")) //basic identity
                return x;

            //trig
            else if(fun.equals("sin"))
                return Math.sin(x);
            else if(fun.equals("cos"))
                return Math.cos(x);
            else if(fun.equals("tan"))
                return Math.tan(x);
            else if(fun.equals("csc"))
                return 1.0/Math.sin(x);
            else if(fun.equals("sec"))
                return 1.0/Math.cos(x);
            else if(fun.equals("cot"))
                return 1.0/Math.tan(x);
            else if(fun.equals("acos"))
                return Math.acos(x);
            else if(fun.equals("asin"))
                return Math.asin(x);
            else if(fun.equals("atan"))
                return Math.atan(x);

            //logs and exponents
            else if(fun.equals("lg2"))
                return Math.log10(x)/Math.log10(2.0);
            else if(fun.equals("log"))
                return Math.log10(x);
            else if(fun.equals("ln"))
                return Math.log(x);
            else if(fun.equals("exp"))
                return Math.exp(x);

            //programming related (in my mind at least)

            //rand generates a random # between 0 and 1,
            //regardless of argument
            else if(fun.equals("rand"))
                return Math.random();
            else if(fun.equals("floor"))
                return Math.floor(x);
            else if(fun.equals("ceil"))
                return Math.ceil(x);
            else if(fun.equals("round"))
                return Math.round(x);

            //misc
            else if(fun.equals("abs"))
                return Math.abs(x);
            else if(fun.equals("square"))
                return Math.pow(x, 2); //square
            else if(fun.equals("sqrt"))
                return Math.sqrt(x);
            else if(fun.equals("const"))
                return constant; //constants
        }
        else
	    {
		return com.apply(f1, f2, x);
	    }

        return 1.2345; //execution should not reach this point

    }

    public MathFun combine(MathFun f, Combinator com)
    {
        //general function for combining
        MathFun g = new MathFun(this, f, com);
        return g;
    }

    public MathFun combineChar(MathFun f, char com)
    {
        //helps with use of combine
        MathFun g = new MathFun(this, f, new Combinator(com));
        return g;
    }

    //interfaces to combining MathFunS
    public MathFun compose(MathFun f)
    {
        if(f.isIdentity())
            return this;
        else
            return this.combineChar(f, 'o');
    }
    public MathFun add(MathFun f)
    {
        return this.combineChar(f, '+');
    }
    public MathFun sub(MathFun f)
    {
        return this.combineChar(f, '-');
    }
    public MathFun div(MathFun f)
    {
        return this.combineChar(f, '/');
    }
    public MathFun pow(MathFun f)
    {
        return this.combineChar(f, '^');
    }
    public MathFun mod(MathFun f)
    {
        return this.combineChar(f, '%');
    }
    public MathFun mul(MathFun f)
    {
        return this.combineChar(f, '*');
    }

    public String str(String varName)
    {
        if(basic)
	    {
		if(fun.equals("const"))
		    return constant + "";
		else if(fun.equals("x"))
		    return varName;
		else if(fun.equals("square"))
		    return varName + "^2";
		else
		    return fun + "(" + varName + ")";
	    }
        else
	    {
		char op = com.getType();
		String first = (f1.isBasic() ? f1.str(varName) : "(" + f1.str(varName) + ")");
		String second = (f2.isBasic() || op=='o' ? f2.str(varName) : "(" + f2.str(varName) + ")");

		if(op == 'o')
                    {
                        first = first.replaceAll(" " + varName + " ", second);
                        first = first.replaceAll("\\(" + varName + "\\)", "(" + second + ")");
                        return first;
                    }
		else
		    return first + op + second;
	    }
    }

    public String lispStr(String varName)
    {
        if(basic)
	    {
		if(fun.equals("const"))
		    return constant + "";
		else if(fun.equals("x"))
		    return varName;
		else
		    return "(" + fun + " " + varName + ")";
	    }
        else
	    {
		char op = com.getType();
		String first = f1.lispStr(varName);
		String second = f2.lispStr(varName);

		if(op == 'o')
                    {
                        first = first.replaceAll(" " + varName + " ", " " + second + " ");
                        first = first.replaceAll(" " + varName + "\\)", " " + second + ")");
                        return first;
                    }
		else
		    return "(" + op + " " + first + " " + second + ")";
	    }
    }

    public String str()
    {
        return this.str("x");
    }

    public String lispStr()
    {
        return this.lispStr("x");
    }

    public String toString()
    {
        return this.str("x");
    }

    public static boolean doubleEqual(double d1, double d2)
    {
        if(Math.abs(d1-d2)<0.000001)
            return true;
        else
            return false;
    }


    public static void main(String[] args)
    {
        MathFun f = FunParser.parseLispExp("(^ (acos x) 2)");
        System.out.println(f.str("t"));
        System.out.println(f.lispStr("t"));
        System.out.println();
        System.out.println(f.compose(f).str("t"));
        System.out.println(f.compose(f).lispStr("t"));
    }

}
