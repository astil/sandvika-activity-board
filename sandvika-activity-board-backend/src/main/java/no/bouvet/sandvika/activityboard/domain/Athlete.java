package no.bouvet.sandvika.activityboard.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Athlete
{
    @Id
    private String lastName;
    private List<Handicap> handicapList;

    public List<Handicap> getHandicapList()
    {
        if (this.handicapList == null)
        {
            this.handicapList = new ArrayList<>();
        }
        return handicapList;
    }

    public void setHandicapList(List<Handicap> handicapList)
    {
        this.handicapList = handicapList;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Athlete athlete = (Athlete) o;

        if (lastName != null ? !lastName.equals(athlete.lastName) : athlete.lastName != null)
        {
            return false;
        }
        return handicapList != null ? handicapList.equals(athlete.handicapList) : athlete.handicapList == null;
    }

    @Override
    public int hashCode()
    {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (handicapList != null ? handicapList.hashCode() : 0);
        return result;
    }

    public double getHandicapForDate(Date startDateLocal)
    {
        return handicapList.stream()
            .filter(h -> h.getTimestamp().before(startDateLocal))
            .sorted(Comparator.comparing(Handicap::getTimestamp).reversed())
            .limit(1)
            .mapToDouble(Handicap::getHandicap)
            .sum();
    }
}
