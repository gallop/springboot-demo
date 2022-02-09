package com.gallop.mongo.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


/**
 * author gallop
 * date 2021-08-23 11:34
 * Description:
 * Modified By:
 */
public class AbstractDocument {
    @Id
    private ObjectId id;

    /**
     * Returns the identifier of the document.
     *
     * @return the id
     */
    public String getId() {
        return id.toHexString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractDocument that = (AbstractDocument) obj;

        return this.id.equals(that.getId());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
