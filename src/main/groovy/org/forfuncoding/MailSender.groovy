package org.forfuncoding

import javax.mail.Session
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Transport
/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 3/11/12
 * Time: 1:37 AM
 * To change this template use File | Settings | File Templates.
 */
class MailSender {
    static user = "this.behappy2009@gmail.com"//"behappy2009@yandex.ru"

    static sendMail(to, subject, body)
    {
        try
        {
            def props = new Properties() ;

            def session = Session.getInstance(props, null) ;
            def msg = new MimeMessage(session) ;

            msg.setText(body) ;
            msg.setSubject(subject) ;
            msg.setFrom(new InternetAddress(user)) ;
            msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to)) ;

            println ("Sending message") ;
            Transport.send(msg, msg.getAllRecipients()) ;

            println ("Message has been sent") ;
        }
        catch (e)
        {
            println ("An error occured: " + e.toString()) ;
        }
    }
}
