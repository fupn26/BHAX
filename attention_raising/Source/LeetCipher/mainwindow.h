#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QGridLayout>
#include <QLabel>
#include <QTextEdit>
#include <QPushButton>
#include <QCheckBox>

#include <cipher.h>

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private:
    QGridLayout     *m_layout;
    QWidget         *m_widget;
    QTextEdit       *m_textedit_result;
    QTextEdit       *m_textedit;
    QPushButton     *m_pushbutton;
    Cipher          *m_cipher;
    QCheckBox       *m_checkbox;

    bool            autotrans_on;

public slots:
    void get_encription();

    void set_autotrans_on();
};
#endif // MAINWINDOW_H
