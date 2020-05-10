/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.BackEndUserSessionBeanLocal;
import ejb.session.stateless.AdminSessionBeanLocal;
import ejb.session.stateless.EmailSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ForumSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocalLocal;
import ejb.session.stateless.ProductCatalogueSessionBeanLocal;
import ejb.session.stateless.SelfCareBoxReviewsSessionBeanLocal;
import ejb.session.stateless.SellerSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Admin;
import entity.Artwork;
import entity.ArtworkOrder;
import entity.ArtworkPrice;
import entity.Customer;
import entity.Offences;
import entity.OrderHistory;
import entity.Post;
import entity.Review;
import entity.SelfCareBox;
import entity.SelfCareBoxOrder;
import entity.SelfCareSubscriptionDiscount;
import entity.Seller;
import entity.Tag;
import entity.Transaction;
import entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.DurationEnum;
import util.enumeration.FormatEnum;
import util.enumeration.PaymentTypeEnum;
import util.exception.ArtworkNotCreatedException;
import util.exception.ArtworkNotFoundException;
import util.exception.ArtworkPriceNotFoundException;
import util.exception.DeleteArtworkException;
import util.exception.InvalidLoginCredentialException;
import util.exception.InputDataValidationException;
import util.exception.OffenceExistException;
import util.exception.PostExistException;
import util.exception.ReviewExistException;
import util.exception.ReviewNotFoundException;
import util.exception.SelfCareBoxExistException;
import util.exception.SelfCareSubscriptionDiscountExistException;
import util.exception.TagExistException;
import util.exception.TagNotFoundException;
import util.exception.UnableToReportUserException;
import util.exception.UnknownPersistenceException;
import util.exception.UserAlreadyBannedException;
import util.exception.UserNotFoundException;
import util.exception.UserUsernameExistException;

/**
 *
 * @author decimatum
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private OrderSessionBeanLocalLocal orderSessionBeanLocal;

    @EJB(name = "SelfCareBoxReviewsSessionBeanLocal")
    private SelfCareBoxReviewsSessionBeanLocal selfCareBoxReviewsSessionBeanLocal;

    @EJB(name = "EmailSessionBeanLocal")
    private EmailSessionBeanLocal emailSessionBeanLocal;

    @EJB(name = "ForumSessionBeanLocal")
    private ForumSessionBeanLocal forumSessionBeanLocal;

    @EJB(name = "ProductCatalogueSessionBeanLocal")
    private ProductCatalogueSessionBeanLocal productCatalogueSessionBeanLocal;

    @EJB(name = "SellerSessionBeanLocal")
    private SellerSessionBeanLocal sellerSessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "BackEndUserSessionBeanLocal")
    private BackEndUserSessionBeanLocal backEndUserSessionBeanLocal;

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    @EJB(name = "TagSessionBeanLocal")
    private TagSessionBeanLocal tagSessionBeanLocal;

    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;

    private String destination = "";

    @PostConstruct
    public void postConstruct() {
        populateTags();
        populateUsersWithTest();
        populateOffences();
        //populateArtworks();
        //populateSelfCareBoxes();
        //populateTestFollowing();
        populateForumWithPost();
        //populateForumWithComment();
        //populateTransactionAndOrder();
    }

    public void populateUsersWithTest() {
        // Seller initiatisation
        if (sellerSessionBeanLocal.retrieveAllSellers().size() <= 0) {
            try {
                sellerSessionBeanLocal.createNewSeller(new Seller("sellerNancy", "password", "nancy", "chen", "nancyChenn@gmail.com"));
                sellerSessionBeanLocal.createNewSeller(new Seller("sellerCharles", "password", "charles", "eng", "charlesEng@gmail.com"));
                sellerSessionBeanLocal.createNewSeller(new Seller("sellerDenise", "password", "denise", "liang", "deniseLiang@hotmail.com"));
//                sellerSessionBeanLocal.createNewSeller(new Seller("artwork", "password", "artwork", "arty", "artwork@gmail.com"));
//                sellerSessionBeanLocal.createNewSeller(new Seller("vishnu", "password", "vishnu", "indian", "vishnuindian@gmail.com"));

//                Seller seller = sellerSessionBeanLocal.viewMySellerDetails("peter", "password");
//                seller.setEmail("peter@gmail.com");
//                seller.setFirstName("Deci");
//                sellerSessionBeanLocal.updateMySellerDetails(seller);
//                sellerSessionBeanLocal.deleteSeller(seller.getUserId());
//
//                List<Seller> listSeller = sellerSessionBeanLocal.retrieveAllSellers();
//                System.out.println("Number of sellers not deleted: " + listSeller.size());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

//        List<Seller> listSellers = sellerSessionBeanLocal.retrieveAllSellers();
//
//        System.out.println("---------List of Sellers ------------- ");
//        for (Seller s : listSellers) {
//            System.out.print("Username: " + s.getUsername() + " ");
//            System.out.print("Email: " + s.getEmail() + " ");
//            System.out.print("First name: " + s.getFirstName() + " ");
//            System.out.print("Last name: " + s.getLastName());
//            System.out.println();
//        }

//        //Email notification test
//        Seller ethan = listSellers.get(0);
//        try{
//            emailSessionBeanLocal.approveSellerNotificationAsync(ethan);
//        }catch(Exception ex){
//            System.out.println("Email attempt to Ethan Failed");
//        }
        //approveSeller testing
//        System.out.println("--------Testing approve seller------");
//        try {
//            adminSessionBeanLocal.approveSeller(listSellers.get(0).getUserId());
//            if (listSellers.get(0).isIsVerified() == true) {
//                System.out.println("Approve seller testing is successful");
//            } else {
//                System.out.println("Approve seller testing not successful");
//            }
//        } catch (UserNotFoundException ex) {
//            ex.printStackTrace();
//        }

        //Customer Initialisation
        System.out.println("Number of customers: " + customerSessionBeanLocal.retrieveAllCustomers().size());
        if(customerSessionBeanLocal.retrieveAllCustomers().size() <= 0){
            List<Tag> tags = tagSessionBeanLocal.retrieveAllTags();
            System.out.println("current tags = " + tags.size());

            try {
                customerSessionBeanLocal.createNewCustomer(new Customer("kaijing", "password", "kaijing", "xu", "kaijing@gmail.com", "address", "unit1", "123456"), tags);
                List<Tag> newTags = new ArrayList<Tag>();
                newTags.add(tags.get(0));
                //customerSessionBeanLocal.createNewCustomer(new Customer("customerA", "password", "CustomerA", "xu", "ashleigh@gmail.com", "address", "unit2", "123456"), newTags);
                customerSessionBeanLocal.createNewCustomer(new Customer("ashleigh", "password", "ashleigh", "xu", "ashleigh@gmail.com", "address", "unit2", "123456"), tags);
                customerSessionBeanLocal.createNewCustomer(new Customer("yiningx", "password", "xing", "xing", "yiningx@gmail.com", "address", "unit3", "123456"), tags);
//                List<Customer> listCustomer = customerSessionBeanLocal.retrieveAllCustomers();
//                System.out.println("Number of customers created: " + listCustomer.size());
//                Customer customer = customerSessionBeanLocal.retrieveCustomerByUsername("kaijing");
//                customer.setEmail("kai@gmail.com");
//                customer.setShippingAddress("Singapore");
//                customerSessionBeanLocal.updateMyCustomerProfile(customer);
//                //try to set tags for only a subset of all the tags
//                Customer ash = customerSessionBeanLocal.retrieveCustomerByUsername("customerA");
//                System.out.println("Customer " + ash.getUsername() + " tags are: " + ash.getTags());
//                //remove current tags - aka tags.get(0)
//                newTags.clear();
//                //add new tag - aka tags.get(1)
//                newTags.add(tags.get(1));
//                //now ash should be associated to tags.get(1) only
//                customerSessionBeanLocal.updateMyCustomerPreferences(ash, newTags);
//                System.out.println("Customer " + ash.getUsername() + " updated tags are: " + ash.getTags());
//
//                //delete ash
//                //ash does not have orderHistory and reviews, hence it should be deleted
//                //and tag association for ash should be un-set
//                customerSessionBeanLocal.deleteCustomer(ash.getUserId());
//                customer.setPassword("password");
//                List<Customer> currCustomers = customerSessionBeanLocal.retrieveAllCustomers();
//                System.out.println("Number of customers not deleted: " + currCustomers.size());

            } catch (InputDataValidationException | TagNotFoundException | UnknownPersistenceException | UserUsernameExistException ex) {
                ex.printStackTrace();
            }
        }

        // Admin Intialisation
        if (adminSessionBeanLocal.retrieveAllAdmins().size() <= 0) {
            try {
                adminSessionBeanLocal.createNewAdmin(new Admin("adminJesscia", "password", "Jesscia", "Huang", "jessciahuang@gmail.com"));
                adminSessionBeanLocal.createNewAdmin(new Admin("adminEmory", "password", "Emory", "Warren", "emoryWarren@hotmail.com"));
//                adminSessionBeanLocal.createNewAdmin(new Admin("baiyun", "password", "fourth", "tan", "fourthtan@gmail.com"));
//                List<Admin> admins = adminSessionBeanLocal.retrieveAllAdmins();
//                System.out.println("number of admins created = " + admins.size());
//                Admin admin1 = adminSessionBeanLocal.retrieveAdminByUsername("yitong");
//                System.out.println("Admin retreive by username success: " + admin1.getUsername());
//
//                admins = adminSessionBeanLocal.retrieveAllAdmins();
//                for (Admin admin : admins) {
//                    System.out.println("Admin ID : " + admin.getUserId());
//                    System.out.println("Admin username : " + admin.getUsername());
//                }
//
//                System.out.println("___________________________________________");
//
//                // Yining ID is 5L
//                Admin admin2 = adminSessionBeanLocal.retrieveAdminByUserId(admins.get(1).getUserId());
//                //this should be the username of Yining
//                System.out.println("Admin retrieve by userId success: " + admin2.getUsername());
//
//                //test admin update using admin2
//                admin2.setEmail("abc@gmail.com");
//                admin2.setFirstName("hi");
//                adminSessionBeanLocal.updateAdmin(admin2);
//                //this should show the new email
//                System.out.println("Admin update success: " + admin2.getEmail());
//
//                //test admin delete
//                adminSessionBeanLocal.deleteAdmin(admin2.getUserId());
                //adminSessionBeanLocal.createNewAdmin(new Admin("yitong","password","yitong","yang","yitong@gmail.com"));
            } catch (UserUsernameExistException | UnknownPersistenceException | InputDataValidationException ex) {
                ex.printStackTrace();
            }
        }

//        try {
//            //try login for seller and admin
//            User user = backEndUserSessionBeanLocal.login("ethan", "password");
//            if (user instanceof Seller) {
//                System.out.println("Seller has been created: Ethan");
//            } else {
//                System.out.println("Seller not created");
//            }
//            user = backEndUserSessionBeanLocal.login("yitong", "password");
//            if (user instanceof Admin) {
//                System.out.println("Admin has been created: Yi Tong");
//            }
//            //yining user has been deleted before, hence this will throw an error
//            user = backEndUserSessionBeanLocal.login("yining", "password");
//            if (user instanceof Admin) {
//                System.out.println("Admin has been created: Yi Ning");
//            }
//        } catch (InvalidLoginCredentialException ex) {
//            System.err.println("Error: Users not properly created");
//        }
//
//        try {
//            //try login for customer
//            List<Tag> tags = tagSessionBeanLocal.retrieveAllTags();
//            Customer customer = customerSessionBeanLocal.customerLogin("ashleigh", "password");
//            System.out.println("Welcome " + customer.getUsername() + " customer login is successful");
//        } catch (InvalidLoginCredentialException ex) {
//            System.err.println("Customer Login Failed");
//        }
    }

    private void populateTags() {
        if(tagSessionBeanLocal.retrieveAllTags().size() <= 0){
            try {
                tagSessionBeanLocal.createNewTag(new Tag("Anxiety-disorder"));
                tagSessionBeanLocal.createNewTag(new Tag("Obsessive compulsive discorder"));
                tagSessionBeanLocal.createNewTag(new Tag("Trauma-related disorders"));
                tagSessionBeanLocal.createNewTag(new Tag("Biploar disorders"));

                List<Tag> tags = tagSessionBeanLocal.retrieveAllTags();
                System.out.println("number of tags created(6) = " + tags.size());
                for (Tag tag : tags) {
                    System.out.println("Tag has been created: " + tag.getTagName());
                }
            } catch (TagExistException ex) {
                System.out.println("Tag exists. Will not be created");
            } catch (UnknownPersistenceException | InputDataValidationException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void populateArtworks() {
        if (productCatalogueSessionBeanLocal.retrieveAllArtwork().size() <= 0) {
            Artwork artwork1 = new Artwork("The screaming man", "This artwork is about a man screaming", "/Image/Artwork.jpg");
            List<Tag> allTags = tagSessionBeanLocal.retrieveAllTags();
            List<Long> allTagIds = new ArrayList<>();
            for (Tag tag : allTags) {
                System.out.println("Tag ID added to List: " + tag.getTagId());
                allTagIds.add(tag.getTagId());
            }
            Artwork artwork2 = new Artwork("New Artwork 2", "This artwork is not deleted", destination + "artwork1.png");
            Artwork artwork3 = new Artwork("New Artwork 3", "Test Delete From UI", destination + "/tailored-images-2.png");
            User user = null;
            try {
                user = backEndUserSessionBeanLocal.login("ethan", "password");
                productCatalogueSessionBeanLocal.createArtwork(artwork1, allTagIds, (Seller) user);
                productCatalogueSessionBeanLocal.createArtwork(artwork2, allTagIds, (Seller) user);
                productCatalogueSessionBeanLocal.createArtwork(artwork3, allTagIds, (Seller) user);

                List<Artwork> artworkList = productCatalogueSessionBeanLocal.retrieveAllArtwork();
                for (Artwork artwork : artworkList) {
                    System.out.println("Artwork ID: " + artwork.getArtworkId());
                    System.out.println("Artwork Name: " + artwork.getName());
                    System.out.println("Artwork Description: " + artwork.getDescription());
                    System.out.println("______________________________________");
                }

                ArtworkPrice ap1 = new ArtworkPrice(1200.0f, FormatEnum.ARTPOSTER);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap1, artwork1);

                ArtworkPrice ap2 = new ArtworkPrice(1400.0f, FormatEnum.CANVASWRAP);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap2, artwork1);

                ArtworkPrice ap3 = new ArtworkPrice(1500.0f, FormatEnum.PHOTOPRINT);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap3, artwork1);

                ArtworkPrice ap1a = new ArtworkPrice(1200.0f, FormatEnum.ARTPOSTER);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap1a, artwork2);

                ArtworkPrice ap2a = new ArtworkPrice(1400.0f, FormatEnum.CANVASWRAP);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap2a, artwork2);

                ArtworkPrice ap3a = new ArtworkPrice(1500.0f, FormatEnum.PHOTOPRINT);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap3a, artwork2);

                ArtworkPrice ap1b = new ArtworkPrice(1200.0f, FormatEnum.ARTPOSTER);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap1b, artwork3);

                ArtworkPrice ap2b = new ArtworkPrice(1400.0f, FormatEnum.CANVASWRAP);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap2b, artwork3);

                ArtworkPrice ap3b = new ArtworkPrice(1500.0f, FormatEnum.PHOTOPRINT);
                productCatalogueSessionBeanLocal.createArtworkPrice(ap3b, artwork3);

                List<ArtworkPrice> prices1 = new ArrayList<>();
                for (ArtworkPrice price : productCatalogueSessionBeanLocal.retrieveArtworkById(artwork1.getArtworkId()).getArtworkPrices()) {
                    System.out.println("Price added to List: " + price);
                    prices1.add(price);
                }

                List<ArtworkPrice> prices2 = new ArrayList<>();
                for (ArtworkPrice price : productCatalogueSessionBeanLocal.retrieveArtworkById(artwork2.getArtworkId()).getArtworkPrices()) {
                    System.out.println("Price added to List: " + price);
                    prices2.add(price);
                }

                List<ArtworkPrice> prices3 = new ArrayList<>();
                for (ArtworkPrice price : productCatalogueSessionBeanLocal.retrieveArtworkById(artwork3.getArtworkId()).getArtworkPrices()) {
                    System.out.println("Price added to List: " + price);
                    prices3.add(price);
                }

                artwork1.setDescription("This is the new description");
                try {
                    productCatalogueSessionBeanLocal.updateArtwork(artwork1, allTagIds, prices1);
                } catch (ArtworkPriceNotFoundException ex) {
                    System.out.println("Unable to update artwork");
                }
                artworkList = productCatalogueSessionBeanLocal.retrieveAllArtwork();
                if (artworkList.isEmpty()) {
                    System.out.println("No artwork found.");
                } else {
                    for (Artwork artwork : artworkList) {
                        System.out.println("Artwork ID: " + artwork.getArtworkId());
                        System.out.println("Artwork Name: " + artwork.getName());
                        System.out.println("Artwork Description: " + artwork.getDescription());
                        System.out.println("______________________________________");
                    }
                }

                productCatalogueSessionBeanLocal.deleteArtwork(artwork1.getArtworkId());
                artworkList = productCatalogueSessionBeanLocal.retrieveAllArtwork();
                if (artworkList.isEmpty()) {
                    System.out.println("No artwork found.");
                } else {
                    for (Artwork artwork : artworkList) {
                        System.out.println("Artwork ID: " + artwork.getArtworkId());
                        System.out.println("Artwork Name: " + artwork.getName());
                        System.out.println("Artwork Description: " + artwork.getDescription());
                        System.out.println("______________________________________");
                    }
                }

                //need to comment out deleteArtwork Before testing this
//                ap3.setPrice(800.0f);
//                productCatalogueSessionBeanLocal.updateArtworkPrice(ap3);
            } catch (ArtworkNotCreatedException | InvalidLoginCredentialException | InputDataValidationException | TagNotFoundException | ArtworkNotFoundException | DeleteArtworkException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void populateOffences() {
        if (customerSessionBeanLocal.retrieveAllOffences().size() <= 0) {
            try {
                //admin create offences
                adminSessionBeanLocal.createNewOffence(new Offences("Offensive language", "rude", 10));
                adminSessionBeanLocal.createNewOffence(new Offences("Irrelevant content posted", "rude", 5));
                adminSessionBeanLocal.createNewOffence(new Offences("Seller did not deliver product", "cheating", 10));
                adminSessionBeanLocal.createNewOffence(new Offences("Seller delivered wrong product", "cheating", 5));
                adminSessionBeanLocal.createNewOffence(new Offences("Poor product quality", "cheating", 5));

//                List<Offences> offences = customerSessionBeanLocal.retrieveAllOffences();
//                System.out.println("Number of offences created : " + offences.size());
//
//                //get customer to report
//                Customer ashleigh = customerSessionBeanLocal.retrieveCustomerByUsername("ashleigh");
//                //report a user
//                customerSessionBeanLocal.reportAUser(ashleigh.getUserId(), offences.get(0).getOffencesId());
//                System.out.println("Customer successfully reported " + ashleigh.getUsername() + " Offence = " + ashleigh.getOffences());
//                //get seller to report
//                Seller ethan = sellerSessionBeanLocal.retrieveSellerByUsername("ethan");
//                //report a user
//                customerSessionBeanLocal.reportAUser(ethan.getUserId(), offences.get(1).getOffencesId());
//                System.out.println("Seller successfully reported " + ethan.getUsername() + " Offence = " + ethan.getOffences());

            } catch (UnknownPersistenceException | InputDataValidationException | OffenceExistException ex) {
                System.out.println("Failed to create offence");
//            } catch (UnableToReportUserException ex) {
//                System.out.println("unable to report user");
//            } catch (UserAlreadyBannedException ex) {
//                System.out.println("user already banned");
//            } catch (UserNotFoundException ex) {
//                System.out.println("user not found");
            }
        }
    }

    private void populateSelfCareBoxes() {
        if (productCatalogueSessionBeanLocal.retrieveAllSelfCareBox().size() <= 0) {
            SelfCareBox selfCareBox1 = new SelfCareBox("Sleep Well Box", "This box helps you sleep well", 10, "/Image/SelfCareBox1.jpg");
            List<Tag> allTags = tagSessionBeanLocal.retrieveAllTags();
            List<Long> allTagIds = new ArrayList<Long>();
            for (Tag tag : allTags) {
                System.out.println("Tag ID added to List: " + tag.getTagId());
                allTagIds.add(tag.getTagId());
            }
            SelfCareBox selfCareBox2 = new SelfCareBox("Aromatherapy Box", "This box to test delete", 20, "/Image/SelfCareBox2.jpg");

            User user = null;
            try {
                user = backEndUserSessionBeanLocal.login("ethan", "password");
                productCatalogueSessionBeanLocal.createSelfCareBox(selfCareBox1, allTagIds, (Seller) user);
                productCatalogueSessionBeanLocal.createSelfCareBox(selfCareBox2, allTagIds, (Seller) user);
                List<SelfCareBox> selfCareBoxList = productCatalogueSessionBeanLocal.retrieveAllSelfCareBox();
                for (SelfCareBox selfCareBox : selfCareBoxList) {
                    System.out.println("Self Care Box ID: " + selfCareBox.getSelfCareBoxId());
                    System.out.println("Self Care Box Name: " + selfCareBox.getName());
                    System.out.println("______________________________________");
                }

                SelfCareSubscriptionDiscount discount1 = new SelfCareSubscriptionDiscount(10, DurationEnum.OnceOff);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(discount1, selfCareBox1);

                SelfCareSubscriptionDiscount discount2 = new SelfCareSubscriptionDiscount(20, DurationEnum.ThreeMonths);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(discount2, selfCareBox1);

                SelfCareSubscriptionDiscount discount3 = new SelfCareSubscriptionDiscount(30, DurationEnum.SixMonths);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(discount3, selfCareBox1);

                SelfCareSubscriptionDiscount discounta = new SelfCareSubscriptionDiscount(10, DurationEnum.OnceOff);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(discounta, selfCareBox2);

                SelfCareSubscriptionDiscount discountb = new SelfCareSubscriptionDiscount(20, DurationEnum.ThreeMonths);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(discountb, selfCareBox2);

                SelfCareSubscriptionDiscount discountc = new SelfCareSubscriptionDiscount(30, DurationEnum.SixMonths);
                productCatalogueSessionBeanLocal.createSelfCareSubscriptionDiscount(discountc, selfCareBox2);

                selfCareBoxList = productCatalogueSessionBeanLocal.retrieveAllSelfCareBox();
                for (SelfCareBox selfCareBox : selfCareBoxList) {
                    System.out.println("Self Care Box ID: " + selfCareBox.getSelfCareBoxId());
                    System.out.println("Self Care Box Name: " + selfCareBox.getName());
                    for (SelfCareSubscriptionDiscount discount : selfCareBox.getSelfCareSubscriptionDiscounts()) {
                        System.out.println("Discount percentage: " + discount.getDiscountPercentage());
                        System.out.println("Discount duration: " + discount.getDurationEnum());
                        System.out.println(".............................................");
                    }
                    System.out.println("______________________________________");
                }

//                user = backEndUserSessionBeanLocal.login("kaijing", "newpassword");
                user = (Customer) customerSessionBeanLocal.retrieveCustomerByUsername("kaijing");
                Review review1 = new Review("This box is good.");
                selfCareBoxReviewsSessionBeanLocal.createNewSelfCareBoxReview(review1, (Customer) user, selfCareBox1);
                selfCareBoxList = productCatalogueSessionBeanLocal.retrieveAllSelfCareBox();
                for (SelfCareBox selfCareBox : selfCareBoxList) {
                    System.out.println("Self Care Box ID: " + selfCareBox.getSelfCareBoxId());
                    System.out.println("Self Care Box Name: " + selfCareBox.getName());
                    for (Review review : selfCareBox.getReviews()) {
                        System.out.println("Comment: " + review.getComment());
                        System.out.println("By customer: " + review.getCustomer().getUsername());
                        System.out.println(".............................................");
                    }
                    System.out.println("______________________________________");
                }

                review1.setComment("This box is not so good.");
                selfCareBoxReviewsSessionBeanLocal.updateSelfCareBoxReview(review1);
                for (SelfCareBox selfCareBox : selfCareBoxList) {
                    System.out.println("Self Care Box ID: " + selfCareBox.getSelfCareBoxId());
                    System.out.println("Self Care Box Name: " + selfCareBox.getName());
                    for (Review review : selfCareBox.getReviews()) {
                        System.out.println("Comment: " + review.getComment());
                        System.out.println("By customer: " + review.getCustomer().getUsername());
                        System.out.println(".............................................");
                    }
                    System.out.println("______________________________________");
                }

//                selfCareBoxReviewsSessionBeanLocal.deleteSelfCareReviewById(review1.getReviewId());
//                for(SelfCareBox selfCareBox : selfCareBoxList){
//                    System.out.println("Self Care Box ID: " + selfCareBox.getSelfCareBoxId());
//                    System.out.println("Self Care Box Name: " + selfCareBox.getName());
//                    for(Review review: selfCareBox.getReviews()){
//                        System.out.println("Comment: " + review.getComment());
//                        System.out.println("By customer: " + review.getCustomer().getUsername());
//                        System.out.println(".............................................");
//                    }
//                    System.out.println("______________________________________");
//                }
            } catch (InputDataValidationException | InvalidLoginCredentialException | ReviewExistException | ReviewNotFoundException | SelfCareBoxExistException | SelfCareSubscriptionDiscountExistException | TagNotFoundException | UnknownPersistenceException | UserNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    //tests customer follow sellers and view their products
    private void populateTestFollowing() {
        //get customer 
        Customer ashleigh;
        try {
            ashleigh = customerSessionBeanLocal.retrieveCustomerByUsername("ashleigh");
            if (ashleigh.getSellers().isEmpty()) {
                try {
                    //get seller to follow
                    Seller ethan = sellerSessionBeanLocal.retrieveSellerByUsername("ethan");

                    customerSessionBeanLocal.followASeller(ashleigh.getUserId(), ethan.getUserId());
                    List<Seller> sellersFollowed = customerSessionBeanLocal.viewAllFollowedSellers(ashleigh.getUserId());
                    System.out.println("Follow success " + ashleigh.getUsername() + " followed sellers = " + sellersFollowed);
                    List<Artwork> artworksSold = customerSessionBeanLocal.viewAllArtworksByFollowedSellers(ashleigh.getUserId());
                    System.out.println("Artworks sold by seller followed = " + artworksSold);
                    List<SelfCareBox> boxSold = customerSessionBeanLocal.viewAllSelfCareBoxByFollowedSellers(ashleigh.getUserId());
                    System.out.println("Self care Box sold by seller followed = " + boxSold);

                } catch (UserNotFoundException ex) {
                    System.out.println("Unable to follow seller");
                }
            }
        } catch (UserNotFoundException ex) {
            System.out.println("Unable to retrieve customer");
        }

    }

    private void populateForumWithPost() {
        List<Admin> adminList = adminSessionBeanLocal.retrieveAllAdmins();

        if (forumSessionBeanLocal.retrieveAllPosts().size() <= 0) {
            try {
                List<Admin> admins = adminSessionBeanLocal.retrieveAllAdmins();
                List<Seller> sellers = sellerSessionBeanLocal.retrieveAllSellers();
                List<Tag> tags = tagSessionBeanLocal.retrieveAllTags();
                List<Long> tagIDToBeAssociated = new ArrayList<>();
                tagIDToBeAssociated.add(tags.get(0).getTagId());
                List<Tag> customerTag = new ArrayList<>();
                customerTag.add(tags.get(0));
                Long customerAbbyLim = customerSessionBeanLocal.createNewCustomer(new Customer("abbylim", "password", "Abby", "Lim", "abbylim89@gmail.com", "address", "unit1", "123456"), customerTag);
                Long customerYingYuLee = customerSessionBeanLocal.createNewCustomer(new Customer("yyLee", "password", "Yingyu", "Lee", "leeyingyu@hotmail.com", "address", "unit2", "123456"), customerTag);

                Long customerJoshuaFeng = customerSessionBeanLocal.createNewCustomer(new Customer("joshuaf", "password", "Joshua", "Feng", "joshuaFeng@gamil.com", "address", "unit3", "123456"), customerTag);
                Long customerLiwen = customerSessionBeanLocal.createNewCustomer(new Customer("liwen", "password", "Li", "Wen", "liwen@outlook.com", "address", "unit4", "123456"), customerTag);

                Long customerMikePompeo = customerSessionBeanLocal.createNewCustomer(new Customer("michael", "password", "Michael", "Pempeooo", "mikep@hotmail.com", "address", "unit5", "123456"), customerTag);
                Long customerNatalieChua = customerSessionBeanLocal.createNewCustomer(new Customer("natalie", "password", "Nataile", "Chua", "natailecchua@outlook.com", "address", "unit6", "123456"), customerTag);

                Long customerAadesh = customerSessionBeanLocal.createNewCustomer(new Customer("aadesh", "password", "Aadesh", "Kunimcihis", "aadeshkuni@hotmail.com", "address", "unit7", "123456"), customerTag);
                Long customerTracyChang = customerSessionBeanLocal.createNewCustomer(new Customer("tracycc", "password", "Xinyue", "Chang", "tracyChang@gamil.com", "address", "unit8", "123456"), customerTag);

                //anxity
                String content = "Are you sensitive, you will be anxious about other people's comment? If so you might be in the range of a HSP. Studies show up to 15-30% of people could fall into this category. And I thought I was alone. Have you had the comments like ”you should toughen up”. ”don’t be so sensitive” "
                        + "Yet these people don’t realise that your sensitivity is part of you";
                forumSessionBeanLocal.createNewPost(new Post("Highly Sensitive People (HSP)", content, "Image/PostImageA.jpg", new Date()), customerAbbyLim, tagIDToBeAssociated);

                content = "Since this bloody virus has put me and my family in lockdown due to a chronic lung condition, I've been home schooling my son, a lot of cleaning, cooking, knitting, diamond painting, reading and my own worst enemy thinking. I feel so overwhelmed and I just want to crawl into a ball and cry. "
                        + "I do have a psychiatrist but I feel like I would be wasting his time. I have never realized how depressed I can get. I feel like a failure, being all these things and not succeeding.";
                //tagIDToBeAssociated.add(tags.get(1).getTagId());
                forumSessionBeanLocal.createNewPost(new Post("I am having a real bad day", content, "Image/PostImageB.jpg", new Date()), customerYingYuLee, tagIDToBeAssociated);

                //obsessive compulsive discorder
                tagIDToBeAssociated.clear();
                tagIDToBeAssociated.add(tags.get(1).getTagId());

                content = "I’m at the point where I don’t know what to do anymore. I’ve been suffering with OCD (hocd,pocd) for a long time. I’ve gone to therapy I’ve talked about it with a loved one. I’ve tried to just ignore these intrusive thoughts. But the fact that I even have these gross thoughts out of my control makes me feel guilty and ashamed. The constant fear "
                        + "and checking all the damn time. Every minute a stupid thought pops into my head and it just tears me apart. I’m mentally and physically drained.";
                forumSessionBeanLocal.createNewPost(new Post("I’m really struggling. Have been for awhile", content, "Image/PostImageC.jpg", new Date()), customerJoshuaFeng, tagIDToBeAssociated);

                content = "I'm writing this because my ocd has been with me since I was about 7 probably longer I'm 20 now so that's at least 13 years of ocd I have had alot of symptoms the (normal) ones when I was younger the hand washing door checking ect then in my teens it was harm ocd sexuality ocd religious ocd fear of schizophrenia ect I thought I had finally beaten it when I "
                        + "done erp for the religious ocd I definetly have beaten the religious ocd but it changed now into checking my phone percentage";
                forumSessionBeanLocal.createNewPost(new Post("Be honest is ocd really life long", content, "Image/PostImageC.jpg", new Date()), customerLiwen, tagIDToBeAssociated);

                //trauma-related discorders
                tagIDToBeAssociated.clear();
                tagIDToBeAssociated.add(tags.get(2).getTagId());
                content = " suffer from a lot of trauma stemming from intense childhood abuse. Due to my trauma, I not only hated my abusers but was indifferent to most people. "
                        + "I've been practising mindfulness meditation for a year or so now using the Waking Up app. I started practising loving-kindness meditation (Metta) last night using the same app, and I think it does make one more compassionate. I also found research that this practice can decrease PTSD:";
                forumSessionBeanLocal.createNewPost(new Post("Loving-kindness meditation can decrease PTSD", content, "Image/PostImageC.jpg", new Date()), customerMikePompeo, tagIDToBeAssociated);
                content = "So today I spoke to a nurse about my trauma for the first time!!! \n" + "He suggested a diagnosis of C-PTSD which is how I found this forum.\n" + "\n" + "Does anyone have any advice on dealing with emotional flashbacks? (The kind where you don't realise your having a flashback?)";
                forumSessionBeanLocal.createNewPost(new Post("Emotional Flashbacks tips?", content, "Image/PostImageC.jpg", new Date()), customerNatalieChua, tagIDToBeAssociated);

//                content = "“My father taught me that you can you read a hundred books on wisdom and write a hundred books on wisdom, but unless you apply what you learned then its only words on a page. Life is not lived with intentions, but action.”\n"
//                        + "― Shannon Alder";
//                forumSessionBeanLocal.createNewPost(new Post("A quote that I want to share with people with OCD", content, "Image/PostImageC.jpg", new Date()), admins.get(1).getUserId(), tagIDToBeAssociated);
                //biopolar discorder
                tagIDToBeAssociated.clear();
                tagIDToBeAssociated.add(tags.get(3).getTagId());
                content = "Hi all\n"
                        + "\n"
                        + "I have BP2 and take Lithium. I had a hypomanic episode in January (lasted around 3-4 weeks - it wasn’t overly pleasant, I essentially became obsessed with the idea of trying to leave the country for a month straight and harassed immigration lawyers- it was impossible to do due to my bipolar debts but I obsessed constantly)\n"
                        + "\n"
                        + "I fell into depression in Feb and my mood stabilised around mid March for 3-4 weeks.";
                forumSessionBeanLocal.createNewPost(new Post("Bipolar and panic - I’m not coping", content, "Image/PostImageC.jpg", new Date()), customerAadesh, tagIDToBeAssociated);

                content = "New to the forum so hello and thanks for having me!\n"
                        + "\n"
                        + "I was recently diagnosed with Bipolar II after thinking I had plain ol' depression for 15+ years... Really opened my eyes and makes a ton of sense. I've been trying to find more alternative remedies since I've had horrible reactions to almost every medication I've tried.";
                forumSessionBeanLocal.createNewPost(new Post("Sleep hygiene for early risers", content, "Image/PostImageC.jpg", new Date()), customerTracyChang, tagIDToBeAssociated);

//                List<Post> posts = forumSessionBeanLocal.retrieveAllMyPosts(admins.get(0).getUserId());
//                System.out.println("number of posts(2) = " + posts.size());
//
                List<Post> postByFilter = new ArrayList<>();
                postByFilter = forumSessionBeanLocal.filterPostsByTags(tags.get(0).getTagId());
                System.out.println("Number of posts retrieved(0):  " + postByFilter.size());
//
//                //output should be 2
//                List<Long> onlyOnetag = new ArrayList<>();
//                onlyOnetag.add(tags.get(1).getTagId());
//                postByFilter = forumSessionBeanLocal.filterPostsByTags(onlyOnetag);
//                System.out.println("Number of posts retrieved(2):  " + postByFilter.size());
//
//                //output should be 3
//                postByFilter = forumSessionBeanLocal.filterPostsByTags(tagIDToBeAssociated);
//                System.out.println("Number of posts retrieved(3):  " + postByFilter.size());
//
//                //retrieve post by postId
//                Post post1 = forumSessionBeanLocal.retrieveSinglePost(posts.get(0).getPostId());
//                System.out.println("Post retrieved: " + post1.getContent());
                //update post, can only update post content and image
//                post1.setContent("change content success");
//                post1.setImage("ImageChanged");
//                forumSessionBeanLocal.updateMyPost(post1);
                //delete post with no comments
//                List<Post> post2 = forumSessionBeanLocal.retrieveAllMyPosts(admins.get(1).getUserId());
//                forumSessionBeanLocal.deletePostById(post2.get(0).getPostId());
//                List<Post> afterDeletionPosts = forumSessionBeanLocal.retrieveAllPosts();
//                System.out.println("Post delete success(2): " + afterDeletionPosts.size());
            } catch (UserUsernameExistException | PostExistException | UnknownPersistenceException | InputDataValidationException | TagNotFoundException | UserNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

//    private void populateForumWithComment() {
//        List<Admin> adminList = adminSessionBeanLocal.retrieveAllAdmins();
//        List<Seller> sellerList = sellerSessionBeanLocal.retrieveAllSellers();
//        List<Comment> commentList = forumSessionBeanLocal.retrieveAllComment();
//        System.out.println("Number of comments: " + commentList.size());
//        if (commentList.size() <= 0) {
//            try {
//                //user id
//                List<Admin> admins = adminSessionBeanLocal.retrieveAllAdmins();
//                List<Post> posts = forumSessionBeanLocal.retrieveAllPosts();
//                Post postToBeDelete = posts.get(1);
//
//                //two tags to be added
//                forumSessionBeanLocal.createNewComment(new Comment("Yes, I totally agree", new Date()), admins.get(1).getUserId(), postToBeDelete.getPostId());
//                forumSessionBeanLocal.createNewComment(new Comment("I can totally feel you", new Date()), admins.get(0).getUserId(), posts.get(0).getPostId());
//                forumSessionBeanLocal.createNewComment(new Comment("This my second comment", new Date()), admins.get(0).getUserId(), posts.get(0).getPostId());
//                forumSessionBeanLocal.createNewComment(new Comment("This is my third comment", new Date()), admins.get(0).getUserId(), posts.get(0).getPostId());
//                forumSessionBeanLocal.createNewComment(new Comment("I do not get why such things would happen", new Date()), admins.get(0).getUserId(), posts.get(0).getPostId());
//                forumSessionBeanLocal.createNewComment(new Comment("keep this comment to avoid repopulate comment in data init", new Date()), sellerList.get(0).getUserId(), posts.get(0).getPostId());
//                //output should be 2
//                List<Comment> comments = forumSessionBeanLocal.retrieveAllMyComments(admins.get(0).getUserId());
//                System.out.println("number of comment made by one person(4) = " + comments.size());
//
//                //retrieve single comment by comment id
//                Comment singleCommentRetrieved = forumSessionBeanLocal.retrieveSingleComment(comments.get(0).getCommentId());
//                System.out.println("Single comment retrieved and the comment is made by:  " + singleCommentRetrieved.getUser().getUsername());
//
//                //update comment, only content of a comment can be updated
//                Comment commentToBeUpdated1 = comments.get(0);
//                commentToBeUpdated1.setContent("Comment update success");
//                forumSessionBeanLocal.updateComment(commentToBeUpdated1);
//
//                //delete comment
//                comments = forumSessionBeanLocal.retrieveAllMyComments(admins.get(0).getUserId());
//                //delete the i do not get why such things would happen
//                forumSessionBeanLocal.deleteCommentById(comments.get(1).getCommentId());
//                List<Comment> commentAfterDeletion = forumSessionBeanLocal.retrieveAllMyComments(admins.get(0).getUserId());
//                System.out.println("Comment delete success(1): " + commentAfterDeletion.size());
//
//                //try to delete a post with associated comment
//                forumSessionBeanLocal.deletePostById(postToBeDelete.getPostId());
//                System.out.println("delete success for post with comments");
    //try to delete tag with association
//                List<Tag> tags = tagSessionBeanLocal.retrieveAllTags(false);
//                tagSessionBeanLocal.deleteTag(tags.get(0).getTagId());
//                if ((tagSessionBeanLocal.retrieveTagByTagId(tags.get(0).getTagId()).isIsDeleted()) == true) {
//                    System.out.println("Tag is correctly detele by setting isDeleted to true in DB");
//                } else {
//                    System.out.println("Tag not deleted correcly");
//                }
    //Repopulate the posts
//                try{
//                    tags = tagSessionBeanLocal.retrieveAllTags(false);
//                    List<Long> tagIDToBeAssociated = new ArrayList<> ();
//                    tagIDToBeAssociated.add(tags.get(0).getTagId());
//                    Long postId = forumSessionBeanLocal.createNewPost(new Post("Repopulated Post AFTER delete post testing", "Image/PostImageA.jpg", new Date()),admins.get(0).getUserId(), tagIDToBeAssociated);
//                    forumSessionBeanLocal.createNewComment(new Comment("Yes, I totally agree", new Date()), admins.get(1).getUserId(), postId);
//                    forumSessionBeanLocal.createNewComment(new Comment("I can totally feel you", new Date()), admins.get(0).getUserId(), postId);
//                    forumSessionBeanLocal.createNewComment(new Comment("This my second comment", new Date()), admins.get(0).getUserId(), postId);
//                    forumSessionBeanLocal.createNewComment(new Comment("This is my third comment", new Date()), admins.get(0).getUserId(), postId);
//                    forumSessionBeanLocal.createNewComment(new Comment("I do not get why such things would happen", new Date()), admins.get(0).getUserId(), postId);
//                }catch(CommentExistException | InputDataValidationException | PostExistException | PostNotFoundException | TagNotFoundException | UnknownPersistenceException | UserNotFoundException ex){
//                    System.out.println("Posts not repopulated.");
//                }
//            } catch (CommentNotFoundException | CommentExistException | PostNotFoundException | UnknownPersistenceException | InputDataValidationException | UserNotFoundException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }


    public void populateTransactionAndOrder() {
        List<Artwork> artworkList = productCatalogueSessionBeanLocal.retrieveAllArtwork();
        List<SelfCareBox> selfCareBoxList = productCatalogueSessionBeanLocal.retrieveAllSelfCareBox();
        List<Customer> custList = customerSessionBeanLocal.retrieveAllCustomers();
        if (orderSessionBeanLocal.retrieveAllTransaction().size() <= 0) {
            Artwork a1 = artworkList.get(0);
            ArtworkPrice ap1 = a1.getArtworkPrices().get(0);
            Customer c = custList.get(0);
            // Float selfCarePrice = selfCareBoxList.get(0).getPricePerMonth();
            try {
                ArtworkOrder ao = orderSessionBeanLocal.createArtworkOrder(a1.getArtworkId(), ap1.getArtworkPriceId(), c.getUserId(), 1);
                List<Long> artworkOrderList = new ArrayList<>();
                artworkOrderList.add(ao.getOrderId());
                List<Long> selfCareOrderList = new ArrayList<>();
                Transaction trans = orderSessionBeanLocal.createTransaction(artworkOrderList, selfCareOrderList, PaymentTypeEnum.MASTERCARD, custList.get(0).getUserId());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            SelfCareBox s1 = selfCareBoxList.get(0);
            SelfCareSubscriptionDiscount sd1 = s1.getSelfCareSubscriptionDiscounts().get(1);
            try {
                SelfCareBoxOrder so = orderSessionBeanLocal.createSelfCareBoxOrder(s1.getSelfCareBoxId(), sd1.getDiscountId(), c.getUserId(), 1);
                List<Long> artworkOrderList = new ArrayList<>();
                List<Long> selfCareOrderList = new ArrayList<>();
                selfCareOrderList.add(so.getOrderId());
                //Transaction trans = orderSessionBeanLocal.createTransaction(artworkOrderList, selfCareOrderList, PaymentTypeEnum.MASTERCARD, custList.get(0).getUserId());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        try {
            List<Transaction> transList = orderSessionBeanLocal.retrieveAllTransaction();
            Transaction trans = transList.get(0);
            System.out.println("--------------Transaction Testing-----------");
            System.out.println("Transaction ID: " + trans.getTransactionId());
            System.out.println("Transaction DateTime: " + trans.getTransactionDateTime());

            Customer c2 = customerSessionBeanLocal.retrieveCustomerById(trans.getCustomer().getUserId());
            System.out.println("Transaction Customer username: " + c2.getUsername());
            for (OrderHistory oh : trans.getOrders()) {
                System.out.print("-");
                System.out.println(orderSessionBeanLocal.checkProductName(oh));

            }
            System.out.println("---------------------------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
