package tqs.es.bookworms.Entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String location;
    @ElementCollection //lista de riders
    private List<Long> riderIds = new ArrayList<>();
    @ElementCollection //lista de livros
    private List<Long> bookIds = new ArrayList<>();
    public Location(){}

    public Location(String location, List<Long> riderIds, List<Long> bookIds) {
        this.location = location;
        this.riderIds = riderIds;
        this.bookIds = bookIds;
    }

    public List<Long> getRiderIds() {
        return riderIds;
    }

    public void setRiderIds(List<Long> riderIds) {
        this.riderIds = riderIds;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
