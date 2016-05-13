import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Band {
  private int id;
  private String name;

  public Band(String name) {
  this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) && this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id = :id;";
      Band band = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void update(String name) {
    if (name.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE bands SET name = :name WHERE id = :id;";
        con.createQuery(sql)
          .addParameter("name", name)
          .addParameter("id", this.getId())
          .executeUpdate();
      }
    }
  }

  public void addVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
        .addParameter("band_id", this.getId())
        .addParameter("venue_id", venue.getId())
        .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT venue_id FROM bands_venues WHERE band_id = :band_id";
      List<Integer> venue_ids = con.createQuery(joinQuery)
        .addParameter("band_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Venue> venues = new ArrayList<Venue>();

      for (Integer venue_id : venue_ids) {
        String venueQuery = "SELECT * FROM venues WHERE id = :venue_id";
        Venue venue = con.createQuery(venueQuery)
          .addParameter("venue_id", venue_id)
          .executeAndFetchFirst(Venue.class);
        venues.add(venue);
      }
      return venues;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM bands WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM bands_venues WHERE band_id = :band_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("band_id", this.getId())
          .executeUpdate();
    }
  }
}
