#include <iostream>
#include <fstream>
void ctest1(int *);
void ctest2(int *);

int main(int argc, char *argv[])
{
    if (argc < 3)
    {
        std::cerr << "No parameter sent, must send the return file location and a statement to echo" << '\n';
        return 1;
    }
    int x = 10;
    int y = 20;

    ctest1(&x);
    ctest2(&y);

    //std::cout << x << "," << y << std::endl;
    std::string retFile = argv[1];
    std::string word = argv[2];

    std::ofstream myfile;
    myfile.open(retFile);
    myfile << word << ":" << x << "," << y << std::endl;
    myfile.close();

    return 0;
}