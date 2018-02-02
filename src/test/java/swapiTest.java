import com.google.gson.Gson;
import io.restassured.response.Response;
import models.AllFilmsList;
import models.AllPeopleList;
import models.Planet;
import models.Starship;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;

/**
 * Пожалуйста, напишите тесты на API сервиса "Звездные войны".
 * Ваша задача написать тесты, используя GET Responses, отвечающие на следующие вопросы и проверяющие это:
 * <p>
 * Сколько фильмов в серии "Звездные войны"?
 * В каком году вышел 4 эпизод? Кто был режиссёром? Как он назывался?
 * Как назывались планеты во 2-ом эпизоде?
 * Какая родная планета Luke Skywalker?
 * На каком звездолете в летал Obi-Wan Kenobi в 3 эпизоде? Какими характеристиками этот звездолет обладал?
 * Существует ли 100-ый персонаж?
 */

public class swapiTest {

    @Test
    public void countFilms() throws InterruptedException {
        System.out.println();
        System.out.println("Сколько фильмов в серии \"Звездные войны\"?");

        Response response = get("https://swapi.co/api/films");
        assertEquals(200, response.getStatusCode());
        String json = response.asString();
        Gson gson = new Gson();
        AllFilmsList allFilmsList = gson.fromJson(json, AllFilmsList.class);

        System.out.println("В серии \"Звездные войны\" " + allFilmsList.count + " фильмов");
        assertEquals(7, allFilmsList.count);
    }

    @Test
    public void fourthEpisode() throws InterruptedException {
        System.out.println();
        System.out.println("В каком году вышел 4 эпизод? Кто был режиссёром? Как он назывался?");

        Response response = get("https://swapi.co/api/films");
        assertEquals(200, response.getStatusCode());
        String json = response.asString();
        Gson gson = new Gson();
        AllFilmsList allFilms = gson.fromJson(json, AllFilmsList.class);
        String year = null;
        String producer = null;
        String title = null;

        for (int i = 0; i < allFilms.results.size(); i++) {
            if (allFilms.results.get(i).episodeId == 4) {

                year = allFilms.results.get(i).release_date.substring(0, 4);
                producer = allFilms.results.get(i).producer;
                title = allFilms.results.get(i).title;
            }
        }

        System.out.println("4 эпизод вышел в " + year + " году");
        assertEquals("1977", year);

        System.out.println("Режиссёром был " + producer);
        assertEquals("Gary Kurtz, Rick McCallum", producer);

        System.out.println("Название эпизода " + title);
        assertEquals("A New Hope", title);
    }


    @Test
    public void planetsNamesEpisodeTwo() throws InterruptedException {
        System.out.println();
        System.out.println("Как назывались планеты во 2-ом эпизоде?");

        Response response1 = get("https://swapi.co/api/films");
        assertEquals(200, response1.getStatusCode());
        String json = response1.asString();
        Gson gson = new Gson();
        AllFilmsList allFilms = gson.fromJson(json, AllFilmsList.class);

        ArrayList<String> planetsUrls = new ArrayList<String>();
        ArrayList<String> actualPlanetsNames = new ArrayList<String>();
        String[] expectedPlanetsNames = {"Naboo", "Coruscant", "Kamino", "Geonosis", "Tatooine"};

        for (int i = 0; i < allFilms.results.size(); i++) {
            if (allFilms.results.get(i).episodeId == 2) {

                planetsUrls.addAll(allFilms.results.get(i).planetsUrls);
            }
        }

        for (int i = 0; i < planetsUrls.size(); i++) {
            Response response2 = get(planetsUrls.get(i));
            assertEquals(200, response2.getStatusCode());
            String jsonPlanet = response2.asString();
            Gson gsonPlanet = new Gson();
            Planet planet = gsonPlanet.fromJson(jsonPlanet, Planet.class);
            actualPlanetsNames.add(planet.name);
        }

        for (int i = 0; i < actualPlanetsNames.size(); i++) {
            System.out.println(actualPlanetsNames.get(i));
            assertEquals(expectedPlanetsNames[i], actualPlanetsNames.get(i));
        }
    }

    @Test
    public void lukeSkywalkerHomeworld() throws InterruptedException {
        System.out.println();
        System.out.println("Какая родная планета Luke Skywalker?");

        Response response1 = get("https://swapi.co/api/people");
        assertEquals(200, response1.getStatusCode());
        String json1 = response1.asString();
        Gson gson1 = new Gson();
        AllPeopleList allPeopleList = gson1.fromJson(json1, AllPeopleList.class);
        String homeWorldUrl = null;

        for (int i = 0; i < allPeopleList.results.size(); i++) {
            if (allPeopleList.results.get(i).name.equals("Luke Skywalker")) {
                homeWorldUrl = allPeopleList.results.get(i).homeWorldUrl;
            }
        }

        Response response2 = get(homeWorldUrl);
        assertEquals(200, response2.getStatusCode());
        String json2 = response2.asString();
        Gson gson2 = new Gson();
        Planet skywalkerHome = gson2.fromJson(json2, Planet.class);
        System.out.println("Родная планета Luke Skywalker - " + skywalkerHome.name);
        assertEquals("Tatooine", skywalkerHome.name);
    }

    @Test
    public void starshipObiWanKenobi() throws InterruptedException {
        System.out.println();
        System.out.println("На каком звездолете в летал Obi-Wan Kenobi в 3 эпизоде? Какими характеристиками этот звездолет обладал?");

        Response response1 = get("https://swapi.co/api/people");
        assertEquals(200, response1.getStatusCode());
        String json1 = response1.asString();
        Gson gson1 = new Gson();
        AllPeopleList allPeopleList = gson1.fromJson(json1, AllPeopleList.class);

        ArrayList<String> allKenobiStarshipsUrl = new ArrayList<String>();
        ArrayList<String> allStarshipsEpisodThreeUrl = new ArrayList<String>();

        String kenobiStarshipUrl = null;

        for (int i = 0; i < allPeopleList.results.size(); i++) {
            if (allPeopleList.results.get(i).name.equals("Obi-Wan Kenobi")) {
                allKenobiStarshipsUrl.addAll(allPeopleList.results.get(i).starshipsUrls);
            }
        }

        Response response2 = get("https://swapi.co/api/films");
        assertEquals(200, response2.getStatusCode());
        String json2 = response2.asString();
        Gson gson2 = new Gson();
        AllFilmsList allFilmsList = gson2.fromJson(json2, AllFilmsList.class);

        for (int i = 0; i < allFilmsList.results.size(); i++) {
            if (allFilmsList.results.get(i).episodeId == 3) {
                allStarshipsEpisodThreeUrl.addAll(allFilmsList.results.get(i).starshipsUrls);
            }
        }

        for (int i = 0; i < allKenobiStarshipsUrl.size(); i++) {
            for (int j = 0; j < allStarshipsEpisodThreeUrl.size(); j++) {

                if (allKenobiStarshipsUrl.get(i).equals(allStarshipsEpisodThreeUrl.get(j))) {
                    kenobiStarshipUrl = allKenobiStarshipsUrl.get(i);
                    i = allKenobiStarshipsUrl.size();
                    break;
                }
            }
        }

        Response response3 = get(kenobiStarshipUrl);
        assertEquals(200, response3.getStatusCode());
        String json3 = response3.asString();
        Gson gson3 = new Gson();
        Starship kenobiStarship = gson3.fromJson(json3, Starship.class);

        System.out.println("Obi-Wan Kenobi в третьем эпизоде летал на звездолёте " + kenobiStarship.name);
        assertEquals("Jedi starfighter", kenobiStarship.name);

        System.out.println("Длина звездолёта - " + kenobiStarship.length);
        assertEquals("8", kenobiStarship.length);

        System.out.println("Максимальная атмосферная скорость - " + kenobiStarship.maxAtmospheringSpeed);
        assertEquals("1150", kenobiStarship.maxAtmospheringSpeed);

        System.out.println("Экипаж - " + kenobiStarship.crew);
        assertEquals("1", kenobiStarship.crew);

        System.out.println("Класс звездолёта - " + kenobiStarship.starshipClass);
        assertEquals("Starfighter", kenobiStarship.starshipClass);
    }

    @Test
    public void isCharacter100() throws InterruptedException {
        System.out.println();
        System.out.println("Существует ли 100-ый персонаж?");

        Response response1 = get("https://swapi.co/api/people");
        assertEquals(200, response1.getStatusCode());
        String json1 = response1.asString();
        Gson gson1 = new Gson();
        AllPeopleList allPeopleList = gson1.fromJson(json1, AllPeopleList.class);

        System.out.println("Общее количество персонажей - " + allPeopleList.count);
        assertEquals(87, allPeopleList.count);

        System.out.println("100-ый персонаж не существует");
        Response response2 = get("https://swapi.co/api/people/100");
        assertEquals(404, response2.getStatusCode());

    }
}





