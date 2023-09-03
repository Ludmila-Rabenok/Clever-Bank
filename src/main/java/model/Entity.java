package model;

import lombok.Getter;
import lombok.Setter;

/**
 * An abstract base class. Which contains one id field for each entity that inherits this class.
 */

@Getter
@Setter
public abstract class Entity {
    private Integer id;

}
