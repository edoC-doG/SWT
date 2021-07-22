/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.payment;

import pnj.products.ProductDAO;
import pnj.products.ProductDTO;
import pnj.histories.HistoryDTO;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class PaymentServices {

    private static final String CLIENT_ID = "Ab8eqBfeqmxYnBk6csNHyH7VBPfF9KcDsgR4Z-xkcMBppwnti4kqBeIIZQzHKQd0ANvXFKK_PSJFMJg7";
    private static final String CLIENT_SECRET = "EJMpuxMjW1uhjOF9E-LfLt40Yx38Ifm07zvf6e3RWh9kGvrhOFA8HfQ3EfeQOhqvKVCx68yeBUm2S0KO";
    private static final String MODE = "sandbox";

    public String authorizePayment(HistoryDTO history,  HashMap<String, Integer> cart,  Set<String> listKeys, int codeValue)
            throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        ProductDAO productDao = new ProductDAO();
        ArrayList<ProductDTO> listProduct = new ArrayList<>();
        try {
            listProduct = productDao.getListProduct("", "", "", "");
        } catch (NamingException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Transaction> listTransaction = getTransactionInformation(history, cart, listKeys, listProduct, codeValue);
        for (Transaction transaction : listTransaction) {
            System.out.println(transaction);
        }
        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);

    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("John")
                .setLastName("Doe")
                .setEmail("sb-hbbjf4582809@personal.example.com");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8084/PNJ/cancel.jsp");
        redirectUrls.setReturnUrl("http://localhost:8084/PNJ/ReviewPayment");

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(HistoryDTO history, HashMap<String, 
            Integer> cart,  Set<String> listKeys, ArrayList<ProductDTO> listProduct, int codeValue) {
        Details details = new Details();
        details.setSubtotal(String.valueOf(history.getTotalPrice()));
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(history.getTotalPrice()));
        amount.setDetails(details);
        
        Transaction transaction = new Transaction();
        transaction.setDescription("Bill payment product of Paypal");
        transaction.setAmount(amount);
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        
        for (String key: listKeys) {
            for (ProductDTO item : listProduct) {
                if (item.getproductID().equals(key)) {
                    items.add(new Item(item.getnameProduct(), String.valueOf(cart.get(key)), 
                            String.valueOf(item.getPrice() - (item.getPrice()*codeValue)/100), "USD"));
                }
            }
        }
        
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }
}
