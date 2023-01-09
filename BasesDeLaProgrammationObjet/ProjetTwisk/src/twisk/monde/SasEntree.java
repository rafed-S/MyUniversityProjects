package twisk.monde;

public class SasEntree extends Activite{
    private static int  dd1 = 0;

    public SasEntree() {
        super("SASENTREE");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entree : ");
        sb.append(" - ").append(this.nbSuccesseurs()).append(" Successeur - ").append(this.gSucc.toString());
        return sb.toString();
    }

    public String toC()
    {
        if (this.getSuccesseur() == null){return "";}
        StringBuilder s = new StringBuilder();
        String s1 = "entrer(" + this.getNum() + ");\n";
        String s2 = "delai(" + this.temps + "," + this.ecartTemps + ");\n";
        s.append(s1).append(s2);
        if(gSucc.nbEtapes() == 1 ) {
            String s3 = "transfert(" + this.getNum() + "," + this.getSuccesseur().getNum() + ");\n";
            String s4 = "delai(" + this.getSuccesseur().getTemps() + "," + this.getSuccesseur().getEcartTemps() + ");\n";
            s.append(s3).append(s4);
            s.append(this.getSuccesseur().toC());
        }else{
            String ss = "int nb"+dd1+" = (int) ( (rand() / (float) RAND_MAX ) * "+this.gSucc.nbEtapes()+") ; \n";
            ss = ss+" switch (nb"+dd1+") { \n";
            dd1++;
            String ss2 = "";
            int d = 0;
            s.append(ss);
            for(int h = 0; h < this.gSucc.nbEtapes(); h++ )
            {
                ss2 = "case "+h+" : \n" ;
                String s11 = "transfert(" + this.getNum() + ","+ this.getSuccesseur(h).getNum() +");\n" ;
                String s21 = "";

                if (this.getSuccesseur(h).estUnGuichet()) {
                    s21 = "\n";
                } else {
                    s21 = "delai(" + this.temps + "," + this.ecartTemps + ");\n";
                }
                s.append(ss2).append(s11).append(s21);
                s.append(this.getSuccesseur(h).toC()).append("\n break ; \n ");
            }
            s.append("} ");
        }
        return  s.toString();
    }
}
