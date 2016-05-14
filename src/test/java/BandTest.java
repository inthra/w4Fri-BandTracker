import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly_true() {
    Band myBand = new Band("AC/DC");
    assertEquals(true, myBand instanceof Band);
  }

  @Test
  public void getName_tagInstantiatesWithBandName_String() {
    Band myBand = new Band("AC/DC");
    assertEquals("AC/DC", myBand.getName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Band.all().size());
  }

  @Test
  public void equals_returnsTrueIfBandNamesAretheSame_true() {
    Band firstBand = new Band("AC/DC");
    Band secondBand = new Band("AC/DC");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Band myBand = new Band("AC/DC");
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Band myBand = new Band("AC/DC");
    myBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(myBand.getId(), savedBand.getId());
  }

  @Test
  public void find_findBandInDatabase_true() {
    Band myBand = new Band("AC/DC");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void update_updateBandInDatabase() {
    Band myBand = new Band("AC/DC");
    myBand.save();
    myBand.update("Linkin Park");
    assertEquals("Linkin Park", Band.find(myBand.getId()).getName());
  }

  @Test
  public void addVenue_addsVenueToBand_true() {
    Band myBand = new Band("AC/DC");
    myBand.save();
    Venue myVenue = new Venue("Moda Center");
    myVenue.save();
    myBand.addVenue(myVenue);
    Venue savedVenue = myBand.getVenues().get(0);
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void getVenues_returnsAllVenues_List() {
    Band myBand = new Band("AC/DC");
    myBand.save();
    Venue myVenue = new Venue("Moda Center");
    myVenue.save();
    myBand.addVenue(myVenue);
    List savedVenues = myBand.getVenues();
    assertEquals(1, savedVenues.size());
  }

  @Test
  public void delete_deletesAllVenuesAndBandsAssociations() {
    Band myBand = new Band("AC/DC");
    myBand.save();
    Venue myVenue = new Venue("Providence Park");
    myVenue.save();
    myBand.addVenue(myVenue);
    myBand.delete();
    assertEquals(0, Band.all().size());
    assertEquals(0, myVenue.getBands().size());
  }

// Add-On feature --------------------
  @Test
  public void sortASC_sortsBandNameByAscending_List() {
    Band firstBand = new Band("Linkin Park");
    firstBand.save();
    Band secondBand = new Band("AC/DC");
    secondBand.save();
    Band thirdBand = new Band("The Beatles");
    thirdBand.save();
    List sortedBand = Band.sortASC();
    assertEquals(secondBand, sortedBand.get(0));
  }

}
