public class Vehicles{
//	public void setNumberOfWheels(){
//		wheels = 0;
//	}
	public int wheels;

	public static class Cars extends Vehicles{
	    	public void setNumberOfWheels(){
		        wheels = 4;
	    	}
	}

	public static void main(String[] args)
	{
    		Vehicles vehicle = new Cars();

		vehicle.setNumberOfWheels(); //Hibás, mert az ős a leszármazott metódusait nem éri el.

		System.out.println("The number of wheels: "+vehicle.wheels);
	}
}

