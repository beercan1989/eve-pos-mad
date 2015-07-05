package PosObjects;
/**
 * @author James Bacon
 */
public class PosBonus
{
    private double value;
    private String description;

    public PosBonus(String description, double value)
    {
        this.value       = value;
        this.description = description;
    }

    public double getValue()        {return value;                      }
    public String getDescription()  {return description;                }
    public String toString()        {return value + "% " + description; }
}
