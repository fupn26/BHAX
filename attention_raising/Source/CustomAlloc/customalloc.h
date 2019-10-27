#ifndef CUSTOMALLOC_H
#define CUSTOMALLOC_H
#include <stddef.h>
#include <cxxabi.h>
#include <iostream>


template<typename T>
class CustomAlloc
{
public:
    CustomAlloc() {}
    CustomAlloc(const CustomAlloc&) {}
    ~CustomAlloc() {}

    using size_type = size_t;
    using value_type = T;
    using pointer = T*;
    using const_pointer = const T*;
    using reference = T&;
    using const_reference = const T&;
    using difference_tyoe = ptrdiff_t;

    pointer allocate( size_type n){
        int s;
        char* p = abi::__cxa_demangle( typeid (T).name(), 0, 0, &s);
        std::cout << "Allocating "
                  << n << " objects of "
                  << n*sizeof (T)
                  << " bytes. "
                  << typeid (T).name() << "=" << p
                  << std::endl;
        delete p;
        return reinterpret_cast<T*>(new char[n*sizeof(T)]);
    }

    void deallocate (pointer p, size_type n){
        delete[] reinterpret_cast<char *>(p);
        std::cout << "Deallocating "
                  << n << " objects of "
                  << n*sizeof (T)
                  << " bytes. "
                  << typeid (T).name() << "=" << p
                  << std::endl;

    }
};

#endif // CUSTOMALLOC_H
