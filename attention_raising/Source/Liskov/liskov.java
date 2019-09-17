class LiskovSert{
	public static class Négyszög{
		public int a;
		public int b;
		public int c;
		public int d;

		public Négyszög(int a_oldal, int b_oldal, int c_oldal, int d_oldal){
			a = a_oldal;
			b = b_oldal;
			c = c_oldal;
			d = d_oldal;
		}

		public int kerület;
		public int terület;
	}
	
	public static class Négyzet extends Négyszög{
		public Négyzet(int a_oldal, int b_oldal, int c_oldal, int d_oldal){
			super(a_oldal, b_oldal, c_oldal, d_oldal);
		}
	}
	
	public static class Téglalap extends Négyzet{
		public Téglalap(int a_oldal, int b_oldal, int c_oldal, int d_oldal){
			super(a_oldal, b_oldal, c_oldal, d_oldal);
		}
	}

	public static class Program{
		public void setKerület(Négyzet negyzet){
			negyzet.kerület = negyzet.a * 4;	
			negyzet.terület = negyzet.a * negyzet.a;
		}
	}	

	public static void main(String[] args){
		Program program = new Program();
		Négyzet negyzet = new Négyzet(5,5,5,5);;
		Téglalap teglalap = new Téglalap(4,5,4,5);
		
		program.setKerület(negyzet);
		program.setKerület(teglalap); 

		System.out.println("Téglalap kerülete: " + teglalap.kerület + ", Négyzet kerülete: "+ negyzet.kerület); 
		
	}
} 
