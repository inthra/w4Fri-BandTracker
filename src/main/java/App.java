import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// Band section ------------------------------
    get("/bands", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String input_band = request.queryParams("input_band");
      Band newBand = new Band(input_band);
      if ((newBand.getName()).trim().length() != 0) {
        newBand.save();
      }
      response.redirect("/bands");
      return null;
    });

    post("/bands/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String delete_band = request.queryParams("delete_band");
      if (!(delete_band.equals(""))) {
        Band band = Band.find(Integer.parseInt(delete_band));
        band.delete();
      }
      response.redirect("/bands");
      return null;
    });

    post("/bands/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String selectedBand = request.queryParams("update_band");
      String inputUpdate = request.queryParams("input_update_band");
      if (!(selectedBand.equals(""))) {
        Band band = Band.find(Integer.parseInt(selectedBand));
        band.update(inputUpdate);
      }
      response.redirect("/bands");
      return null;
    });

    get("/bands/:band_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      model.put("allVenues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// Venue section ------------------------------
    get("/venues", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String input_venue = request.queryParams("input_venue");
      Venue newVenue = new Venue(input_venue);
      if ((newVenue.getName()).trim().length() != 0) {
        newVenue.save();
      }
      response.redirect("/venues");
      return null;
    });

    post("/venues/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String delete_venue = request.queryParams("delete_venue");
      if (!(delete_venue.equals(""))) {
        Venue venue = Venue.find(Integer.parseInt(delete_venue));
        venue.delete();
      }
      response.redirect("/venues");
      return null;
    });

    post("/venues/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String selectedVenue = request.queryParams("update_venue");
      String inputUpdate = request.queryParams("input_update_venue");
      if (!(selectedVenue.equals(""))) {
        Venue venue = Venue.find(Integer.parseInt(selectedVenue));
        venue.update(inputUpdate);
      }
      response.redirect("/venues");
      return null;
    });

    get("/venues/:venue_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      model.put("venue", venue);
      model.put("allBands", Band.all());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

// Join table section ------------------------------
    post("/bands/:band_id/add_venues", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int bandId = Integer.parseInt(request.params(":band_id"));
      int selectedVenueId = Integer.parseInt(request.queryParams("venue_id"));
      Band band = Band.find(bandId);
      Venue venue = Venue.find(selectedVenueId);
      band.addVenue(venue);
      response.redirect("/bands/" + bandId);
      return null;
    });
  }
}
