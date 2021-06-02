package com.bodamed.ussd.domain.claim;

import java.util.List;

/**
 * @author Collins Magondu
 */
public class ClaimProcessor {
    private int id;
    private String name;
    private List<ClaimProcessorContract> contracts;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setContracts(List<ClaimProcessorContract> contracts) {
        this.contracts = contracts;
    }

    public List<ClaimProcessorContract> getContracts() {
        return contracts;
    }
}
