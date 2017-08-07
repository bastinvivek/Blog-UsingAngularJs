package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.JobDao;
import com.niit.model.Job;
import com.niit.model.Persons;
import com.niit.model.BlogPost;
import com.niit.model.Error;
@RestController
public class JobController {
	@Autowired
	private JobDao jobDao;
	@RequestMapping(value="/savejob",method=RequestMethod.POST)
	public ResponseEntity<?> saveJob(@RequestBody Job job,HttpSession session)
	{
		Persons persons=(Persons)session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(1,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
				job.setPostedOn(new Date());
				jobDao.saveJob(job);
				return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@RequestMapping(value="/getalljobs",method=RequestMethod.GET)
 public ResponseEntity<?> getAllJobs(HttpSession session)
 {
		Persons persons=(Persons) session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(1,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
	 List<Job> jobs=jobDao.getAllJobs();
	 return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
 }
	@RequestMapping(value="/getjob/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getJob(@PathVariable int id,HttpSession session){
		Persons persons=(Persons) session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(1,"unauthorized user");
			return new ResponseEntity<Error> (error,HttpStatus.UNAUTHORIZED);
		}	
		Job job=jobDao.getJobById(id);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
}
