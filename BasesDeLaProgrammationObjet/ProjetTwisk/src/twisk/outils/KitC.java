package twisk.outils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.Runtime;
import static java.nio.file.Files.newBufferedReader;
import java.lang.Process;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class KitC {
    private  int cptt = 0;

    public void creerEnvironnement() {
        try {
            Path directories = Files.createDirectories(Paths.get("/tmp/twisk"));
            String[] liste = {"programmeC.o", "def.h", "codeNatif.o"};
            for (String nom : liste) {
                InputStream source = getClass().getResource("/codeC/" + nom).openStream() ;
                File destination = new File("/tmp/twisk/" + nom) ;
                copier(source, destination);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copier(InputStream source, File dest) throws IOException {
        InputStream sourceFile = source;
        OutputStream destinationFile = new FileOutputStream(dest) ;
        byte buffer[] = new byte[512 * 1024];
        int nbLecture;
        while ((nbLecture = sourceFile.read(buffer)) != -1){
            destinationFile.write(buffer, 0, nbLecture);
        }
        destinationFile.close();
        sourceFile.close();
    }

    public void creerFichier(String codeC) {
        FileWriter flot;
        String finDeLigne = System.getProperty("line.separator");
        try {
            flot = new FileWriter("/tmp/twisk/client.c");
            flot.write(codeC);
            flot.write(finDeLigne);
            flot.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void compiler()
    {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("gcc -Wall -fPIC -c /tmp/twisk/client.c -o /tmp/twisk/client.o");
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String ligne ;
            while ((ligne = output.readLine()) != null) {
                System.out.println(ligne);
            }while ((ligne = error.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void construireLaLibrairie() {
        Runtime runtime = Runtime.getRuntime();
        try {
            FabriqueNumero s = FabriqueNumero.getInstance();
            this.cptt = s.getNumeroLib();
            Process p = runtime.exec("gcc -shared /tmp/twisk/programmeC.o /tmp/twisk/codeNatif.o /tmp/twisk/client.o -o /tmp/twisk/libTwisk"+this.cptt+".so");
            //      Process p = runtime.exec("gcc -shared /tmp/twisk/programmeC.o /tmp/twisk/codeNatif.o /tmp/twisk/client.o -o /tmp/twisk/libTwisk.so");

            p.waitFor();
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String ligne ;

            while((ligne = output.readLine()) !=null)
            {
                System.out.println(ligne);
            }
            while((ligne = error.readLine()) !=null)
            {
                System.out.println(ligne);
            }
            // | InterruptedException e
        }catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  int getCptt() {
        return cptt;
    }

}


