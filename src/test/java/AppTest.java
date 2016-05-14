import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band Tracker");
    assertThat(pageSource()).contains("Bands");
    assertThat(pageSource()).contains("Venues");
  }
// Band test section ---------------
  @Test
  public void bandDoesNotExistTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Bands"));
    assertThat(pageSource()).contains("Currently, no band in databases");
  }

  @Test
  public void bandIsCreatedAndDisplayedTest() {
    goTo("http://localhost:4567/bands");
    fill("#input_band").with("U2");
    submit("#add_button_band");
    assertThat(pageSource()).contains("U2");
  }

  @Test
  public void bandBlankInputFieldIsAddedTest() {
    goTo("http://localhost:4567/bands");
    fill("#input_band").with("");
    submit("#add_button_band");
    assertThat(pageSource()).doesNotContain("U2");
  }

  @Test
  public void bandIsDeletedTest() {
    goTo("http://localhost:4567/bands");
    fill("#input_band").with("U2");
    submit("#add_button_band");
    click("option", withText("U2"));
    submit("#delete_button_band");
    assertThat(pageSource()).doesNotContain("U2");
  }

  @Test
  public void bandIsUpdatedTest() {
    Band testBand = new Band("U2");
    testBand.save();
    goTo("http://localhost:4567/bands");
    click("option", withText("U2"));
    fill("#input_update_band").with("Gun N Roses");
    submit("#update_button_band");
    assertThat(pageSource()).contains("Gun N Roses");
  }

  @Test
  public void bandShowPageDisplaysName() {
    Band testBand = new Band("AC/DC");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("AC/DC");
  }

// Venue test section --------------
  @Test
  public void venueDoesNotExistTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Venues"));
    assertThat(pageSource()).contains("Currently, no venue in databases");
  }

  @Test
  public void venueIsCreatedAndDisplayedTest() {
    goTo("http://localhost:4567/venues");
    fill("#input_venue").with("Rose Garden");
    submit("#add_button_venue");
    assertThat(pageSource()).contains("Rose Garden");
  }

  @Test
  public void venueBlankInputFieldIsAddedTest() {
    goTo("http://localhost:4567/venues");
    fill("#input_venue").with("");
    submit("#add_button_venue");
    assertThat(pageSource()).doesNotContain("Rose Garden");
  }

  @Test
  public void venueIsDeletedTest() {
    goTo("http://localhost:4567/venues");
    fill("#input_venue").with("Rose Garden");
    submit("#add_button_venue");
    click("option", withText("Rose Garden"));
    submit("#delete_button_venue");
    assertThat(pageSource()).doesNotContain("Rose Garden");
  }

  @Test
  public void venueIsUpdatedTest() {
    Venue testVenue = new Venue("Rose Garden");
    testVenue.save();
    goTo("http://localhost:4567/venues");
    click("option", withText("Rose Garden"));
    fill("#input_update_venue").with("Pioneer Square");
    submit("#update_button_venue");
    assertThat(pageSource()).contains("Pioneer Square");
  }

  @Test
  public void venueShowPageDisplaysName() {
    Venue testVenue = new Venue("Pioneer Square");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(url);
    assertThat(pageSource()).contains("Pioneer Square");
  }
////////////////////////////////////////////////
  //
  // @Test
  // public void venueIsAddedToBandTest() {
  //   Band testBand = new Band("U2");
  //   testBand.save();
  //   String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
  //   goTo(url);
  //   fill("#venue_input").with("Park Block");
  //   submit("#add_venue");
  //   assertThat(pageSource()).contains("Park Block");
  // }
  //
  // @Test
  // public void bandNameIsUpdatedTest() {
  //   Band testBand = new Band("U2");
  //   testBand.save();
  //   String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
  //   goTo(url);
  //   fill("#update_band").with("U2 Classic");
  //   submit("#update_band_button");
  //   goTo(url);
  //   assertThat(pageSource()).contains("U2 Classic");
  // }
  //
}
