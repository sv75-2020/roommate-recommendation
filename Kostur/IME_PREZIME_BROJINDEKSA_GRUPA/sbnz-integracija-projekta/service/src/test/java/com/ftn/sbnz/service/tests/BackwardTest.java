package com.ftn.sbnz.service.tests;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;



public class BackwardTest {
    
    @Test
    public void TestBackward(){
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer(); 
        KieSession ksession = kContainer.newKieSession("bwKsession");

        ksession.fireAllRules();
      
    }
}
