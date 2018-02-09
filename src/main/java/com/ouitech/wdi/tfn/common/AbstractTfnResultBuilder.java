package com.ouitech.wdi.tfn.common;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractTfnResultBuilder<TFN extends TfnResult> {
	/**
	 * Printer Tfn
	 */
	private Optional<TfnPrinter<TFN>> printer;

	/**
	 * Permet de construire les résultats de validation des tests fonctionnels en objet java
	 *
	 * @return Liste d'objet java tfn resultats
	 */
	public abstract Collection<TFN> build();

	public String print(TFN tfnResult){
		if (printer.isPresent()) {
			return printer.get().print(tfnResult);
		}
		throw new IllegalArgumentException("Pas de printer prédéfini !");
	}

	public void withPrinter(final Optional<TfnPrinter<TFN>> printer) {
		this.printer = printer;
	}
}
