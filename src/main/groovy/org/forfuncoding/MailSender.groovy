package org.forfuncoding

import javax.mail.Session
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Transport
/**
 * Send email
 */
class MailSender {
    static from = "this.behappy2009@gmail.com"

    def sendMail(to, subject, body)
    {
        try
        {
            def props = new Properties() ;

            def session = Session.getInstance(props, null) ;
            def msg = new MimeMessage(session) ;

            msg.setContent(body, "text/html") ;
            msg.setSubject(subject) ;
            msg.setFrom(new InternetAddress(from)) ;
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
