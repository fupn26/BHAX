#ifndef CIPHER_H
#define CIPHER_H

#include <QVector>
#include <QRandomGenerator>

class Cipher
{
public:
    Cipher();
    QString get_result();
    void encryption(QString);

private:
    struct m_cipher{
        char c;
        char const *leet[4];
    };

    QVector<struct m_cipher> m_vector;

    QString m_result;
};

#endif // CIPHER_H
