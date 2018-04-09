package no.bouvet.sandvika.activityboard.domain;

import java.util.Date;
import java.util.List;

public class Club {
    private Integer id;
    private String name;
    private List<Integer> memberIds;
    private Date competitonStartDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Integer> memberIds) {
        this.memberIds = memberIds;
    }

    public Date getCompetitonStartDate() {
        return competitonStartDate;
    }

    public void setCompetitonStartDate(Date competitonStartDate) {
        this.competitonStartDate = competitonStartDate;
    }
}
