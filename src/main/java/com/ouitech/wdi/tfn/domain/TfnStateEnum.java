package com.ouitech.wdi.tfn.domain;

public enum TfnStateEnum {

	//Test desactive volontairement
	INACTIVE,
	//Test en échec
	FAILED,
	//Test dont l'erreur provoquée fait partie d'un groupe d'erreur à éviter
	SKIPPED,
	//Erreur provoqué lors de l'execution du test
	ERROR,
	//Test en succès
	SUCCESS,
	//Non défini
	NONE

}
