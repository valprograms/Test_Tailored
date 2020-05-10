/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ArtworkOrder;
import entity.OrderHistory;
import entity.SelfCareBoxOrder;
import entity.Transaction;
import java.util.List;
import javax.ejb.Local;
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
@Local
public interface OrderSessionBeanLocalLocal {

    public ArtworkOrder createArtworkOrder(Long artworkID, Long artworkPriceID, Long customerID,int quantity) throws ArtworkPriceNotFoundException, ArtworkNotFoundException, OrderNotCreatedException, UserNotFoundException;

    public OrderHistory retrieveOrderById(Long orderID) throws OrderNotFoundException;

    public Transaction createTransaction(List<Long> artworkOrderList, List<Long> selfCareList, PaymentTypeEnum paymentType, Long custID) throws OrderNotFoundException;

    public List<Transaction> retrieveTransactionsByCustID(Long custID) throws UserNotFoundException;

    public String checkProductName(OrderHistory oh) throws OrderNotFoundException;

    public List<Transaction> retrieveAllTransaction();
    
    public List<ArtworkOrder> retrieveAllArtworkOrderByCustomerId(Long customerId) throws UserNotFoundException;
    
    public List<SelfCareBoxOrder> retrieveAllSelfCareBoxOrderByCustomerId(Long customerId) throws UserNotFoundException;

    public SelfCareBoxOrder createSelfCareBoxOrder(Long selfCareBoxID, Long discountID, Long customerID, int quantity) throws DiscountNotFoundException, SelfCareBoxNotFoundException, OrderNotCreatedException, UserNotFoundException;
    
}
