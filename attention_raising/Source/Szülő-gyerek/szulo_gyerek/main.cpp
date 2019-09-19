#include <iostream>

using namespace std;

class Vehicles{
public:
//    void setNumberOfWheels(){
//        wheels = 0;
//    }
    int wheels = 0;
};

class Cars: public Vehicles{
public:
    void setNumberOfWheels(){
        wheels = 4;
    }
};

int main()
{
    Vehicles &vehicle = *new Cars();

    vehicle.setNumberOfWheels(); //Hibás, mivel az ős nem tudja elérni a gyermek metódusait.

    cout << "The number of wheels: " << vehicle.wheels << endl;
}
