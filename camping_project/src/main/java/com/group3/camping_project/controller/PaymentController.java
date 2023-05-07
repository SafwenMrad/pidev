package com.group3.camping_project.controller;

import com.group3.camping_project.entities.ChargeRequest;
import com.group3.camping_project.service.equipement_service.ImpEquipe;
import com.group3.camping_project.service.equipement_service.ImpPayment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.ChargeCreateParams;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@CrossOrigin(origins = "*")
@RestController
public class PaymentController {

    static {
        Stripe.apiKey = "sk_test_51MxJRoJyDi4kbDaxdB6yovp4TTSTWsvNCP8NUEhHgf4WgyMGqvqjgKjNYWJVwBAQImPLLgh7sApos86UhbcZ6Ms900xCp9eAX7";
    }

    @Autowired
    ImpEquipe iEquipmentRepo;
    @Autowired
    ImpPayment impPayment;
@GetMapping("getall")
public List<ChargeRequest> requestList(){
    return impPayment.requestList();
}
    @PostMapping("/charge")
    public String charge(@RequestBody ChargeRequest chargeRequest) throws StripeException {

        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", chargeRequest.getCardNumber());
        cardParams.put("exp_month", chargeRequest.getExp_month());
        cardParams.put("exp_year", chargeRequest.getExp_year());
        cardParams.put("cvc", chargeRequest.getCvv());
        cardParams.put("name", chargeRequest.getName());
        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);
        Token token = Token.create(tokenParams);
        ChargeCreateParams params = new ChargeCreateParams.Builder()
                .setAmount(chargeRequest.getAmount())
                .setCurrency(chargeRequest.getCurrency())
                .setDescription(chargeRequest.getDescription())
                .setReceiptEmail(chargeRequest.getEmail())
                .setSource(token.getId())
                .build();
//        String accountSid = "ACce68051a70f5d4095d633ac8477e18cf";
//        String authToken = "28006f292fe803915b17aa1c7417db7d";
//        Twilio.init(accountSid, authToken);
          Charge charge = Charge.create(params);
        if (charge.getStatus().equals("succeeded")) {
            impPayment.ajouter(chargeRequest);
//            String message = "Thank you for your payment!";
//            String toPhoneNumber = chargeRequest.getPhone();
//            Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber("+16073262416"), message).create();
            final String username = "klaimohamed1994@gmail.com";
            final String password = "eblgesjukcqncydy";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {



                javax.mail.Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("klaimohamed1994@gmail.com"));
                msg.setRecipients(javax.mail.Message.RecipientType.TO,
                        InternetAddress.parse(chargeRequest.getEmail()));
                msg.setSubject("Testing JavaMail API");
                String htmlContent = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<meta charset=\"utf-8\">\n" +
                        "\t<meta name=\"viewport\" content=\"width=device-width\">\n" +
                        "\t<title>Payment Receipt</title>\n" +
                        "\t<style>\n" +
                        "\t\t/* Main style */\n" +
                        "\t\t.wrapper {\n" +
                        "\t\t\tbackground-color: #f9f9f9;\n" +
                        "\t\t\tpadding: 20px;\n" +
                        "\t\t\tfont-family: Arial, sans-serif;\n" +
                        "\t\t\tfont-size: 16px;\n" +
                        "\t\t\tline-height: 1.5;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t.container {\n" +
                        "\t\t\tmax-width: 600px;\n" +
                        "\t\t\tmargin: 0 auto;\n" +
                        "\t\t\tbackground-color: #fff;\n" +
                        "\t\t\tpadding: 20px;\n" +
                        "\t\t\tborder-radius: 4px;\n" +
                        "\t\t\tbox-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\th1 {\n" +
                        "\t\t\tfont-size: 28px;\n" +
                        "\t\t\tmargin-bottom: 20px;\n" +
                        "\t\t\tcolor: #333;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\ttable {\n" +
                        "\t\t\twidth: 100%;\n" +
                        "\t\t\tborder-collapse: collapse;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\tth {\n" +
                        "\t\t\ttext-align: left;\n" +
                        "\t\t\tpadding: 10px;\n" +
                        "\t\t\tbackground-color: #f5f5f5;\n" +
                        "\t\t\tcolor: #333;\n" +
                        "\t\t\tfont-weight: bold;\n" +
                        "\t\t\tborder: 1px solid #ddd;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\ttd {\n" +
                        "\t\t\tpadding: 10px;\n" +
                        "\t\t\tborder: 1px solid #ddd;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t/* Footer */\n" +
                        "\t\t.footer {\n" +
                        "\t\t\tmargin-top: 20px;\n" +
                        "\t\t\ttext-align: center;\n" +
                        "\t\t\tcolor: #999;\n" +
                        "\t\t\tfont-size: 12px;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t.footer a {\n" +
                        "\t\t\tcolor: #999;\n" +
                        "\t\t\ttext-decoration: none;\n" +
                        "\t\t}\n" +
                        "\n" +
                        "\t\t.footer a:hover {\n" +
                        "\t\t\tcolor: #333;\n" +
                        "\t\t}\n" +
                        "\t</style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\t<div class=\"wrapper\">\n" +
                        "\t\t<div class=\"container\">\n" +
                        "\t\t\t<h1>Payment Receipt</h1>\n" +
                        "\t\t\t<p>Thank you for your payment. Below is your receipt:</p>\n" +
                        "\t\t\t<p>Votre nom:</p>"+chargeRequest.getName() +
                        "\t\t\t<p>Date :</p>"+chargeRequest.getCreatedAt() +"\n"+
                        "\t\t\t\t\t\t<strong>Total :</strong>\n" +
                        "\t\t\t\t\t\t<strong>"+chargeRequest.getAmount()+"TND" +
                        "</ strong>\n" +
                        "\t\t\t<p>Please keep this receipt for your records. If you have any questions about your payment, please contact us at <a href=\"mailto:klaimohamed1994@gmail.com\">klaimohamed1994@gmail.com</a>.</p>\n" +
                        "\t\t</div>\n" +
                        "\t\t<div class=\"footer\">\n" +
                        "\t\t\t<p>Copyright &copy; 2023 Example Corp.\n" +
                        "\t\t\t\t<br>All rights reserved.</p>\n" +
                        "\t\t</div>\n" +
                        "\t</div>\n" +
                        "</body>\n" +
                        "</html>\n";

                // create message body part for HTML content
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(htmlContent, "text/html");

                // create multipart message and add message body part to it
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                // add multipart message to email message
                msg.setContent(multipart);

                // send email message
                Transport.send(msg);
//

                System.out.println("Email sent successfully!");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        }
            return charge.toJson();

    }}