#include <QCoreApplication>
#include <iostream>
#include <boost/filesystem.hpp>
#include <boost/regex.hpp>


int iterator(boost::filesystem::path p){
    int osztalyok_szama = 0;
    boost::regex expr{"(.*\\.java)"};

    for (const boost::filesystem::directory_entry& x : boost::filesystem::recursive_directory_iterator(p)){

        if(boost::regex_match(x.path().string(), expr) && boost::filesystem::is_regular_file(x.path())){
            std::cout << ++osztalyok_szama << std::endl;
            std::cout << "    " << x.path() << '\n';
        }
    }
    return osztalyok_szama;
}

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    if (argc < 2)
    {
        std::cout << "Usage: tut3 path\n";
        return 1;
    }

    boost::filesystem::path p (argv[1]);

    try
    {
        int osztalyok_szama = iterator(p);
        std::cout << "Java JDK osztályok száma " << osztalyok_szama << std::endl;
    }

    catch (const boost::filesystem::filesystem_error& ex)
    {
        std::cout << ex.what() << '\n';
    }


    return a.exec();
}
