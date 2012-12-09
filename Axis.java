public class Axis
{
	public double min, max, interval;

	public Axis(double min, double max, double interval) throws ArithmeticException
	{
		if(min>=max)
		{
			throw new ArithmeticException("min of Axis is greater than max!");
		}
		else
		{
			this.min = min;
			this.max = max;
			this.interval = interval;
		}
	}
	
	public Axis() throws ArithmeticException
	{
		this(-10, 10, 1);
	}
	
	public double range()
	{
		return max-min;
	}
	
	public void setAxis(double min, double max, double interval)
	{
		this.min = min;
		this.max = max;
		this.interval = interval;
	}
	
	public int numberPoints()
	{
		return (int) Math.floor((max-min)/interval + 1);
	}
		
}
