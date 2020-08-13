package com.bodamed.ussd.domain.finance;

/**
 * @author Collins Magondu
 */
public class Finance {
    private long id;
    private long organizationId;

    public void setId(long id) {
        this.id = id;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public long getId() {
        return id;
    }

    public long getOrganizationId() {
        return organizationId;
    }
}
