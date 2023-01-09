package twisk.outils;

import javafx.concurrent.Task;
import java.util.ArrayList;

public class GestionnaireThreads {
    private ArrayList<Thread> listThreads;
    private static GestionnaireThreads gt = new GestionnaireThreads();

    private GestionnaireThreads()
    {
        listThreads = new ArrayList<>();
    }

    public static GestionnaireThreads getInstance() {
        return gt;
    }

    public void lancer(Task task)
    {
        Thread t = new Thread(task);
        listThreads.add(t);
        t.start();
    }

    public void detruireTout()
    {
        for (Thread t : listThreads){
            t.interrupt();
        }
    }
}
