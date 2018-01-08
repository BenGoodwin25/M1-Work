package com.example.user.myapplication;

import android.widget.TextView;

/**
 * Created by user on 12/09/17.
 */

public class CalcApp {

    final int ETAT_0 = 0;
    final int ETAT_1 = 1;
    final int ETAT_2 = 2;
    final int ETAT_3 = 3;

    int etat;

    String operandeTexte;
    double accumulateur;
    double operande;

    final int OP_PLUS = 0;
    final int OP_MOINS = 1;
    final int OP_MULT = 2;
    final int OP_DIV = 3;
    int operationPrecedente;

    TextView cw;

    public void setVue(TextView cw) {
        this.cw = cw;
    }

    private void miseAJourVue(String nombreAAfficher) {
        cw.setText(nombreAAfficher);
    }

    public void point() {
        if (etat == ETAT_1) {
            operandeTexte = operandeTexte + '.';
            miseAJourVue(operandeTexte);
            etat = ETAT_2;
        }
    }

    public void chiffre(String chiffreTexte) {
        if (etat == ETAT_0) {
            operandeTexte = chiffreTexte;
            miseAJourVue(operandeTexte);
            etat = ETAT_1;
        } else if (etat == ETAT_1 || etat == ETAT_3) {
            operandeTexte = operandeTexte + chiffreTexte;
            miseAJourVue(operandeTexte);
        } else if (etat == ETAT_2) {
            operandeTexte = operandeTexte + chiffreTexte;
            miseAJourVue(operandeTexte);
            etat = ETAT_3;
        }
    }

    public void plus() {
        if (etat == ETAT_1 || etat == ETAT_3) {
            double operande = Double.valueOf(operandeTexte).doubleValue();
            switch (operationPrecedente) {
                case OP_PLUS:
                    accumulateur += operande;
                    break;
                case OP_MOINS:
                    accumulateur -= operande;
                    break;
                case OP_MULT:
                    accumulateur *= operande;
                    break;
                case OP_DIV:
                    accumulateur /= operande;
                    break;
            }
            operationPrecedente = OP_PLUS;
            miseAJourVue(new Double(accumulateur).toString());
            etat = ETAT_0;
        }
    }

    public void moins() {
        if (etat == ETAT_1 || etat == ETAT_3) {
            double operande = Double.valueOf(operandeTexte).doubleValue();
            switch (operationPrecedente) {
                case OP_PLUS:
                    accumulateur += operande;
                    break;
                case OP_MOINS:
                    accumulateur -= operande;
                    break;
                case OP_MULT:
                    accumulateur *= operande;
                    break;
                case OP_DIV:
                    accumulateur /= operande;
                    break;
            }
            operationPrecedente = OP_MOINS;
            miseAJourVue(new Double(accumulateur).toString());
            etat = ETAT_0;
        }
    }

    public void mult() {
        if (etat == ETAT_1 || etat == ETAT_3) {
            double operande = Double.valueOf(operandeTexte).doubleValue();
            switch (operationPrecedente) {
                case OP_PLUS:
                    accumulateur += operande;
                    break;
                case OP_MOINS:
                    accumulateur -= operande;
                    break;
                case OP_MULT:
                    accumulateur *= operande;
                    break;
                case OP_DIV:
                    accumulateur /= operande;
                    break;
            }
            operationPrecedente = OP_MULT;
            miseAJourVue(new Double(accumulateur).toString());
            etat = ETAT_0;
        }
    }

    public void div() {
        if (etat == ETAT_1 || etat == ETAT_3) {
            double operande = Double.valueOf(operandeTexte).doubleValue();
            switch (operationPrecedente) {
                case OP_PLUS:
                    accumulateur += operande;
                    break;
                case OP_MOINS:
                    accumulateur -= operande;
                    break;
                case OP_MULT:
                    accumulateur *= operande;
                    break;
                case OP_DIV:
                    accumulateur /= operande;
                    break;
            }
            operationPrecedente = OP_DIV;
            miseAJourVue(new Double(accumulateur).toString());
            etat = ETAT_0;
        }
    }

    public void egal() {
        if (etat == ETAT_1 || etat == ETAT_3) {
            double operande = Double.valueOf(operandeTexte).doubleValue();
            switch (operationPrecedente) {
                case OP_PLUS:
                    accumulateur += operande;
                    break;
                case OP_MOINS:
                    accumulateur -= operande;
                    break;
                case OP_MULT:
                    accumulateur *= operande;
                    break;
                case OP_DIV:
                    accumulateur /= operande;
                    break;
            }
            miseAJourVue(new Double(accumulateur).toString());
            operationPrecedente = OP_PLUS;
            accumulateur = 0;
            etat = ETAT_0;
        }
    }

    private void terminer() {
        System.exit(0);
    }

    public void cloreVue() {
        terminer();
    }

    public void fin() {
        terminer();
    }

    public CalcApp(TextView cw) {

        accumulateur = 0;
        operationPrecedente = OP_PLUS;
        operandeTexte = "0";

        etat = ETAT_0;

        setVue (cw) ;
        miseAJourVue(operandeTexte);
    }

}
