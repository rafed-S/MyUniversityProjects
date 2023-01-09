package twisk.vues;

import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class VueClient extends Circle implements Observateur {
    static int b = 1;

    public VueClient() {
        super();
        this.setRadius(8);
        Random rand = new Random();
        int nb = rand.nextInt(4);
        if(b%2 == 0)
        {
            Color p = Color.GOLD;
            this.setFill(p);
        }
        else
        {
            if(b%3 == 0)
            {
                Color p = Color.RED;
                this.setFill(p);
            }
            else
            {
                Color p = Color.GREEN;
                this.setFill(p);
            }
        }
        b++;
    }
    @Override
    public void reagir() {}
}
