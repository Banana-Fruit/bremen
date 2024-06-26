package control;


import model.userInterface.Game;
import resources.constants.Constants_ExceptionMessages;


public class ImageController implements Runnable
{
    private static volatile ImageController instance = null;
    private static Game game;
    
    
    private ImageController (Game game)
    {
        this.game = game;
    }
    
    
    public static synchronized void initialize (Game game)
    {
        if (instance == null)
        {
            instance = new ImageController(game);
        } else
        {
            throw new IllegalStateException(Constants_ExceptionMessages.ALREADY_INITIALIZED);
        }
    }
    

    
    
    @Override
    public void run ()
    {
        while (true)
        {
            /*for (Map.Entry<ImageView, Integer> entry : this.game.getCurrentShowable().getImageViewsWithSizePercentage().entrySet())
            {
                try
                {
                    Thread.sleep(Constants_Game.THREAD_SLEEP_DEFAULT_TIME);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
                //OutputImageView.scaleImageViewToSceneSize(entry.getKey(), entry.getValue(), this.game.getCurrentShowable().getScene());
            }*/
        }
    }



    // Method to retrieve the Singleton instance without parameters
    public static ImageController getInstance ()
    {
        if (instance == null)
        {
            throw new IllegalStateException(Constants_ExceptionMessages.SINGLETON_NOT_INITIALIZED);
        }
        return instance;
    }
}
