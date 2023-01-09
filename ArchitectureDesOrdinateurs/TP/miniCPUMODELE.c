/*
 * -------------------------- TP du module Archi -------------------------
 *
 * ATTENTION : un outil de détection de plagiat logiciel sera utilisé lors de la correction, vous avez donc tout intérêt à effectuer un travail PERSONNEL
 *
 * Un mot/registre de NBITS bits (par défaut NBITS=16) est représenté par un tableau d'entiers égaux à 0 ou 1.
 * Une ALU est représentée par une structure ALU, avec registre accumulateur et registre d'état.
 * Un CPU (très très simplifié) est représenté par une ALU et quelques registres nécessaires pour stocker les résultats intermédiaires.
 *
 * Certaines fonctions vous sont fournies, d'autres sont à implanter ou à compléter, de préférence dans l'ordre où eles sont indiquées.
 * Il vous est fortement conseillé de lire attentivement l'ensemble des commentaires.
 *
 * Parmi les opérations arithmétiques et logiques, seules 4 opérations de base sont directement fournies par l'ALU, les autres doivent être décrites comme des algorithmes
 * travaillant à l'aide des opérateurs de base de l'ALU simplifiée et pouvant utiliser les registres du CPU.
 *
 * La fonction main() vous permet de tester au fur et à mesure les fonctions que vous implantez.
 *
 * ----------------------------------------------------------------------------------------------
 *
 * author: B. Girau
 */
#include <stdio.h>
#include <stdlib.h>

#define NBITS 16  // attention, votre programme doit pouvoir être adapté à d'autres tailles juste en modifiant la valeur de cette constante
// en ayant toujours NBITS < 32

/////////////////////////////////////////////////////////
// définition de types
/////////////////////////////////////////////////////////

typedef struct {
  int* accu;
  int* flags; // indicateurs ZF CF OF NF
} ALU;

typedef struct {
  ALU alu;
  int* R0;
  int* R1;
  int* R2;
} CPU;

/////////////////////////////////////////////////////////
// fonctions d'initialisation
/////////////////////////////////////////////////////////

/*
 * allocation d'un mot entier de NBITS bits initialisé à 0
 */
int* word() {
  int* tab;
  int i;
  tab=(int*)malloc(NBITS*sizeof(int));
  for(i=0;i<NBITS;i++) tab[i]=0;
  // poids faible : tab[0]
  // poids fort : tab[NBITS-1]
  return tab;
}

/*
 * Initialisation du mot (mot de NBITS bits, codant un entier en Cà2) avec une valeur entière.
 */
void setValue(int* word,int n) {
  int n_low = n & 0x7FFFFFFF;
  // revient à mettre à 0 le bit de poids fort en 32 bits
  // on peut alors travailler sur la partie positive du codage de n
  // remarque : si n est bien codable en Ca2 sur NBITS, et si n est négatif, on récupère quand même le codage de n sur NBITS en Ca2 en prenant les NBITS de poids faible de n_low
  
  // à compléter
  /*
  if(n>0){
        int i;
        for (i=0;i<NBITS;i++){
            word[i]=n%2;
            //printf("%d",word[i]);
            n/=2;
      }
  }
  if(n<0){
        int j;
        for (j=0;j<NBITS;j++){
            word[j]=n_low%2;
            //printf("%d",word[j]);
            n_low/=2;
  }
  }
  */
  for(int i = NBITS-1; i>=0; i--){
    int p = 1<<i;
    word[i] = n_low/p;
    n_low = n_low%p;
  }
  if(n<0){
    word[NBITS-1] = 1;
  }
}

/*
 * instanciation d'un mot de NBITS bits initialisé avec la valeur n
 */
int* initWord(int n) {
  int* tab=word();
  setValue(tab,n) ;
  return tab;
}
    
/*
 * Initialisation du mot (mot de NBITS bits) par recopie des bits du mot en paramètre.
 */
void copyValue(int* word,int* src) {
    // à compléter
    for(int i = 0; i < NBITS; i++){
        word[i] = src[i];
    }
}

/*
 * instanciation d'un mot de NBITS bits initialisé par recopie d'un mot
 */
int* copyWord(int* src) {
  int* tab=word();
  copyValue(tab,src) ;
  return tab;
}

/*
 * initialise l'ALU
 */
ALU initALU() {
  ALU res;
  res.accu=word();
  res.flags=(int*)malloc(4*sizeof(int));
  return res;
}

/*
 * initialise le CPU
 */
CPU initCPU() {
  CPU res;
  res.alu=initALU();
  res.R0=(int*)malloc(NBITS*sizeof(int));
  res.R1=(int*)malloc(NBITS*sizeof(int));
  res.R2=(int*)malloc(NBITS*sizeof(int));
  return res;
}

/////////////////////////////////////////////////////////
// fonctions de lecture
/////////////////////////////////////////////////////////

/*
 * Retourne la valeur entière signée représentée par le mot (complément à 2).
 */    
int intValue(int* word) {
    // à compléter
    
    /*
       int value = 0;
  int* copie=copyWord(word);
  if(copie[NBITS - 1] == 0)
  {
    for (int i = 0; i < NBITS; i++)
      {
        value = value + (copie[i] << i);
      }
      return value;
  }
  else
  {
    for (int i = 0; i < NBITS; i++)
    {
      if (copie[i] == 0)
      {
        value = value + ((-1) << i);
      }
    }
    return value-1;
  }
     */
    int rs = 0;
    for(int i = 0; i < NBITS-1; i++){
        int p ;
        p = 1<<i;
        rs = rs + word[i] * p;
    }
    rs = rs - word[NBITS-1] * (1<<(NBITS-1));
    return rs;
}

/*
 * Retourne une chaîne de caractères décrivant les NBITS bits
 */
char* toString(int* word) {
  int i,j=0;
  char* s=(char*)malloc((2+NBITS)*sizeof(char));
  for (i=NBITS-1;i>=0;i--) {
    if (word[i]==1) s[j]='1';
    else s[j]='0';
    j++;
  }
  s[j]=0;
  return s;
}

/*
 * Retourne l'écriture des indicateurs associés à l'ALU.
 */
char* flagsToString(ALU alu) {
  char *string=(char*)malloc(10*sizeof(char));
  sprintf(string,"z%dc%do%dn%d",alu.flags[0],alu.flags[1],alu.flags[2],alu.flags[3]);
  return string;
}

/*
 * affiche à l'écran le contenu d'une ALU
 */
void printing(ALU alu) {
    // à compléter
    toString(alu.accu);
    printf("%s\n",toString(alu.accu));
    char* flags = flagsToString(alu);
    printf("ZF = %c CF = %c OF = %c NF = %c", flags[1], flags[3], flags[5], flags[7]);
}

/////////////////////////////////////////////////////////
// fonctions de manipulations élémentaires
/////////////////////////////////////////////////////////

/*
 * Mise à la valeur b du bit spécifié dans le mot
 */
void set(int* word,int bitIndex,int b) {
  if ((bitIndex > NBITS-1) || (bitIndex < 0)) 
    printf("valeur d'index incorrecte\n");
  word[bitIndex] = b ;
}

/*
 * Retourne la valeur du bit spécifié dans le mot
 */
int get(int* word,int bitIndex) {
  if ((bitIndex > NBITS-1) || (bitIndex < 0)) 
    printf("valeur d'index incorrecte\n");
  return word[bitIndex] ;
}

/*
 * Positionne l'indicateur ZF en fonction de l'état de l'accumulateur
 */
void setZ(ALU alu) {
  // à compléter
    
    /*
     *     int z_flags = 0;
    for(int i=0;i<NBITS;i++)
    {
        z_flags += alu.accu[i];
    }
    if(z_flags == 0)
    {
        alu.flags[0] = 1;
    }else{
        alu.flags[0] = 0;
    }
     */
    
    alu.flags[0] = (intValue(alu.accu) == 0);
}

/////////////////////////////////////////////////////////
// opérateurs de base de l'ALU
// IMPORTANT : les indicateurs doivent être mis à jour
/////////////////////////////////////////////////////////

/*
 * Stocke le paramètre dans le registre accumulateur
 */
void pass(ALU alu,int* B) {
   // à compléter
    copyValue(alu.accu, B);
    setZ(alu);
    alu.flags[1] = 0;
    alu.flags[2] = 0;
    alu.flags[3] = alu.accu[NBITS - 1];
}

/*
 * Effectue un NAND (NON-ET) entre le contenu de l'accumulateur et le paramètre.
 */
void nand(ALU alu,int* B) {
  // à compléter
    for(int i = 0; i < NBITS; i++){
        if(alu.accu[i] && B[i]){
            alu.accu[i] = 0;
        }else{
            alu.accu[i] = 1;
        }
    }
    setZ(alu);
    alu.flags[2] = 0;
    alu.flags[3] = alu.accu[NBITS - 1];
}

/*
 * Décale le contenu de l'accumulateur de 1 bit vers la droite
 */
void shift(ALU alu) {
  // à compléter
    for(int i = 0; i < NBITS; i++){
        alu.accu[i] = alu.accu[i+1];
    }
    alu.accu[NBITS-1] = 0;
    setZ(alu);
    alu.flags[3] = alu.accu[NBITS - 1];
}

/*
 * module Full Adder : a+b+c_in = s + 2 c_out
 * retourne un tableau contenant s et c_out
 */
int* fullAdder(int a,int b,int c_in) {
  int* res=(int*)malloc(2*sizeof(int));
  // à compléter
    res[0] = (a + b + c_in) % 2;
    res[1] = (a + b + c_in) >= 2;
  return res;
}

/*
 * Additionne le paramètre au contenu de l'accumulateur (addition entière Cà2).
 * Les indicateurs sont positionnés conformément au résultat de l'opération.
 */
void add(ALU alu,int* B) {
  // à compléter
    int* rs = (int*) malloc(2 * sizeof(int));
    //int Cin = 0;
    int c1 ,c2;
    c1 = 0;
    c2 = 0;
    for(int i = 0; i < NBITS; i++){
        rs = fullAdder(alu.accu[i], B[i], alu.flags[1]);
        alu.accu[i] = rs[0];
        alu.flags[1] = rs[1];
        if( i == NBITS - 2){
            c1 = alu.flags[1];
        }
        if( i == NBITS - 1){
        c2 = alu.flags[1];  
        }
    }
    setZ(alu);
    alu.flags[2] = (c1 != c2);
    alu.flags[3] = (alu.accu[NBITS-1]);
}

////////////////////////////////////////////////////////////////////
// Opérations logiques :
////////////////////////////////////////////////////////////////////

/*
 * Négation.
 */
void not(CPU cpu){
  // à compléter
    //cpu.R0 = copyWord(cpu.alu.accu);
    nand(cpu.alu, cpu.alu.accu);
}

/*
 * Et.
 */
void and(CPU cpu,int* B) {
  // à compléter
    nand(cpu.alu, B);
    //copyValue(cpu.R0, cpu.alu.accu);
    nand(cpu.alu, cpu.alu.accu);
}


/*
 * Ou.
 */
void or(CPU cpu,int* B) {
  // à compléter
    nand(cpu.alu, cpu.alu.accu);
    //copyValue(cpu.R0, cpu.alu.accu);
    //pass(cpu.alu, B);
    //nand(cpu.alu, cpu.alu.accu);
    //nand(cpu.alu, cpu.R0);
    cpu.R0 = copyWord(cpu.alu.accu);
    
    
    //setValue(cpu.R2, intValue(B));
    pass(cpu.alu, B);
    nand(cpu.alu, cpu.alu.accu);
    nand(cpu.alu, cpu.R0);
}

/*
 * Xor.
 */
void xor(CPU cpu,int* B) {
  // à compléter
    cpu.R0 = copyWord(cpu.alu.accu);
    nand(cpu.alu, cpu.alu.accu);
    cpu.R1 = copyWord(cpu.alu.accu);
    pass(cpu.alu, B);
    nand(cpu.alu, cpu.alu.accu);
    nand(cpu.alu, cpu.R0);
    cpu.R2 = copyWord(cpu.alu.accu);
    pass(cpu.alu, B);
    nand(cpu.alu, cpu.R1);
    nand(cpu.alu, cpu.R2);
}

/*
 * Décale le receveur de |n| positions.
 * Le décalage s'effectue vers la gauche si n>0 vers la droite dans le cas contraire.
 * C'est un décalage logique (pas de report du bit de signe dans les positions 
 * libérées en cas de décalage à droite).
 * L'indicateur CF est positionné avec le dernier bit "perdu".
 */
void logicalShift(CPU cpu,int n) {
  // à compléter
    
    //setValue(cpu.R0,0);
    //setValue(cpu.R1,0);
    
    if(n < 0){
        for(int i = 0; i > n; i--){
            shift(cpu.alu);
        }
    }else{
        for(int i = 0; i < n; i++){
            for(int j = NBITS - 1; j >= 0; j--){
                cpu.alu.flags[1] = cpu.alu.accu[j];
                if(j){
                    cpu.alu.accu[j] = cpu.alu.accu[j-1];
                }else{
                    cpu.alu.accu[j] = 0;
                }
            }
        }
    }
}

/////////////////////////////////////////////////////////
// Opérations arithmétiques entières
/////////////////////////////////////////////////////////

/*
 * Opposé.
 */
void opp(CPU cpu) {
  // à compléter
    not(cpu);
    cpu.R0 = initWord(1);
    //set(cpu.R0, 0,1);
    add(cpu.alu, cpu.R0);
}

/*
 * Soustraction.
 */
void sub(CPU cpu,int* B) {
  // à compléter
    cpu.R0 = copyWord(cpu.alu.accu);
    pass(cpu.alu, B);
    opp(cpu);
    add(cpu.alu, cpu.R0);
}

/*
 * Multiplication.
 */
void mul(CPU cpu,int* B) {
  // à compléter
    cpu.R0 = copyWord(cpu.alu.accu);
    cpu.R1 = initWord(0);
    for(int i = 0; i < NBITS; i++){
        if(B[i]){
            add(cpu.alu, cpu.R1);
            cpu.R1 = copyWord(cpu.alu.accu);
        }
        pass(cpu.alu, cpu.R0);
        logicalShift(cpu, 1);
        cpu.R0 = copyWord(cpu.alu.accu);
    }
    pass(cpu.alu, cpu.R1);
}

/////////////////////////////////////////////////////////
// Programme de test
/////////////////////////////////////////////////////////

int main(int argc,char *argv[]) {
  
  /*
    Ce programme est fourni à titre d'exemple pour permettre de tester simplement vos fonctions.
    Il vous est bien entendu possible de le modifier/compléter, ou encore d'écrire vos propres fonctions de test.
   */
  
  int* operand;
  ALU alu;
  CPU cpu;
  
  int chosenInt,integer ;
  int go_on = 1 ;
  
  char* menu =     
    "              Programme de test\n\n0  Quitter\n1  setValue(operande,int)\n2  pass(alu,operande)\n3  printing(alu)\n4  afficher toString(operande)\n5  afficher intValue(operande)\n6  afficher intValue(accu)\n7  accu=nand(accu,operande)\n8  accu=add(accu,operande)\n9  accu=sub(accu,operande)\n10  accu=and(accu,operande)\n11 accu=or(accu,operande)\n12 accu=xor(accu,operande)\n13 accu=not(accu)\n14 accu=opp(accu)\n15 accu=shift(accu)\n16 accu=logicalShift(accu,int)\n17 accu=mul(accu,operande)\n\n" ;
  
  char* invite = "--> Quel est votre choix  ? " ;
  
  printf("%s",menu) ; 

  operand=word();
  cpu=initCPU();
  alu=cpu.alu;
  
  while (go_on==1) {
    printf("%s",invite);
    scanf("%d",&chosenInt);
    switch (chosenInt) {
    case 0 : 
      go_on = 0 ;
      break ;
    case 1 :
      printf("Entrez un nombre :"); 
      scanf("%d",&integer);
      setValue(operand,integer);
      break ;
    case 2 : 
      pass(alu,operand);
      break ;
    case 3 : 
      printing(alu);
      break ;
    case 4 : 
      printf("%s\n",toString(operand));
      break ;
    case 5 : 
      printf("intValue(operand)=%d\n",intValue(operand));
      break ;
    case 6 : 
      printf("intValue(accu)=%d\n",intValue(alu.accu));
      break ;
    case 7 : 
      nand(alu,operand);
      printf("apres nand(), accu = ");
      printing(alu);
      break ;
    case 8 : 
      add(alu,operand);
      printf("apres add(), accu = ");
      printing(alu);
      break ;
    case 9 : 
      sub(cpu,operand);
      printf("apres sub(), A = ");
      printing(alu);
      break ;
    case 10 : 
      and(cpu,operand);
      printf("apres and(), A = ");
      printing(alu);
      break ;
    case 11 : 
      or(cpu,operand);
      printf("apres or(), A = ");
      printing(alu);
      break ;
    case 12 : 
      xor(cpu,operand);
      printf("apres xor(), A = ");
      printing(alu);
      break ;
    case 13 : 
      not(cpu);
      printf("apres not(), A = ");
      printing(alu);
      break ;
    case 14 : 
      opp(cpu);
      printf("apres opp(), A = ");
      printing(alu);
      break ;
    case 15 : 
      shift(alu);
      printf("apres shift(), A = ");
      printing(alu);
      break ;
    case 16 : 
      printf("Entrez un entier :") ;
      scanf("%d",&integer);
      logicalShift(cpu,integer);
      printf("apres logicalshift(%d), A = ",integer);
      printing(alu);
      break ;
    case 17 : 
      mul(cpu,operand);
      printf("apres mul(), A = ");
      printing(alu);
      break ;
    default : 
      printf("Choix inexistant !!!!\n");
      printf("%s",menu);
    }
  }
  printf("Au revoir et a bientot\n");
  return 0;
}


  
