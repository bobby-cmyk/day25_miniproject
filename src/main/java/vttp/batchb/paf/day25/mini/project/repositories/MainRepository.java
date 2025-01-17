package vttp.batchb.paf.day25.mini.project.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MainRepository {
    
    @Autowired
    private JdbcTemplate template;
    
    public List<Map<String, Object>> getResults(String query) {
        
        List<Map<String, Object>> results = template.queryForList(query);

        return results;
    }
}
