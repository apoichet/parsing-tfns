package com.ouitech.wdi.tfn.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public abstract class AbstractBuilderTfn<LAUNCH extends TfnLaunch, RESULT extends TfnResult>{
	/**
	 * Converter Tfn
	 */
	private TfnConverter<LAUNCH, RESULT> converter;

	/**
	 * Manager Status Tfn
	 */
	private TfnStatusManager<LAUNCH> manager;

	public AbstractBuilderTfn(TfnConverter<LAUNCH, RESULT> converter, TfnStatusManager<LAUNCH> manager) {
		this.converter = converter;
		this.manager = manager;
	}

	/**
	 * Permet de construire les résultats de validation des tests fonctionnels en objet java
	 *
	 * @return Liste d'objet java tfn resultats
	 */
	public Collection<RESULT> build(){

		Collection<LAUNCH> TfnLaunches = load();
		Collection<RESULT> TfnResults = new ArrayList<>();

		for (LAUNCH tfnLaunch : TfnLaunches) {

			RESULT tfnResult = converter.transform(tfnLaunch);

			//Business
			tfnResult.setStatus(manager.define(tfnLaunch));

			TfnResults.add(tfnResult);
			
		}
		
		return TfnResults.stream()
				.sorted(compare())
				.collect(Collectors.toList());
	}

	/**
	 * Chargement /Parsing des Tfns lancés par un job de validation
	 *
	 * @return tfn lauches
	 */
	public abstract Collection<LAUNCH> load();

	/**
	 * Compare les résultats
	 *
	 * @return tfn results
	 */
	public abstract Comparator<RESULT> compare();

}
