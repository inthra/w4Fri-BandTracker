import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Band {
  private int id;
  private String band_name;

  public Band(String name) {
  this.band_name = name;
  }

  public String getBandName() {
    return band_name;
  }

  public int getBandId() {
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
      return this.getBandName().equals(newBand.getBandName()) && this.getBandId() == newBand.getBandId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(band_name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.band_name)
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

  public void update(String band_name) {
    if (band_name.trim().length() != 0) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE bands SET band_name = :name WHERE id = :id;";
        con.createQuery(sql)
          .addParameter("name", band_name)
          .addParameter("id", this.getBandId())
          .executeUpdate();
      }
    }
  }

  // public void addVenue(Venue venue) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
  //     con.createQuery(sql)
  //       .addParameter("band_id", this.getBandId())
  //       .addParameter("venue_id", venue.getVenueId())
  //       .executeUpdate();
  //   }
  // }
  //
  // public List<Venue> getVenues() {
  //   try(Connection con = DB.sql2o.open()){
  //     String joinQuery = "SELECT venue_id FROM bands_venues WHERE band_id = :band_id";
  //     List<Integer> venue_ids = con.createQuery(joinQuery)
  //       .addParameter("band_id", this.getBandId())
  //       .executeAndFetch(Integer.class);
  //
  //     List<Venue> venues = new ArrayList<Venue>();
  //
  //     for (Integer venue_id : venue_ids) {
  //       String venueQuery = "SELECT * FROM venues WHERE id = :venue_id";
  //       Venue venue = con.createQuery(venueQuery)
  //         .addParameter("venue_id", venue_id)
  //         .executeAndFetchFirst(Venue.class);
  //       venues.add(venue);
  //     }
  //     return venues;
  //   }
  // }
  //
  // public void delete() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String deleteQuery = "DELETE FROM bands WHERE id = :id;";
  //       con.createQuery(deleteQuery)
  //         .addParameter("id", this.getBandId())
  //         .executeUpdate();
  //
  //     String joinDeleteQuery = "DELETE FROM bands_venues WHERE band_id = :band_id";
  //       con.createQuery(joinDeleteQuery)
  //         .addParameter("band_id", this.getBandId())
  //         .executeUpdate();
  //   }
  // }

}
