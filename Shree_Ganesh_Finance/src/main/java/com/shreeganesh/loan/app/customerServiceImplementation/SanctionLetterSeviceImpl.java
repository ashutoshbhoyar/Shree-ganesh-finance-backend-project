package com.shreeganesh.loan.app.customerServiceImplementation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shreeganesh.loan.app.customerEntities.CustomerDetails;
import com.shreeganesh.loan.app.customerEntities.Enquiry;
import com.shreeganesh.loan.app.customerEntities.LoanDisbursement;
import com.shreeganesh.loan.app.customerEntities.SanctionLetter;
import com.shreeganesh.loan.app.customerEnum.CustomerStatus;
import com.shreeganesh.loan.app.customerRepository.CustomerRepository;
import com.shreeganesh.loan.app.customerRepository.EnquiryRepository;
import com.shreeganesh.loan.app.customerRepository.SanctionLetterRepository;
import com.shreeganesh.loan.app.customerServiceInterfaces.SanctionLetterService;

@Service
public class SanctionLetterSeviceImpl implements SanctionLetterService {

	@Autowired
	SanctionLetter sanletter;

	@Autowired
	SanctionLetterRepository sanrepo;

	@Autowired
	CustomerRepository custrepo;

	@Autowired
	JavaMailSender sender;

	@Autowired
	EnquiryRepository enquiryRepository;

	@Value("${spring.mail.username}") // @Value is used for binding property to variable....
	private String fromEmail;

	private Logger logger = LoggerFactory.getLogger(SanctionLetterSeviceImpl.class);

	@Override
	public void updateSanctionLetter(CustomerDetails customerDetails) {

		customerDetails.setCustomerStatus(String.valueOf(CustomerStatus.SanctionLetterGenreted));
		customerDetails.getCustomerSanctionLetter()
				.setSactionStatus(String.valueOf(CustomerStatus.SanctionLetterGenreted));
		customerDetails.getCustomerSanctionLetter().setTermsCondition("Ok");

		Optional<Enquiry> optional = enquiryRepository.findById(customerDetails.getCustomerId());
		if (optional.isPresent()) {
			Enquiry enquiry = optional.get();
			enquiry.setEnquiryStatus(String.valueOf(CustomerStatus.SanctionLetterGenreted));
		}

		logger.info("Loan Sanction PDF started");
		Document document = new Document();
		String title = "Shree Ganesh Finace";

		String content1 = "\n\n Dear" + customerDetails.getCustomerFirstName() + customerDetails.getCustomerLastName()
				+ ","
				+ "\nShree Ganesh Finance is Happy to informed you that your loan application has been approved. ";

//				+ "\n\nThe loan amount of " + customerDetails.getCustomerSanctionLetter().getLoanAmountSanctioned()
//				+ " has been sanctioned in your favor and will be disbursed within the next few days."
//				+ " The loan tenure is " + customerDetails.getCustomerSanctionLetter().getLoanTenure()
//				+ " and the interest rate applicable is "
//				+ customerDetails.getCustomerSanctionLetter().getRateOfInterest() + ". "
//				+ "The installment amount will be " + customerDetails.getCustomerSanctionLetter().getMonthlyEmiAmount()
//				+ "Total loan Amount with Intrest "
//				+ customerDetails.getCustomerSanctionLetter().getTotalLoanAmountWithInterest() + ".";

		String content2 = "\n\nWe hope that you find the terms and conditions of this loan satisfactory "
				+ "and that it will help you meet your financial needs.\n\nIf you have any questions or need any assistance regarding your loan, "
				+ "please do not hesitate to contact us.\n\nWe wish you all the best and thank you for choosing us."
				+ "\n\nSincerely,\n\n" + "Ashutosh m. Bhoyar (Credit manager)";

		ByteArrayOutputStream opt = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, opt);

		

		
		document.open();

		Font titlefont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
		Paragraph titlepara = new Paragraph(title, titlefont);
		titlepara.setAlignment(Element.ALIGN_CENTER);
		document.add(titlepara);

		Font titlefont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
		Paragraph paracontent1 = new Paragraph(content1, titlefont2);
		document.add(paracontent1);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 2, 2 });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(CMYKColor.WHITE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(5, 5, 161);

		Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(5, 5, 161);

		cell.setPhrase(new Phrase("The loan amount Sanctioned", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase(String.valueOf(customerDetails.getCustomerSanctionLetter().getLoanAmountSanctioned()),
				font1));
		table.addCell(cell);

		cell.setPhrase(new Phrase("loan tenure", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase(String.valueOf(customerDetails.getCustomerSanctionLetter().getLoanTenure()), font1));
		table.addCell(cell);

		cell.setPhrase(new Phrase("interest rate", font));
		table.addCell(cell);

		cell.setPhrase(
				new Phrase(String.valueOf(customerDetails.getCustomerSanctionLetter().getRateOfInterest()), font1));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Sanction letter gemerated Date", font));
		table.addCell(cell);

		Date date = new Date();

		sanletter.setSanctionDate(date);
		cell.setPhrase(new Phrase(String.valueOf(sanletter.getSanctionDate()), font1));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Total loan Amount with Intrest", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase(
				String.valueOf(customerDetails.getCustomerSanctionLetter().getTotalLoanAmountWithInterest()), font1));
		table.addCell(cell);

		document.add(table);

		Font titlefont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
		Paragraph paracontent2 = new Paragraph(content2, titlefont3);
		document.add(paracontent2);

		document.close();
		ByteArrayInputStream byt = new ByteArrayInputStream(opt.toByteArray());
         byte[] bytes=byt.readAllBytes();
         customerDetails.getCustomerSanctionLetter().setSactionLetter(bytes);
		MimeMessage mimemessage = sender.createMimeMessage();

		try {
			MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimemessage, true);
			mimemessageHelper.setFrom(fromEmail);
			mimemessageHelper.setTo(customerDetails.getCustomerEmail());
			mimemessageHelper.setSubject("Shree Ganesh Finance Sanction Letter");
			String text = "Dear " + customerDetails.getCustomerFirstName() + customerDetails.getCustomerLastName()
					+ ",\n" + "\n"
					+ "This letter is to inform you that we have reviewed your request for a credit lone . We are pleased to offer you a credit lone of "
					+ customerDetails.getCustomerSanctionLetter().getTotalLoanAmountWithInterest() + " for "
					+ customerDetails.getCustomerSanctionLetter().getLoanTenure() + ".\n" + "\n"
					+ "We are confident that you will manage your credit lone responsibly, and we look forward to your continued business.\n"
					+ "\n"
					+ "Should you have any questions about your credit lone, please do not hesitate to contact us.\n"
					+ "\n" + "Thank you for your interest in our services.";

			mimemessageHelper.setText(text);

			PdfWriter.getInstance(document, opt);
			byte[] bytearray = byt.readAllBytes();
			
			

			mimemessageHelper.addAttachment("loanSanctionLetter.pdf", new ByteArrayResource(bytearray));
			sender.send(mimemessage);
			
			// customerDetails.getCustomerSanctionLetter().setSactionLetter(bytearray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		custrepo.save(customerDetails);

	}

	@Override
	public List<CustomerDetails> getAllGenratedSanction() {

		List<CustomerDetails> list = sanrepo
				.findAllByCustomerStatus(String.valueOf(CustomerStatus.SanctionLetterGenreted));

		return list;

	}

	@Override
	public CustomerDetails changeCustomerFormStatusSanctioned(Integer customerId, String customerStatus) {
		Optional<CustomerDetails> findById = custrepo.findById(customerId);

		if (findById.isPresent()) {
			CustomerDetails customerDetails = findById.get();
			customerDetails.setCustomerStatus(customerStatus);
			System.out.println(customerDetails);

			CustomerDetails customerDetails2 = custrepo.save(customerDetails);
			Optional<Enquiry> optional = enquiryRepository.findById(customerId);
			if (optional.isPresent()) {
				Enquiry enquiry = optional.get();
				enquiry.setEnquiryStatus(customerStatus);
				enquiryRepository.save(enquiry);

			}

			return customerDetails2;
		} else {
			return null;
		}
	}


	@Override
	public List<CustomerDetails> getCustomersBySanctionLetterApproved() {
		
		return sanrepo.findAllByCustomerStatus(String.valueOf(CustomerStatus.SanctionLetterApproved));
	}

	@Override
	public List<CustomerDetails> getAllSanctionLetterSignByCustomer() {
		
		return sanrepo.findAllByCustomerStatus(String.valueOf(CustomerStatus.SanctionLetterSignByCustomer));
		
	}



}