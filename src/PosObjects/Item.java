package PosObjects;

/**
 * @author (James Bacon)
 * @version (Created 08/07/2008)
 */
public abstract class Item
{
    private int     mass;
    private double  volume, basePrice, currentPrice;
    private String  type, description;
    
    Item(int mass, double volume, String type, String description, double basePrice)
    {
        this.mass = mass;
        this.volume = volume;
        this.currentPrice = 0 ;
        this.description = description;
        this.basePrice = basePrice;
        this.type = type;
    }
    
    public int    getMass()         {return mass;           }
    public double getVolume()       {return volume;         }
    public String getType()         {return type;           }
    public double getBaseprice()    {return basePrice;      }
    public double getCurrentPrice() {return currentPrice;   }
    public String getDescription()  {return description;    }
}