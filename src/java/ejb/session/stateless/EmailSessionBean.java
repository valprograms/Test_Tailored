/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Seller;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import util.email.EmailManager;

/**
 *
 * @author decimatum
 */
@Stateless
public class EmailSessionBean implements EmailSessionBeanLocal
{
//    private final String GMAIL_USERNAME = "tailoredis3106";
//    private final String GMAIL_PASSWORD = "tailored123";
//    private final String GMAIL_EMAIL = "tailoredis3106@gmail.com";
//    
//    @Override
//    public Boolean approveSellerNotificationSync(Seller seller)
//    {
//        EmailManager emailManager = new EmailManager(GMAIL_USERNAME, GMAIL_PASSWORD);
//        Boolean result = emailManager.approveSellerNotification(seller, GMAIL_EMAIL,seller.getEmail());
//        
//        return result;
//    } 
//    
//    @Asynchronous
//    @Override
//    public Future<Boolean> approveSellerNotificationAsync(Seller seller) throws InterruptedException
//    {        
//        EmailManager emailManager = new EmailManager(GMAIL_USERNAME, GMAIL_PASSWORD);
//        Boolean result = emailManager.approveSellerNotification(seller, GMAIL_EMAIL, seller.getEmail());
//        
//        return new AsyncResult<>(result);
//    }
}
