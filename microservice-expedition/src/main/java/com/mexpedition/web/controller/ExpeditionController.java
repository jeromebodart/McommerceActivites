package com.mexpedition.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mexpedition.dao.ExpeditionDao;
import com.mexpedition.model.Expedition;
import com.mexpedition.web.exceptions.ExpeditionNotFound;
import com.mexpedition.web.exceptions.ImpossibleAjouterExpeditionException;



@RestController
public class ExpeditionController {

	@Autowired
	private ExpeditionDao expeditionDao;

	@PostMapping (value = "/suivi")
	public ResponseEntity<Expedition> ajouterExpedition(@RequestBody Expedition expedition){

		Expedition nouvelleexpedition = expeditionDao.save(expedition);

		if(nouvelleexpedition == null) throw new ImpossibleAjouterExpeditionException("Impossible d'ajouter cette expedition");

		return new ResponseEntity<Expedition>(expedition, HttpStatus.CREATED);
	}

	@GetMapping(value = "/suivi/{id}")
	public Optional<Expedition> recupererUneExpedition(@PathVariable int id){

		Optional<Expedition> expedition = expeditionDao.findById(id);

		if(!expedition.isPresent()) throw new ExpeditionNotFound("Cette expedition n'existe pas");

		return expedition;
	}

	@GetMapping(value = "/suivis")
	public List<Expedition> listeDesExpeditions(){
		return expeditionDao.findAll();
	}

	/*
	 * Permet de mettre à jour une commande existante.
	 * save() mettra à jours uniquement les champs renseignés dans l'objet commande reçu. Ainsi dans ce cas, comme le champs date dans "commande" n'est
	 * pas renseigné, la date précédemment enregistrée restera en place
	 **/
	@PutMapping(value = "/suivi")
	public void updateExpedition(@RequestBody Expedition expedition) {

		expeditionDao.save(expedition);
	}


	/*
	 * Opération pour enregistrer un paiement et notifier le microservice commandes pour mettre à jour le statut de la commande en question
	 **/

	//ajouter un produit
	/*
	 * @PostMapping(value = "/Expedition") public ResponseEntity<Void>
	 * ajouterExpedition(@Valid @RequestBody Expedition expedition) { Expedition
	 * expeditionAdd = expeditionDao.save(expedition); if (expeditionAdd == null)
	 * return ResponseEntity.noContent().build();
	 * 
	 * URI location = ServletUriComponentsBuilder .fromCurrentRequest()
	 * .path("/{id}") .buildAndExpand(expeditionAdd.getId()) .toUri(); return
	 * ResponseEntity.created(location).build(); }
	 */
}
