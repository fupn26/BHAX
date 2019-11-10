privileged aspect Aspect{
    void around(LZWBinFa fa, LZWBinFa.Csomopont elem, java.io.BufferedWriter os):call(public void LZWBinFa.kiir(LZWBinFa.Csomopont, java.io.BufferedWriter)) && target(fa) && args(elem, os){
        if (elem != null)
        {
            try{
                ++fa.melyseg;
                for (int i = 0; i < fa.melyseg; ++i)
                    os.write("---");
                os.write(elem.getBetu () + "(" + (fa.melyseg - 1) + ")\n");
                fa.kiir(elem.egyesGyermek (), os);
                fa.kiir(elem.nullasGyermek (), os);
                --fa.melyseg;
            }
            catch(java.io.IOException e){
                System.out.println("Csomópont írása nem sikerült.");
            }
        }

    }
}