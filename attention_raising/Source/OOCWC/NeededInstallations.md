Necessary prequitions before installing OOCWC
=============================
[Based on Gyula Juhasz's video](https://www.youtube.com/watch?v=gyr0OiaY820&t "Video installation guide")

*Tested on Ubuntu 19.04*

Install necessary pre-requisitions
----------------------------------
```
sudo apt install git

sudo apt install autoconf

sudo apt install protobuf-compiler libprotobuf-dev

sudo apt install libboost-all-dev

sudo apt install flex
```
Install OSM-Binary
-------------
```
git clone https://github.com/scrosby/OSM-binary.git

cd OSM-binary/

make -C src

sudo make -C src install
```

Install neccessary packages
--------------------
```
sudo apt install libexpat1-dev

sudo apt install zlib1g-dev

sudo apt install libbz2-dev

sudo apt install libsparsehash-dev

sudo apt install libodbc1

sudo apt install libgdal20

sudo apt install libgdal-dev

sudo apt install libgeos++-dev

sudo apt install libproj-dev

sudo apt install doxygen graphviz xmlstarlet

sudo apt install cmake
```

Install Osmium library
----------------------
```
git clone https://github.com/osmcode/libosmium.git

cd libosmium/

cmake .

make

sudo make install
```

Get RoboCar source
----------
```
git clone https://github.com/nbatfai/robocar-emulator.git
```

Get older OSM file for full compatibility
--------------
```
git clone https://git.code.sf.net/p/udprog/code udprog-code

cd udprog-code/

cp source/vedes/negyedik/TJ_CsA_vedes/debrecen.osm ../robocar-emulator/justine/debrecen.osm
```

Neccessary modifications in sources
---------
1. add the following line to `osmreader.hpp`
   ```
   #include <google/protobuf/descriptor.h>
   ```
2. replace the following line in `smartcity.hpp`
   ```
   std::cerr << " Out of shared memory..." << std::cerr;
   ```
   to
   ```
   std::cerr << " Out of shared memory..." << std::endl;
   ```
3. replace the following line in `traffic-main.cpp`
   ```
   catchdist {15.5};
   ```
   to
   ```
   catchdist (15.5);
   ```
4. replace the following line in `traffic.hpp`
   ```
   double m_catchdist {15.5};
   ```
   to
   ```
   double m_catchdist = 15.5;
   ```

Build RoboCar project's server
-----------
```
cd robocar-emulator/justine/rcemu

autoreconf --install

./configure

make
```

Build RoboCar project's window
--------
1. Go to rcwin dir
    ```
    cd robocar-emulator/justine/rcwin
    ```
2. Replace every occurance of "1.5" to "1.6" in `pom.xml`
    ```
    eg.: <source>1.5</source> to  <source>1.6</source>
    ```
3. Add the following line to `CarWindow.java`'s line 537
    ```
    scan.useLocale(java.util.Locale.US);
    ```
    This is neccessary because some languages use "," instead of "." in double values and it cause `InputMismatchException`.
4. Build with Maven
    ```
    sudo apt install maven

    mvn clean compile package site assembly:assembly
    ```
Run RoboCar
------
1. Open new terminal
    ```
    cd robocar-emulator/justine/rcemu

    src/smartcity --osm=../debrecen.osm --city=Debrecen --shm=DebrecenSharedMemory --node2gps=../debrecen_lmap.txt
    ```
2. Open another terminal
    ```
    cd robocar-emulator/justine/rcemu

    src/traffic --port=10007 --shm=DebrecenSharedMemory
    ```
3. In another terminal
    ```
    cd robocar-emulator/justine/rcwin

    java -jar target/site/justine-rcwin-0.0.16-jar-with-dependencies.jar ../debrecen_lmap.txt
    ```
4. (Optional) Add more car
    ```
    (sleep 1;, echo "<init Norbi 500 g>"; sleep 1) | telnet localhost 10007
    ```
5. Start own agent
    ```
    cd robocar-emulator/justine/rcemu
    
    src/sampleshmclient --port=10007 --shm=DebrecenSharedMemory
    ```