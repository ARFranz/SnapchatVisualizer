public class snapInfo
{
    private String name;
    private int date, snapCount, r, g, b;
    public snapInfo(String n, int d, int red, int green, int blue)
    {
        name = n;
        date = d;
        snapCount = 0;
        r = red;
        g = green;
        b = blue;
    } 
    public snapInfo(String n)
    {
        name = n;
    } 
    public String getName()
    {
        return name;
    }
    public int getDate()
    {
        return date; 
    }
    public void snapCount()
    {
        snapCount++;
    }
    public void resetCount()
    {
        snapCount = 0;
    }
    public int getSnapCount()
    {
        return snapCount;
    }
    public int getR()
    {
        return r; 
    }
    public int getG()
    {
        return g; 
    }
    public int getB()
    {
        return b; 
    }
}