package my.playground.office.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetTime;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Office {

    @Id @GeneratedValue(strategy = SEQUENCE, generator = "office_seq")
    @SequenceGenerator(name = "office_seq", sequenceName = "office_seq")
    protected Integer id;

    @Basic @NotNull
    protected String city;

    @Basic @NotNull
    protected String country;

    @Basic @NotNull
    @Temporal(TemporalType.TIME) @Type(type = "my.playground.office.support.OffsetTimeType")
    @Column(columnDefinition = "TIME WITH TIME ZONE")
    protected OffsetTime openFrom;

    @Basic @NotNull
    @Temporal(TemporalType.TIME) @Type(type = "my.playground.office.support.OffsetTimeType")
    @Column(columnDefinition = "TIME WITH TIME ZONE")
    protected OffsetTime openUntil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public OffsetTime getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(OffsetTime openFrom) {
        this.openFrom = openFrom;
    }

    public OffsetTime getOpenUntil() {
        return openUntil;
    }

    public void setOpenUntil(OffsetTime openUntil) {
        this.openUntil = openUntil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return Objects.equals(city, office.city) &&
                Objects.equals(country, office.country) &&
                Objects.equals(openFrom, office.openFrom) &&
                Objects.equals(openUntil, office.openUntil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, openFrom, openUntil);
    }
}
