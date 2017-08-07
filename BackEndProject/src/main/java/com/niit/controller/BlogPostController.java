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

import com.niit.dao.BlogDao;
import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.Persons;
import com.niit.model.Error;

@RestController
public class BlogPostController {
	@Autowired
	private BlogDao blogDao;
	@RequestMapping(value="/saveblog",method=RequestMethod.POST)
	public ResponseEntity<?> saveBlog(@RequestBody BlogPost blogPost,HttpSession session)
	{
		Persons persons=(Persons) session.getAttribute("person");
		if(persons==null)
		{
			Error error=new Error(1,"unauthorized user");
			return new ResponseEntity<Error> (error,HttpStatus.UNAUTHORIZED);
		}
		try
		{
			blogPost.setPostedOn(new Date());
			blogPost.setCreatedBy(persons);
			blogDao.saveBlog(blogPost);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch(Exception e)
		{
			Error error=new Error(2,"cannot post");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
		@RequestMapping(value="/getallblogs/{approved}",method=RequestMethod.GET)
		public ResponseEntity<?> getAllBlogs(@PathVariable int approved,HttpSession session)
		{
			Persons persons=(Persons) session.getAttribute("person");
			if(persons==null)
			{
				Error error=new Error(1,"unauthorized user");
				return new ResponseEntity<Error> (error,HttpStatus.UNAUTHORIZED);
			}	
			List<BlogPost> blogPosts=blogDao.getAllBlogs(approved);
			return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
		}
		@RequestMapping(value="/getblogpost/{id}",method=RequestMethod.GET)
		public ResponseEntity<?> getBlog(@PathVariable int id,HttpSession session){
			Persons persons=(Persons) session.getAttribute("person");
			if(persons==null)
			{
				Error error=new Error(1,"unauthorized user");
				return new ResponseEntity<Error> (error,HttpStatus.UNAUTHORIZED);
			}	
			BlogPost blogPost=blogDao.getBlogById(id);
			return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
		}
		@RequestMapping(value="/updateblogpost",method=RequestMethod.PUT)
		public ResponseEntity<?> updateBlog(@RequestBody BlogPost blogPost,HttpSession session){
			Persons persons=(Persons) session.getAttribute("person");
			System.out.println("entering into update blog");
			if(persons==null)
			{
				Error error=new Error(1,"unauthorized user");
				return new ResponseEntity<Error> (error,HttpStatus.UNAUTHORIZED);
			}	
			blogDao.updateBlogPost(blogPost);
			System.out.println(blogPost.isApproved());
			System.out.println("success");
			
			return new ResponseEntity<Void>(HttpStatus.OK);
			
		}
		@RequestMapping(value="/addblogcomment",method=RequestMethod.POST)
		public ResponseEntity<?> addBlogComment(@RequestBody BlogComment blogComment,HttpSession session)
		{
			Persons persons=(Persons) session.getAttribute("person");
			System.out.println("entering into add comment");
			
			if(persons==null)
			{
				Error error=new Error(1,"unauthorized user");
				return new ResponseEntity<Error> (error,HttpStatus.UNAUTHORIZED);
			}	
			try
			{
				blogComment.setCommentedBy(persons);
				blogComment.setCommentedOn(new Date());
				blogDao.addComment(blogComment);
				System.out.println(blogComment.getBody());
				System.out.println(blogComment.getCommentedBy());
				System.out.println("comment added successfully");
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			catch(Exception e)
			{
				Error error=new Error(4,"unable to add"+e.getMessage());
				return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}}
			@RequestMapping(value="/getblogcomments/{blogId}",method=RequestMethod.GET)
			public ResponseEntity<?> getBlogComments(@PathVariable int blogId,HttpSession session)
			{
				Persons persons=(Persons) session.getAttribute("person");
				System.out.println("entering into show comment");
				if(persons==null)
				{
					Error error=new Error(1,"unauthorized user");
					return new ResponseEntity<Error> (error,HttpStatus.UNAUTHORIZED);
				}	
				try
				{
					System.out.println(blogDao.getBlogComments(blogId));
					List<BlogComment> blogComments=blogDao.getBlogComments(blogId);
					return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
					
				}
			}
}
