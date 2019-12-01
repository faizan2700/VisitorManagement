package SendingSMS;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class simpleSMS {
 // Find your Account Sid and Token at twilio.com/user/account
 public static final String ACCOUNT_SID = "<ACCOUNT_SID>";
 public static final String AUTH_TOKEN = "<AUTH_TOKEN>";

 public static void main(String[] args) {
 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

 Message message = Message
 .creator(new PhoneNumber("<PHONE_NUMBER>"), new PhoneNumber("<TWILIO_NUMBER>"),
 "Welcome to Innovacer!").create();

 System.out.println(message.getSid());
 }
}