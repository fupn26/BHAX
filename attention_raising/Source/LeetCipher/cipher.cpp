#include "cipher.h"

Cipher::Cipher()
{
    m_vector = {

        {'a', {"4", "4", "@", "/-\\"}},
        {'b', {"b", "8", "|3", "|}"}},
        {'c', {"c", "(", "<", "{"}},
        {'d', {"d", "|)", "|]", "|}"}},
        {'e', {"3", "3", "3", "3"}},
        {'f', {"f", "|=", "ph", "|#"}},
        {'g', {"g", "6", "[", "[+"}},
        {'h', {"h", "4", "|-|", "[-]"}},
        {'i', {"1", "1", "|", "!"}},
        {'j', {"j", "7", "_|", "_/"}},
        {'k', {"k", "|<", "1<", "|{"}},
        {'l', {"l", "1", "|", "|_"}},
        {'m', {"m", "44", "(V)", "|\\/|"}},
        {'n', {"n", "|\\|", "/\\/", "/V"}},
        {'o', {"0", "0", "()", "[]"}},
        {'p', {"p", "/o", "|D", "|o"}},
        {'q', {"q", "9", "O_", "(,)"}},
        {'r', {"r", "12", "12", "|2"}},
        {'s', {"s", "5", "$", "$"}},
        {'t', {"t", "7", "7", "'|'"}},
        {'u', {"u", "|_|", "(_)", "[_]"}},
        {'v', {"v", "\\/", "\\/", "\\/"}},
        {'w', {"w", "VV", "\\/\\/", "(/\\)"}},
        {'x', {"x", "%", ")(", ")("}},
        {'y', {"y", "", "", ""}},
        {'z', {"z", "2", "7_", ">_"}},

        {'0', {"D", "0", "D", "0"}},
        {'1', {"I", "I", "L", "L"}},
        {'2', {"Z", "Z", "Z", "e"}},
        {'3', {"E", "E", "E", "E"}},
        {'4', {"h", "h", "A", "A"}},
        {'5', {"S", "S", "S", "S"}},
        {'6', {"b", "b", "G", "G"}},
        {'7', {"T", "T", "j", "j"}},
        {'8', {"X", "X", "X", "X"}},
        {'9', {"g", "g", "j", "j"}}

      // https://simple.wikipedia.org/wiki/Leet
        };

}

QString Cipher::get_result(){
    return m_result;
}

void Cipher::encryption(QString input){
    m_result.clear();
    for (int x = 0; x < input.length(); ++x){
        int found = 0;
        for(int i=0; i<m_vector.length(); ++i)
        {

            if(m_vector[i].c == input[x].toLower())
            {

//                int r = 1+(int) (100.0*rand()/(RAND_MAX+1.0));
                int r = static_cast<int>(QRandomGenerator::global()->generate()%4);

                m_result.append(m_vector[i].leet[r]);
//                if(r<91)
//                    printf("%s", l337d1c7[i].leet[0]);
//                else if(r<95)
//                    printf("%s", l337d1c7[i].leet[1]);
//                else if(r<98)
//                    printf("%s", l337d1c7[i].leet[2]);
//                else
//                    printf("%s", l337d1c7[i].leet[3]);

                found = 1;
                break;
            }

        }

        if(!found)
//            printf("%c", *yytext);
            m_result.append(input[x]);
    }
}
