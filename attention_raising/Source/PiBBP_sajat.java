class PiBBP{
    //{16^dπ} = {4{16^dS1} − 2{16^dS4} − {16^dS5} − {16^dS6}}
    public double result;
    public String HexaJegyek;
    public PiBBP(int d){
        double d16S1 = dj16s(d,1);
        double d16S2 = dj16s(d,4);
        double d16S3 = dj16s(d,5);
        double d16S4 = dj16s(d, 6);
        
        double temp = 4.0d*d16S1 - 2.0d*d16S2 - d16S3 - d16S4;
        
        result = temp - java.lang.StrictMath.floor(temp);
        
        StringBuffer sb = new StringBuffer();
        
        Character hexaJegyek[] = {'A', 'B', 'C', 'D', 'E', 'F'};
        
        while(result != 0.0d) {
            
            int jegy = (int)StrictMath.floor(16.0d*result);
            
            if(jegy<10)
                sb.append(jegy);
            else
                sb.append(hexaJegyek[jegy-10]);
            
            result = (16.0d*result) - StrictMath.floor(16.0d*result);
        }
        
        HexaJegyek = sb.toString();

        
    }
    
    
    public double dj16s(int d, int j){
        double sum = 0.0d;
        for(int k = 0; k <= d; ++k){
            sum += (double)binmodk(8*k+j, d-k) / (double)(8*k + j);
        }
        
//        long iPart = (long) sum;
//        Double fpart = sum -iPart;
        
        return sum - java.lang.StrictMath.floor(sum);
    }
    
    public long binmodk(int k, int d){
        int t = 1;
        long r = 1;
        while(t <= d){
            t *= 2;
        }
        
//        while(true) {
            
//            if(d >= t) {
//                r = (16*r) % k;
//                d = d - t;
//            }
            
//            t = t/2;
            
//            if(t < 1)
//                break;
            
//            r = (r*r) % k;
            
//        }


        while(t < 1){
            if(d >= t){
                r = (16*r) % k;
                d = d-t;
            }
            t = t/2;
            if (t >= 1){
                r = (r*r) % k;
            }
        }
//        System.out.println(r);
        return r;
    }
    
    public static void main(String[] args){
       PiBBP m_pibbp = new PiBBP(10000000);
       System.out.println(m_pibbp.HexaJegyek);
    }
}
