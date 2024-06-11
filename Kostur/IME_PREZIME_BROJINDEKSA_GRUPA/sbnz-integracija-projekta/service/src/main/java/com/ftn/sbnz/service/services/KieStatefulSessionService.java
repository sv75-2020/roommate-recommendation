package com.ftn.sbnz.service.services;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KieStatefulSessionService {

    @Autowired
    private KieContainer kieContainer;
    private KieSession eventsSession;

    public KieContainer getKieContainer() {
        return kieContainer;
    }
    public KieSession getEventsSession() {
        if(eventsSession == null){
            eventsSession = kieContainer.newKieSession("eventsSession");
        }
        return eventsSession;
    }

    public void setEventsSession(KieSession kieSession) {
        this.eventsSession = kieSession;
    }

}