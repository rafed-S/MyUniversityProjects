#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QtDebug>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}


void MainWindow::on_compte_clicked()
{
    ui->stackedWidget->setCurrentIndex(1); // Vers la page de création
}


void MainWindow::on_returnFromCreerCompt_clicked()
{
    ui->stackedWidget->setCurrentIndex(0); // Retour vers la page initiale
}


void MainWindow::on_connecter_clicked()
{
    ui->stackedWidget->setCurrentIndex(2); // Vers écran de connexion
}


void MainWindow::on_returnFromSeConnecter_clicked()
{
    ui->stackedWidget->setCurrentIndex(0); // Retour vers la page initiale
}


void MainWindow::on_seConnecterDansSeConnecter_clicked()
{
    // Récupération du nom du client
    QString name = ui->nomEditConnecter->toPlainText();
    //qDebug().nospace() << name;
}

