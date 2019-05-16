package com.clientui.proxies;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.clientui.beans.ExpeditionBean;


@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-expedition")
public interface MicroserviceExpeditionProxy {
	
	@GetMapping(value = "/microservice-expedition/suivis")
	List<ExpeditionBean> listeDesExpeditions();

	@GetMapping( value = "/microservice-expedition/suivi/{id}")
	ExpeditionBean recupererUneExpedition(@PathVariable("id") int id);
	
    @PostMapping(value = "/microservice-expedition/suivi")
    ExpeditionBean ajouterExpedition(@RequestBody ExpeditionBean expedition);
}
