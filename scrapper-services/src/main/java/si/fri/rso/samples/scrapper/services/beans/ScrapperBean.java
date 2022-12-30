package si.fri.rso.samples.scrapper.services.beans;

//import si.fri.rso.samples.artikli.lib.Artikli;
//import si.fri.rso.samples.artikli.models.converters.ArtikliConverter;
//import si.fri.rso.samples.artikli.models.entities.ArtikliEntity;

import javax.enterprise.context.RequestScoped;
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@RequestScoped
public class ScrapperBean {

    private Logger log = Logger.getLogger(ScrapperBean.class.getName());

    public List<Float> getScrapperPrice(String artikelName) {
        ArrayList<Float> list = new ArrayList<Float>();

//        float valueMercator = scrapeMercator(artikelName);
        float valueMercator = 1;
        float valueSpar = scrapeSpar(artikelName);
//        float valueSpar = scrapeSpar();
        if (valueMercator > 0) list.add(valueMercator);
        if (valueSpar > 0) list.add(valueSpar);

        return list;
    }

    public static float scrapeSpar(String artikelName){
        float price = 0;
        try {
            /**
             * Here we create a document object,
             * Then we use JSoup to fetch the website.
             */
            Document doc = Jsoup.connect("https://www.spar.si/online/search/?q=" + artikelName).get();
//            System.out.println(doc.getAllElements().toString());
            /**
             * With the document fetched,
             * we use JSoup???s title() method to fetch the title
             */
//            System.out.printf("\nWebsite Title: %s\n\n", doc.title());

            // Get the list of repositories
            //*[@id="grid"]/div[1]/div[1]/div[4]/div[2]/strong
            Elements repositories = doc.getElementsByClass("spar-productBox__price--priceInteger");
            if (!repositories.isEmpty()) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                Element repository = repositories.first();
                System.out.println(repositories.toString());
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                price = Float.parseFloat(repository.text().replace(',','.'));
            }
            repositories = doc.getElementsByClass("spar-productBox__price--priceDecimal");
            if (!repositories.isEmpty()) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                Element repository = repositories.first();
                System.out.println(repositories.toString());
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                price = price + Float.parseFloat(repository.text().replace(',','.')) / 100;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static float scrapeMercator(String artikelName){
        float price = 0;
        try {
            /**
             * Here we create a document object,
             * Then we use JSoup to fetch the website.
             */
            Document doc = Jsoup.connect("https://trgovina.mercator.si/market/brskaj#search=" + artikelName).get();
//            System.out.println(doc.getAllElements().toString());
            /**
             * With the document fetched,
             * we use JSoup???s title() method to fetch the title
             */
//            System.out.printf("\nWebsite Title: %s\n\n", doc.title());

            // Get the list of repositories
            //*[@id="grid"]/div[1]/div[1]/div[4]/div[2]/strong
            Elements repositories = doc.getElementsByClass("lib-product-price");
            if (!repositories.isEmpty()) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                Element repository = repositories.first();
                System.out.println(repositories.toString());
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                price = Float.parseFloat(repository.text().replace(',','.'));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return price;
    }



//    @Inject
//    private EntityManager em;
//
//    public List<Artikli> getArtikli() {
//
//        TypedQuery<ArtikliEntity> query = em.createNamedQuery(
//                "ArtikliEntity.getAll", ArtikliEntity.class);
//
//        List<ArtikliEntity> resultList = query.getResultList();
//
//        return resultList.stream().map(ArtikliConverter::toDto).collect(Collectors.toList());
//
//    }
//
//    @Timed
//    public List<Artikli> getArtikliFilter(UriInfo uriInfo) {
//
//        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
//                .build();
//
//        return JPAUtils.queryEntities(em, ArtikliEntity.class, queryParameters).stream()
//                .map(ArtikliConverter::toDto).collect(Collectors.toList());
//    }
//
//    public Artikli getArtikli(Integer id) {
//
//        ArtikliEntity artikliEntity = em.find(ArtikliEntity.class, id);
//
//        if (artikliEntity == null) {
//            throw new NotFoundException();
//        }
//
//        Artikli artikli = ArtikliConverter.toDto(artikliEntity);
//
//        return artikli;
//    }
//
//    public Artikli createArtikli(Artikli artikli) {
//
//        ArtikliEntity artikliEntity = ArtikliConverter.toEntity(artikli);
//
//        try {
//            beginTx();
//            em.persist(artikliEntity);
//            commitTx();
//        }
//        catch (Exception e) {
//            rollbackTx();
//        }
//
//        if (artikliEntity.getId() == null) {
//            throw new RuntimeException("Entity was not persisted");
//        }
//
//        return ArtikliConverter.toDto(artikliEntity);
//    }
//
//    public Artikli putArtikli(Integer id, Artikli artikli) {
//
//        ArtikliEntity c = em.find(ArtikliEntity.class, id);
//
//        if (c == null) {
//            return null;
//        }
//
//        ArtikliEntity updatedArtikliEntity = ArtikliConverter.toEntity(artikli);
//
//        try {
//            beginTx();
//            updatedArtikliEntity.setId(c.getId());
//            updatedArtikliEntity = em.merge(updatedArtikliEntity);
//            commitTx();
//        }
//        catch (Exception e) {
//            rollbackTx();
//        }
//
//        return ArtikliConverter.toDto(updatedArtikliEntity);
//    }
//
//    public boolean deleteArtikli(Integer id) {
//
//        ArtikliEntity Artikli = em.find(ArtikliEntity.class, id);
//
//        if (Artikli != null) {
//            try {
//                beginTx();
//                em.remove(Artikli);
//                commitTx();
//            }
//            catch (Exception e) {
//                rollbackTx();
//            }
//        }
//        else {
//            return false;
//        }
//
//        return true;
//    }
//
//    private void beginTx() {
//        if (!em.getTransaction().isActive()) {
//            em.getTransaction().begin();
//        }
//    }
//
//    private void commitTx() {
//        if (em.getTransaction().isActive()) {
//            em.getTransaction().commit();
//        }
//    }
//
//    private void rollbackTx() {
//        if (em.getTransaction().isActive()) {
//            em.getTransaction().rollback();
//        }
//    }
}
