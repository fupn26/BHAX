#include "mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
{
    m_widget = new QWidget(this);
    m_layout = new QGridLayout(m_widget);
    m_textedit = new QTextEdit(m_widget);
    m_pushbutton = new QPushButton("Cipher", m_widget);
    m_textedit_result = new QTextEdit(m_widget);
    m_checkbox = new QCheckBox("Auto translate", m_widget);

    m_textedit_result->setReadOnly(true);
    m_textedit_result->setToolTip("Output");

    m_layout->addWidget(m_textedit, 0, 0);
    m_layout->addWidget(m_pushbutton, 0, 1);
    m_layout->addWidget(m_checkbox, 1, 1);
    m_layout->addWidget(m_textedit_result, 0, 2);

    m_widget->setLayout(m_layout);

    setCentralWidget(m_widget);

    m_cipher = new Cipher();

    connect(m_pushbutton, &QPushButton::clicked, this, &MainWindow::get_encription);
    connect(m_checkbox, &QCheckBox::stateChanged, this, &MainWindow::set_autotrans_on);

}

MainWindow::~MainWindow()
{
    delete m_cipher;
    delete m_checkbox;
    delete m_pushbutton;
    delete m_textedit;
    delete m_textedit_result;
    delete m_layout;
    delete m_widget;
}

void MainWindow::get_encription(){
    m_cipher->encryption(m_textedit->toPlainText());
    m_textedit_result->setText(m_cipher->get_result());
}

void MainWindow::set_autotrans_on(){
    if(m_checkbox->isChecked()){
        autotrans_on = true;
        m_pushbutton->setEnabled(false);
        connect(m_textedit, &QTextEdit::textChanged, this, &MainWindow::get_encription);
    }
    else{
        m_textedit->disconnect(this);
        m_pushbutton->setEnabled(true);
    }

}
