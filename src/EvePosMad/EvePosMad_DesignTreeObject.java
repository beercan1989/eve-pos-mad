package eveposmad;
import java.util.ArrayList;
/**
 * @author James Bacon
 */
public class EvePosMad_DesignTreeObject{
    public String categoryName;
    public long[] categoryIDs;

    EvePosMad_DesignTreeObject(){}
    EvePosMad_DesignTreeObject(String categoryName, long[] categoryIDs)
    {
        this.categoryName = categoryName;
        this.categoryIDs = categoryIDs;
    }

    @Override
    public String toString(){
        return categoryName;
    }
}
