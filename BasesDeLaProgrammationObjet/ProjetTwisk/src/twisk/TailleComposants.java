package twisk;

public class TailleComposants {

    private static final TailleComposants  instance = new TailleComposants ();

    private  final int sizeActiviteHeight= 300;
    private  final int sizeActiviteWidth = 100;
    private  final int sizeGuichetHeight= 400;
    private  final int sizeGuichetWidth = 70;
    private  final int sizeMondeHeight= 2000 ;
    private  final int sizeMondeWidth = 2000;
    private  final int sizeButtonHeight = 30;
    private  final int sizeButtonWidth = 90;

    public int getSizeGuichetHeight() {
        return this.sizeGuichetHeight;
    }
    public int getSizeGuichetWidth() {
        return this.sizeGuichetWidth;
    }
    public  int getSizeButtonHeight() {
        return sizeButtonHeight;
    }
    public  int getSizeButtonWidth() {
        return sizeButtonWidth;
    }
    public  static TailleComposants  getInstance() { return instance; }
    public  int getSizeActiviteHeight() {
        return sizeActiviteHeight;
    }
    public  int getSizeActiviteWidth() {
        return sizeActiviteWidth;
    }
    public  int getSizeMondeHeight() {
        return sizeMondeHeight;
    }
    public  int getSizeMondeWidth() {
        return sizeMondeWidth;
    }
}