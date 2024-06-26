package model.panel;


import model.userInterface.showables.Map;
import resources.constants.Constants_DefaultValues;
import resources.constants.Constants_Panel;
import view.OutputImageView;


/**
 * Panel consists of Tiles
 */
public class Panel
{
    private final Tile[][] originArray;
    private final Tile[][] tileArray;
    private final int tileSize;
    private final int maxRows;
    private final int maxColumns;
    
    
    public Panel (Tile[][] tileArray, int tileSize, int maxRows, int maxColumns)
    {
        this.originArray = tileArray;
        this.tileSize = tileSize;
        this.tileArray = new Tile[maxRows][maxColumns];
        this.maxRows = maxRows;
        this.maxColumns = maxColumns;
        init();
    }
    
    
    private void init ()
    {
        setTileArrayToRestrictedOriginArray();
    }
    
    
    private void setTileArrayToRestrictedOriginArray ()
    {
        for (int row = Constants_Panel.MIN_TILE_INDEX; row < maxRows; row++)
        {
            for (int column = Constants_Panel.MIN_TILE_INDEX; column < maxColumns; column++)
            {
                if (row < originArray.length && column < originArray[row].length)
                {
                    tileArray[row][column] = originArray[row][column];
                } else
                {
                    tileArray[row][column] = null; // Handle out-of-bounds by setting to null or another default value
                }
            }
        }
    }
    
    
    public Tile getTileAt (int row, int column)
    {
        return tileArray[row][column];
    }
    
    
    public int getMaxRows ()
    {
        return tileArray.length - Constants_DefaultValues.LENGTH_TO_SIZE_SUBTRACTOR;
    }
    
    
    public int getMaxColumns ()
    {
        return tileArray[Constants_Panel.INDEX_WITH_MAX_VALUE].length - Constants_DefaultValues.LENGTH_TO_SIZE_SUBTRACTOR;
    }
    
    
    public Tile[][] getTileArray ()
    {
        return tileArray;
    }
    
    
    public int getTileSize ()
    {
        return tileSize;
    }
}
