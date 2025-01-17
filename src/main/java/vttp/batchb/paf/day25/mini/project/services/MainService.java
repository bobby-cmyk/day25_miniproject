package vttp.batchb.paf.day25.mini.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batchb.paf.day25.mini.project.repositories.MainRepository;

@Service
public class MainService {

    @Autowired
    private MainRepository mainRepo;

    @Autowired
    private GPTService gptSvc;
    
    public String getResultsStr(String query) {
        
        String gptSQLquery = gptSvc.generateSQLquery(query);

        System.err.printf("\nGPT SQL: %s\n", gptSQLquery);

        String databaseResults = mainRepo.getResults(gptSQLquery).toString();

        System.err.printf("\nDATABASE RESULTS: %s\n", databaseResults);

        return gptSvc.generateFriendlyResponse(databaseResults);
    }


}
