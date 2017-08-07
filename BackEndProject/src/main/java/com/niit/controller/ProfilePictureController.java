package com.niit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.dao.ProfilePictureDao;
import com.niit.model.Persons;
import com.niit.model.ProfilePicture;
import com.niit.model.Error;

@Controller
public class ProfilePictureController {
	@Autowired
private ProfilePictureDao profilePictureDao;
	@RequestMapping(value="/uploadprofilepicture",method=RequestMethod.POST)
public ResponseEntity<?> uploadProfilePicture(@RequestParam CommonsMultipartFile image,HttpSession session){
	Persons persons=(Persons)session.getAttribute("person");
	if(persons==null)		{
		    Error error=new Error(3,"UnAuthorized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	} 
	ProfilePicture profilePicture=new ProfilePicture();
	profilePicture.setUsername(persons.getUsername());
	profilePicture.setImage(image.getBytes());
	profilePictureDao.saveProfilePicture(profilePicture);
	return new ResponseEntity<Persons>(persons,HttpStatus.OK);
}
	
	//http://localhost:8080/backend_project2/getimage/admin
	@RequestMapping(value="/getimage/{username}", method=RequestMethod.GET)
	public @ResponseBody byte[] getProfilePic(@PathVariable String username,HttpSession session){
		Persons persons=(Persons)session.getAttribute("person");
		if(persons==null)
			return null;
		else
		{
			ProfilePicture profilePicture=profilePictureDao.getProfilePicture(username);
			if(profilePicture==null)
				return null;
			else
				return profilePicture.getImage();
		}
		
}
}

