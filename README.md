## Tämä on Ohjelmointi 2, JSP - kurssin harjoitustyö.

Projektissa on käytetty lähteenä Vaadin - tutoriaalia: https://vaadin.com/learn/tutorials/modern-web-apps-with-spring-boot-and-vaadin

## Harjoitustyö

Tämä harjoitus työ on funktionaalinen full-stack web-sovellus, mikä on kirjoitettu modernilla Javalla. Harjoitustyössä on seurattu Vaadinin opastusta modernin web-sovelluksen kehittämiseksi. Web-sovellus on muunnelma Ohjelmointi 2 - kurssilla tehdystä "Kerho" - henkisestä harjoitustyöstä.

### Ominaisuudet

- Kirjautusmisruutu
- Responsiivinen leiska.
- Tietokanta, mihin voi tallentaa, sekä mistä voi lukea dataa.
- Listanäkymä, missä voi järjestää ja filtteröidä dataa.
- Lomake, missä voi muokata asiakasdataa.
- Datan tuonti REST - rajapinnan kautta.
- Dashboard, missä on yleisnäkymä datasta.
- Sovelluksen voi ladata itselleen PWA - sovelluksena. Sovellus lisäksi deployataan verkkoon testimielessä.

### Teknologiat

- Visuaalisesti "ns. front-end" sovellus on toteutettu Vaadin - web-sovelluskehyksellä, joka perustuu avoimeen lähdekoodiin.
- Vaadin tarjoaa laajasti mahdollisuuden erilaisten UI - komponenttien käyttöön, ja reitityksen eri näkymien (ns. Views) välillä.

- Taustalla "ns. back-end" sovelluksen muskeleina toimii Spring Boot, joka on Java:lla kirjoitettu web-sovelluskehys, millä on mahdollista luoda erilaisia web-sovelluksia.
- Olennaisimmat Spring Bootin ominaisuudet mitä projektissa on käytetty
* Riippuuvuusinjektiot, millä pystyy erotella komponentit toisistaan.
* Spring Data JPA, sovelluskehitysrajapinta, relationaalisen datan hallintaan. Näin sovellus voi tallentaa muistista dataa tietokantaan.
* Spring Security, tietoturvakehys autentikoinnin ja saavutettavuuden rajoittamiseksi.
* Tomcat, sisäänrakennettu "servlet", eli Javan ohjelmistokomponentti, joka käsittelee selaimen palvelinpyynnöt ja välittää nämä palvelimelle.
* Spring Boot, ohjelmistokehitystyökalut.

## Mitä uutta opin projektissa ?

- Opin käyttämään erilaisia moderneja web-kehitystekniikoita, ja soveltamaan oppimaani Ohjelmointi 2 - kurssilta.
- Teknologia: Maven
 * Projektin riippuvuuksien hallintaan suunnattu teknologia, tämä oli uusi tuttavuus.
- Ohjelmisto: IntelliJ IDE
 * Opettelin käyttämään uutta ohjelmistoympäristöä.
- Teknologia: Amazon Corretto
 * Oracle siirsi oman JDK:n kaupalliseksi tuotteeksi, tämän myötä tutustuin muihin vaihtoehtoihin, valitsin projektiin ohjeiden suositusten mukaisesti "Amazon Corretto" JDK:n.

## Author

Jari Haapasaari