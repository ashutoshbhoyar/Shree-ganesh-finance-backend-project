package com.shreeganesh.loan.app.customerUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSender 
{
	private String toEmail;
	private String fromEmail;
	private String subject;
	private String text;

}
