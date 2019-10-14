using namespace std;

#include <iostream>
#include "../perceptron.hpp"
#include "png++/png.hpp"
#include <time.h>

int main (int argc, char **argv)
{
    png::image <png::rgb_pixel> png_image (argv[1]);

    int size = png_image.get_width() * png_image.get_height();

    Perceptron* p = new Perceptron (3, size, 256, 1);

    double* image = new double[size];

    for (int i = 0; i<png_image.get_width(); ++i)
        for (int j = 0; j<png_image.get_height(); ++j)
            image[i*png_image.get_width() + j] = png_image[i][j].red;


    double* newPNG = new double[size];
    srand(time(nullptr));

    for(int i = 0; i < png_image.get_width(); ++i)
        for(int j = 0; j<png_image.get_height(); ++j){
            png_image[i][j].green = rand()%256;//newPNG[j*png_image.get_height()+j];
        }
    png_image.write("../output.png");


    double value = (*p) (image);

    std::cout << value << std::endl;

    delete p;
    delete [] image;

}
