package net.engineeringdigest.journalApp.respository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query= new Query();
        query.addCriteria(Criteria.where("userName").is("aman"));
        List<User> users= mongoTemplate.find(query, User.class);
        System.out.println("Current DB: "+mongoTemplate.getDb().getName());
        return users;
    }
}
