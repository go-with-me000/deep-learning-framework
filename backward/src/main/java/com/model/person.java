package com.model;

public class person {
    private Integer id;

    private String groupCandidateName;

    private Integer votesNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupCandidateName() {
        return groupCandidateName;
    }

    public void setGroupCandidateName(String groupCandidateName) {
        this.groupCandidateName = groupCandidateName;
    }

    public Integer getVotesNumber() {
        return votesNumber;
    }

    public void setVotesNumber(Integer votesNumber) {
        this.votesNumber = votesNumber;
    }
}
