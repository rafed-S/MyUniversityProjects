package twisk.monde;

public class Activite extends Etape{

    protected int temps;
    protected int ecartTemps;
    private static int compt = 0;

    public Activite(String nom) {
        super(nom);
        temps = 3;
        ecartTemps = 1;
    }
    public Activite(String nom, int t, int e) {
        super(nom);
        this.temps = t;
        this.ecartTemps = e;
    }
    //**----------------- Getters -----------------**//

    public int getTemps() {
        return temps;
    }
    public int getEcartTemps() {
        return ecartTemps;
    }

    //**---------------------------------**//
    public boolean estUneActivite() {
        return true;
    }
    @Override
    public String toString() {
        return super.toString();
    }
    public String toC()
    {
        if (this.getSuccesseur() == null){return "";}

        StringBuilder s = new StringBuilder();
        if(this.gSucc.nbEtapes() == 1) {
        String s1 = "transfert(" + this.getNum() + ","+ this.getSuccesseur().getNum() +");\n" ;
        String s2 = "";

            if (this.getSuccesseur().estUnGuichet()) {
                s2 = "";
            } else {
                s2 = "delai(" + this.temps + "," + this.ecartTemps + ");\n";
            }
            s.append(s1).append(s2);
            s.append(this.getSuccesseur().toC());
        }
        else
        {
            String ss = "int nb"+ compt +" = (int) ( (rand() / (float) RAND_MAX ) * "+this.gSucc.nbEtapes()+") ; \n";
             ss = ss+" switch (nb"+ compt +") { \n";
             compt++;
            String ss2 = "";
            int d = 0;
            s.append(ss);
            for(int h = 0; h < this.gSucc.nbEtapes(); h++ )
            {
                ss2 = "case "+h+" : \n" ;
                String s1 = "transfert(" + this.getNum() + ","+ this.getSuccesseur(h).getNum() +");\n" ;
                String s2 = "";

                if (this.getSuccesseur(h).estUnGuichet()) {
                    s2 = "\n";
                } else {
                    s2 = "delai(" + this.temps + "," + this.ecartTemps + ");\n";
                }
                s.append(ss2).append(s1).append(s2);
                s.append(this.getSuccesseur(h).toC()).append("\n break ; \n ");
            }
            s.append("} ");
        }
        return s.toString() ;
    }
}
