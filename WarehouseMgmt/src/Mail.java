import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Mail {
	public static void send(String recipient, String subject, String text, File file, String fileName) throws Exception {
		FileInputStream f = new FileInputStream("db.properties");
		Properties pros = new Properties();
		pros.load(f);

		// assign db parameters
		String email = pros.getProperty("emailuser");
		String pass = pros.getProperty("emailpassword");

		Properties props = new Properties();

		
		props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
        props.put("mail.smtp.starttls.enable", "true");
	    
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, pass);
			}
		});

		try {
			Message m = new MimeMessage(session);
			m.setFrom(new InternetAddress(email));
			m.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			m.setSubject(subject);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText(text);

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			m.setContent(multipart);

			Transport.send(m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "Error sending email to " + recipient);
		}
	}
	
	public static void sendReceipt(String recipient, String subject, String text) throws Exception {
		FileInputStream f = new FileInputStream("db.properties");
		Properties pros = new Properties();
		pros.load(f);

		// assign db parameters
		String email = pros.getProperty("emailuser");
		String pass = pros.getProperty("emailpassword");

		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
        props.put("mail.smtp.starttls.enable", "true");
        
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, pass);
			}
		});

		try {
			Message m = new MimeMessage(session);
			m.setFrom(new InternetAddress(email));
			m.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			m.setSubject(subject);
			m.setText(text);
			
			
			Transport.send(m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "Error sending email");
		}
	}
}