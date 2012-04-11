package models;

import javax.persistence.Id;

public abstract class AbstractModel extends play.db.ebean.Model {

    @Id
    public Long id;

    public Long getId() {
        return id;
    }

}
