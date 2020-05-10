/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Artwork;
import entity.ArtworkOrder;
import entity.ArtworkPrice;
import entity.Customer;
import entity.OrderHistory;
import entity.SelfCareBox;
import entity.SelfCareBoxOrder;
import entity.SelfCareSubscriptionDiscount;
import entity.Transaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.DurationEnum;
import util.enumeration.OrderStatusEnum;
import util.enumeration.PaymentTypeEnum;
import util.exception.ArtworkNotFoundException;
import util.exception.ArtworkPriceNotFoundException;
import util.exception.DiscountNotFoundException;
import util.exception.OrderNotCreatedException;
import util.exception.OrderNotFoundException;
import util.exception.SelfCareBoxNotFoundException;
import util.exception.UserNotFoundException;

/**
 *
 * @author decimatum
 */
@Stateless
public class OrderSessionBeanLocal implements OrderSessionBeanLocalLocal {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "ProductCatalogueSessionBeanLocal")
    private ProductCatalogueSessionBeanLocal productCatalogueSessionBeanLocal;

    
    
    @PersistenceContext(unitName = "tailoredJsf-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public OrderSessionBeanLocal(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public ArtworkOrder createArtworkOrder(Long artworkID, Long artworkPriceID, Long customerID,int quantity) throws ArtworkPriceNotFoundException, ArtworkNotFoundException, OrderNotCreatedException, UserNotFoundException{
        ArtworkOrder artworkOrder = new ArtworkOrder();
        ArtworkPrice ap = productCatalogueSessionBeanLocal.retrieveArtworkPriceById(artworkPriceID);
        
        Artwork artwork = productCatalogueSessionBeanLocal.retrieveArtworkById(artworkID);
        
        artworkOrder.setArtwork(artwork);
        artwork.getArtworkOrders().add(artworkOrder);
        
        Customer c = customerSessionBeanLocal.retrieveCustomerById(customerID);
        
        artworkOrder.setCustomer(c);
        c.getOrders().add(artworkOrder);
        
        artworkOrder.setPriceAtTimeOfPurchase(ap.getPrice() * quantity);
        
        artworkOrder.setQuantity(quantity);
        artworkOrder.setOrderStatusEnum(OrderStatusEnum.Processing);
        Set<ConstraintViolation<ArtworkOrder>> constraintViolations = validator.validate(artworkOrder);
        if(constraintViolations.isEmpty()){
            em.persist(artworkOrder);
            em.flush();
           
            return artworkOrder;
        }
        else {
            throw new OrderNotCreatedException("Artwork order was not created.");
        }

        
    }
    
    @Override
    public SelfCareBoxOrder createSelfCareBoxOrder(Long selfCareBoxID, Long discountID, Long customerID,int quantity) throws DiscountNotFoundException, SelfCareBoxNotFoundException, OrderNotCreatedException, UserNotFoundException{
        SelfCareBoxOrder selfCareBoxOrder = new SelfCareBoxOrder();
        SelfCareSubscriptionDiscount d = productCatalogueSessionBeanLocal.retrieveSubscriptionDiscountById(discountID);
        
        SelfCareBox selfCareBox = productCatalogueSessionBeanLocal.retrieveSelfCareBoxById(selfCareBoxID);
        selfCareBoxOrder.setDurationEnum(d.getDurationEnum());
        Float monthlyPrice = selfCareBox.getPricePerMonth()*(1-d.getDiscountPercentage()/100);
        selfCareBoxOrder.setPricePerMthAtPurchase(monthlyPrice);
        //for debugging
//        System.out.println("discount percentage: " + d.getDiscountPercentage());
//        System.out.println("discount percentage" + d.getDiscountPercentage());
//        System.out.println("updated price after discount; " + selfCareBox.getPricePerMonth()*(1-d.getDiscountPercentage()/100));
        selfCareBoxOrder.setSelfCareBox(selfCareBox);
        selfCareBox.getSelfCareBoxOrders().add(selfCareBoxOrder);
        
        if (d.getDurationEnum().equals(DurationEnum.OnceOff)) {
            selfCareBoxOrder.setTotalPriceAtPurchase(monthlyPrice * quantity);
        } else if (d.getDurationEnum().equals(DurationEnum.ThreeMonths)){
            selfCareBoxOrder.setTotalPriceAtPurchase(monthlyPrice * 3 * quantity);
        } else {
            selfCareBoxOrder.setTotalPriceAtPurchase(monthlyPrice * 6 * quantity);
        }
        Customer c = customerSessionBeanLocal.retrieveCustomerById(customerID);
        
        selfCareBoxOrder.setCustomer(c);
        c.getOrders().add(selfCareBoxOrder);
        
        //selfCareBoxOrder.setPricePerMthAtPurchase(selfCareBox.getPricePerMonth()*(1-d.getDiscountPercentage()/100));
        
        
        selfCareBoxOrder.setQuantity(quantity);
        selfCareBoxOrder.setOrderStatusEnum(OrderStatusEnum.Processing);
        Set<ConstraintViolation<SelfCareBoxOrder>> constraintViolations = validator.validate(selfCareBoxOrder);
        if(constraintViolations.isEmpty()){
            em.persist(selfCareBoxOrder);
            em.flush();
            
            return selfCareBoxOrder;
        }
        else {
            throw new OrderNotCreatedException("selfCareBox order was not created.");
        }

        
    }
    
    @Override
    public OrderHistory retrieveOrderById(Long orderID) throws OrderNotFoundException{
        OrderHistory order = em.find(OrderHistory.class,orderID);
        
        if(order != null){
            return order;
        } else {
            throw new OrderNotFoundException("Order ID: " + orderID + " does not exist!");
        }
    }
    
    @Override
    public List<ArtworkOrder> retrieveAllArtworkOrderByCustomerId(Long customerId) throws UserNotFoundException {
        List<ArtworkOrder> artworkOrder = new ArrayList<>();
        try {
        List<OrderHistory> or = customerSessionBeanLocal.retrieveCustomerById(customerId).getOrders();
        for(OrderHistory orderHistory:or) {
            System.out.println("reach1");
            if(orderHistory instanceof ArtworkOrder) {
                System.out.println("reach2");
                artworkOrder.add((ArtworkOrder) orderHistory);
            }
        }
        } catch(UserNotFoundException ex){
            throw new UserNotFoundException("Customer Id can not be found"); 
        }
        return artworkOrder;
    }
    
    @Override
    public List<SelfCareBoxOrder> retrieveAllSelfCareBoxOrderByCustomerId(Long customerId) throws UserNotFoundException {
        List<SelfCareBoxOrder> sbo = new ArrayList<>();
        try {
        List<OrderHistory> or = customerSessionBeanLocal.retrieveCustomerById(customerId).getOrders();
        for(OrderHistory orderHistory:or) {
            if(orderHistory instanceof SelfCareBoxOrder) {
                sbo.add((SelfCareBoxOrder) orderHistory);
            }
        }
        } catch(UserNotFoundException ex){
            throw new UserNotFoundException("Customer Id can not be found"); 
        }
        return sbo;
    }
    
    
    @Override
    public Transaction createTransaction(List<Long> artworkOrderList, List<Long> selfCareList, PaymentTypeEnum paymentType, Long custID) throws OrderNotFoundException{
        Transaction newTrans = new Transaction();
       Customer existCust = em.find(Customer.class, custID);
       newTrans.setIsPaid(true);
       newTrans.setCustomer(existCust);
       existCust.getTransactions().add(newTrans);
       newTrans.setPaymentTypeEnum(paymentType);
       newTrans.setTransactionDateTime(new Date());
       List<OrderHistory> orderList = new ArrayList<>();
       if(artworkOrderList.size() > 0){
           for(Long aoID : artworkOrderList){
               ArtworkOrder ao = (ArtworkOrder)retrieveOrderById(aoID);
               ao.setTransaction(newTrans);
               newTrans.getOrders().add(ao);
               orderList.add(retrieveOrderById(aoID));
           }
       }
       if(!selfCareList.isEmpty()){
           //follow how artwork is done 
           for(Long scID : selfCareList){
               orderList.add(retrieveOrderById(scID));
           }
       }
       
       em.persist(newTrans);
       em.flush();
       
       return newTrans;
    }
    
    @Override
    public List<Transaction> retrieveTransactionsByCustID(Long custID) throws UserNotFoundException{
        Customer cust = customerSessionBeanLocal.retrieveCustomerById(custID);
        List<Transaction> transList = cust.getTransactions();
        return transList;
    }
    
    //no corresponding rest api
    @Override
    public String checkProductName(OrderHistory oh) throws OrderNotFoundException{
        if(oh instanceof ArtworkOrder){
            Artwork a = ((ArtworkOrder) oh).getArtwork();
            return a.getName();
        } else if(oh instanceof SelfCareBoxOrder){
            SelfCareBox sc = ((SelfCareBoxOrder) oh).getSelfCareBox();
            return sc.getName();
        }
        else{
            throw new OrderNotFoundException("Order not found.");
        }
    }
    
    //not in restful api, cannot think of any reason why we need to retrieve all transaction in angular site
    @Override
    public List<Transaction> retrieveAllTransaction(){
        Query query = em.createQuery("SELECT t from Transaction t");
        List<Transaction> transactions = query.getResultList();
        return transactions;
    } 
}
