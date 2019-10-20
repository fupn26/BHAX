#include <iostream>
#include "perceptron.h"
#include "png++/png.hpp"
#include <time.h>


using namespace std;

int main (int argc, char **argv)
{
    png::image <png::rgb_pixel> png_image (argv[1]);

    int size = png_image.get_width() * png_image.get_height();

    Perceptron* p = new Perceptron (3, size, 256, size);

    double* image = new double[size];

    for (int i = 0; i<png_image.get_width(); ++i)
        for (int j = 0; j<png_image.get_height(); ++j)
            image[i*png_image.get_width() + j] = png_image[i][j].red;

    std::vector< double > value = (*p) (image);

    for (int i = 0; i<png_image.get_width(); ++i)
        for (int j = 0; j<png_image.get_height(); ++j){
            png_image[i][j].green = value[i*png_image.get_width() + j];

    }

    png_image.write("output.png");


//    std::cout << value << std::endl;

    delete p;
    delete [] image;

}
