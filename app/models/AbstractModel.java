package models;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractModel extends play.db.ebean.Model {

    @Id
    public Long id;

    public Long getId() {
        return id;
    }

}
