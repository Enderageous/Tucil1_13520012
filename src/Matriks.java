package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Matriks {
    int Brs,Kol;
    String [][][] matriks;

    /*Konstruktor Matriks*/
    public Matriks (int Brs, int Kol){
        this.Brs = Brs;
        this.Kol = Kol;
        this.matriks = new String [Brs][Kol][2];
    }

    /*Selektor nilai elemen pada Matriks[i][j]*/
    public String Elmt (int i, int j, int k){
        return matriks[i][j][k];
    }

    /*Mengganti nilai elemen pada Matriks[i][j] menjadi val*/
    public void SetElmt (int i, int j, int k, String val){
        matriks[i][j][k] = val;
    }

    /*Mereturn banyak baris Matriks*/
    public int Baris(){
        return Brs;
    }

    /*Mereturn banyak kolom Matriks*/
    public int Kolom(){
        return Kol;
    }


    /*Membaca Matriks dari file Eksternal dan mengolahnya*/
    public static void bacaFileMatriks (String namaFile) throws FileNotFoundException{
        int Brs = 0;
        int wordBrs = -1;
        int Kol = 0;
        int wordKol = 0;
        int compare = 0;
        int i,ii,j;
        Matriks M = null;
        boolean found = false;
        boolean inputMode = true;
        pair result;

        File myFile = new File(namaFile);
        Scanner fileReader = new Scanner(myFile);
        String fileLine;

        while (fileReader.hasNextLine()){
            fileLine = fileReader.nextLine();
            if (fileLine == "")
            {
                inputMode = false;
            }
            if (inputMode)
            {
                Kol = fileLine.split(" ").length;
                Brs++;
            }
            else
            {
                wordBrs++;
            }
        }
        fileReader.close();

        fileReader = new Scanner(myFile);
        M = new Matriks(Brs, Kol);
        for (i=0; i<Brs; i++){
            for (j=0; j<Kol; j++){
                M.SetElmt(i, j, 0, fileReader.next());
                M.SetElmt(i, j, 1, "-");
            }
        }

        long startTime = System.nanoTime();
        for (i=Brs+1; i<Brs+wordBrs+1;i++)
        {
            fileLine = fileReader.next();
            System.out.printf("\n+=+ Word %s +=+\n", fileLine);
            wordKol = fileLine.split("").length;
            Matriks word = new Matriks(1,wordKol);
            for (j=0; j<wordKol; j++)
            {
                word.SetElmt(0, j, 0, fileLine.substring(j, j+1));
            }

            outerloop:
            for (ii=0; ii<Brs; ii++)
            {
                for (j=0; j<Kol; j++)
                {
                    compare++;
                    if (M.Elmt(ii, j, 0).equals(word.Elmt(0, 0, 0)))
                    {
                        M.SetElmt(ii, j, 1, "a");
                        result = M.cekAround(word, ii, j, wordKol);
                        compare = compare + result.getComp();
                        found = result.getBoolean();
                        M.SetElmt(ii, j, 1, "-");
                        if (found)
                        {
                            break outerloop;
                        }
                    }
                }
            }

            if(!found)
            {
                System.out.printf("Kata tidak ditemukan\n");
            }
        }
        fileReader.close();
        long endTime = System.nanoTime();
        System.out.format("\nElapsed Time for this program is %d ms", (endTime - startTime)/1000000);
        System.out.format("\nTotal comparison is %d time(s)", compare);
    }

    /*Menuliskan suatu Matriks pada layar user*/
    public void tulisMatriks(){
        int i,j;
        for(i=0; i < this.Brs; i++){
            for(j=0; j < this.Kol; j++){
                if (!"-".equals(matriks[i][j][1])){
                    System.out.printf("%S", this.matriks[i][j][0]);
                    }
                else{
                    System.out.printf("-");
                    }
                if(j < this.Kol - 1){
                    System.out.printf(" ");
                    }
                }
            if(i < this.Brs - 1) System.out.println("");
        }
        System.out.printf("\n");
    }

    /* Pengecekan kata pada word search */
    public pair cekAround(Matriks word, int i, int j, int wordKol){
        pair result;
        int compare = 0;

        result = this.cekKanan(word, i, j, wordKol);
        compare = result.getComp();
        if (!result.getBoolean())
        {
            result = this.cekBawah(word, i, j, wordKol);
            compare = compare + result.getComp();
        }
        if (!result.getBoolean())
        {
            result = this.cekKiri(word, i, j, wordKol);
            compare = compare + result.getComp();
        }
        if (!result.getBoolean())
        {
            result = this.cekAtas(word, i, j, wordKol);
            compare = compare + result.getComp();
        }
        if (!result.getBoolean())
        {
            result = this.cekKananBawah(word, i, j, wordKol);
            compare = compare + result.getComp();
        }
        if (!result.getBoolean())
        {
            result = this.cekKiriBawah(word, i, j, wordKol);
            compare = compare + result.getComp();
        }
        if (!result.getBoolean())
        {
            result = this.cekKananAtas(word, i, j, wordKol);
            compare = compare + result.getComp();
        }
        if (!result.getBoolean())
        {
            result = this.cekKiriAtas(word, i, j, wordKol);
            compare = compare + result.getComp();
        }
        return new pair (compare,result.getBoolean());
    }

    public pair cekKiri(Matriks word, int i, int j, int wordKol){
        boolean found = false;
        int wordNum = 1;
        int compare = 0;

        j--;
        while(!found && j>=0)
        {
            compare++;
            if (this.Elmt(i, j, 0).equals(word.Elmt(0, wordNum, 0)))
            {
                this.SetElmt(i, j, 1, "a");
                j--;
                wordNum++;
                if (wordNum==wordKol)
                {
                    found = true;
                    this.tulisMatriks();
                }
            }
            
            else
            {
                break;
            }
        }

        while(wordNum != 1)
        {
            j++;
            this.SetElmt(i, j, 1, "-");
            wordNum--;
        }
        return new pair(compare,found);
    }
    public pair cekKanan(Matriks word, int i, int j, int wordKol){
        boolean found = false;
        int wordNum = 1;
        int compare = 0;

        j++;
        while(!found && j<this.Kol)
        {
            compare++;
            if (this.Elmt(i, j, 0).equals(word.Elmt(0, wordNum, 0)))
            {
                this.SetElmt(i, j, 1, "a");
                j++;
                wordNum++;
                if (wordNum==wordKol)
                {
                    found = true;
                    this.tulisMatriks();
                }
            }
            
            else
            {
                break;
            }
        }

        while(wordNum != 1)
        {
            j--;
            this.SetElmt(i, j, 1, "-");
            wordNum--;
        }
        return new pair(compare,found);
    }

    public pair cekAtas(Matriks word, int i, int j, int wordKol){
        boolean found = false;
        int wordNum = 1;
        int compare = 0;

        i--;
        while(!found && i>=0)
        {
            compare++;
            if (this.Elmt(i, j, 0).equals(word.Elmt(0, wordNum, 0)))
            {
                this.SetElmt(i, j, 1, "a");
                i--;
                wordNum++;
                if (wordNum==wordKol)
                {
                    found = true;
                    this.tulisMatriks();
                }
            }
            
            else
            {
                break;
            }
        }

        while(wordNum != 1)
        {
            i++;
            this.SetElmt(i, j, 1, "-");
            wordNum--;
        }
        return new pair(compare,found);
    }

    public pair cekBawah(Matriks word, int i, int j, int wordKol){
        boolean found = false;
        int wordNum = 1;
        int compare = 0;

        i++;
        while(!found && i<this.Brs)
        {
            compare++;
            if (this.Elmt(i, j, 0).equals(word.Elmt(0, wordNum, 0)))
            {
                this.SetElmt(i, j, 1, "a");
                i++;
                wordNum++;
                if (wordNum==wordKol)
                {
                    found = true;
                    this.tulisMatriks();
                }
            }
            
            else
            {
                break;
            }
        }

        while(wordNum != 1)
        {
            i--;
            this.SetElmt(i, j, 1, "-");
            wordNum--;
        }
        return new pair(compare,found);
    }

    public pair cekKiriAtas(Matriks word, int i, int j, int wordKol){
        boolean found = false;
        int wordNum = 1;
        int compare = 0;

        j--;
        i--;
        while(!found && j>=0 && i>=0)
        {
            compare++;
            if (this.Elmt(i, j, 0).equals(word.Elmt(0, wordNum, 0)))
            {
                this.SetElmt(i, j, 1, "a");
                j--;
                i--;
                wordNum++;
                if (wordNum==wordKol)
                {
                    found = true;
                    this.tulisMatriks();
                }
            }
            
            else
            {
                break;
            }
        }

        while(wordNum != 1)
        {
            j++;
            i++;
            this.SetElmt(i, j, 1, "-");
            wordNum--;
        }
        return new pair(compare,found);
    }

    public pair cekKiriBawah(Matriks word, int i, int j, int wordKol){
        boolean found = false;
        int wordNum = 1;
        int compare = 0;

        j--;
        i++;
        while(!found && j>=0 && i<this.Brs)
        {
            compare++;
            if (this.Elmt(i, j, 0).equals(word.Elmt(0, wordNum, 0)))
            {
                this.SetElmt(i, j, 1, "a");
                j--;
                i++;
                wordNum++;
                if (wordNum==wordKol)
                {
                    found = true;
                    this.tulisMatriks();
                }
            }
            
            else
            {
                break;
            }
        }

        while(wordNum != 1)
        {
            j++;
            i--;
            this.SetElmt(i, j, 1, "-");
            wordNum--;
        }
        return new pair(compare,found);
    }

    public pair cekKananAtas(Matriks word, int i, int j, int wordKol){
        boolean found = false;
        int wordNum = 1;
        int compare = 0;

        j++;
        i--;
        while(!found && j<this.Kol && i>=0)
        {
            compare++;
            if (this.Elmt(i, j, 0).equals(word.Elmt(0, wordNum, 0)))
            {
                this.SetElmt(i, j, 1, "a");
                j++;
                i--;
                wordNum++;
                if (wordNum==wordKol)
                {
                    found = true;
                    this.tulisMatriks();
                }
            }
            
            else
            {
                break;
            }
        }

        while(wordNum != 1)
        {
            j--;
            i++;
            this.SetElmt(i, j, 1, "-");
            wordNum--;
        }
        return new pair(compare,found);
    }

    public pair cekKananBawah(Matriks word, int i, int j, int wordKol){
        boolean found = false;
        int wordNum = 1;
        int compare = 0;

        j++;
        i++;
        while(!found && j<this.Kol && i<this.Brs)
        {
            compare++;
            if (this.Elmt(i, j, 0).equals(word.Elmt(0, wordNum, 0)))
            {
                this.SetElmt(i, j, 1, "a");
                j++;
                i++;
                wordNum++;
                if (wordNum==wordKol)
                {
                    found = true;
                    this.tulisMatriks();
                }
            }
            
            else
            {
                break;
            }
        }

        while(wordNum != 1)
        {
            j--;
            i--;
            this.SetElmt(i, j, 1, "-");
            wordNum--;
        }
        return new pair(compare,found);
    }
}
