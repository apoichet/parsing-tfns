package com.ouitech.wdi.tfn.domain;

public class Results {

    public int totalTfns;
    public int totalTfns1;
    public int totalTfns3;
    public int totalTfns2;
    public int totalTfns4;
    public int totalTfns5;

    public int activateTotalTfns;
    public int activateTotalTfns1;
    public int activateTotalTfns3;
    public int activateTotalTfns2;
    public int activateTotalTfns4;
    public int activateTotalTfns5;

    public void build(Tfn tfn){

        totalTfns++;
        if (tfn.isActive())activateTotalTfns++;

        switch (tfn.nbrInterface()){

            case 1:totalTfns1++;if(tfn.isActive())activateTotalTfns1++;break;
            case 2:totalTfns2++;if(tfn.isActive())activateTotalTfns2++;break;
            case 3:totalTfns3++;if(tfn.isActive())activateTotalTfns3++;break;
            case 4:totalTfns4++;if(tfn.isActive())activateTotalTfns4++;break;
            case 5:totalTfns5++;if(tfn.isActive())activateTotalTfns5++;break;

        }

    }

}
