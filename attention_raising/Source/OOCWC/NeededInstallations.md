Necessary prequitions before installing OOCWC
=============================
Install Protocol Buffers
------------------------
1. Needed tools
```
sudo apt-get install autoconf automake libtool curl make g++ unzip
```
2. Create configure script
```
    git clone https://github.com/protocolbuffers/protobuf.git
    cd protobuf
    git submodule update --init --recursive
    ./autogen.sh
```
3. Build and install
```
    ./configure
     make
     make check
     sudo make install
     sudo ldconfig
```
Install Boost
-------------
```
    sudo apt-get install libboost-all-dev
```

Install flex package
--------------------
```
    sudo apt install
libfl-dev
```

Install Osmium library
----------------------
```
    sudo apt install libosmium2-dev
```

Modify the configure file
-------------------------
(If you get error: `invalid value: boost_major_version=`)
1. Get Boost version
```
    dpkg -s libboost-dev | grep 'Version'
```
2. Modifying
Add the gotten version to the following line:
```
eg.:
    boost_cv_lib_version="1_67_0_1"
```