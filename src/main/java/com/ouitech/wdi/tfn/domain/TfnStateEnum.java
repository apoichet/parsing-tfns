package com.ouitech.wdi.tfn.domain;

public enum TfnStateEnum {

	//Test que l'on a choisit volontairement de desactiver
	FAILED,
	//Test dont l'erreur provoquée fait partie d'un groupe d'erreur à éviter
	SKIPPED,
	//Test en erreur
	ERROR,
	//Test en succès
	SUCCESS,
	//Non défini
	NONE

}
